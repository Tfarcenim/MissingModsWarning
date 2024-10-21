package tfar.missingmodswarning;

import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraft.world.level.storage.LevelVersion;
import org.apache.maven.artifact.versioning.ArtifactVersion;

import java.nio.file.Path;
import java.util.Map;

public class MissingModsSummary extends LevelSummary {
    private final Map<String, ArtifactVersion> missing;
    private final Map<String, ArtifactVersion> mismatched;

    public MissingModsSummary(LevelSettings pSettings, LevelVersion pLevelVersion, String pLevelId, boolean pRequiresManualConversion, boolean pLocked, boolean pExperimental, Path pIcon, Map<String, ArtifactVersion> missing, Map<String, ArtifactVersion> mismatched) {
        super(pSettings, pLevelVersion, pLevelId, pRequiresManualConversion, pLocked, pExperimental, pIcon);
        this.missing = missing;
        this.mismatched = mismatched;
    }

    public Map<String, ArtifactVersion> getMissing() {
        return missing;
    }

    public Map<String, ArtifactVersion> getMismatched() {
        return mismatched;
    }
}
