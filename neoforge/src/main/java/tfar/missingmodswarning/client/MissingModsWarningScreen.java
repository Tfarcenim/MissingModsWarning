package tfar.missingmodswarning.client;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import tfar.missingmodswarning.MissingModsSummary;
import tfar.missingmodswarning.ModComponents;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * see LoadingErrorScreen
 */
public class MissingModsWarningScreen extends Screen {
    private MultiLineLabel message = MultiLineLabel.EMPTY;
    private final LevelStorageSource.LevelStorageAccess pLevelStorage;
    private final Runnable pOnFail;
    private final MissingModsSummary missingModsSummary;
    private final Runnable openAnyway;
    private int textHeight;
    private final Path modsDir;
    int listHeight = 9;
    protected SimpleModListWidget list;
    protected List<SimpleModInfo> simpleModInfoList = new ArrayList<>();

    public MissingModsWarningScreen(LevelStorageSource.LevelStorageAccess pLevelStorage, Runnable pOnFail, Component title, MissingModsSummary missingModsSummary, Runnable openAnyway)
    {
        super(title);
        this.pLevelStorage = pLevelStorage;
        this.pOnFail = pOnFail;
        this.missingModsSummary = missingModsSummary;
        this.openAnyway = openAnyway;
        this.modsDir = FMLPaths.MODSDIR.get();
        buildModInfo();
    }

    void buildModInfo() {
        for (Map.Entry<String, ArtifactVersion> entry : missingModsSummary.getMissing().entrySet()) {
            String modid = entry.getKey();
            ModContainer modContainer = ModList.get().getModContainerById(modid).orElse(null);
            String version = modContainer != null ? modContainer.getModInfo().getVersion().toString() : "[MISSING]";
            simpleModInfoList.add(new SimpleModInfo(entry.getKey(), entry.getValue().toString(),version));
        }
    }

    @Override
    protected void init() {
        message = MultiLineLabel.create(font,ModComponents.WORLD_MISSING_MODS,width /2);
        int listLeft = Math.max(8, this.width / 2 - 220);
        int listWidth = Math.min(440, this.width - 16);
        int margin = 44;
        int upperButtonHeight = height - margin + 6;

        this.list = new SimpleModListWidget(this, this.width);


        int buttonWidth = Math.min(135, this.width / 3 - 20);
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_PROCEED, this::loadAnyway)
                .bounds((int) Math.max(this.width  * .20 - buttonWidth / 2, listLeft), upperButtonHeight, buttonWidth, 20)
                .build());
        this.addRenderableWidget(Button.builder(Component.translatable("fml.button.open.mods.folder"), button -> Util.getPlatform().openFile(modsDir.toFile()))
                .bounds((int) Math.min(this.width * .80 - buttonWidth / 2, listLeft + listWidth - buttonWidth), upperButtonHeight, buttonWidth, 20)
                .build());
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_TO_TITLE, button -> {
                    pLevelStorage.safeClose();
                    pOnFail.run();
                })
                .bounds((this.width - buttonWidth) / 2, upperButtonHeight, buttonWidth, 20)
                .build());
    }

    void loadAnyway(Button b) {
        openAnyway.run();
    }

    public static class SimpleModInfo {
        final String modid;
        final String oldVersion;
        final String version;

        public SimpleModInfo(String modid, String oldVersion, String version) {
            this.modid = modid;
            this.oldVersion = oldVersion;
            this.version = version;
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        this.list.render(guiGraphics, mouseX, mouseY, partialTicks);
        int textYOffset = 18;//modMismatchData.containsMismatches() ? 18 : 0;
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, (this.height - this.listHeight - this.textHeight) / 2 - textYOffset - 9 * 2, 0xAAAAAA);

        Component modidC = Component.translatable("Modid").withStyle(ChatFormatting.UNDERLINE);
        Component versionC = Component.translatable("fml.modmismatchscreen.table.youhave").withStyle(ChatFormatting.UNDERLINE);

        guiGraphics.drawString(font,modidC,10,50,0xffffff);
        guiGraphics.drawString(font, ModComponents.SAVED_WITH,width/2,50,0xffffff);
        guiGraphics.drawString(font,versionC, (int) (width* .75),50,0xffffff);

        this.message.renderCentered(guiGraphics, this.width / 2, 20,30,0xff00ff);

        this.renderables.forEach(button -> button.render(guiGraphics, mouseX, mouseY, partialTicks));
    }
}