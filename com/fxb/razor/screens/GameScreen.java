package com.fxb.razor.screens;

import com.fxb.razor.stages.*;
import com.fxb.razor.utils.Debug;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.*;
import com.fxb.razor.*;
import com.fxb.razor.common.*;

public class GameScreen extends BaseScreen
{
    private BackStage backStage;
    float curTime;
    private GameStage gameStage;
    float levelTime;
    
    public GameScreen(final MainGame mainGame) {
        super(mainGame);
        this.curTime = 0.0f;
        this.levelTime = 0.0f;
        this.backStage = new BackStage();
        this.gameStage = new GameStage();
        Global.gameScreen = this;
//        while (true) {
//            if (Global.maxGameLevelEasy > 2) {
//                BaseScreen.addFadeInAction(this.gameStage, 0.2f);
//                this.multiplexer.addProcessor(0, this.gameStage);
//                return;
//            }
//            continue;
//        }
        BaseScreen.addFadeInAction(this.gameStage, 0.2f);
        this.multiplexer.addProcessor(0, this.gameStage);
    }
    
    @Override
    public void dispose() {
        super.dispose();
        this.gameStage.clear();
        this.gameStage.dispose();
        Control.unloadForGame();
        System.gc();
    }
    
    @Override
    protected void keyBack() {
        super.keyBack();
        if (PlatformHandle.isShowingAd()) {
            PlatformHandle.closeAd();
            return;
        }
        this.gameStage.keyBack();
    }
    
    @Override
    public void pause() {
        super.pause();
        this.gameStage.pauseGame(false);
        PreferHandle.writeCommon();
    }
    
    @Override
    public void render(final float n) {
        super.render(n);
        if (Global.gameState == Constant.GameState.Game_On) {
            this.backStage.act(n);
        }
        this.gameStage.act(n);
        this.backStage.draw();
        this.gameStage.draw();
    }
    
    @Override
    public void resize(final int n, final int n2) {
        this.gameStage.setViewport(800.0f, 480.0f);
        super.resize(n, n2);
    }
    
    @Override
    public void resume() {
        super.resume();
        PreferHandle.readCommon();
    }
    
    public void screenClear() {
        this.backStage.myclear();
        this.gameStage.myclear();
    }
    
    @Override
    public void show() {
        super.show();
        PlatformHandle.closeFeatureView();
    }
}
