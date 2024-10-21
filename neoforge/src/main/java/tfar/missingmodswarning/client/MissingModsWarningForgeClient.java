package tfar.missingmodswarning.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.missingmodswarning.MissingModsSummary;
import tfar.missingmodswarning.ModComponents;

public class MissingModsWarningForgeClient {


    public static void levelHook(GuiGraphics pGuiGraphics, int pIndex, int pTop, int pLeft, int pWidth, int pHeight,
                                 int pMouseX, int pMouseY, boolean pHovering, float pPartialTick, CallbackInfo ci,
    LevelSummary summary, Minecraft minecraft, Screen screen) {
        if (summary instanceof MissingModsSummary) {
            int j = pMouseX - pLeft;
            boolean flag = j < 32;
            int k = flag ? 32 : 0;
            ResourceLocation resourcelocation1 = flag ? WorldSelectionList.WARNING_HIGHLIGHTED_SPRITE : WorldSelectionList.WARNING_SPRITE;

            pGuiGraphics.blitSprite(resourcelocation1, pLeft, pTop, 32, 32);


          //  pGuiGraphics.blit(MissingModsWarningNeoForge.ICON_OVERLAY_LOCATION, pLeft, pTop, 32.0F, (float)k, 32, 32, 256, 256);

          //  pGuiGraphics.blit(MissingModsWarningNeoForge.ICON_OVERLAY_LOCATION, pLeft, pTop, 96.0F, (float)k, 32, 32, 256, 256);

            if (flag) {
                screen.setTooltipForNextRenderPass(minecraft.font.split(ModComponents.WORLD_MISSING_MODS_TOOLTIP, 175));
            }
            ci.cancel();
        }
    }

    public static void showMissingModWarning(MissingModsSummary missingModsSummary,Runnable openAnyway) {
        Minecraft.getInstance().setScreen(new MissingModsWarningScreen(Minecraft.getInstance().screen, Component.empty(), missingModsSummary,openAnyway));
    }

}
