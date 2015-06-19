package com.fxb.razor.utils.ui;

import com.badlogic.gdx.utils.*;
import com.fxb.razor.utils.*;
import com.badlogic.gdx.math.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.graphics.g2d.*;

public class BackScene
{
    Array<Sprite> arrSprMove;
    Array<Sprite> arrSprStill;
    TextureAtlas atlasScene;
    TextureRegion farHill;
    TextureRegion front;
    TextureRegion ground;
    TextureRegion sky;
    
    public BackScene() {
        this.arrSprStill = new Array<Sprite>();
        this.arrSprMove = new Array<Sprite>();
        final int sceneLevel = Global.sceneLevel;
        this.atlasScene = Global.manager.get("scene/scene" + Global.sceneLevel + ".pack", TextureAtlas.class);
        this.sky = this.atlasScene.findRegion(StrHandle.get("tian", sceneLevel));
        this.farHill = this.atlasScene.findRegion(StrHandle.get("yuanshan", sceneLevel));
        this.ground = this.atlasScene.findRegion(StrHandle.get("dimian", sceneLevel));
        this.front = this.atlasScene.findRegion(StrHandle.get("qianjing", sceneLevel));
        this.initSprite();
    }
    
    private void initSprite() {
        final int sceneLevel = Global.sceneLevel;
        float n = 0.0f;
        switch (sceneLevel) {
            case 1: {
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "shan1-1", 23.0f, 150.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "shan1-2", 336.0f, 150.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "shan1-3", 633.0f, 148.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "shan1-3", 274.0f, 150.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi1-3", 697.0f, 146.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi1-2", 427.0f, 150.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi1-1", 98.0f, 153.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi1-3", 15.0f, 145.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi1-3", 323.0f, 146.0f);
                n = -50.0f;
                break;
            }
            case 2: {
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "shan2-1", 578.0f, 108.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "shan2-2", 27.0f, 110.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "shan2-3", 247.0f, 109.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi2-3", 87.0f, 111.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi2-2", 10.0f, 109.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi2-1", 325.0f, 104.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi2-3", 501.0f, 107.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi2-3", 674.0f, 106.0f);
                n = 0.0f;
                break;
            }
            case 3: {
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "shan3-2", 192.0f, 106.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "shan3-3", 565.0f, 107.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "shan3-1", 498.0f, 107.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "shan3-1", 329.0f, 108.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "shan3-1", 23.0f, 109.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi3-3", 7.0f, 111.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi3-2", 409.0f, 107.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi3-1", 141.0f, 106.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi3-3", 698.0f, 103.0f);
                n = 0.0f;
                break;
            }
            case 4: {
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "shan4-1", 39.0f, 109.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "shan4-2", 250.0f, 109.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "shan4-3", 537.0f, 107.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi4-1", 19.0f, 111.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi4-3", 207.0f, 105.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi4-1", 602.0f, 108.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi4-2", 476.0f, 105.0f);
                n = 0.0f;
                break;
            }
            case 5: {
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "shan5-1", 75.0f, 98.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "shan5-2", 296.0f, 99.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "shan5-3", 597.0f, 98.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi5-1", 288.0f, 96.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi5-3", 670.0f, 97.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi5-1", 21.0f, 99.0f);
                UiHandle.addSprite(this.arrSprStill, this.atlasScene, "fangzhi5-2", 409.0f, 97.0f);
                n = 0.0f;
                break;
            }
        }
        for (int i = 0; i < this.arrSprStill.size; ++i) {
            this.arrSprStill.get(i).translateY(n);
        }
        UiHandle.addSprite(this.arrSprMove, this.atlasScene, StrHandle.get("yun", sceneLevel, "-1"), 15.0f, 408.0f);
        UiHandle.addSprite(this.arrSprMove, this.atlasScene, StrHandle.get("yun", sceneLevel, "-2"), 284.0f, 364.0f);
        UiHandle.addSprite(this.arrSprMove, this.atlasScene, StrHandle.get("yun", sceneLevel, "-3"), 669.0f, 404.0f);
        UiHandle.addSprite(this.arrSprMove, this.atlasScene, StrHandle.get("yun", sceneLevel, "-1"), 954.0f, 369.0f);
        UiHandle.addSprite(this.arrSprMove, this.atlasScene, StrHandle.get("yun", sceneLevel, "-2"), 1269.0f, 406.0f);
        UiHandle.addSprite(this.arrSprMove, this.atlasScene, StrHandle.get("yun", sceneLevel, "-3"), 1646.0f, 369.0f);
        for (int j = 0; j < this.arrSprMove.size; ++j) {
            this.arrSprMove.get(j).translateX(MathUtils.random(-50.0f, -200.0f));
        }
    }
    
    public void act(final float n) {
        for (int i = 0; i < this.arrSprMove.size; ++i) {
            final Sprite sprite = this.arrSprMove.get(i);
            sprite.translateX(-0.525f);
            if (MyMethods.getRight(sprite) < -10.0f) {
                sprite.setX(sprite.getX() + 1900.0f);
            }
        }
    }
    
    public void draw() {
        final SpriteBatch batch = Global.batch;
        batch.begin();
        for (float n = -5.0f; n < 805.0f; n += this.sky.getRegionWidth() - 1) {
            batch.draw(this.sky, n, 0.0f);
        }
        for (float n2 = 0.0f; n2 < 805.0f; n2 += this.farHill.getRegionWidth() - 0.5f) {
            batch.draw(this.farHill, n2, 108.0f);
        }
        for (int i = 0; i < this.arrSprMove.size; ++i) {
            final Sprite sprite = this.arrSprMove.get(i);
            if (MyMethods.getRight(sprite) >= 0.0f && sprite.getX() < 800.0f) {
                sprite.draw(batch);
            }
        }
        for (int j = 0; j < this.arrSprStill.size; ++j) {
            final Sprite sprite2 = this.arrSprStill.get(j);
            if (MyMethods.getRight(sprite2) >= 0.0f && sprite2.getX() < 800.0f) {
                sprite2.draw(batch);
            }
        }
        for (float n3 = 0.0f; n3 < 805.0f; n3 += this.ground.getRegionWidth() - 1) {
            float n4;
            if (Global.sceneLevel == 4) {
                n4 = -15.0f;
            }
            else {
                n4 = -47.0f;
            }
            batch.draw(this.ground, n3, n4);
        }
        for (float n5 = 0.0f; n5 < 805.0f; n5 += this.front.getRegionWidth() - 1) {
            float n6;
            if (Global.sceneLevel == 4) {
                n6 = -5.0f;
            }
            else {
                n6 = -28.0f;
            }
            batch.draw(this.front, n5, n6);
        }
        batch.end();
    }
}
