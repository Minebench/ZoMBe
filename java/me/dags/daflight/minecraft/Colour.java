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

package me.dags.daflight.minecraft;

import net.minecraft.util.EnumChatFormatting;

/**
 * @author dags_ <dags@dags.me>
 */

public class Colour
{

    public static final String GREY = EnumChatFormatting.GRAY.toString();
    public static final String DARK_AQUA = EnumChatFormatting.DARK_AQUA.toString();
    public static final String DARK_PURPLE = EnumChatFormatting.DARK_PURPLE.toString();

    public static String stripColour(String s)
    {
        return s.replaceAll("(?i)§([a-f0-9k-or])", "&" + "$1");
    }

    public static String addColour(String s)
    {
        return s.replaceAll("(?i)&([a-f0-9k-or])", "§" + "$1");
    }

}