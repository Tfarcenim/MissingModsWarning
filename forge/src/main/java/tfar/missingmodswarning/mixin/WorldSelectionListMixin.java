package tfar.missingmodswarning.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.missingmodswarning.MissingModsSummary;
import tfar.missingmodswarning.client.MissingModsWarningForgeClient;

@Mixin(WorldSelectionList.WorldListEntry.class)
public class WorldSelectionListMixin {


    @Shadow @Final private LevelSummary summary;

    @Shadow @Final private Minecraft minecraft;

    @Shadow @Final private SelectWorldScreen screen;

    @Inject(method = "render",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/level/storage/LevelSummary;isLocked()Z"),cancellable = true)
    private void showModWarning(GuiGraphics pGuiGraphics, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pHovering, float pPartialTick, CallbackInfo ci) {
        MissingModsWarningForgeClient.levelHook(pGuiGraphics, pIndex, pTop, pLeft, pWidth, pHeight, pMouseX, pMouseY, pHovering, pPartialTick, ci,summary,minecraft,screen);
    }

    @Inject(method = "joinWorld",at = @At(value = "INVOKE",target = "Lnet/minecraft/world/level/storage/LevelSummary;askToOpenWorld()Z"),cancellable = true)
    private void customScreen(CallbackInfo ci) {
        if (summary instanceof MissingModsSummary missingModsSummary) {
            MissingModsWarningForgeClient.showMissingModWarning(missingModsSummary,(WorldSelectionList.WorldListEntry)(Object)this);
            ci.cancel();
        }
    }
}