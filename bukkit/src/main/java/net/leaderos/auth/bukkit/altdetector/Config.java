package net.leaderos.auth.bukkit.altdetector;

import net.leaderos.auth.bukkit.configuration.Config.Settings.AltDetector;

public class Config {

    private final AltDetectorController plugin;

    public Config(AltDetectorController plugin) {
        this.plugin = plugin;
    }

    private AltDetector alt() {
        return plugin.getMainPlugin().getConfigFile().getSettings().getAltDetector();
    }

    public int getExpirationTime() {
        return alt().getExpirationTime();
    }

    public String getDatabaseType() {
        return alt().getDatabaseType();
    }

    public String getMysqlHostname() {
        return alt().getMysql().getHostname();
    }

    public String getMysqlUsername() {
        return alt().getMysql().getUsername();
    }

    public String getMysqlPassword() {
        return alt().getMysql().getPassword();
    }

    public String getMysqlDatabase() {
        return alt().getMysql().getDatabase();
    }

    public String getMysqlPrefix() {
        return alt().getMysql().getPrefix();
    }

    public int getMysqlPort() {
        return alt().getMysql().getPort();
    }

    public String getJdbcurlProperties() {
        String properties = alt().getMysql().getJdbcurlProperties();
        if (properties == null || properties.isEmpty()) {
            return "";
        }
        return properties.startsWith("?") ? properties : "?" + properties;
    }

    public enum ConvertFromType {
        NONE,
        YML,
        SQLITE,
        MYSQL,
        ERROR
    }

    public ConvertFromType getConvertFrom() {
        String cf = alt().getConvertFrom();
        if (cf == null) {
            return ConvertFromType.NONE;
        }
        if (cf.equalsIgnoreCase("none")) {
            return ConvertFromType.NONE;
        }
        if (cf.equalsIgnoreCase("yml") || cf.equalsIgnoreCase("yaml")) {
            return ConvertFromType.YML;
        }
        if (cf.equalsIgnoreCase("sqlite")) {
            return ConvertFromType.SQLITE;
        }
        if (cf.equalsIgnoreCase("mysql")) {
            return ConvertFromType.MYSQL;
        }
        return ConvertFromType.ERROR;
    }

    public boolean getSqlDebug() {
        return alt().isSqlDebug();
    }

    public String getJoinPlayerPrefix() {
        return alt().getJoinPlayerPrefix();
    }

    public String getJoinPlayer() {
        return alt().getJoinPlayer();
    }

    public String getJoinPlayerList() {
        return alt().getJoinPlayerList();
    }

    public String getJoinPlayerSeparator() {
        return alt().getJoinPlayerSeparator();
    }

    public String getAltCmdPlayer() {
        return alt().getAltcmdPlayer();
    }

    public String getAltCmdPlayerList() {
        return alt().getAltcmdPlayerList();
    }

    public String getAltCmdPlayerSeparator() {
        return alt().getAltcmdPlayerSeparator();
    }

    public String getAltCmdPlayerNoAlts() {
        return alt().getAltcmdPlayernoalts();
    }

    public String getAltCmdNoAlts() {
        return alt().getAltcmdNoalts();
    }

    public String getAltCmdPlayerNotFound() {
        return alt().getAltcmdPlayernotfound();
    }

    public String getAltCmdParamError() {
        return alt().getAltcmdParamerror();
    }

    public String getAltCmdNoPerm() {
        return alt().getAltcmdNoperm();
    }

    public String getDelCmdRemovedSingular() {
        return alt().getDelcmdRemovedsingular();
    }

    public String getDelCmdRemovedPlural() {
        return alt().getDelcmdRemovedplural();
    }

    public boolean isPlaceholderEnabled() {
        return alt().isPlaceholderEnabled();
    }

    public String getPlaceholderSeparator() {
        return alt().getPlaceholderSeparator();
    }

    public boolean isDiscordEnabled() {
        return alt().getDiscord().isEnabled();
    }

    public String getDiscordWebhookUrl() {
        return alt().getDiscord().getWebhookUrl();
    }

    public String getDiscordUsername() {
        return alt().getDiscord().getUsername();
    }

    public String getMCServerName() {
        return alt().getDiscord().getMcServerName();
    }

    public String getDiscordEmbedTitle() {
        return alt().getDiscord().getEmbedTitle();
    }

    public String getDiscordEmbedDescription() {
        return alt().getDiscord().getEmbedDescription();
    }

    public String getDiscordEmbedThumbnailUrl() {
        return alt().getDiscord().getEmbedThumbnailUrl();
    }

    public String getDiscordAvatarUrl() {
        return alt().getDiscord().getAvatarUrl();
    }

    public int getDiscordEmbedColor() {
        return alt().getDiscord().getEmbedColor();
    }

    public boolean isEnabled() {
        return alt().isEnabled();
    }

    public boolean isRegisterLimitEnabled() {
        return alt().getRegisterLimit().isEnabled();
    }

    public int getRegisterLimitPerIp() {
        return alt().getRegisterLimit().getMaxAccountsPerIp();
    }
}
