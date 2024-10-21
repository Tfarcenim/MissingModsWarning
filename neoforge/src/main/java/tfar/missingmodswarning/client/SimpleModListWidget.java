package tfar.missingmodswarning.client;

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

    public SimpleModListWidget(MissingModsWarningScreen parent, int listWidth) {
        super(parent.getMinecraft(), listWidth, parent.height - 120, 68, 18);//y,itemheight
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

   // @Override
  //  protected void renderBackground(GuiGraphics guiGraphics) {
   //     this.parent.renderBackground(guiGraphics);
   // }

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
            Component mOldversion = Component.literal(simpleModInfo.oldVersion);
            Component mversion = Component.literal(simpleModInfo.version);
            Font font = Minecraft.getInstance().font;
            guiGraphics.drawString(font, Language.getInstance().getVisualOrder(FormattedText.composite(font.substrByWidth(name, listWidth))), left + 3, top + 2, 0xFFFFFF, false);
            guiGraphics.drawString(font, Language.getInstance().getVisualOrder(FormattedText.composite(font.substrByWidth(mOldversion, listWidth))), left + 3 + width / 2, top + 2, 0xCCCCCC, false);
            guiGraphics.drawString(font, Language.getInstance().getVisualOrder(FormattedText.composite(font.substrByWidth(mversion, listWidth))), left + 3 + width * 3 / 4, top + 2, 0xCCCCCC, false);
        }

        @Override
        public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
          //  parent.setSelected(this);
          //  ModListWidget.this.setSelected(this);
            return false;
        }
    }
}