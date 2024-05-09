package tfar.missingmodswarning.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;

public class SimpleModListWidget extends ObjectSelectionList<SimpleModListWidget.SimpleModEntry> {

    private final int listWidth;

    private MissingModsWarningScreen parent;

    public SimpleModListWidget(MissingModsWarningScreen parent, int listWidth, int top, int bottom) {
        super(Minecraft.getInstance(), listWidth, parent.height, top, bottom, 10);
        this.parent = parent;
        this.listWidth = listWidth;
        this.refreshList();
    }

    @Override
    protected int getScrollbarPosition() {
        return this.listWidth;
    }

    @Override
    public int getRowWidth() {
        return this.listWidth;
    }

    public void refreshList() {
        this.clearEntries();
        this.buildModList();
    }

    void buildModList() {
        for (MissingModsWarningScreen.SimpleModInfo simpleModInfo : parent.simpleModInfoList) {
            addEntry(new SimpleModEntry(simpleModInfo,parent));
        }
    }

    @Override
    protected void renderBackground(GuiGraphics guiGraphics) {
        this.parent.renderBackground(guiGraphics);
    }

    public class SimpleModEntry extends ObjectSelectionList.Entry<SimpleModEntry> {
        private final MissingModsWarningScreen.SimpleModInfo simpleModInfo;
        private final MissingModsWarningScreen parent;

        SimpleModEntry(MissingModsWarningScreen.SimpleModInfo simpleModInfo, MissingModsWarningScreen parent) {
            this.simpleModInfo = simpleModInfo;
            this.parent = parent;
        }

        @Override
        public Component getNarration() {
            return Component.empty();
        }

        @Override
        public void render(GuiGraphics guiGraphics, int entryIdx, int top, int left, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isMouseOver, float partialTick) {
            Component name = Component.literal(simpleModInfo.modid);
            Component mversion = Component.literal(simpleModInfo.version);
            Font font = Minecraft.getInstance().font;
            guiGraphics.drawString(font, Language.getInstance().getVisualOrder(FormattedText.composite(font.substrByWidth(name, listWidth))), left + 3, top + 2, 0xFFFFFF, false);
            guiGraphics.drawString(font, Language.getInstance().getVisualOrder(FormattedText.composite(font.substrByWidth(mversion, listWidth))), left + 3, top + 2 + font.lineHeight, 0xCCCCCC, false);
            if (true) {
                //TODO: Consider adding more icons for visualization
                RenderSystem.setShaderColor(1, 1, 1, 1);
                guiGraphics.pose().pushPose();
          //      guiGraphics.blit(VERSION_CHECK_ICONS, getLeft() + width - 12, top + entryHeight / 4, vercheck.status().getSheetOffset() * 8, (vercheck.status().isAnimated() && ((System.currentTimeMillis() / 800 & 1)) == 1) ? 8 : 0, 8, 8, 64, 16);
                guiGraphics.pose().popPose();
            }
        }

        @Override
        public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
          //  parent.setSelected(this);
          //  ModListWidget.this.setSelected(this);
            return false;
        }
    }
}