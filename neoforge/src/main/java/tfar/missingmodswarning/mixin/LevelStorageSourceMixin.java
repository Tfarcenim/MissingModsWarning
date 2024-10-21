package tfar.missingmodswarning.mixin;

import com.mojang.serialization.Dynamic;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraft.world.level.storage.LevelVersion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import tfar.missingmodswarning.MissingModsWarningNeoForge;

import javax.annotation.Nullable;
import java.nio.file.Path;

@Mixin(LevelStorageSource.class)
public abstract class LevelStorageSourceMixin {
    @Shadow
    @Nullable
    private static Tag readLightweightData(Path pFile) {
        throw new AssertionError();
    }

    @Inject(method = "makeLevelSummary"
            ,at = @At(value = "RETURN"), locals = LocalCapture.CAPTURE_FAILHARD,cancellable = true)
    private void modifySummary(Dynamic<?> pDynamic, LevelStorageSource.LevelDirectory pLevelDirectory, boolean pLocked, CallbackInfoReturnable<LevelSummary> cir, LevelVersion levelversion, int i, boolean flag, Path path, WorldDataConfiguration worlddataconfiguration, LevelSettings levelsettings, FeatureFlagSet featureflagset, boolean flag1) {
        Path path2 = pLevelDirectory.dataFile();
        if (readLightweightData(path2) instanceof CompoundTag compoundtag) {
            CompoundTag compoundtag1 = compoundtag.getCompound("Data");
            MissingModsWarningNeoForge.checkForIssues(cir, compoundtag);
        }
    }
}