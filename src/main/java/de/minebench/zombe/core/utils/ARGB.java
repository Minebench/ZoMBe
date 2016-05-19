/**
 * Copyright (c) 2012 totemo
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

// --------------------------------------------------------------------------
/**
 * Stores a 32-bit Alpha + RGB colour value.
 * 
 * The alpha value is in the topmost 8 bits, with red, green and blue in
 * progressively less significant bits.
 */
public class ARGB
{
    // --------------------------------------------------------------------------
    /**
     * Default constructor.
     * 
     * Set's the colour to opaque white.
     */
    public ARGB()
    {
        this(0xFFFFFFFF);
    }

    // --------------------------------------------------------------------------
    /**
     * Constructor that sets all four components.
     * 
     * @param alpha the alpha component, in the range [0,255].
     * @param red the red component, in the range [0,255].
     * @param green the green component, in the range [0,255].
     * @param blue the blue component, in the range [0,255].
     */
    public ARGB(int alpha, int red, int green, int blue)
    {
        _value = ((alpha & 0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF);
    }

    // --------------------------------------------------------------------------
    /**
     * Constructor to set the 32-bit integer value of this colour.
     * 
     * @param value the new value, with A, R, G and B components in the most
     *                    significant to least significant octets, respectively.
     */
    public ARGB(int value)
    {
        _value = value;
    }

    @Override
    public String toString() {
            return Integer.toString(_value);
    }

    // --------------------------------------------------------------------------
    /**
     * Set the 32-bit integer value of this colour.
     * 
     * @param value the new value, with A, R, G and B components in the most
     *                    significant to least significant octets, respectively.
     */
    public void setValue(int value)
    {
        _value = value;
    }

    // --------------------------------------------------------------------------
    /**
     * Return the integer value, with A, R, G and B components in the most
     * significant to least significant octets, respectively.
     * 
     * @return the integer value, with A, R, G and B components in the most
     *                 significant to least significant octets, respectively.
     */
    public int getValue()
    {
        return _value;
    }

    // --------------------------------------------------------------------------
    /**
     * Set the alpha component of this colour.
     * 
     * @param alpha the new alpha value, in the range [0,255].
     */
    public void setAlpha(int alpha)
    {
        _value = (_value & RGB_BITS) | ((alpha & 0xFF) << 24);
    }

    // --------------------------------------------------------------------------
    /**
     * Return the alpha component, in the range [0,255].
     * 
     * @return the alpha component, in the range [0,255].
     */
    public int getAlpha()
    {
        return (_value >> 24) & 0xFF;
    }

    // --------------------------------------------------------------------------
    /**
     * Set the R, G and B components together as a 24-bit integer.
     * 
     * @param rgb the R, G and B components together as a 24-bit integer.
     */
    public void setRGB(int rgb)
    {
        _value = (_value & ALPHA_BITS) | (rgb & RGB_BITS);
    }

    // --------------------------------------------------------------------------
    /**
     * Return the R, G and B components together as a 24-bit integer.
     * 
     * @return the R, G and B components together as a 24-bit integer.
     */
    public int getRGB()
    {
        return _value & RGB_BITS;
    }

    // --------------------------------------------------------------------------
    /**
     * Set the red component, in the range [0,255].
     * 
     * @param red the red component, in the range [0,255].
     */
    public void setRed(int red)
    {
        _value = (_value & 0xFF00FFFF) | ((red & 0xFF) << 16);
    }

    // --------------------------------------------------------------------------
    /**
     * Return the red component, in the range [0,255].
     * 
     * @return the red component, in the range [0,255].
     */
    public int getRed()
    {
        return (_value >> 16) & 0xFF;
    }

    // --------------------------------------------------------------------------
    /**
     * Set the green component, in the range [0,255].
     * 
     * @param green the green component, in the range [0,255].
     */
    public void setGreen(int green)
    {
        _value = (_value & 0xFFFF00FF) | ((green & 0xFF) << 8);
    }

    // --------------------------------------------------------------------------
    /**
     * Return the green component, in the range [0,255].
     * 
     * @return the green component, in the range [0,255].
     */
    public int getGreen()
    {
        return (_value >> 8) & 0xFF;
    }

    // --------------------------------------------------------------------------
    /**
     * Set the blue component, in the range [0,255].
     * 
     * @param blue the blue component, in the range [0,255].
     */
    public void setBlue(int blue)
    {
        _value = (_value & 0xFFFFFF00) | (blue & 0xFF);
    }

    // --------------------------------------------------------------------------
    /**
     * Return the blue component, in the range [0,255].
     * 
     * @return the blue component, in the range [0,255].
     */
    public int getBlue()
    {
        return _value & 0xFF;
    }

    // --------------------------------------------------------------------------
    /*
     * The integer value, with A, R, G and B components in the most significant to
     * least significant octets, respectively.
     */
    protected int                        _value;

    /**
     * A bit mask selecting only the R, G and B components.
     */
    private static final int RGB_BITS     = 0x00FFFFFF;

    /**
     * A bit mask selecting only the alpha component.
     */
    private static final int ALPHA_BITS = ~RGB_BITS;
} // class ARGB
