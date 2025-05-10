# TinkerSlashBlade - Patch

## Overview

Patch the legacy version of [TinkerSlashBlade](https://github.com/0999312/TinkerSlashBlade) to apply fix and compat continued version of MMF mods.

《刀锻冶匠魂》模组的修复补丁。

---

## How to contibute:

This develop environment utilizes [CleanroomMC/TemplateDevEnv](https://github.com/CleanroomMC/TemplateDevEnv), runs on Java 21 and support your latest Java IDE!

Currently utilizies **Gradle 8.12** + **[RetroFuturaGradle](https://github.com/GTNewHorizons/RetroFuturaGradle) 1.4.1** + **Forge 14.23.5.2847**.

With **coremod and mixin support** that is easy to configure.

1. Clone the repository that you have created with this template to your local machine.
2. Make sure IDEA is using Java 21 for Gradle before you sync the project. Verify this by going to IDEA's `Settings > Build, Execution, Deployment > Build Tools > Gradle > Gradle JVM`.
3. Open the project folder in IDEA. When prompted, click "Load Gradle Project" as it detects the `build.gradle`, if you weren't prompted, right-click the project's `build.gradle` in IDEA, select `Link Gradle Project`, after completion, hit `Refresh All` in the gradle tab on the right.
4. Run gradle tasks such as `runClient` and `runServer` in the IDEA gradle tab, or use the auto-imported run configurations like `1. Run Client`.

### Notes:
- Dependencies script in [gradle/scripts/dependencies.gradle](gradle/scripts/dependencies.gradle), explanations are commented in the file.
- Publishing script in [gradle/scripts/publishing.gradle](gradle/scripts/publishing.gradle).
- When writing Mixins on IntelliJ, it is advisable to use latest [MinecraftDev Fork for RetroFuturaGradle](https://github.com/eigenraven/MinecraftDev/releases).
