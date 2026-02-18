package io.github.maninmyvan.anticrash;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;

import java.util.Set;

import static io.github.maninmyvan.anticrash.AntiCrashMod.config;

@SuppressWarnings("unused")
public class AntiCrashGuiFactory implements IModGuiFactory {
    @Override
    public void initialize(Minecraft minecraft) {}

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return Gui.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement runtimeOptionCategoryElement) {
        return null;
    }

    public static class Gui extends GuiConfig {
        public Gui(GuiScreen parent) {
            super(
                    parent,
                    new ConfigElement(config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                    "@MOD_ID@",
                    false,
                    false,
                    "@MOD_NAME@ Config"
            );
        }
    }
}
