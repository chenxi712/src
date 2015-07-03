package com.gleejet.sun.stages;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.utils.*;
import com.badlogic.gdx.graphics.g2d.*;

public class BackStage
{
    private static final int cloudGapWidth = 60;
    private static final int gapWidth1 = -1;
    private static final int hillGapWidth = 60;
    private static final int houseGapWidth = 150;
    Array<Sprite> arrSprMove1;
    Array<Sprite> arrSprMove2;
    Array<Sprite> arrSprStill;
    TextureAtlas atlasScene;
    private TextureRegion cloud1;
    private TextureRegion cloud2;
    private TextureRegion cloud3;
    float curTime;
    private TextureRegion farHill;
    private TextureRegion front;
    GameStage gameStage;
    private TextureRegion ground;
    private TextureRegion hill1;
    private TextureRegion hill2;
    private TextureRegion hill3;
    private TextureRegion house1;
    private TextureRegion house2;
    private TextureRegion house3;
    float indicatorWidth;
    JsonValue jsonScene;
    Label labelLength;
    float levelTime;
    private TextureRegion sky;
    Sprite sprCoin;
    Sprite sprEnd;
    Sprite sprIndicator;
    Sprite sprLength;
    Sprite sprRate;
    Sprite sprSnail;
    Sprite sprTime;
    float startX;
    
    public BackStage() {
        this.arrSprMove1 = new Array<Sprite>();
        this.arrSprMove2 = new Array<Sprite>();
        this.arrSprStill = new Array<Sprite>();
        this.indicatorWidth = 270.0f;
        this.startX = 0.0f;
        this.gameStage = null;
        this.atlasScene = Global.manager.get("scene/scene" + Global.sceneLevel + ".pack", TextureAtlas.class);
        this.jsonScene = Global.manager.get("json/scene/backscene" + Global.sceneLevel + ".json", JsonValue.class).get(0);
        this.sprCoin = UiHandle.addSprite(this.arrSprStill, Assets.atlasUiGame, "jinbi", 298.0f, 434.0f);
        if (Global.isEndlessMode) {
            this.sprTime = UiHandle.addSprite(this.arrSprStill, Assets.atlasUiGame, "shijian", 418.0f, 434.0f);
            this.sprLength = UiHandle.addSprite(this.arrSprStill, Assets.atlasUiGame, "juli", 538.0f, 434.0f);
        }
        else {
            this.sprRate = UiHandle.addSprite(this.arrSprStill, Assets.atlasUiGame, "jindutiao", 409.0f, 433.0f);
            (this.sprIndicator = UiHandle.addSprite(this.arrSprStill, Assets.atlasUiGame, "nengliang", 441.0f, 442.0f)).setSize(0.0f, this.sprIndicator.getHeight());
            this.initRate();
        }
        this.setRegion();
        this.myclear();
    }
    
    private float getBossRateX(float n, final Constant.EnemyType enemyType) {
        switch (enemyType) {
            default: {
                n -= 500.0f;
                break;
            }
            case Bear1: {
                n -= 650.0f;
                break;
            }
            case Bear2: {
                n -= 650.0f;
                break;
            }
            case Wasp1: {
                n -= 500.0f;
                break;
            }
            case Wasp2: {
                n -= 500.0f;
                break;
            }
        }
        n /= Global.levelEndPosX;
        return 420.0f + this.indicatorWidth * n;
    }
    
    private float getBoxRateX(float n) {
        n /= Global.levelEndPosX;
        return 420.0f + this.indicatorWidth * n;
    }
    
    public static float getRight(final Sprite sprite) {
        return sprite.getX() + sprite.getWidth();
    }
    
    private void initRate() {
        this.sprEnd = UiHandle.addSprite(this.arrSprStill, Assets.atlasUiGame, "zhongdian", 425.0f + this.indicatorWidth, 431.0f);
        for (int i = 0; i < Global.arrBoxPos.size; ++i) {
            UiHandle.addSprite(this.arrSprStill, Assets.atlasUiGame, "baoxiang", this.getBoxRateX(Global.arrBoxPos.get(i)), 431.0f);
        }
        for (int j = 0; j < Global.arrBossPos.size; ++j) {
            UiHandle.addSprite(this.arrSprStill, Assets.atlasUiGame, "boss", this.getBossRateX(Global.arrBossPos.get(j), Global.arrBossType.get(j)), 431.0f);
        }
        this.sprSnail = UiHandle.addSprite(this.arrSprStill, Assets.atlasUiGame, "woniu", 406.0f, 431.0f);
    }
    
    private void setRegion() {
        final int sceneLevel = Global.sceneLevel;
        this.cloud1 = this.atlasScene.findRegion(StrHandle.get("yun", sceneLevel, "-1"));
        this.cloud2 = this.atlasScene.findRegion(StrHandle.get("yun", sceneLevel, "-2"));
        this.cloud3 = this.atlasScene.findRegion(StrHandle.get("yun", sceneLevel, "-3"));
        this.hill1 = this.atlasScene.findRegion(StrHandle.get("shan", sceneLevel, "-1"));
        this.hill2 = this.atlasScene.findRegion(StrHandle.get("shan", sceneLevel, "-2"));
        this.hill3 = this.atlasScene.findRegion(StrHandle.get("shan", sceneLevel, "-3"));
        this.house1 = this.atlasScene.findRegion(StrHandle.get("fangzhi", sceneLevel, "-1"));
        this.house2 = this.atlasScene.findRegion(StrHandle.get("fangzhi", sceneLevel, "-2"));
        this.house3 = this.atlasScene.findRegion(StrHandle.get("fangzhi", sceneLevel, "-3"));
        this.farHill = this.atlasScene.findRegion(StrHandle.get("yuanshan", sceneLevel));
        this.ground = this.atlasScene.findRegion(StrHandle.get("dimian", sceneLevel));
        this.sky = this.atlasScene.findRegion(StrHandle.get("tian", sceneLevel));
        this.front = this.atlasScene.findRegion(StrHandle.get("qianjing", sceneLevel));
    }
    
