package com.gleejet.sun.utils.ui;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.screens.*;
import com.gleejet.sun.stages.*;

public class Coin extends MyImage
{
    float coinNum;
    
    public static void addCoin(final Group group, final float n, final float n2, final float n3, final int n4, final float n5) {
        for (int i = 0; i < n4; ++i) {
            final Coin coin = Pools.obtain(Coin.class);
            coin.setCoinNum(n5 / n4);
            coin.setRegion(Assets.atlasUiGame.findRegion("jinbi"));
            coin.setOrigin(coin.getWidth() / 2.0f, coin.getHeight() / 2.0f);
            coin.setScale(MathUtils.random(0.5f, 0.8f));
            final float n6 = n3 * MathUtils.random(0.45f, 1.0f);
            final float n7 = 360.0f / n4;
            final float n8 = i * n7 + MathUtils.random(-n7 / 3.0f, n7 / 3.0f);
            coin.setPosition(n, n2);
            final MoveToAction moveTo = Actions.moveTo(MathUtils.cosDeg(n8) * n6 + n, MathUtils.sinDeg(n8) * n6 + n2, 0.2f);
            final DelayAction delay = Actions.delay(0.03f);
            final MoveToAction moveTo2 = Actions.moveTo(300.0f, 434.0f, MathUtils.random(0.45f, 0.65f), Interpolation.pow2In);
            final int tmp = i;
            final RunnableAction run = Actions.run(new Runnable() {
            	final boolean val$isAddSound = tmp == 0;
            	
                @Override
                public void run() {
                    Global.totalCoinNum += coin.getCoinNum();
                    Global.coinGet += coin.getCoinNum();
                    Global.inCoinGet = Math.round(Global.coinGet);
                    ((GameStage)coin.getStage()).coinChanged();
                    coin.remove();
                    Pools.free(coin);
                    if (this.val$isAddSound) {
                        SoundHandle.playForGold();
                    }
                }
            });
            coin.clearActions();
            coin.addAction(Actions.sequence(moveTo, delay, moveTo2, run));
            group.addActor(coin);
        }
    }
    
    public static void addCoin(final Group group, final float n, final float n2, final float n3, final int n4, final float n5, final LevelSmallScreen levelSmallScreen) {
        for (int i = 0; i < n4; ++i) {
            final Coin coin = Pools.obtain(Coin.class);
            coin.setCoinNum(n5 / n4);
            coin.setRegion(Assets.atlasUiGame.findRegion("jinbi"));
            coin.setOrigin(coin.getWidth() / 2.0f, coin.getHeight() / 2.0f);
            coin.setScale(MathUtils.random(0.5f, 0.8f));
            final float n6 = n3 * MathUtils.random(0.45f, 1.0f);
            final float n7 = 360.0f / n4;
            final float n8 = i * n7 + MathUtils.random(-n7 / 3.0f, n7 / 3.0f);
            coin.setPosition(n, n2);
            final MoveToAction moveTo = Actions.moveTo(MathUtils.cosDeg(n8) * n6 + n, MathUtils.sinDeg(n8) * n6 + n2, 0.2f);
            final DelayAction delay = Actions.delay(0.03f);
            final MoveToAction moveTo2 = Actions.moveTo(295.0f, 425.0f, MathUtils.random(0.45f, 0.65f), Interpolation.pow2In);
            final int tmp = i;
            final RunnableAction run = Actions.run(new Runnable() {
                final /* synthetic */ boolean val$isAddSound = tmp == 0;
                
                @Override
                public void run() {
                    Global.totalCoinNum += coin.getCoinNum();
                    levelSmallScreen.refresh();
                    coin.remove();
                    Pools.free(coin);
                    if (this.val$isAddSound) {
                        SoundHandle.playForGold();
                    }
                }
            });
            coin.clearActions();
            coin.addAction(Actions.sequence(moveTo, delay, moveTo2, run));
            group.addActor(coin);
        }
    }
    
    public static void addMond(final Group group, final float n, final float n2, final float n3, final int n4, final float n5, final LevelSmallScreen levelSmallScreen) {
        for (int i = 0; i < n4; ++i) {
            final Coin coin = Pools.obtain(Coin.class);
            coin.setCoinNum(n5 / n4);
            coin.setRegion(Assets.atlasUiGame.findRegion("zuanshi"));
            coin.setOrigin(coin.getWidth() / 2.0f, coin.getHeight() / 2.0f);
            coin.setScale(MathUtils.random(0.5f, 0.8f));
            final float n6 = n3 * MathUtils.random(0.45f, 1.0f);
            final float n7 = 360.0f / n4;
            final float n8 = i * n7 + MathUtils.random(-n7 / 3.0f, n7 / 3.0f);
            coin.setPosition(n, n2);
            final MoveToAction moveTo = Actions.moveTo(MathUtils.cosDeg(n8) * n6 + n, MathUtils.sinDeg(n8) * n6 + n2, 0.2f);
            final DelayAction delay = Actions.delay(0.03f);
            final MoveToAction moveTo2 = Actions.moveTo(470.0f, 425.0f, MathUtils.random(0.45f, 0.65f), Interpolation.pow2In);
            final int tmp = i;
            final RunnableAction run = Actions.run(new Runnable() {
                final /* synthetic */ boolean val$isAddSound = tmp == 0;
                
                @Override
                public void run() {
                    Global.totalMondNum += coin.getCoinNum();
                    levelSmallScreen.refresh();
                    coin.remove();
                    Pools.free(coin);
                    if (this.val$isAddSound) {
                        SoundHandle.playForGold();
                    }
                }
            });
            coin.clearActions();
            coin.addAction(Actions.sequence(moveTo, delay, moveTo2, run));
            group.addActor(coin);
        }
    }
    
    public float getCoinNum() {
        return this.coinNum;
    }
    
    public void setCoinNum(final float coinNum) {
        this.coinNum = coinNum;
    }
}
