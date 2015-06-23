package com.fxb.razor.flash;

import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;

public class ElementPlayer
{
    public static float cos;
    public static float sin;
    private static TextureRegion tempRegion;
    public static float x1;
    public static float x2;
    public static float x3;
    public static float x4;
    public static float y1;
    public static float y2;
    public static float y3;
    public static float y4;
    public Element element;
    private FlashPlayer flashPlayer;
    private boolean flipX;
    private boolean flipY;
    private float originX;
    private float originY;
    public TextureRegion textureRegion;
    
    public ElementPlayer(Element element, TextureAtlas textureAtlas, FlashPlayer flashPlayer) {
        this.flipX = false;
        this.flipY = false;
        this.element = element;
//        while (true) {
//            try {
//                this.textureRegion = new TextureRegion(textureAtlas.findRegion(element.textureName));
//                this.flashPlayer = flashPlayer;
//            }
//            catch (Exception ex) {
//                continue;
//            }
//            break;
//        }
        try {
            this.textureRegion = new TextureRegion(textureAtlas.findRegion(element.textureName));
            this.flashPlayer = flashPlayer;
        }
        catch (Exception ex) {
        }
    }
    
    public void SetFlipX(boolean flipX) {
        if (this.flipX != flipX) {
            this.flipX = flipX;
            if (this.flipX) {
                return;
            }
        }
    }
    
    public void SetOrigin(float originX, float originY) {
        this.originX = originX;
        this.originY = originY;
    }
    
    public void changeRegion(TextureAtlas textureAtlas, String s) {
        ElementPlayer.tempRegion = textureAtlas.findRegion(s);
        if (ElementPlayer.tempRegion != null) {
            this.textureRegion = ElementPlayer.tempRegion;
        }
    }
    
    public void changeRegion(TextureAtlas textureAtlas, String[] array, String[] array2) {
        for (int n = 0; n < array.length && !this.changeRegion(textureAtlas, array2[n], array[n]); ++n) {}
    }
    
    public void changeRegion(TextureAtlas textureAtlas, String[] array, String[] array2, String s) {
        for (int n = 0; n < array.length && !this.changeRegion(textureAtlas, s + "_" + array2[n], array[n]); ++n) {}
    }
    
    public boolean changeRegion(TextureAtlas textureAtlas, String s, String s2) {
        if (this.element.textureName.equals(this.flashPlayer.flashElement.name + "_" + s2)) {
            if (s == null) {
                this.textureRegion = null;
            }
            else if (s.equals(s2)) {
                this.changeRegion(textureAtlas, this.element.textureName);
            }
            else {
                this.changeRegion(textureAtlas, s);
            }
            return true;
        }
        return false;
    }
    
    public void defaultRegion(TextureAtlas textureAtlas) {
        this.textureRegion = textureAtlas.findRegion(this.element.textureName);
    }
    
    public void drawElement(SpriteBatch spriteBatch, float n, float n2, float n3) {
        if (this.element.alphaMultiplier != 1.0f || this.flashPlayer.alphaMultiplier != 1.0f) {
            Color color = spriteBatch.getColor();
            spriteBatch.setColor(color.r, color.g, color.b, color.a * this.element.alphaMultiplier * this.flashPlayer.alphaMultiplier);
        }
        float n4 = this.element.vertex4.x0 * n3;
        float n5 = this.element.vertex4.x1 * n3;
        float n6 = this.element.vertex4.x2 * n3;
        float n7 = this.element.vertex4.x3 * n3;
        float n8 = this.element.vertex4.y0 * n3;
        float n9 = this.element.vertex4.y1 * n3;
        float n10 = this.element.vertex4.y2 * n3;
        float n11 = this.element.vertex4.y3 * n3;
        if (this.textureRegion != null) {
            float n12 = n4;
            float n13 = n5;
            float n14 = n6;
            n3 = n7;
            if (this.flipX) {
                n12 = 2.0f * this.originX - n4;
                n13 = 2.0f * this.originX - n5;
                n14 = 2.0f * this.originX - n6;
                n3 = 2.0f * this.originX - n7;
            }
            float n15 = n8;
            float n16 = n9;
            float n17 = n10;
            float n18 = n11;
            if (this.flipY) {
                n15 = 2.0f * this.originY - n8;
                n16 = 2.0f * this.originY - n9;
                n17 = 2.0f * this.originY - n10;
                n18 = 2.0f * this.originY - n11;
            }
            if (this.element.textureName.equals("turtle_1_zhijia")) {
                Global.pMain1.set(n + n14 + 70.0f, n2 + n17 + 88.0f);
                Global.pMain2.set(n + n3 + 70.0f, n2 + n18 + 88.0f);
            }
            else if (this.element.textureName.equals("turtle_1_fuwuqizhijia")) {
                Global.pSub1.set(n + n14 + 70.0f, n2 + n17 + 88.0f);
                Global.pSub2.set(n + n3 + 70.0f, n2 + n18 + 88.0f);
            }
            spriteBatch.draw(this.textureRegion, n + n12, n2 + n15, n + n13, n2 + n16, n + n14, n2 + n17, n + n3, n2 + n18);
        }
        if (this.element.alphaMultiplier != 1.0f || this.flashPlayer.alphaMultiplier != 1.0f) {
            spriteBatch.setColor(1.0f, 1.0f, 1.0f, this.flashPlayer.alphaMultiplier);
        }
    }
    