    public void act(final float n) {
        for (int i = 0; i < this.arrSprMove1.size; ++i) {
            final Sprite sprite = this.arrSprMove1.get(i);
            sprite.translate(-Constant.tranSpeed, 0.0f);
            if (getRight(sprite) < 0.0f) {
                sprite.setX(sprite.getX() + 3200.0f);
            }
        }
        for (int j = 0; j < this.arrSprMove2.size; ++j) {
            final Sprite sprite2 = this.arrSprMove2.get(j);
            sprite2.translate(-0.35f - Constant.tranSpeed, 0.0f);
            if (getRight(sprite2) < 0.0f) {
                sprite2.setX(sprite2.getX() + 3200.0f);
            }
        }
        Global.levelRate = Math.abs(this.startX) / (Global.levelEndPosX - 250.0f);
        if (Global.levelRate < 0.0f) {
            Global.levelRate = 0.0f;
        }
        else if (Global.levelRate > 1.0f) {
            Global.levelRate = 1.0f;
        }
        if (!Global.isEndlessMode) {
            this.sprIndicator.setSize(this.indicatorWidth * Global.levelRate, this.sprIndicator.getHeight());
            this.sprSnail.setX(425.0f + this.indicatorWidth * Global.levelRate);
        }
        this.curTime += n;
        this.startX -= Constant.tranSpeed;
        Global.tranLength = Math.abs(this.startX);
    }
    
    public void addSprite(final String s, final float n, final float n2) {
        if (s == null || s.contains("yuanshan") || s.contains("dimian") || s.contains("qianjing")) {
            return;
        }
        final Sprite sprite = new Sprite(this.atlasScene.findRegion(s));
        sprite.setPosition(n, n2);
        if (s.contains("yun")) {
            this.arrSprMove2.add(sprite);
            return;
        }
        this.arrSprMove1.add(sprite);
    }
    
    public void draw() {
        final float deltaY = Global.deltaY;
        final SpriteBatch batch = Global.batch;
        batch.begin();
        if (Global.bossLevelState != Constant.BossLevelState.Boss_Start) {
            batch.setColor(Color.WHITE);
        }
        for (float n = -5.0f; n < 805.0f; n += this.sky.getRegionWidth() - 1) {
            batch.draw(this.sky, n, 0.0f);
        }
        for (float startX = this.startX; startX < 805.0f; startX += this.farHill.getRegionWidth() - 0.5f) {
            if (startX > -800.0f) {
                batch.draw(this.farHill, startX, 108.0f + deltaY);
            }
        }
        for (int i = 0; i < this.arrSprMove1.size; ++i) {
            final Sprite sprite = this.arrSprMove1.get(i);
            if (getRight(sprite) >= 0.0f && sprite.getX() < 800.0f) {
                final float y = sprite.getY();
                sprite.setY(y + deltaY);
                sprite.draw(batch);
                sprite.setY(y);
            }
        }
        for (int j = 0; j < this.arrSprMove2.size; ++j) {
            final Sprite sprite2 = this.arrSprMove2.get(j);
            if (getRight(sprite2) >= 0.0f && sprite2.getX() < 800.0f) {
                final float y2 = sprite2.getY();
                sprite2.setY(y2 + deltaY);
                sprite2.draw(batch);
                sprite2.setY(y2);
            }
        }
        for (float startX2 = this.startX; startX2 < 805.0f; startX2 += this.ground.getRegionWidth() - 1) {
            float n2 = -47.0f;
            if (Global.sceneLevel == 4) {
                n2 = -15.0f;
            }
            if (startX2 > this.ground.getRegionWidth() - 800.0f) {
                batch.draw(this.ground, startX2, n2 + deltaY);
            }
        }
        for (float startX3 = this.startX; startX3 < 805.0f; startX3 += this.front.getRegionWidth() - 1) {
            float n3 = -28.0f;
            if (Global.sceneLevel == 4) {
                n3 = -5.0f;
            }
            if (startX3 > this.front.getRegionWidth() - 800.0f) {
                batch.draw(this.front, startX3, n3 + deltaY);
            }
        }
        for (int k = 0; k < this.arrSprStill.size; ++k) {
            this.arrSprStill.get(k).draw(batch);
        }
        if (Global.isEndlessMode) {
            FontHandle.draw(batch, Constant.FontType.common, StrHandle.get((int)Math.abs(this.curTime)), this.sprTime.getX() + 35.0f, this.sprTime.getY() + 6.0f);
            FontHandle.draw(batch, Constant.FontType.common, StrHandle.get((int)(Math.abs(this.startX) / 5.0f)), this.sprLength.getX() + 35.0f, this.sprLength.getY() + 6.0f);
        }
        batch.end();
    }
    
    public void myclear() {
        this.startX = 0.0f;
        this.readJson();
    }
    
    public void readJson() {
        this.arrSprMove1.clear();
        this.arrSprMove2.clear();
        int n = 0;
        while (true) {
            final JsonValue value = this.jsonScene.get(n);
            if (value == null) {
                break;
            }
            final String string = value.getString("name");
            final float float1 = value.getFloat("x");
            float float2 = value.getFloat("y");
            if (Global.sceneLevel == 2) {
                float2 -= 18.0f;
            }
            this.addSprite(string, float1, float2);
            ++n;
        }
    }
    
    public void setGameStage(final GameStage gameStage) {
        this.gameStage = gameStage;
    }
}
