package com.fxb.razor.screens;

import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.*;
import com.fxb.razor.*;

public class LoadingScreen extends BaseScreen
{
    float curTime;
    boolean isLevelClear;
    boolean isLoad;
    float loadTime;
    private TextureRegion regionBar1;
    private TextureRegion regionBar2;
    private TextureRegion regionBar3;
    private TextureRegion regionBorder;
    private TextureRegion regionLoad;
    private TextureRegion regionLoadBg;
    private TextureRegion regionSnail;
    
    public LoadingScreen(final MainGame mainGame) {
        super(mainGame);
        this.curTime = 0.0f;
        this.isLevelClear = false;
        this.isLoad = false;
        this.loadTime = 0.0f;
        final TextureAtlas textureAtlas = Assets.atlasLoad = Global.manager.get("data/load.pack", TextureAtlas.class);
        Assets.regionWhite1 = textureAtlas.findRegion("white");
        this.regionSnail = textureAtlas.findRegion("tupian");
        this.regionLoad = textureAtlas.findRegion("loading");
        this.regionLoadBg = textureAtlas.findRegion("di");
        this.regionBorder = textureAtlas.findRegion("waikuang");
        this.regionBar1 = textureAtlas.findRegion("tiao1");
        this.regionBar2 = textureAtlas.findRegion("tiao2");
        (this.regionBar3 = new TextureRegion(this.regionBar1)).flip(true, false);
        this.loadTime = 0.0f;
        this.isLoad = true;
        RazorActivity.InitServerTime();
        this.loadForScreen();
    }
    
    private void loadForScreen() {
        if (Global.nextScreen == Constant.NextScreen.Start_Screen) {
            Control.loadForStart();
        }
        else {
            if (Global.nextScreen == Constant.NextScreen.Game_Screen) {
                Control.loadForGame();
                return;
            }
            if (Global.nextScreen == Constant.NextScreen.Level_Small && Global.preScreen == Constant.NextScreen.Game_Screen) {
                Control.loadForMenu();
            }
        }
    }
    
    @Override
    public void dispose() {
        super.dispose();
    }
    
    public float getLen(final float n) {
        float n2 = 244.0f;
        if (n <= 0.9f) {
            n2 = 244.0f * (n - 0.1f) / 0.8f;
        }
        return n2;
    }
    
    @Override
    public void render(final float n) {
        Gdx.gl.glClear(16384);
        if (this.isLoad && Global.manager.update()) {
            if (Global.nextScreen == Constant.NextScreen.Start_Screen) {
                Assets.setAtlas();
                this.game.setNextScreen(new StartScreen(this.game));
            }
            else if (Global.nextScreen == Constant.NextScreen.Load_Enemy) {
                Global.nextScreen = Constant.NextScreen.Game_Screen;
            }
            else if (Global.nextScreen == Constant.NextScreen.Game_Screen) {
                if (!this.isLevelClear) {
                    Global.createEnemyArrayState = 1;
                    this.isLevelClear = true;
                }
                if (Global.createEnemyArrayState == 2) {
                    if (Global.isGameAgain) {
                        if (this.curTime > 2.0f) {
                            this.game.setNextScreen(new GameScreen(this.game));
                        }
                    }
                    else {
                        this.game.setNextScreen(new GameScreen(this.game));
                    }
                }
                this.curTime += Gdx.graphics.getDeltaTime();
            }
            else if (Global.nextScreen == Constant.NextScreen.Level_Screen) {
                this.game.setNextScreen(new LevelScreen(this.game));
            }
            else if (Global.nextScreen == Constant.NextScreen.Level_Small) {
                Assets.setAtlas();
                this.game.setNextScreen(new LevelSmallScreen(this.game));
            }
            else if (Global.nextScreen == Constant.NextScreen.Weapon_Screen) {
                this.game.setNextScreen(new WeaponScreen(this.game));
            }
            else if (Global.nextScreen == Constant.NextScreen.Weapon_Enhance) {
                this.game.setNextScreen(new WeaponEnhanceScreen(this.game));
            }
        }
        if (Global.preScreen == Constant.NextScreen.Game_Start || Global.preScreen == Constant.NextScreen.Game_Screen || Global.nextScreen == Constant.NextScreen.Game_Screen) {
            Global.batch.begin();
            Global.batch.draw(this.regionSnail, 276.0f, 131.0f);
            Global.batch.draw(this.regionLoadBg, 234.0f, 64.0f);
            Global.batch.draw(this.regionBorder, 259.0f, 82.0f);
            float progress = Global.manager.getProgress();
            if (Global.nextScreen != Constant.NextScreen.Game_Screen) {}
            if (Global.isGameAgain) {
                progress = this.curTime / 2.0f;
            }
            if (progress >= 0.05f) {
                Global.batch.draw(this.regionBar1, 259.0f, 85.0f);
            }
            if (progress > 0.1f) {
                Global.batch.draw(this.regionBar2, 277.0f, 85.0f, this.getLen(progress), this.regionBar2.getRegionHeight());
            }
            if (progress >= 0.95f) {
                Global.batch.draw(this.regionBar3, 521.0f, 85.0f);
            }
            Global.batch.draw(this.regionLoad, 336.0f, 74.0f);
            Global.batch.end();
        }
        if (Global.nextScreen != Constant.NextScreen.Start_Screen) {
            this.showFps(n);
        }
    }
    
    @Override
    public void resize(final int n, final int n2) {
    }
    
    @Override
    public void show() {
        super.show();
        PlatformHandle.closeFeatureView();
    }
}
