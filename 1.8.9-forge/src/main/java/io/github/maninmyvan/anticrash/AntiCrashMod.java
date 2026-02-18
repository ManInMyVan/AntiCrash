package io.github.maninmyvan.anticrash;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

@Mod(modid = "@MOD_ID@", name = "@MOD_NAME@", version = "@MOD_VERSION@", guiFactory = "io.github.maninmyvan.anticrash.AntiCrashGuiFactory")
public class AntiCrashMod {
    public static boolean fixChestAnvilCollisions = true;
    static Configuration config;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        config = new Configuration(new File(Loader.instance().getConfigDir(), "anticrash.cfg"));
        config.load();
        reloadConfig();
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if ("@MOD_ID@".equals(event.modID)) {
            reloadConfig();
        }
    }

    private void reloadConfig() {
        fixChestAnvilCollisions = config.get(Configuration.CATEGORY_GENERAL, "Fix Chest/Anvil Collisions", true).getBoolean();

        if (config.hasChanged()) {
            config.save();
        }
    }
}
