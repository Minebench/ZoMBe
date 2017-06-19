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

package de.minebench.zombe.core.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import de.minebench.zombe.api.render.ARGB;
import de.minebench.zombe.core.Zombe;
import de.minebench.zombe.core.player.ZController;
import de.minebench.zombe.core.player.Speed;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * @author dags_ <dags@dags.me>
 */

public class Config
{
    private static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    private static final String FILE_NAME = "zombe.json";

    /**
     * KeyBinds
     */
    @Expose
    @SerializedName("Fly_Up_Key")
    public String upKey = "SPACE";
    @Expose
    @SerializedName("Fly_Down_Key")
    public String downKey = "LSHIFT";
    @Expose
    @SerializedName("FullBright_Key")
    public String fullBrightKey = "B";
    @Expose
    @SerializedName("OreHighlighter_Key")
    public String oreHighlighterKey = "M";
    @Expose
    @SerializedName("MobHighlighter_Key")
    public String mobHighlighterKey = "COMMA";
    @Expose
    @SerializedName("Flight_Key")
    public String flyKey = "F";
    @Expose
    @SerializedName("Sprint_Key")
    public String sprintKey = "Y";
    @Expose
    @SerializedName("SpeedMod_Key")
    public String speedKey = "C";
    @Expose
    @SerializedName("CineFlight_Key")
    public String cineFlyKey = "V";
    @Expose
    @SerializedName("SeeThrough_Key")
    public String seeThroughKey = "PERIOD";
    @Expose
    @SerializedName("NoClip_Key")
    public String noClipKey = "NONE";
    @Expose
    @SerializedName("SpeedUp_Key")
    public String speedUpKey = "PLUS";
    @Expose
    @SerializedName("SpeedDown_Key")
    public String speedDownKey = "MINUS";
    @Expose
    @SerializedName("IngameMenu_Key")
    public String quickMenuKey = "F7";

    /**
     * ControlToggles
     */
    @Expose
    @SerializedName("Fly_Is_Toggle")
    public boolean flyIsToggle = true;
    @Expose
    @SerializedName("Sprint_Is_Toggle")
    public boolean sprintIsToggle = true;
    @Expose
    @SerializedName("SpeedMod_Is_Toggle")
    public boolean speedIsToggle = true;
    @Expose
    @SerializedName("FullBright_Is_Toggle")
    public boolean fullbrightIsToggle = true;
    @Expose
    @SerializedName("OreHighlighter_Is_Toggle")
    public boolean oreHighlighterIsToggle = true;
    @Expose
    @SerializedName("MobHighlighter_Is_Toggle")
    public boolean mobHighlighterIsToggle = true;
    @Expose
    @SerializedName("SeeThrough_Is_Toggle")
    public boolean seeThroughIsToggle = true;
    @Expose
    @SerializedName("NoClip_Is_Toggle")
    public boolean noCLipIsToggle = true;

    /**
     * Preferences
     */
    @Expose
    @SerializedName("Disable_Mod")
    public boolean disabled = false;
    @Expose
    @SerializedName("3D_Flight")
    public boolean threeDFlight = false;
    @Expose
    @SerializedName("Show_Hud")
    public boolean showHud = true;
    @Expose
    @SerializedName("Disable_FOV")
    public boolean disableFov = true;
    @Expose
    @SerializedName("Enable_Step")
    public boolean enableStep = false;
    @Expose
    @SerializedName("Disable_Creative_Flight")
    public boolean disableCreativeFlight = true;

    /**
     * Parameters
     */
    @Expose
    @SerializedName("Fly_Speed")
    public float flySpeed = 0.1F;
    @Expose
    @SerializedName("Fly_Speed_Multiplier")
    public float flySpeedMult = 5.0F;
    @Expose
    @SerializedName("Fly_Smoothing_Factor")
    public float flySmoothing = 0.7F;
    @Expose
    @SerializedName("Sprint_Speed")
    public float sprintSpeed = 0.1F;
    @Expose
    @SerializedName("Sprint_Speed_Multiplier")
    public float sprintSpeedMult = 5.0F;
    @Expose
    @SerializedName("Jump_Multiplier")
    public float jumpModifier = 0.9F;
    @Expose
    @SerializedName("Left-Right_Modifier")
    public float lrModifier = 0.85F;
    @Expose
    @SerializedName("OreHighlighter_Range")
    public float oreHighlighterRange = 16F;
    @Expose
    @SerializedName("OreHighlighter_UpdateRate")
    public int oreHighlighterUpdateRate = 10;
    @Expose
    @SerializedName("MobHighlighter_Range")
    public float mobHighlighterRange = 16F;
    @Expose
    @SerializedName("SeeThrough_Range")
    public float seeThroughRange = 4F;

    /**
     * HudElements
     */
    @Expose
    @SerializedName("Flight_Status")
    public String flightStatus = "f";
    @Expose
    @SerializedName("Cine_Flight_Status")
    public String cineFlightStatus = "c";
    @Expose
    @SerializedName("Sprint_Status")
    public String runStatus = "r";
    @Expose
    @SerializedName("FullBright_Status")
    public String fullBrightStatus = "fb";
    @Expose
    @SerializedName("OreHighlighter_Status")
    public String oreHighlighterStatus = "o";
    @Expose
    @SerializedName("MobHighlighter_Status")
    public String mobHighlighterStatus = "m";
    @Expose
    @SerializedName("SeeThrough_Status")
    public String seeThroughStatus = "s";
    @Expose
    @SerializedName("Speed_Status")
    public String speedStatus = "*";
    @Expose
    @SerializedName("NoClip_Status")
    public String noClipStatus = "n";
    @Expose
    @SerializedName("Status_Shadow")
    public boolean textShadow = true;

