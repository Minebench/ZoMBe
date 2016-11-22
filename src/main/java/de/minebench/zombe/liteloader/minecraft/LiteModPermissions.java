/*
 * Copyright 2016 Max Lee (https://github.com/Phoenix616/)
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

package de.minebench.zombe.liteloader.minecraft;

import com.mumfrey.liteloader.Permissible;
import com.mumfrey.liteloader.permissions.PermissionsManagerClient;
import net.minecraft.client.Minecraft;

public class LiteModPermissions {
    public static final String MOD_NAME = "zombe";
    private static PermissionsManagerClient permissionsManager;
    private static Permissible mod;
    private static boolean isInitiated = false;

    public static void init(Permissible mod, PermissionsManagerClient pmc) {
        if (!isInitiated || LiteModPermissions.permissionsManager != pmc) {
            isInitiated = true;
            LiteModPermissions.mod = mod;
            LiteModPermissions.permissionsManager = pmc;

            initPermissions();
        }
    }

    private static void initPermissions() {
        registerPermission("*");
    }

    public static void refreshPermissions(Minecraft minecraft) {
        try {
            permissionsManager.tamperCheck();
            permissionsManager.sendPermissionQuery(mod);
        } catch (IllegalArgumentException ignored) {}
    }

    public static long getLastUpdateTime(){
        return permissionsManager.getPermissionUpdateTime(mod);
    }

    public static void registerPermission(String permission) {
        permissionsManager.tamperCheck();
        permissionsManager.registerModPermission(mod, permission);
    }

    public static boolean hasPermission(String permission) {
        permissionsManager.tamperCheck();
        return permissionsManager.getModPermission(mod, permission);
    }
}

