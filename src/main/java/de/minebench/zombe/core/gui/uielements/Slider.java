/*
 * Copyright (c) 2014, dags_ <dags@dags.me>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package de.minebench.zombe.core.gui.uielements;

import de.minebench.zombe.api.ui.element.UIElement;
import de.minebench.zombe.core.Zombe;
import de.minebench.zombe.core.utils.Tools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Slider extends GuiOptionSlider implements UIElement
{

    private static final DecimalFormat FORMAT;

    static
    {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.ENGLISH);
        dfs.setDecimalSeparator(".".charAt(0));
        FORMAT = new DecimalFormat("0.00", dfs);
    }

    private ToolTip toolTip;
    private String name;
    private float min;
    private float max;
    private float value;
    private float defaultValue;
    private int defaultY;

    public Slider(int a, int x, int y, float min, float max, float defaultValue, int width)
    {
        this("", a, x, y, min, max, defaultValue, width);
    }

    public Slider(String name, int a, int x, int y, float min, float max, float defaultValue, int width) {
        super(a, x, y, GameSettings.Options.byOrdinal(0), min, max);
        this.name = name;
        this.min = min;
        this.max = max;
        this.width = width;
        setDefaultValue(defaultValue);
        this.defaultY = y;
    }

    public Slider setDefaultValue(float f)
    {
        this.value = normalise(f);
        this.displayString = this.name + ": " + getStringValue();
        return this;
    }

    @Override
    public void drawElement(int mouseX, int mouseY)
    {
        this.drawButton(Zombe.getMC().getMinecraft(), mouseX, mouseY, 0);
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
        return super.mousePressed(Zombe.getMC().getMinecraft(), mouseX, mouseY);
    }

    @Override
    public boolean shiftClicked()
    {
        if (this.hovered)
            this.setDefaultValue(this.defaultValue);
        return this.hovered;
    }

    @Override
    public void mouseUnpressed(int mouseX, int mouseY)
    {
        super.mouseReleased(mouseX, mouseY);
    }

    @Override
    public boolean keyInput(char keyChar, int keyId)
    {
        return false;
    }

    @Override
    public void setYOffset(int offset)
    {
        super.y += offset;
    }

    @Override
    public void setYPos(int pos)
    {
        this.y = this.defaultY + pos;
    }

    @Override
    public void resetYOffset()
    {
        super.y = defaultY;
    }

    public Slider setDisplayString(String s)
    {
        this.displayString = s + ": " + getStringValue();
        this.name = s;
        return this;
    }

    private String getStringValue()
    {
        return Tools.round2Dp(value);
    }

    public String getName()
    {
        return name;
    }

    public float getValue()
    {
        return Float.valueOf(getStringValue());
    }

    @Override
    public void mouseDragged(Minecraft m, int x, int y)
    {
        if (this.enabled)
        {
            if (this.dragging)
            {
                this.value = (float) (x - (this.x + 4)) / (float) (((this.width) - 8) / max);

                if (this.value < min)
                {
                    this.value = min;
                }

                if (this.value > max)
                {
                    this.value = max;
                }

                this.value = normalise(value);
                this.displayString = name + ": " + getStringValue();
            }
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.x + (int) (this.value * (float) ((this.width - 8)) / max), this.y, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.x + (int) (this.value * (float) ((this.width - 8)) / max) + 4, this.y, 196, 66, 4, 20);
        }
    }

    private float normalise(float f)
    {
        return MathHelper.clamp(f, this.min, this.max);
    }

}
