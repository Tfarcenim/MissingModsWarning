package tfar.missingmodswarning;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ModComponents {
    public static final String SAVED_WITH_KEY = MissingModsWarning.MOD_ID+".savedwith";
    public static final MutableComponent SAVED_WITH = Component.translatable(SAVED_WITH_KEY).withStyle(ChatFormatting.UNDERLINE);

    public static final String WORLD_MISSING_MODS_KEY = MissingModsWarning.MOD_ID+".worldmissingmods";
    public static final MutableComponent WORLD_MISSING_MODS = Component.translatable(WORLD_MISSING_MODS_KEY).withStyle(ChatFormatting.GOLD);

    public static final String WORLD_MISSING_MODS_TOOLTIP_KEY = MissingModsWarning.MOD_ID+".worldmissingmods.tooltip";

    public static final Component WORLD_MISSING_MODS_TOOLTIP = Component.translatable(WORLD_MISSING_MODS_TOOLTIP_KEY).withStyle(ChatFormatting.RED);
}
