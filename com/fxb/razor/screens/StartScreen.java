package com.fxb.razor.screens;

import com.fxb.razor.utils.ui.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.*;
import com.fxb.razor.utils.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.*;
import com.fxb.razor.common.*;
import com.fxb.razor.roles.Turtle;
import com.fxb.razor.stages.dialogs.*;

public class StartScreen extends BaseScreen
{
    TextureAtlas atlasStart;
    TextureAtlas atlasStartBg;
    float backTime;
    private ButtonListener btnListener;
    DialogBonus dialogBonus;
    DialogExit dialogExit;
    DialogSpeSold dialogSpeSold;
    DialogStore dialogStore;
    MyImage imgEndless;
    MyImage imgFlag;
    MyImage imgLoginReward;
    MyImage imgLogo;
    MyImage imgMore;
    MyImage imgRate;
    MyImage imgSpeSold;
    MyImage imgStory;
    MondLackDialog mondLackDialog;
    TextureRegion regionBg;
    Stage stage;
    
    public StartScreen(final MainGame mainGame) {
        super(mainGame);
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (!this.isDown) {
                    return;
                }
                final Actor listenerActor = inputEvent.getListenerActor();
                if (listenerActor == StartScreen.this.imgRate) {
                    StartScreen.this.rateHandle();
                }
                else if (listenerActor == StartScreen.this.imgMore) {
                    StartScreen.this.moreHandle();
                }
                else if (listenerActor == StartScreen.this.imgStory) {
                    Global.preScreen = Constant.NextScreen.Start_Screen;
                    Global.nextScreen = Constant.NextScreen.Level_Screen;
                    BaseScreen.addFadeOutAction(StartScreen.this.stage, 0.3f);
                }
                else if (listenerActor == StartScreen.this.imgLoginReward) {
                    StartScreen.this.loginRewardHandle();
                }
                else if (listenerActor == StartScreen.this.imgSpeSold) {
                    StartScreen.this.speSoldHandle();
                }
                else if (listenerActor == StartScreen.this.imgEndless) {
                    Global.isEndlessMode = true;
                    Global.sceneLevel = Math.min(5, (Global.maxGameLevelEasy - 1) / 10 + 1);
                    Global.nextScreen = Constant.NextScreen.Weapon_Screen;
                    Global.preScreen = Constant.NextScreen.Start_Screen;
                    BaseScreen.addFadeOutAction(StartScreen.this.stage, 0.3f);
                }
                SoundHandle.playForButton2();
            }
        };
        this.atlasStart = Global.manager.get("ui/ui_start.pack", TextureAtlas.class);
        this.atlasStartBg = Global.manager.get("ui/ui_start_bg.pack", TextureAtlas.class);
        this.stage = new Stage(800.0f, 480.0f, false, Global.batch);
        this.init();
        BaseScreen.addFadeInAction(this.stage, 0.3f);
        this.multiplexer.addProcessor(0, this.stage);
        MusicHandle.playForMenu();
    }
    
    private void init() {
        for (float n = 0.0f; n < 800.0f; n += UiHandle.addItem(this.stage.getRoot(), this.atlasStartBg, "tiankong", n, 0.0f).getWidth() - 1.0f) {}
        UiHandle.addItem(this.stage.getRoot(), this.atlasStartBg, "yun1", 15.0f, 408.0f, null, "yun1");
        UiHandle.addItem(this.stage.getRoot(), this.atlasStartBg, "yun2", 284.0f, 364.0f, null, "yun2");
        UiHandle.addItem(this.stage.getRoot(), this.atlasStartBg, "yun3", 669.0f, 404.0f, null, "yun3");
        UiHandle.addItem(this.stage.getRoot(), this.atlasStartBg, "yun1", 954.0f, 369.0f, null, "yun1");
        UiHandle.addItem(this.stage.getRoot(), this.atlasStartBg, "yun2", 1269.0f, 406.0f, null, "yun2");
        UiHandle.addItem(this.stage.getRoot(), this.atlasStartBg, "yun3", 1646.0f, 369.0f, null, "yun3");
        UiHandle.addItem(this.stage.getRoot(), this.atlasStartBg, "shan", 0.0f, 0.0f);
        UiHandle.addItem(this.stage.getRoot(), this.atlasStartBg, "qianjing", 0.0f, 0.0f);
        this.initFlag();
//        UiHandle.addItem(this.stage.getRoot(), this.atlasStartBg, "mengban", 0.0f, 0.0f);
        (this.imgLogo = UiHandle.addItem(this.stage.getRoot(), this.atlasStart, "logo", 7.0f, 340.0f)).setY(630.0f);
        this.imgMore = UiHandle.addItem(this.stage.getRoot(), this.atlasStart, "more", 13.0f, 13.0f, this.btnListener);
        this.imgStory = UiHandle.addItem(this.stage.getRoot(), this.atlasStart, "story", 610.0f, 13.0f, this.btnListener);
        (this.imgEndless = UiHandle.addItem(this.stage.getRoot(), this.atlasStart, "endless", 410.0f, 13.0f, this.btnListener)).setVisible(Global.maxGameLevelEasy > 10);
        this.imgSpeSold = UiHandle.addItem(this.stage.getRoot(), this.atlasStart, "xianshitemai", 580.0f, 403.0f, this.btnListener);
        this.imgLoginReward = UiHandle.addItem(this.stage.getRoot(), this.atlasStart, "denglujiangli", 653.0f, 403.0f, this.btnListener);
        this.imgRate = UiHandle.addItem(this.stage.getRoot(), this.atlasStart, "rate", 725.0f, 403.0f, this.btnListener);
        this.checkSpeSold();
        this.shakeSpeSold();
        this.dialogBonus = new DialogBonus();
        this.imgSpeSold.setY(680.0f);
        this.imgLoginReward.setY(680.0f);
        this.imgRate.setY(680.0f);
        this.imgMore.setY(-200.0f);
        this.imgStory.setY(-200.0f);
        this.imgEndless.setY(-200.0f);
        this.imgLogo.addAction(Actions.sequence(Actions.moveTo(7.0f, 340.0f, 0.7f, MyInterpolation.shake1), Actions.run(new Runnable() {
            @Override
            public void run() {
                StartScreen.this.imgSpeSold.addAction(Actions.moveTo(580.0f, 403.0f, 0.5f, Interpolation.swingOut));
                StartScreen.this.imgLoginReward.addAction(Actions.moveTo(653.0f, 403.0f, 0.5f, Interpolation.swingOut));
                StartScreen.this.imgRate.addAction(Actions.moveTo(725.0f, 403.0f, 0.5f, Interpolation.swingOut));
                StartScreen.this.imgMore.addAction(Actions.moveTo(13.0f, 13.0f, 0.5f, Interpolation.swingOut));
                StartScreen.this.imgStory.addAction(Actions.moveTo(610.0f, 13.0f, 0.5f, Interpolation.swingOut));
                StartScreen.this.imgEndless.addAction(Actions.moveTo(410.0f, 13.0f, 0.5f, Interpolation.swingOut));
            }
        })));
        this.dialogExit = new DialogExit(this);
        this.mondLackDialog = new MondLackDialog(this);
        this.dialogSpeSold = new DialogSpeSold(this, this.mondLackDialog);
        this.dialogStore = new DialogStore(this);
        
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.absolute("/system/fonts/DroidSansFallback.ttf"));
//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fangsong.ttf"));
//        BitmapFont font15 = generator.generateFont(30); 
        FreeTypeBitmapFontData data = generator.generateData(25, "陈Xi", false);
        BitmapFont font15 = new BitmapFont(data, data.getTextureRegion(), false);
		generator.dispose(); 
		
        Label.LabelStyle style = new Label.LabelStyle(font15, Color.YELLOW);
