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
import net.minecraft.util.text.TextFormatting;

public class Label implements UIElement
{

    private String color;
    private String label;
    private int xPos;
    private int yPos;
    private int defaultY;

    public Label(int x, int y, String label)
    {
        this.xPos = x;
        this.yPos = y;
        this.label = label;
        this.color = "";
        defaultY = y;
    }

    public Label setColour(TextFormatting e)
    {
        color = e.toString();
        return this;
    }

    @Override
    public void drawElement(int mouseX, int mouseY)
    {
        Zombe.getMC().getMinecraft().fontRenderer.drawStringWithShadow(this.color + this.label, this.xPos, this.yPos, 0xFFFFFF);
    }

    @Override
    public void renderToolTips(int mouseX, int mouseY)
    {

    }

    @Override
    public void addToolTip(ToolTip t)
    {

    }

    @Override
    public boolean mouseInput(int mouseX, int mouseY, int button)
    {
        return false;
    }

    @Override
    public boolean shiftClicked()
    {
        return false;
    }

    @Override
    public void mouseUnpressed(int mouseX, int mouseY)
    {

    }

    @Override
    public boolean keyInput(char keyChar, int keyId)
    {
        return false;
    }

    @Override
    public void setYOffset(int offset)
    {
        yPos += offset;
    }

    @Override
    public void setYPos(int pos)
    {
        this.yPos = this.defaultY + pos;
    }

    @Override
    public void resetYOffset()
    {
        yPos = defaultY;
    }
}