    @Expose
    @SerializedName("OreColors")
    public Map<String, String> oreColorsString = new HashMap<String, String>() {{
        put("oreGold",     "255, 215, 0");
        put("oreIron",     "210, 105, 30");
        put("oreCoal",     "0, 0, 0");
        put("oreLapis",    "115, 135, 155");
        put("oreDiamond",  "0, 190, 255");
        put("oreRedstone", "255, 0, 0");
        put("oreEmerald",  "65, 205, 130");
        put("netherquartz","200, 200, 200");
    }};

    public Map<String, ARGB> oreColors = new HashMap<String, ARGB>();

    @Expose
    @SerializedName("MobColors")
    public Map<String, String> mobColorsString = new HashMap<String, String>() {{
        put("Slime", "255, 0, 0");
        put("Enderman", "255, 0, 0");
        put("Villager", "0, 255, 0");
        put("Zombie", "255, 0, 0");
        put("Lohe", "255, 0, 0");
        put("Witch", "255, 0, 0");
        put("Enderdragon", "255, 0, 0");
        put("Furnaceminecart", "0, 255, 0");
        put("Wolf", "255, 0, 0");
        put("Boat", "0, 255, 0");
        put("Minecart", "0, 255, 0");
        put("Item", "255, 0, 0");
        put("Skeleton", "255, 0, 0");
        put("Cavespider", "255, 0, 0");
        put("Player", "255, 255, 255");
        put("Spawnerminecart", "0, 255, 0");
        put("Spider", "255, 0, 0");
        put("Rabbit", "0, 255, 0");
        put("Guardian", "255, 0, 0");
        put("Silverfish", "255, 0, 0");
        put("Ghast", "255, 0, 0");
        put("Squid", "0, 255, 0");
        put("Giant", "255, 0, 0");
        put("Hopperminecart", "0, 255, 0");
        put("Sheep", "0, 255, 0");
        put("Chestminecart", "0, 255, 0");
        put("Magmacube", "255, 0, 0");
        put("Chicken", "0, 255, 0");
        put("Ocelot", "0, 255, 0");
        put("Mushroomcow", "0, 255, 0");
        put("Endermite", "255, 0, 0");
        put("Snowman", "0, 255, 0");
        put("Pig", "0, 255, 0");
        put("Cow", "0, 255, 0");
        put("Tntminecart", "0, 255, 0");
        put("Horse", "0, 255, 0");
        put("Bat", "0, 255, 0");
        put("PigZombie", "255, 100, 25");
        put("Blaze", "255, 0, 0");
        put("Wither", "255, 0, 0");
        put("Irongolem", "0, 255, 0");
        put("Xp", "255, 0, 0");
        put("Creeper", "255, 0, 0");
        put("unknown", "150, 150, 150");
    }};

    public Map<String, ARGB> mobColors = new HashMap<String, ARGB>();

    private File saveFile;

    private Config()
    {
    }

    public Config setSaveFile(File file)
    {
        saveFile = file;
        return this;
    }

    public void setSpeeds(Speed speed)
    {
        switch (speed.getType())
        {
            case FLY:
                flySpeed = speed.getBaseSpeed();
                flySpeedMult = speed.getMultiplier();
                saveConfig();
                break;
            case SPRINT:
                sprintSpeed = speed.getBaseSpeed();
                sprintSpeedMult = speed.getMultiplier();
                saveConfig();
                break;
        }
    }

    public void applySettings()
    {
        ZController zController = Zombe.get().ZController;
        ZController.KEY_BINDS.initSettings();
        zController.flySpeed.setSpeedValues(flySpeed, flySpeedMult);
        zController.sprintSpeed.setSpeedValues(sprintSpeed, sprintSpeedMult);
        Zombe.getHud().refreshStatuses();
        if (!speedIsToggle)
        {
            zController.flySpeed.setBoost(false);
            zController.sprintSpeed.setBoost(false);
            Zombe.getHud().updateMsg();
        }

        for(Map.Entry<String, String> entry : oreColorsString.entrySet()) {
            try {
                oreColors.put(entry.getKey(), new ARGB(entry.getValue()));
            } catch(NumberFormatException e) {
                Zombe.log(Level.WARNING, "Could not load ore highlight color setting for " + entry.getKey() + ": " + e.getMessage());
            }
        }

        for(Map.Entry<String, String> entry : mobColorsString.entrySet()) {
            try {
                mobColors.put(entry.getKey().toLowerCase(), new ARGB(entry.getValue()));
            } catch(NumberFormatException e) {
                Zombe.log(Level.WARNING, "Could not load mob highlight color setting for " + entry.getKey() + ": " + e.getMessage());
            }
        }
    }

    public Config saveConfig()
    {
        try
        {
            if (!saveFile.exists())
                saveFile.createNewFile();
            FileWriter writer = new FileWriter(saveFile);
            writer.write(GSON.toJson(this));
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return this;
    }

    public static Config getDefaultConfig()
    {
        File file = new File(Zombe.getConfigFolder(), Config.FILE_NAME);
        try
        {
            Config config = GSON.fromJson(new FileReader(file), Config.class);
            if (config != null)
                return config.setSaveFile(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return new Config().setSaveFile(file).saveConfig();
    }

    public static Config getServerConfig()
    {
        File file = new File(Tools.getOrCreateFolder(Zombe.getConfigFolder(), "servers"), Zombe.getMC().getServerData().serverIP.replace(":", "-") + ".json");
        try
        {
            return GSON.fromJson(new FileReader(file), Config.class).setSaveFile(file);
        }
        catch (IOException e)
        {
            return new Config().setSaveFile(file).saveConfig();
        }
    }
}
