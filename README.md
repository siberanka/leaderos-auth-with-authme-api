# LeaderOS Auth (Fork)

Minecraft auth plugin integrated with LeaderOS (Bukkit, Bungee, Velocity).

## Version

- Current fork version: `1.0.6-fork`

## Bukkit (Current State)

### Auth Flow

- Login/register/TFA flow with LeaderOS API
- Session support
- Auth timeout, command cooldown, password policy
- Optional spawn teleport and bossbar/title prompts

### AuthMe Compatibility

- AuthMe-compatible events and API stubs are included
- AuthMe plugin messaging support:
  - `AuthMe.v2.Broadcast` (`login` / `logout`)
  - `AuthMe.v2` (`perform.login`)
- `plugin.yml`:
  - `softdepend: [AuthMe, PlaceholderAPI, PremiumVanish, SuperVanish]`
  - `provides: [AuthMe]`

### AltDetector Integration

AltDetector-leaderos modules are integrated into the Bukkit plugin:

- Alt detection after successful auth (login/register/TFA)
- SQL backend support:
  - SQLite
  - MySQL
- Data expiration/purge support
- Optional conversion setting (`none`, `yml`, `sqlite`, `mysql`)
- `/alt` command support
- PlaceholderAPI expansion support
- Discord webhook notifications
- Vanish integration support (PremiumVanish/SuperVanish)

### New Register Limit (IP History)

You can block new registration if IP history already has too many accounts.

- Check is done before register API call
- If account count in IP history is `>= limit`, registration is blocked
- Limit is configurable and can be enabled/disabled via config

Config path:

- `settings.alt-detector.register-limit.enabled`
- `settings.alt-detector.register-limit.max-accounts-per-ip`

## Build

```bash
mvn -pl bukkit -am -DskipTests package
```

Output jar:

- `bukkit/target/leaderos-auth-bukkit-1.0.6-fork-shaded.jar`
