/*
 * Copyright (c) 2014, dags_ <dags@dags.me>
 *
 *  Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby
 *  granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING
 *  ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL,
 *  DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS,
 *  WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE
 *  USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package me.dags.daflight.ui.pages;

import com.mumfrey.liteloader.client.gui.GuiCheckbox;
import me.dags.daflight.input.binds.KeyBind;
import me.dags.daflight.minecraft.Colour;
import me.dags.daflight.minecraft.MinecraftGame;
import me.dags.daflight.minecraft.uielements.GuiEntryBox;
import me.dags.daflight.minecraft.uielements.GuiLabel;
import me.dags.daflight.ui.Settings;
import me.dags.daflight.utils.Config;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Page1 extends MinecraftGame
{
    /*
        Holds keyBinds and status messages
     */

    private Settings settings;

    public String pageTitle;
    public int topMargin;
    public int margin;
    public int pageNumber;

    private final Set<GuiLabel> labels;
    private final Map<String, GuiEntryBox> keyBinds;
    private final Map<String, GuiEntryBox> statuses;
    private final Map<String, GuiCheckbox> checkBoxes;

    public Page1(Settings s, int width, int height, int page)
    {
        labels = new HashSet<GuiLabel>();
        keyBinds = new HashMap<String, GuiEntryBox>();
        statuses = new HashMap<String, GuiEntryBox>();
        checkBoxes = new HashMap<String, GuiCheckbox>();

        settings = s;
        pageTitle = "New Page";
        pageNumber = 1;
        margin = (int) Math.round(width / 2.1) * pageNumber;
        topMargin = height;

        if (pageNumber == 1)
        {
            margin += 5;
        }
    }

    public void drawPage(int mouseX, int mouseY)
    {
        for (GuiLabel l : labels)
        {
            l.draw();
        }
        for (GuiEntryBox gb : keyBinds.values())
        {
            gb.draw();
        }
        for (GuiEntryBox gb : statuses.values())
        {
            gb.draw();
        }
        for (GuiCheckbox gc : checkBoxes.values())
        {
            gc.drawButton(getMinecraft(), mouseX, mouseY);
            if (hover(gc, mouseX, mouseY))
            {
                getMinecraft().fontRendererObj.drawString(gc.getHoverMessage(), mouseX + 10, mouseY, 0XAFAFAF);
            }
        }
    }

    private boolean hover(GuiCheckbox gb, int mouseX, int mouseY)
    {
        int checkX = gb.xPosition;
        int checkY = gb.yPosition;
        return mouseX >= checkX && mouseX <= checkX + 10 && mouseY >= checkY && mouseY <= checkY + 10;
    }

    public void clicked(int mouseX, int mouseY, int mouseButton)
    {
        for (GuiEntryBox gb : keyBinds.values())
        {
            gb.mouseClicked(mouseX, mouseY, mouseButton);
            if (gb.isFocused())
            {
                gb.setActive();
            }
            else
            {
                gb.unsetActive();
            }
        }
        for (GuiEntryBox gb : statuses.values())
        {
            gb.mouseClicked(mouseX, mouseY, mouseButton);
            if (gb.isFocused())
            {
                gb.setActive();
            }
            else
            {
                gb.unsetActive();
            }
        }
        for (GuiCheckbox gc : checkBoxes.values())
        {
            if (gc.mousePressed(getMinecraft(), mouseX, mouseY))
            {
                gc.checked = !gc.checked;
            }
        }
    }

    public void released(int mouseX, int mouseY)
    {

    }

    public boolean keyPress(char keyChar, int keyCode)
    {
        boolean escape = keyCode == Keyboard.KEY_ESCAPE;
        for (GuiEntryBox gb : keyBinds.values())
        {
            if (gb.isActive())
            {
                gb.unsetActive();
                if (keyCode == Keyboard.KEY_RETURN)
                {
                    break;
                }
                if (keyCode == Keyboard.KEY_ESCAPE)
                {
                    gb.setText("NONE");
                    escape = false;
                    break;
                }
                gb.setText(Keyboard.getKeyName(keyCode));
            }
        }
        for (GuiEntryBox gb : statuses.values())
        {
            if (gb.isActive())
            {
                if (keyCode == Keyboard.KEY_RETURN)
                {
                    gb.setFocused(false);
                    gb.unsetActive();
                    break;
                }
                if (keyCode == Keyboard.KEY_ESCAPE)
                {
                    gb.setFocused(false);
                    gb.setText("");
                    gb.unsetActive();
                    escape = false;
                    break;
                }
                gb.entry(keyChar, keyCode);
            }
            else
            {
                gb.setFocused(false);
            }
        }
        return escape;
    }

    public void setWidth(int i)
    {
        margin = (int) Math.round(i / 2.1) * pageNumber;
        if (pageNumber == 1)
        {
            margin += 5;
        }
    }

    public void setMargin(int i)
    {
        margin = i;
    }

    public void setTopMargin(int i)
    {
        topMargin = i;
    }

    public void save()
    {
        for (String s : keyBinds.keySet())
        {
            GuiEntryBox gb = keyBinds.get(s);
            settings.updateSetting(s, gb.getString());
        }
        for (String s : statuses.keySet())
        {
            GuiEntryBox gb = statuses.get(s);
            settings.updateSetting(s, gb.getString());
        }
        for (String s : checkBoxes.keySet())
        {
            GuiCheckbox gb = checkBoxes.get(s);
            settings.setToggles(s, gb.checked);
        }
    }

    public void load()
    {
        int[] xy = new int[]{margin + 10, topMargin + 10};
        xy = loadBinds(xy[0], xy[1]);
        loadStatuses(xy[0], xy[1]);
    }

    private int[] setTitle(String s, int x, int y)
    {
        int z = s.length();
        GuiLabel gl = new GuiLabel(getMinecraft().fontRendererObj, x, y);
        gl.setLabel(Colour.DARK_AQUA + s + ":");
        gl.setShadow(true);
        labels.add(gl);
        y += 10;
        return new int[]{x, y};
    }

    private int[] loadBinds(int x, int y)
    {
        int[] temp = new int[]{x, y};

        if (!settings.getKeyBinds().isEmpty())
        {
            temp = setTitle("KeyBinds", temp[0] - 3, temp[1]);
            temp[0] += 3;
            temp[1] += 5;

            for (KeyBind kb : settings.getBinds())
            {
                GuiLabel gl = new GuiLabel(getMinecraft().fontRendererObj, temp[0], temp[1]);
                gl.setLabel(kb.getName());
                labels.add(gl);

                GuiEntryBox gb = new GuiEntryBox(getMinecraft().fontRendererObj, temp[0] + 70, temp[1] - 2, 60, 10);
                gb.name(kb.getKeyName());
                keyBinds.put(kb.getName(), gb);

                if (kb.canHold())
                {
                    GuiCheckbox gc = new GuiCheckbox(0, temp[0] + 135, temp[1] - 3, "");
                    gc.checked = kb.isToggle();
                    gc.setHoverMessages("ToggleKey", "HoldKey");
                    checkBoxes.put(kb.getName(), gc);
                }

                temp[1] += 15;
            }
            temp[1] += 5;
        }
        return temp;
    }

    private int[] loadStatuses(int x, int y)
    {
        int[] temp = new int[]{x, y};
        if (!settings.getStatuses().isEmpty())
        {
            temp = setTitle("Status Messages", temp[0] - 3, temp[1]);

            temp[0] += 3;
            temp[1] += 5;
            for (String s : settings.getStatuses().keySet())
            {
                String status = settings.getStatuses().get(s);
                GuiLabel gl = new GuiLabel(getMinecraft().fontRendererObj, temp[0], temp[1]);
                gl.setLabel(s);
                labels.add(gl);

                GuiEntryBox gb = new GuiEntryBox(getMinecraft().fontRendererObj, temp[0] + 70, temp[1] - 2, 60, 10);
                gb.name(status);
                gb.setEnabled(true);
                statuses.put(s, gb);

                temp[1] += 15;
            }
            GuiLabel gl = new GuiLabel(getMinecraft().fontRendererObj, temp[0], temp[1]);
            gl.setLabel("Text Shadow");
            labels.add(gl);
            GuiCheckbox gc = new GuiCheckbox(0, temp[0] + 70, temp[1] - 3, "");
            gc.checked = Config.getInstance().textShadow;
            gc.setHoverMessages("", "");
            checkBoxes.put("TextShadow", gc);
        }
        return temp;
    }
}