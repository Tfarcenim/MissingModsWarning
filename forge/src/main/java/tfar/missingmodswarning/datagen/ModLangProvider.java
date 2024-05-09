package tfar.missingmodswarning.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import tfar.missingmodswarning.MissingModsWarning;
import tfar.missingmodswarning.ModComponents;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, MissingModsWarning.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ModComponents.SAVED_WITH_KEY,"Saved with");
        add(ModComponents.WORLD_MISSING_MODS_KEY,"Missing mods in world");
        add(ModComponents.WORLD_MISSING_MODS_TOOLTIP_KEY,"This world was saved with missing mods!");
    }
}
