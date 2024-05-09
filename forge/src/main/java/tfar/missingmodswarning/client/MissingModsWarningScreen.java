package tfar.missingmodswarning.client;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.ForgeI18n;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import tfar.missingmodswarning.MissingModsSummary;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MissingModsWarningScreen  extends Screen
{
    private MultiLineLabel message = MultiLineLabel.EMPTY;
    private final Screen parent;
    private final MissingModsSummary missingModsSummary;
    private int textHeight;
    private final Path modsDir;
    int listHeight = 9;
    protected SimpleModListWidget list;
    protected List<SimpleModInfo> simpleModInfoList = new ArrayList<>();

    public MissingModsWarningScreen(Screen parentScreen, Component title, MissingModsSummary missingModsSummary)
    {
        super(title);
        this.parent = parentScreen;
        this.missingModsSummary = missingModsSummary;
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
        message = MultiLineLabel.create(font,Component.literal("Missing mods in world").withStyle(ChatFormatting.GOLD),width /2);
        int listLeft = Math.max(8, this.width / 2 - 220);
        int listWidth = Math.min(440, this.width - 16);
        int margin = 52;
        int upperButtonHeight = height - margin + 6;
        int lowerButtonHeight = height - margin + 30;

        this.list = new SimpleModListWidget(this, this.width, margin, height - margin);


        int buttonWidth = Math.min(210, this.width / 2 - 20);
        this.addRenderableWidget(Button.builder(Component.literal("load anyway"), this::loadAnyway)
                .bounds(Math.max(this.width / 4 - buttonWidth / 2, listLeft), upperButtonHeight, buttonWidth, 20)
                .build());
        this.addRenderableWidget(Button.builder(Component.literal(ForgeI18n.parseMessage("fml.button.open.mods.folder")), button -> Util.getPlatform().openFile(modsDir.toFile()))
                .bounds(Math.min(this.width * 3 / 4 - buttonWidth / 2, listLeft + listWidth - buttonWidth), upperButtonHeight, buttonWidth, 20)
                .build());
        this.addRenderableWidget(Button.builder(Component.translatable("gui.toMenu"), button -> this.minecraft.setScreen(this.parent))
                .bounds((this.width - buttonWidth) / 2, lowerButtonHeight, buttonWidth, 20)
                .build());
    }

    void loadAnyway(Button b) {

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
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        this.list.render(guiGraphics, mouseX, mouseY, partialTicks);
        int textYOffset = 18;//modMismatchData.containsMismatches() ? 18 : 0;
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, (this.height - this.listHeight - this.textHeight) / 2 - textYOffset - 9 * 2, 0xAAAAAA);
        this.message.renderCentered(guiGraphics, this.width / 2, height );
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }
}