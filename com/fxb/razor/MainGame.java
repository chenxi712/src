package com.fxb.razor;

import java.util.concurrent.*;
import com.fxb.razor.common.*;
import com.fxb.razor.screens.*;
import com.badlogic.gdx.*;

public class MainGame extends Game
{
    private Screen nextScreen;
    private ArrayBlockingQueue<Runnable> tasks;
    
    public MainGame() {
        this.nextScreen = null;
        this.tasks = new ArrayBlockingQueue<Runnable>(10);
    }
    
    private void executeRunnable() {
        while (!this.tasks.isEmpty()) {
            try {
                this.tasks.take().run();
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    @Override
    public void create() {
        Global.init();
        Assets.init();
        MusicHandle.init();
        SoundHandle.init();
        Control.loadForLoading();
        Global.manager.finishLoading();
        Global.game = this;
        Global.preScreen = Constant.NextScreen.Game_Start;
        Global.nextScreen = Constant.NextScreen.Start_Screen;
        this.setScreen(new LoadingScreen(this));
        this.nextScreen = null;
        new Thread(new SoundRunnable()).start();
    }
    
    @Override
    public void dispose() {
        super.dispose();
        Global.dispose();
        Assets.dispose();
        Gdx.app.log("buyMondTest", "game dispose!");
    }
    
    public void postTask(final Runnable runnable) {
        if (!this.tasks.offer(runnable)) {
            return;
        }
    }
    
    @Override
    public void render() {
        super.render();
        this.executeRunnable();
        if (this.nextScreen != null) {
            final Screen screen = this.getScreen();
            this.setScreen(this.nextScreen);
            this.nextScreen = null;
            screen.dispose();
        }
    }
    
    public void setNextScreen(final Screen nextScreen) {
        this.nextScreen = nextScreen;
    }
}
