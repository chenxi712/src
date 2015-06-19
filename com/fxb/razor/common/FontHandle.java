package com.fxb.razor.common;

import java.util.*;
import com.badlogic.gdx.graphics.g2d.*;

public class FontHandle
{
    private static final HashMap<Constant.FontType, String> mapRegion;
    
    static {
        mapRegion = new HashMap<Constant.FontType, String>();
    }
    
    public static void draw(final SpriteBatch spriteBatch, final Constant.FontType fontType, final int n, final float n2, final float n3) {
        draw(spriteBatch, fontType, Integer.toString(n), n2, n3);
    }
    
    public static void draw(final SpriteBatch spriteBatch, final Constant.FontType fontType, final String s, final float n, final float n2) {
        draw(spriteBatch, fontType, s, n, n2, 0.0f, 0.0f, 1.0f, 1.0f);
    }
    
    public static void draw(final SpriteBatch spriteBatch, final Constant.FontType fontType, final String s, float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            float n7;
            if (fontType == Constant.FontType.star) {
                n7 = -1.0f;
            }
            else {
                n7 = -2.0f;
            }
            String string;
            float n8;
            if (char1 == '-') {
                string = "jianhao";
                n8 = n;
            }
            else if (char1 == '*') {
                string = "combo";
                n7 = 15.0f;
                n8 = n + 15.0f;
            }
            else if (char1 == '/') {
                string = "xiegang";
                n8 = n;
            }
            else if (char1 == ':') {
                string = "maohao";
                n8 = n;
            }
            else {
                if (char1 == ' ') {
                    n += 5.0f + n7;
                    continue;
                }
                string = getStrRegion(char1, fontType) + (char1 - '0');
                n8 = n;
            }
            final TextureAtlas.AtlasRegion region = Assets.atlasNumber.findRegion(string);
            n = n8;
            if (spriteBatch != null) {
                n = n8;
                if (region != null) {
                    spriteBatch.draw(region, n8, n2, n3, n4, region.getRegionWidth(), region.getRegionHeight(), n5, n6, 0.0f);
                    n = n8 + (region.getRegionWidth() + n7) * n5;
                }
            }
        }
    }
    
    public static float getHeight(final Constant.FontType fontType) {
        float n = 15.0f;
        switch (fontType) {
            default: {
                n = 0.0f;
                return n;
            }
            case combo:
            case damage: {
                return n;
            }
            case common: {
                return 17.0f;
            }
            case star: {
                return 11.0f;
            }
        }
    }
    
    private static String getStrRegion(final int n, final Constant.FontType fontType) {
        if (FontHandle.mapRegion.get(fontType) == null) {
            FontHandle.mapRegion.put(fontType, fontType.toString());
        }
        return FontHandle.mapRegion.get(fontType);
    }
    
    public static float getWidth(final String s, final Constant.FontType fontType) {
        float n = 0.0f;
        switch (fontType) {
            case common: {
                n = 15.0f;
                break;
            }
            case combo: {
                n = 13.0f;
                break;
            }
            case damage: {
                n = 14.0f;
                break;
            }
            case star: {
                n = 11.0f;
                break;
            }
        }
        return (n - 2.0f) * s.length() + 2.0f;
    }
}