    public void drawElement(SpriteBatch spriteBatch, float n, float n2, float n3, float n4, float n5, float n6) {
        if (this.element.alphaMultiplier != 1.0f) {
            Color color = spriteBatch.getColor();
            spriteBatch.setColor(color.r, color.g, color.b, color.a * this.element.alphaMultiplier * this.flashPlayer.alphaMultiplier);
        }
        if (this.textureRegion != null) {
            if (n5 == 0.0f || !this.isRotate()) {
                this.drawElement(spriteBatch, n, n2, n6);
            }
            else {
                ElementPlayer.cos = MathUtils.cosDeg(n5);
                ElementPlayer.sin = MathUtils.sinDeg(n5);
                ElementPlayer.x1 = ElementPlayer.cos * (this.element.vertex4.x0 * n6 - n3) - ElementPlayer.sin * (this.element.vertex4.y0 * n6 - n4);
                ElementPlayer.y1 = ElementPlayer.sin * (this.element.vertex4.x0 * n6 - n3) + ElementPlayer.cos * (this.element.vertex4.y0 * n6 - n4);
                ElementPlayer.x2 = ElementPlayer.cos * (this.element.vertex4.x1 * n6 - n3) - ElementPlayer.sin * (this.element.vertex4.y1 * n6 - n4);
                ElementPlayer.y2 = ElementPlayer.sin * (this.element.vertex4.x1 * n6 - n3) + ElementPlayer.cos * (this.element.vertex4.y1 * n6 - n4);
                ElementPlayer.x3 = ElementPlayer.cos * (this.element.vertex4.x2 * n6 - n3) - ElementPlayer.sin * (this.element.vertex4.y2 * n6 - n4);
                ElementPlayer.y3 = ElementPlayer.sin * (this.element.vertex4.x2 * n6 - n3) + ElementPlayer.cos * (this.element.vertex4.y2 * n6 - n4);
                ElementPlayer.x4 = ElementPlayer.x1 + (ElementPlayer.x3 - ElementPlayer.x2);
                ElementPlayer.y4 = ElementPlayer.y3 - (ElementPlayer.y2 - ElementPlayer.y1);
                spriteBatch.draw(this.textureRegion, ElementPlayer.x1 + n + n3, ElementPlayer.y1 + n2 + n4, ElementPlayer.x2 + n + n3, ElementPlayer.y2 + n2 + n4, ElementPlayer.x3 + n + n3, ElementPlayer.y3 + n2 + n4, ElementPlayer.x4 + n + n3, ElementPlayer.y4 + n2 + n4);
            }
        }
        if (this.element.alphaMultiplier != 1.0f) {
            spriteBatch.setColor(1.0f, 1.0f, 1.0f, this.flashPlayer.alphaMultiplier);
        }
    }
    
    public void drawElement(SpriteBatch spriteBatch, Vector2 vector2, float n) {
        this.drawElement(spriteBatch, vector2.x, vector2.y, n);
    }
    
    public void drawElement(SpriteBatch spriteBatch, Vector2 vector2, float n, float n2, float n3, float n4) {
        this.drawElement(spriteBatch, vector2.x, vector2.y, n, n2, n3, n4);
    }
    
    public boolean isRotate() {
        return !this.element.textureName.contains("maingun_") || (!this.element.textureName.contains("_taizi") && !this.element.textureName.contains("_zhijia"));
    }
    
    public boolean selectRegion(String[] array) {
        for (int i = 0; i < array.length; ++i) {
            if (this.element.textureName.equals(this.flashPlayer.flashElement.name + "_" + array[i])) {
                return true;
            }
        }
        this.textureRegion = null;
        return false;
    }
}
