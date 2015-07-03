package com.gleejet.sun.roles.boss;

import com.badlogic.gdx.graphics.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.roles.*;
import com.gleejet.sun.utils.ui.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;

public abstract class BaseBoss extends FlashEnemy
{
    float bossAttackTime;
    protected Color bossColor;
    protected Constant.BossState bossState;
    protected MyImage imgWarning;
    protected boolean isCall1;
    protected boolean isCall2;
    protected boolean isCall3;
    float soundTime;
    
    public BaseBoss() {
        this.bossAttackTime = 0.0f;
        this.soundTime = 3.0f;
        this.isCall3 = false;
        this.isCall2 = false;
        this.isCall1 = false;
        this.imgWarning = UiHandle.createWarning();
    }
    
    @Override
    public void BeAttacked(final float n) {
        super.BeAttacked(n);
        this.bossAttackTime = 2.0f;
    }
    
    @Override
    public void Die() {
        super.Die();
        SoundHandle.playForBoss();
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkBossAttackTime(n);
        this.checkBossCall();
        this.checkPlaySound(n);
    }
    
    protected void checkBossAttackTime(final float n) {
        this.bossAttackTime -= n;
    }
    
    protected void checkBossCall() {
        final float n = this.currentHp / this.maxHp;
        if (!this.isCall1 && n < 0.8f) {
            this.isCall1 = true;
            Global.bossCallState = 1;
            this.flashPlayers[0].SetTimeScale(1.5f);
            this.setColor(this.oriColor = new Color(1.0f, 0.7f, 0.7f, 1.0f));
        }
        if (!this.isCall2 && n < 0.5f) {
            this.isCall2 = true;
            Global.bossCallState = 2;
            this.flashPlayers[0].setPlayPercent(2.0f);
            this.setColor(this.oriColor = new Color(1.0f, 0.55f, 0.55f, 1.0f));
        }
        if (!this.isCall3 && n < 0.2f) {
            this.isCall3 = true;
            Global.bossCallState = 3;
            this.flashPlayers[0].setPlayPercent(3.0f);
            this.setColor(this.oriColor = new Color(1.0f, 0.4f, 0.4f, 1.0f));
        }
    }
    
    protected void checkPlaySound(final float n) {
        this.soundTime -= n;
        if (this.soundTime <= 0.0f) {
            SoundHandle.playForBoss();
            this.soundTime = MathUtils.random(2.5f, 3.5f);
        }
    }
    
    @Override
    public void deadHandle() {
        this.remove();
        Global.bossLevelState = Constant.BossLevelState.Boss_Dead;
        Constant.tranSpeed = 0.35f;
    }
    
    public int getFrame() {
        return this.flashPlayers[this.curIndex].getFrameIndex();
    }
    
    @Override
    public void setAttackTime() {
        this.attackTime = 0.08f;
        this.bossColor = this.getColor();
        this.setColor(Color.GRAY);
    }
    
    public void showWarning() {
        UiHandle.startShake(this.imgWarning, 0.5f);
    }
    
    public void stopWarning() {
        UiHandle.stopShake(this.imgWarning);
    }
}
