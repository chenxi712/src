package com.fxb.razor.objects.subgun;

import com.fxb.razor.utils.ui.*;
import com.fxb.razor.flash.*;
import com.fxb.razor.utils.action.*;
import com.fxb.razor.stages.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.common.*;
import com.fxb.razor.roles.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;

public abstract class BaseSubGun extends Actor
{
    protected AnimationActor actorEffect;
    protected AnimationActor actorEffect2;
    protected int attackEnd;
    protected float attackInterval;
    protected int attackStart;
    int count;
    protected int curIndex;
    protected float curTime;
    protected int effectFrame;
    protected FlashPlayer[] flashPlayers;
    protected int idleEnd;
    protected int idleStart;
    protected boolean isAttack;
    protected boolean isEffectStart;
    float lastAngle;
    protected float lastAttackTime;
    InputListener listener;
    protected Vector2 pos;
    protected float scale;
    protected Constant.SubGunState state;
    protected final String strAtlas;
    protected SubGunIcon subGunIcon;
    
    public BaseSubGun() {
        this.scale = 0.33333334f;
        this.curIndex = 0;
        this.pos = new Vector2();
        this.curTime = 0.0f;
        this.lastAttackTime = -100.0f;
        this.attackInterval = 3.0f;
        this.state = Constant.SubGunState.Idle;
        this.subGunIcon = new SubGunIcon();
        this.isAttack = false;
        this.isEffectStart = false;
        this.strAtlas = "game/archer.pack";
        this.lastAngle = 0.0f;
        this.count = 0;
        this.listener = new InputListener() {
            @Override
            public boolean touchDown(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                final Actor listenerActor = inputEvent.getListenerActor();
                listenerActor.setOrigin(listenerActor.getWidth() / 2.0f, listenerActor.getHeight() / 2.0f);
                listenerActor.addAction(TouchAction.downAction());
                if (listenerActor instanceof Group) {
                    ((Group)listenerActor).setTransform(true);
                }
                return true;
            }
            
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                final Actor listenerActor = inputEvent.getListenerActor();
                SoundHandle.playForButton3();
                listenerActor.setOrigin(listenerActor.getWidth() / 2.0f, listenerActor.getHeight() / 2.0f);
                listenerActor.addAction(TouchAction.upAction());
                if (inputEvent.getListenerActor() == BaseSubGun.this.subGunIcon.getIcon()) {
                    BaseSubGun.this.startAttack();
                }
                if (listenerActor instanceof Group) {
                    ((Group)listenerActor).setTransform(false);
                }
                ((GameStage)BaseSubGun.this.getStage()).doInstruction();
            }
        };
        this.subGunIcon.getIcon().addListener(this.listener);
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.flashPlayers[this.curIndex].updateRunTime(n);
        this.flashPlayers[this.curIndex].setPosition(this.getX(), this.getY());
        this.curTime += n;
        this.subGunIcon.setValid(this.curTime - this.lastAttackTime >= this.attackInterval);
        this.subGunIcon.setColdTime(this.attackInterval - this.curTime + this.lastAttackTime);
    }
    
    public void attack() {
        if (!this.isEffectStart && this.flashPlayers[this.curIndex].getFrameIndex() >= this.effectFrame) {
            if (this.actorEffect != null) {
                this.actorEffect.setStart();
            }
            if (this instanceof Invince) {
                this.actorEffect2.setStart();
            }
            this.isEffectStart = true;
        }
    }
    
    public void checkState() {
        switch (this.state) {
            case Idle: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= this.idleEnd) {
                    this.flashPlayers[this.curIndex].setFrameIndex(this.idleStart);
                }
                if (this.isAttack) {
                    this.state = Constant.SubGunState.Attack;
                    this.flashPlayers[this.curIndex].setFrameIndex(this.attackStart);
                    this.isEffectStart = false;
                    return;
                }
                break;
            }
            case Attack: {
                if (this.flashPlayers[this.curIndex].getFrameIndex() >= this.attackEnd) {
                    this.isAttack = false;
                    this.state = Constant.SubGunState.Idle;
                    this.flashPlayers[this.curIndex].setFrameIndex(this.idleStart);
                }
                if (!this.isEffectStart && this.flashPlayers[this.curIndex].getFrameIndex() >= this.effectFrame) {
                    this.attack();
                    this.isEffectStart = true;
                    return;
                }
                break;
            }
        }
    }
    
    public void draw(final SpriteBatch spriteBatch, final float n) {
        this.setPos(this, Global.pSub2, Global.pSub1);
        super.draw(spriteBatch, n);
        final Color color = spriteBatch.getColor();
        spriteBatch.setColor(((Player)this.getParent()).getColor());
        final FlashPlayer flashPlayer = this.flashPlayers[this.curIndex];
        flashPlayer.drawFlashRotation(spriteBatch, flashPlayer.getWidth() / 2.0f, flashPlayer.getHeight() / 2.0f, this.getRotation());
        spriteBatch.setColor(color);
    }
    
    public AnimationActor getActorEffect() {
        return this.actorEffect;
    }
    
    public AnimationActor getActorEffect2() {
        return this.actorEffect2;
    }
    
    public SubGunIcon getSubGunIcon() {
        return this.subGunIcon;
    }
    
    protected void setEnhanceLevel() {
    }
    
    public void setFrame(final int idleStart, final int idleEnd, final int attackStart, final int attackEnd) {
        this.idleStart = idleStart;
        this.idleEnd = idleEnd;
        this.attackStart = attackStart;
        this.attackEnd = attackEnd;
        this.effectFrame = this.attackStart;
    }
    
    public void setPos(final Actor actor, final Vector2 v1, final Vector2 v2) {        
        actor.setPosition(v1.x - 69.0f + Global.pAdd.x, v1.y - 93.0f + Global.pAdd.y);
        float lastAngle;
        final float n = lastAngle = MathUtils.atan2(v2.y - v1.y, v2.x - v1.x) * 57.295776f;
        if (Math.abs(n) < 2.0f) {
            lastAngle = n;
            if (Math.abs(n - this.lastAngle) < 0.1f) {
                lastAngle = 0.0f;
            }
        }
        actor.setRotation(this.lastAngle = lastAngle);
    }
    
    public void startAttack() {
        if (!this.isAttack && this.curTime - this.lastAttackTime >= this.attackInterval) {
            this.isAttack = true;
            this.lastAttackTime = this.curTime;
        }
    }
}
