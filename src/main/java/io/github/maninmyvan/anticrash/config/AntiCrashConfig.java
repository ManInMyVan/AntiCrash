package io.github.maninmyvan.anticrash.config;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Checkbox;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import io.github.maninmyvan.anticrash.AntiCrashMod;

public class AntiCrashConfig extends Config {
    @Checkbox(name = "Debug")
    public static boolean warn = true;

    public AntiCrashConfig() {
        super(new Mod(AntiCrashMod.NAME, ModType.UTIL_QOL), AntiCrashMod.MODID + ".json");
        initialize();
    }
}
