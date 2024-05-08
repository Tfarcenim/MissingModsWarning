package tfar.missingmodswarning.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.missingmodswarning.MissingModsSummary;
import tfar.missingmodswarning.MissingModsWarningForge;

import static tfar.missingmodswarning.MissingModsWarningForge.ICON_OVERLAY_LOCATION;

public class MissingModsWarningForgeClient {

    public static void levelHook(GuiGraphics pGuiGraphics, int pIndex, int pTop, int pLeft, int pWidth, int pHeight,
                                 int pMouseX, int pMouseY, boolean pHovering, float pPartialTick, CallbackInfo ci,
    LevelSummary summary, Minecraft minecraft, Screen screen) {
        if (summary instanceof MissingModsSummary missingModsSummary) {
            int j = pMouseX - pLeft;
            boolean flag = j < 32;
            int k = flag ? 32 : 0;

            pGuiGraphics.blit(ICON_OVERLAY_LOCATION, pLeft, pTop, 96.0F, (float)k, 32, 32, 256, 256);

            if (flag) {
                screen.setTooltipForNextRenderPass(minecraft.font.split(MissingModsWarningForge.MISSING_MODS_TOOLTIP, 175));
            }
            ci.cancel();
        }
    }

}
