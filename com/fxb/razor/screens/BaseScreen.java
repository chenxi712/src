package com.fxb.razor.screens;

import com.fxb.razor.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.fxb.razor.common.*;
import com.fxb.razor.utils.ui.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.*;

public abstract class BaseScreen extends ScreenAdapter
{
    protected float curScreenTime;
    float fpsTime;
    protected MainGame game;
    private InputAdapter keyAdapter;
    float logTime;
    protected InputMultiplexer multiplexer;
    
    public BaseScreen(final MainGame game) {
        this.keyAdapter = new InputAdapter() {
            @Override
            public boolean keyUp(final int n) {
                if (n == 4) {
                    BaseScreen.this.keyBack();
                }
                else if (n == 82) {
                    BaseScreen.this.keyMenu();
                }
                else if (n == 52) {
                    BaseScreen.this.keyBack();
                }
                return super.keyUp(n);
            }
        };
        this.game = game;
        (this.multiplexer = new InputMultiplexer()).addProcessor(this.keyAdapter);
    }
    
    public static void addFadeInAction(final Stage stage, final float n) {
        final MyShade myShade = new MyShade();
        myShade.fadeInRemove(n);
        myShade.setTouchable(Touchable.disabled);
        stage.addActor(myShade);
    }
    
    public static void addFadeOutAction(final Stage stage, final float n) {
        final MyShade myShade = new MyShade();
        myShade.fadeOutRemove(n);
        myShade.setTouchable(Touchable.disabled);
        stage.addActor(myShade);
        stage.getRoot().addAction(Actions.sequence(Actions.delay(0.05f + n), Actions.run(new Runnable() {
            @Override
            public void run() {
                Global.game.setNextScreen(new LoadingScreen(Global.game));
            }
        })));
    }
    
    public static void addInstruction(final Stage stage, final Actor actor) {
        MyShade.createShade(stage);
        actor.toFront();
        ArrowRect.createArrowRect(stage, actor);
        UiHandle.createHand(stage, actor);
    }
    
    public static void setCircleTarget(final MyImage myImage, final Actor actor) {
        final Vector2 vector2 = new Vector2();
        actor.localToStageCoordinates(vector2);
        myImage.setOrigin(myImage.getWidth() / 2.0f, myImage.getHeight() / 2.0f);
        myImage.setPosition(vector2.x + (actor.getWidth() - myImage.getWidth()) / 2.0f, vector2.y + (actor.getHeight() - myImage.getHeight()) / 2.0f);
        myImage.setTouchable(Touchable.disabled);
        myImage.toFront();
    }
    
    public MyImage addItem(final Group group, final TextureAtlas textureAtlas, final String s, final float n, final float n2) {
        return UiHandle.addItem(group, textureAtlas, s, n, n2);
    }
    
    public MyImage addItem(final Group group, final TextureAtlas textureAtlas, final String s, final float n, final float n2, final InputListener inputListener) {
        return UiHandle.addItem(group, textureAtlas, s, n, n2, inputListener);
    }
    
    protected void addTopToCenterAction(final Stage stage, final float n) {
        stage.getRoot().setY(480.0f);
        stage.getRoot().addAction(Actions.moveTo(0.0f, 0.0f, n, Interpolation.swingIn));
    }
    
    @Override
    public void dispose() {
        super.dispose();
    }
    
    protected void keyBack() {
    }
    
    protected void keyMenu() {
    }
    
    protected void logFps(final float n) {
        this.logTime += n;
        if (this.logTime >= 1.0f) {
            this.logTime = 0.0f;
        }
    }
    
    @Override
    public void render(final float n) {
        super.render(n);
        Gdx.gl.glClear(16384);
        this.curScreenTime += n;
    }
    
    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(this.multiplexer);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setCatchMenuKey(true);
    }
    
    protected void showFps(final float n) {
        Global.batch.totalRenderCalls = 0;
    }
}
