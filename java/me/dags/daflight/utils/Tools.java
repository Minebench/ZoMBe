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

package me.dags.daflight.utils;

import com.mumfrey.liteloader.util.log.LiteLoaderLogger;
import me.dags.daflight.abstraction.MinecraftGame;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Tools extends MinecraftGame
{

    public static DecimalFormat df1;
    public static DecimalFormat df2;
    public static DecimalFormat df3;

    static
    {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.ENGLISH);
        dfs.setDecimalSeparator(".".charAt(0));
        df1 = new DecimalFormat("#.#", dfs);
        df2 = new DecimalFormat("#.##", dfs);
        df3 = new DecimalFormat("#.###", dfs);
    }

    public static void log(String msg)
    {
        LiteLoaderLogger.info("[DaFlight] " + msg);
    }

    public static double round(double d)
    {
        return Double.valueOf(df3.format(d));
    }

    public static void tellPlayer(String msg)
    {
        getPlayer().addChatMessage(getMessage(msg));
    }

}
