package net.leaderos.auth.bukkit.altdetector;

import net.leaderos.auth.bukkit.Bukkit;
import net.leaderos.auth.bukkit.altdetector.Config.ConvertFromType;
import net.leaderos.auth.bukkit.altdetector.database.Database;
import net.leaderos.auth.bukkit.altdetector.database.Mysql;
import net.leaderos.auth.bukkit.altdetector.database.Sqlite;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.Server;

import java.io.File;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public class AltDetectorController {

    public int expirationTime = 60;
    public Config config;
    public Database database;
    public Listeners listeners;
    public boolean superVanish = false;
    public DiscordWebhook discordWebhook;
    public boolean placeholderEnabled = false;

    private final Bukkit mainPlugin;

    public AltDetectorController(Bukkit mainPlugin) {
        this.mainPlugin = mainPlugin;
    }

    public void enable() {
        config = new Config(this);
        expirationTime = config.getExpirationTime();

        if (config.getDatabaseType().equalsIgnoreCase("mysql")) {
            database = new Mysql(this, config.getSqlDebug(), config.getMysqlPrefix());
        } else {
            database = new Sqlite(this, config.getSqlDebug(), "");
        }

        boolean initSuccessful = database.initialize();
        if (!initSuccessful) {
            getLogger().warning("Initialization of " + database.toString() + " database failed.");
            return;
        }

        getLogger().info("Using " + database.toString() + " database, version " + database.getSqlVersion() + ", driver version " + database.getDriverVersion());

        ConvertFromType convertFrom = config.getConvertFrom();
        switch (convertFrom) {
            case NONE:
                break;
            case YML:
            case SQLITE:
            case MYSQL:
                convertDb(convertFrom);
                break;
            case ERROR:
                getLogger().warning("Invalid convert-from database conversion option specified in config.yml.");
                break;
        }

        int entriesRemoved = database.purge(expirationTime);
        getLogger().info(entriesRemoved + " record" + (entriesRemoved == 1 ? "" : "s") + " removed, expiration time " + expirationTime + " days.");
        database.generatePlayerList();

        listeners = new Listeners(this);

        PluginCommand altCommand = mainPlugin.getCommand("alt");
        if (altCommand != null) {
            altCommand.setExecutor(new Commands(this));
            altCommand.setTabCompleter(new TabComplete(this));
        }

        superVanish = mainPlugin.getServer().getPluginManager().isPluginEnabled("PremiumVanish")
                || mainPlugin.getServer().getPluginManager().isPluginEnabled("SuperVanish");

        if (config.isDiscordEnabled()) {
            if (config.getDiscordWebhookUrl() != null && !config.getDiscordWebhookUrl().isEmpty()) {
                discordWebhook = new DiscordWebhook(this);
                getLogger().info("Discord webhook integration enabled.");
            } else {
                getLogger().warning("Discord webhook is enabled but no URL is provided in the config.");
            }
        }

        if (config.isPlaceholderEnabled() && mainPlugin.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            placeholderEnabled = true;
            new Placeholder(this).register();
            database.generatePlayerAltList();
            getLogger().info("PlaceholderAPI integration enabled.");
        }
    }

    public void disable() {
        if (database != null) {
            database.closeDataSource();
        }
    }

    public void processAuthenticatedPlayer(Player player) {
        if (listeners != null) {
            listeners.processPlayer(player);
        }
    }

    public CompletableFuture<Boolean> canRegisterWithIp(String ip) {
        return CompletableFuture.supplyAsync(() -> {
            if (!config.isEnabled() || !config.isRegisterLimitEnabled() || database == null) {
                return true;
            }
            int limit = Math.max(1, config.getRegisterLimitPerIp());
            int accountCount = database.countAccountsByIp(ip.toLowerCase(Locale.ROOT), expirationTime);
            return accountCount < limit;
        });
    }

    private void convertDb(ConvertFromType convertFrom) {
        boolean conversionSuccessful = false;

        if (convertFrom == ConvertFromType.YML) {
            getLogger().info("Converting from YML to " + database.toString() + " database. This may take a while, please be patient.");
            ConvertYaml convertYml = new ConvertYaml(this);
            conversionSuccessful = convertYml.convert();
        } else if (convertFrom == ConvertFromType.SQLITE || convertFrom == ConvertFromType.MYSQL) {
            Database oldDb = convertFrom == ConvertFromType.MYSQL
                    ? new Mysql(this, config.getSqlDebug(), config.getMysqlPrefix())
                    : new Sqlite(this, config.getSqlDebug(), "");

            if (!database.getClass().equals(oldDb.getClass())) {
                boolean initSuccessful = oldDb.initialize();
                if (initSuccessful) {
                    getLogger().info("Converting from " + oldDb.toString() + " to " + database.toString() + " database. This may take a while, please be patient.");
                    ConvertSql convertSql = new ConvertSql(this);
                    conversionSuccessful = convertSql.convert(oldDb, database);
                } else {
                    getLogger().warning("Initialization of " + oldDb.toString() + " database failed.");
                }
                oldDb.closeDataSource();
            } else {
                getLogger().warning("Invalid database conversion options specified in config.yml.");
            }
        }

        if (conversionSuccessful) {
            getLogger().info("Successfully converted to " + database.toString() + " database.");
        } else {
            getLogger().warning("Conversion to " + database.toString() + " database failed. Old data not converted.");
        }
    }

    public Bukkit getMainPlugin() {
        return mainPlugin;
    }

    public Logger getLogger() {
        return mainPlugin.getLogger();
    }

    public Server getServer() {
        return mainPlugin.getServer();
    }

    public PluginManager getPluginManager() {
        return mainPlugin.getServer().getPluginManager();
    }

    public BukkitScheduler getScheduler() {
        return mainPlugin.getServer().getScheduler();
    }

    public File getDataFolder() {
        return mainPlugin.getDataFolder();
    }

    public PluginDescriptionFile getDescription() {
        return mainPlugin.getDescription();
    }
}
