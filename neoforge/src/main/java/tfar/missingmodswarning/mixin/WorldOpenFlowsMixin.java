package tfar.missingmodswarning.mixin;

import com.mojang.serialization.Dynamic;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.missingmodswarning.MissingModsSummary;
import tfar.missingmodswarning.client.MissingModsWarningForgeClient;

@Mixin(WorldOpenFlows.class)
public abstract class WorldOpenFlowsMixin {
    @Shadow protected abstract void openWorldLoadLevelStem(LevelStorageSource.LevelStorageAccess pLevelStorage, Dynamic<?> pLevelData, boolean pSafeMode, Runnable pOnFail);

    @Inject(method = "openWorldCheckVersionCompatibility",at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/worldselection/WorldOpenFlows;openWorldLoadLevelStem(Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;Lcom/mojang/serialization/Dynamic;ZLjava/lang/Runnable;)V")
    ,cancellable = true)
    private void showMissingModsScreen(LevelStorageSource.LevelStorageAccess pLevelStorage, LevelSummary pLevelSummary, Dynamic<?> pLevelData, Runnable pOnFail, CallbackInfo ci) {
        if (pLevelSummary instanceof MissingModsSummary missingModsSummary) {
            MissingModsWarningForgeClient.showMissingModWarning(missingModsSummary,() -> this.openWorldLoadLevelStem(pLevelStorage,pLevelData,false,pOnFail));
            ci.cancel();
        }
    }
}
