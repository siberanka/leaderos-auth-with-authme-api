# minecraft-leaderos-auth
Minecraft plugin for in-game authentication integrated with LeaderOS.

## Son Değişiklikler (TR)

Bu güncelleme ile Bukkit modülüne AuthMe uyumluluk katmanı eklendi.

- AuthMe uyumlu event sınıfları eklendi:
  - `fr.xephi.authme.events.LoginEvent`
  - `fr.xephi.authme.events.RegisterEvent`
  - `fr.xephi.authme.events.LogoutEvent`
  - `fr.xephi.authme.events.FailedLoginEvent`
  - `fr.xephi.authme.events.AuthMeAsyncPreLoginEvent`
  - `fr.xephi.authme.events.AuthMeAsyncPreRegisterEvent`
- AuthMe API uyumluluğu için minimal `fr.xephi.authme.api.v3.AuthMeApi` eklendi.
- Bukkit akışına AuthMe köprüsü eklendi:
  - Login/Register öncesi pre-event tetikleme
  - Başarılı login/register/TFA sonrası AuthMe-style login event tetikleme
  - Başarısız parola girişinde failed-login event tetikleme
  - Quit/unauthenticated durumlarında logout/unauth mesajı yayınlama
- Plugin mesajlaşma tarafında AuthMe kanalları desteklendi:
  - `AuthMe.v2.Broadcast` üzerinden `login` / `logout` yayınları
  - `AuthMe.v2` üzerinden `perform.login` alımı ve oyuncuyu doğrulama
- Mevcut haberleşme sistemi korunmuştur:
  - `BungeeCord` + `losauth:status` akışı kaldırılmadı, devam ediyor.
- `plugin.yml` güncellendi:
  - `softdepend: [ AuthMe ]`
  - `provides: [ AuthMe ]`

Not: Bu değişiklikler sadece Bukkit tarafına ekleme/uyumluluk amaçlıdır; `bungee`, `shared` ve `velocity` modüllerinden özellik kaldırılmamıştır.

## Recent Changes (EN)

This update adds an AuthMe compatibility layer to the Bukkit module.

- Added AuthMe-compatible event classes:
  - `fr.xephi.authme.events.LoginEvent`
  - `fr.xephi.authme.events.RegisterEvent`
  - `fr.xephi.authme.events.LogoutEvent`
  - `fr.xephi.authme.events.FailedLoginEvent`
  - `fr.xephi.authme.events.AuthMeAsyncPreLoginEvent`
  - `fr.xephi.authme.events.AuthMeAsyncPreRegisterEvent`
- Added a minimal `fr.xephi.authme.api.v3.AuthMeApi` for API compatibility.
- Added an AuthMe bridge in Bukkit flow:
  - Pre-events before login/register
  - AuthMe-style login event after successful login/register/TFA
  - Failed-login event on wrong password
  - Logout/unauthenticated broadcast on quit and unauth states
- Added AuthMe plugin messaging channel support:
  - `login` / `logout` broadcasts over `AuthMe.v2.Broadcast`
  - `perform.login` handling over `AuthMe.v2` to authenticate player
- Existing communication was preserved:
  - `BungeeCord` + `losauth:status` flow is still active.
- Updated `plugin.yml`:
  - `softdepend: [ AuthMe ]`
  - `provides: [ AuthMe ]`

Note: These are additive compatibility changes only on Bukkit; no feature removal was made in `bungee`, `shared`, or `velocity` modules.
