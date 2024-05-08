package tfar.missingmodswarning.mixin;

import com.mojang.datafixers.DataFixer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import org.checkerframework.checker.units.qual.C;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import tfar.missingmodswarning.MissingModsWarningForge;

import java.nio.file.Path;

@Mixin(LevelStorageSource.class)
public class LevelStorageSourceMixin {
    @Inject(method = "*(Lnet/minecraft/world/level/storage/LevelStorageSource$LevelDirectory;ZLjava/nio/file/Path;Lcom/mojang/datafixers/DataFixer;)Lnet/minecraft/world/level/storage/LevelSummary;"
            ,at = @At(value = "RETURN",ordinal = 1),locals = LocalCapture.CAPTURE_FAILHARD,cancellable = true)
    private void modifySummary(LevelStorageSource.LevelDirectory pLevelDirectory, boolean pLocked, Path p_289916_, DataFixer p_289917_,
                               CallbackInfoReturnable<LevelSummary> cir, Tag exception, CompoundTag var6) {
        MissingModsWarningForge.checkForIssues(pLevelDirectory,pLocked,p_289916_,p_289917_,cir,var6);
    }
}