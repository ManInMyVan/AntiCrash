package io.github.maninmyvan.anticrash;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;

@Mod(modid = AntiCrashMod.MODID, name = AntiCrashMod.NAME, version = AntiCrashMod.VERSION)
public class AntiCrashMod {
    public static final String MODID = "@MOD_ID@";
    public static final String NAME = "@MOD_NAME@";
    public static final String VERSION = "@MOD_VERSION@";

    @Instance(MODID)
    public static AntiCrashMod INSTANCE;
}
