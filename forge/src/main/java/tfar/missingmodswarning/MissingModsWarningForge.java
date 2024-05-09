package tfar.missingmodswarning;

import com.mojang.datafixers.DataFixer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.missingmodswarning.datagen.ModDatagen;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Mod(MissingModsWarning.MOD_ID)
public class MissingModsWarningForge {

    public static final ResourceLocation ICON_OVERLAY_LOCATION = new ResourceLocation("textures/gui/world_selection.png");

    public MissingModsWarningForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(ModDatagen::start);
        MissingModsWarning.init();
    }

    public static void checkForIssues(LevelStorageSource.LevelDirectory pLevelDirectory, boolean pLocked, Path p289916, DataFixer p289917, CallbackInfoReturnable<LevelSummary> cir, CompoundTag var6) {
        LevelSummary original = cir.getReturnValue();
        if (original.getClass() == LevelSummary.class) {
            CompoundTag fmlTag = var6.getCompound("fml");
            ListTag modList = fmlTag.getList("LoadingModList", net.minecraft.nbt.Tag.TAG_COMPOUND);
            Map<String, ArtifactVersion> missing = new HashMap<>(modList.size());
            Map<String, ArtifactVersion> mismatched = new HashMap<>(modList.size());

            for (int i = 0; i < modList.size(); i++) {
                Tag tag = modList.get(i);
                CompoundTag mod = (CompoundTag) tag;

                String modId = mod.getString("ModId");
                if (Objects.equals("minecraft",  modId)) {
                    continue;
                }

                String modVersion = mod.getString("ModVersion");
                final var previousVersion = new DefaultArtifactVersion(modVersion);

                ModList.get().getModContainerById(modId).ifPresentOrElse(container ->
                {
                    final var loadingVersion = container.getModInfo().getVersion();
                    if (!loadingVersion.equals(previousVersion))
                    {
                        // Enqueue mismatched versions for bulk event
                        mismatched.put(modId, previousVersion);
                    }
                }, () -> missing.put(modId, previousVersion));
            }

            if (!missing.isEmpty()) {
                cir.setReturnValue(new MissingModsSummary(original.getSettings(),original.levelVersion(),original.getLevelId(),
                        original.requiresManualConversion(),original.isLocked(),original.isExperimental(),original.getIcon(),missing,mismatched));
            }
        }
    }
}