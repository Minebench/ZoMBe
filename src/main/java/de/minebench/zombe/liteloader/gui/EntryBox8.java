package de.minebench.zombe.liteloader.gui;

import de.minebench.zombe.core.minecraft.Colour;
import de.minebench.zombe.api.ui.element.IEntryBox;
import de.minebench.zombe.core.Zombe;
import de.minebench.zombe.core.gui.uielements.ToolTip;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

/**
 * @author dags_ <dags@dags.me>
 */

public class EntryBox8 extends GuiTextField implements IEntryBox
{
    private ToolTip toolTip;
    private boolean hovered;
    private String label;
    private String defaultValue;
    private boolean coloured = true;
    private boolean isActive;
    private int xPos;
    private int height;
    private int defaultY;

    public EntryBox8(int x, int y, int width, int height, String label, String defaultValue, boolean colour)
    {
        super(0, Zombe.getMC().getMinecraft().fontRendererObj, x, y, width, height);
        this.setFocused(false);
        this.setEnabled(true);
        this.coloured = colour;
        this.xPos = x;
        this.height = height;
        this.defaultY = y;
        this.label = label + ": ";
        this.defaultValue = defaultValue;
    }

    public IEntryBox setString(String s)
    {
        if (coloured)
        {
            s = Colour.addColour(s);
        }
        this.setText(label + s);
        return this;
    }

    public void setActive()
    {
        this.setText(Colour.stripColour(getText().replace(label, "")));
        this.setFocused(coloured);
        this.setTextColor(0xFF5555);
        this.isActive = true;
    }

    public void unsetActive()
    {
        this.isActive = false;
        this.setFocused(false);
        this.setTextColor(0xFFFFFF);
        this.setString(getText());
        this.setCursorPositionZero();
    }

    public String getValue()
    {
        return Colour.stripColour(getText().replace(label, ""));
    }

    @Override
    public void drawElement(int mouseX, int mouseY)
    {
        super.drawTextBox();
        hovered = mouseX >= this.xPos && mouseX <= this.xPos + this.getWidth() && mouseY >= this.yPosition && mouseY <= this.yPosition + this.height;
    }

    @Override
    public void renderToolTips(int mouseX, int mouseY)
    {
        if (this.hovered && this.toolTip != null)
            this.toolTip.draw(mouseX, mouseY);
    }

    @Override
    public void addToolTip(ToolTip t)
    {
        this.toolTip = t;
    }

    @Override
    public boolean mouseInput(int mouseX, int mouseY, int button)
    {
        super.mouseClicked(mouseX, mouseY, button);
        if (this.isFocused())
        {
            if (!this.isActive)
            {
                this.setActive();
                return true;
            }
        }
        else if (this.isActive)
        {
            this.unsetActive();
        }
        return false;
    }

    @Override
    public boolean shiftClicked()
    {
        if (this.hovered)
            this.setString(this.defaultValue);
        return this.hovered;
    }

    @Override
    public void mouseUnpressed(int mouseX, int mouseY)
    {
    }

    @Override
    public boolean keyInput(char keyChar, int keyId)
    {
        if (keyId == Keyboard.KEY_BACK && this.getText().length() == 0)
        {
            return false;
        }
        super.textboxKeyTyped(keyChar, keyId);
        if (this.isActive && (keyId == Keyboard.KEY_RETURN || keyId == Keyboard.KEY_ESCAPE))
        {
            this.unsetActive();
            return true;
        }
        return false;
    }

    @Override
    public void setYOffset(int offset)
    {
        this.yPosition += offset;
    }

    @Override
    public void setYPos(int pos)
    {
        this.yPosition = this.defaultY + pos;
    }

    @Override
    public void resetYOffset()
    {
        this.yPosition = defaultY;
    }
}
