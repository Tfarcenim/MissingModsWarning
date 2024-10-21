package tfar.missingmodswarning.mixin;

import com.mojang.serialization.Dynamic;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import tfar.missingmodswarning.MissingModsWarningNeoForge;

@Mixin(LevelStorageSource.class)
public class LevelStorageSourceMixin {
    @Inject(method = "makeLevelSummary"
            ,at = @At(value = "RETURN",ordinal = 1),locals = LocalCapture.CAPTURE_FAILHARD,cancellable = true)
    private void modifySummary(Dynamic<?> pDynamic, LevelStorageSource.LevelDirectory pLevelDirectory, boolean pLocked, CallbackInfoReturnable<LevelSummary> cir) {
        MissingModsWarningNeoForge.checkForIssues(cir,var6);
    }
}