package io.github.maninmyvan.anticrash;

import io.github.maninmyvan.anticrash.config.AntiCrashConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.Logger;

import static org.apache.logging.log4j.LogManager.getLogger;

@Mod(modid = AntiCrashMod.MODID, name = AntiCrashMod.NAME, version = AntiCrashMod.VERSION)
public class AntiCrashMod {
    private static final Logger logger = getLogger("AntiCrash");

    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";

    @Instance(MODID)
    public static AntiCrashMod INSTANCE;
    public static AntiCrashConfig config;

    public static void warn(String message, Throwable t) {
        if (AntiCrashConfig.warn) {
            logger.warn(message, t);
        }
    }

    @EventHandler
    public void onInit(FMLInitializationEvent event) {
        config = new AntiCrashConfig();
    }
}