//        AutoLineLabel l = new AutoLineLabel("陈Xi", style, 50);
        Label l = new Label("陈西", style);
        l.setPosition(200,  200);
        stage.addActor(l);
    }
    
    private void initFlag() {
        MyMethods.setActorOrigin(this.imgFlag = UiHandle.addItem(this.stage.getRoot(), this.atlasStartBg, "qizhi", 120.0f, 274.0f), 0.5f, 0.7f);
        this.imgFlag.setRotation(-4.0f);
        this.imgFlag.addAction(Actions.forever(Actions.sequence(Actions.rotateTo(13.0f, 0.7f), Actions.rotateTo(-4.0f, 0.7f))));
    }
    
    public static boolean isBonusValid() {
        return (PlatformHandle.getServerTime() - Global.lastLoginRewardTime) / 1000L > 0L;
    }
    
    private void rateHandle() {
        PlatformHandle.goToRate();
    }
    
    private void shakeSpeSold() {
        this.imgSpeSold.setOrigin(this.imgSpeSold.getWidth() / 2.0f, this.imgSpeSold.getHeight() / 2.0f);
        this.imgSpeSold.addAction(Actions.forever(Actions.sequence(Actions.repeat(5, Actions.sequence(Actions.scaleTo(1.0f - 0.08f, 0.98f + 0.08f, 0.15f), Actions.scaleTo(1.02f + 0.08f, 1.0f - 0.08f, 0.15f), Actions.scaleTo(1.0f, 1.0f, 0.75f * 0.15f))), Actions.delay(3.5f))));
    }
    
    private void speSoldHandle() {
        DialogHandle.openDialog(this.stage, this.dialogSpeSold, 0.45f);
        Global.isSpeSoldShowing = true;
    }
    
    public void checkSpeSold() {
        this.imgSpeSold.setVisible(Global.isSpesoldValid());
    }
    
    @Override
    public void dispose() {
        super.dispose();
        this.stage.dispose();
    }
    
    public void fadeOut() {
        BaseScreen.addFadeOutAction(this.stage, 0.2f);
    }
    
    @Override
    protected void keyBack() {
        super.keyBack();
        if (this.curScreenTime >= 1.3f) {
            if (PlatformHandle.isShowingAd() && this.dialogExit.getParent() == null) {
                PlatformHandle.closeAd();
                return;
            }
            if (this.dialogBonus.getParent() != null) {
                this.dialogBonus.backHandle();
                return;
            }
            if (this.dialogStore.getParent() != null) {
                this.dialogStore.keyBack();
                return;
            }
            if (this.dialogSpeSold.getParent() != null) {
                this.dialogSpeSold.keyBack();
                return;
            }
            if (this.dialogExit.getParent() != null) {
                if (this.backTime <= 0.0f) {
                    this.dialogExit.noHandle();
                    this.backTime = 0.5f;
                }
            }
            else if (this.backTime <= 0.0f) {
                this.dialogExit.openDialog(this.stage, 0.5f);
                PlatformHandle.showAdSmall();
                this.backTime = 0.5f;
            }
        }
    }
    
    public void loginRewardHandle() {
        this.dialogBonus.checkDailyBonus();
        this.dialogBonus.setY(480.0f);
        this.dialogBonus.addAction(Actions.sequence(Actions.moveTo(this.dialogBonus.getX(), 81.0f, 0.55f, MyInterpolation.shake2), Actions.run(new Runnable() {
            @Override
            public void run() {
            }
        })));
        this.stage.addActor(this.dialogBonus.getShade());
        this.stage.addActor(this.dialogBonus);
    }
    
    public void moreHandle() {
        PlatformHandle.showMore();
    }
    
    public void openStore(final int n) {
        this.dialogStore.selectItem(1);
        this.dialogStore.openDialog(this.stage);
    }
    
    @Override
    public void render(final float n) {
        super.render(n);
        for (int i = 0; i < this.stage.getRoot().getChildren().size; ++i) {
            final Actor actor = this.stage.getRoot().getChildren().get(i);
            final String name = actor.getName();
            if (name != null && name.contains("yun")) {
                actor.translate(-0.7f, 0.0f);
                if (actor.getRight() < -10.0f) {
                    actor.setX(actor.getX() + 1950.0f);
                }
                if (actor.getX() > 810.0f) {
                    actor.setVisible(false);
                }
                else {
                    actor.setVisible(true);
                }
            }
        }
        this.stage.act();
        this.stage.draw();
        this.showFps(n);
        if (this.backTime > 0.0f) {
            this.backTime -= n;
        }
    }
    
    @Override
    public void show() {
        super.show();
        PlatformHandle.closeFeatureView();
        if (Global.preScreen == Constant.NextScreen.Game_Start && Global.gameInitNum > 1) {
            PlatformHandle.showAdBig();
        }
        if (isBonusValid() && Global.gameInitNum >= 1) {
            MyMethods.delayRun(this.stage.getRoot(), new Runnable() {
                @Override
                public void run() {
                    StartScreen.this.loginRewardHandle();
                }
            }, 1.0f);
        }
        if (Global.isSpesoldValid() && Global.isSpeSoldShowing) {
            MyMethods.delayRun(this.stage.getRoot(), new Runnable() {
                @Override
                public void run() {
                    DialogHandle.openDialog(StartScreen.this.stage, StartScreen.this.dialogSpeSold, 0.45f);
                }
            }, 1.0f);
        }
        Global.setUseRGBA4444(true);
    }
}
