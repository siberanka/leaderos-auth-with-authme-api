package net.leaderos.auth.bukkit.configuration;

import com.google.common.collect.Lists;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import lombok.Getter;
import lombok.Setter;
import net.leaderos.auth.shared.enums.DebugMode;
import net.leaderos.auth.shared.enums.RegisterSecondArg;

import java.util.List;

/**
 * Main config file
 */
@Getter
@Setter
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class Config extends OkaeriConfig {

    /**
     * Settings menu of config
     */
    @Comment("Main settings")
    private Settings settings = new Settings();

    /**
     * Settings configuration of config
     */
    @Getter
    @Setter
    public static class Settings extends OkaeriConfig {
        @Comment("Language of plugin")
        private String lang = "en";

        @Comment("Url of your website")
        private String url = "https://yourwebsite.com";

        @Comment({
                "API Key for request",
                "You can get your API key from Dashboard > API",
        })
        private String apiKey = "YOUR_API_KEY";

        @Comment({
                "Debug mode for API requests.",
                "Available modes:",
                "DISABLED: No debug messages",
                "ENABLED: All debug messages",
                "ONLY_ERRORS: Only error messages"
        })
        private DebugMode debugMode = DebugMode.ONLY_ERRORS;

        @Comment({
                "Should session system be enabled?",
                "If enabled, players will be able to join the server without authentication if they succeeded an auth before (with the same IP)."
        })
        private boolean session = false;

        @Comment("Force survival gamemode when player joins?")
        private boolean forceSurvivalMode = false;

        @Comment("Should unregistered players be kicked immediately?")
        private boolean kickNonRegistered = false;

        @Comment("Should players be kicked if they fail to log in with the wrong password?")
        private boolean kickOnWrongPassword = true;

        @Comment("How many seconds should players who fail to log in or register be given before they are kicked?")
        private int authTimeout = 60; // in seconds

        @Comment("How many seconds should players wait before sending another command?")
        private int commandCooldown = 3; // in seconds

        @Comment("Minimum password length for registration.")
        private int minPasswordLength = 5;

        @Comment({
                "Second argument the /register command should take:",
                "PASSWORD_CONFIRM: password confirmation (/register <password> <password>)",
                "EMAIL: email address (/register <password> <email>)"
        })
        private RegisterSecondArg registerSecondArg = RegisterSecondArg.PASSWORD_CONFIRM;

        @Comment("Send players to another server after login/register")
        private SendAfterAuth sendAfterAuth = new SendAfterAuth();

        @Getter
        @Setter
        public static class SendAfterAuth extends OkaeriConfig {
            @Comment("Should player be sent to another server after authentication?")
            private boolean enabled = false;

            @Comment("Name of the server to send player to after authentication")
            private String server = "lobby";
        }

        @Comment({
                "Email verification settings",
                "To use this feature, make sure the Email Verification module is enabled on your website."
        })
        private EmailVerification emailVerification = new EmailVerification();

        @Getter
        @Setter
        public static class EmailVerification extends OkaeriConfig {
            @Comment("Should unverified players be kicked?")
            private boolean kickNonVerified = false;

            @Comment("Should players be kicked immediately after registration to verify their email?")
            private boolean kickAfterRegister = false;
        }

        @Comment("Teleport players to spawn on join")
        private Spawn spawn = new Spawn();

        @Getter
        @Setter
        public static class Spawn extends OkaeriConfig {
            @Comment("Force teleport to spawn on join?")
            private boolean forceTeleportOnJoin = true;

            @Comment({
                    "Spawn location in format world,x,y,z,yaw,pitch",
                    "Use /leaderosauth setspawn to set the location"
            })
            private String location = "";
        }

        @Comment("Should the title messages be shown to players?")
        private boolean showTitle = true;

        @Comment("Bossbar settings")
        private BossBar bossBar = new BossBar();

        @Getter
        @Setter
        public static class BossBar extends OkaeriConfig {
            @Comment("Should bossbar be enabled?")
            private boolean enabled = false;

            @Comment({
                    "Bossbar color",
                    "AUTO: The color will change based on the remaining time.",
                    "Available colors: PINK, BLUE, RED, GREEN, YELLOW, PURPLE, WHITE, BLACK, AUTO"
            })
            private String color = "AUTO";

            @Comment({
                    "Bossbar style",
                    "Available styles: PROGRESS, NOTCHED_6, NOTCHED_10, NOTCHED_12, NOTCHED_20"
            })
            private String style = "PROGRESS";
        }

        @Comment("List of login commands")
        private List<String> loginCommands = Lists.newArrayList("login", "log", "l", "giris", "giriş", "gir");

        @Comment("List of register commands")
        private List<String> registerCommands = Lists.newArrayList("register", "reg", "kayit", "kayıt", "kaydol");

        @Comment("List of tfa commands")
        private List<String> tfaCommands = Lists.newArrayList("tfa", "2fa");

        @Comment("Blacklist of passwords that cannot be used")
        private List<String> unsafePasswords = Lists.newArrayList("123456", "password", "qwerty", "123456789", "help", "sifre", "12345", "asd123", "qwe123");

        @Comment("AltDetector settings")
        private AltDetector altDetector = new AltDetector();

        @Getter
        @Setter
        public static class AltDetector extends OkaeriConfig {
            @Comment("Enable AltDetector module")
            private boolean enabled = true;

            @Comment("How many days account/IP history is kept")
            private int expirationTime = 60;

            @Comment("Database type. Available: sqlite, mysql")
            private String databaseType = "sqlite";

            @Comment("MySQL settings")
            private Mysql mysql = new Mysql();

            @Getter
            @Setter
            public static class Mysql extends OkaeriConfig {
                private String hostname = "127.0.0.1";
                private String username = "username";
                private String password = "password";
                private String database = "databasename";
                private String prefix = "altdetector_";
                private int port = 3306;
                private String jdbcurlProperties = "";
            }

            @Comment("Data migration source. Available: none, yml, sqlite, mysql")
            private String convertFrom = "none";

            @Comment("Print SQL statements")
            private boolean sqlDebug = false;

            @Comment("Join notify format")
            private String joinPlayerPrefix = "&b[AltDetector] ";
            private String joinPlayer = "{0} may be an alt of ";
            private String joinPlayerList = "{0}";
            private String joinPlayerSeparator = ", ";

            @Comment("/alt command format")
            private String altcmdPlayer = "&c{0}&6 may be an alt of ";
            private String altcmdPlayerList = "&c{0}";
            private String altcmdPlayerSeparator = "&6, ";
            private String altcmdPlayernoalts = "&c{0}&6 has no known alts";
            private String altcmdNoalts = "&6No alts found";
            private String altcmdPlayernotfound = "&4{0} not found";
            private String altcmdParamerror = "&4Must specify at most one player";
            private String altcmdNoperm = "&4You do not have permission for this command";
            private String delcmdRemovedsingular = "&6{0} record removed";
            private String delcmdRemovedplural = "&6{0} records removed";

            @Comment("PlaceholderAPI settings")
            private boolean placeholderEnabled = true;
            private String placeholderSeparator = " ";

            @Comment("Discord webhook settings")
            private Discord discord = new Discord();

            @Getter
            @Setter
            public static class Discord extends OkaeriConfig {
                private boolean enabled = false;
                private String webhookUrl = "";
                private String username = "AltDetector";
                private String mcServerName = "Minecraft Server";
                private String avatarUrl = "https://minotar.net/helm/{creator}/100.png";
                private String embedTitle = "Alt Account Detection - {creator}";
                private String embedDescription = "`{content}`";
                private String embedThumbnailUrl = "";
                private int embedColor = 0xff0000;
            }

            @Comment("Block new register when too many accounts exist in IP history")
            private RegisterLimit registerLimit = new RegisterLimit();

            @Getter
            @Setter
            public static class RegisterLimit extends OkaeriConfig {
                private boolean enabled = true;
                private int maxAccountsPerIp = 3;
            }
        }
    }
}
