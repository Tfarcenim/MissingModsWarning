package tfar.missingmodswarning;

import net.minecraftforge.fml.common.Mod;

@Mod(MissingModsWarning.MOD_ID)
public class MissingModsWarningForge {
    
    public MissingModsWarningForge() {
    
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
    
        // Use Forge to bootstrap the Common mod.
        MissingModsWarning.LOG.info("Hello Forge world!");
        MissingModsWarning.init();
        
    }
}