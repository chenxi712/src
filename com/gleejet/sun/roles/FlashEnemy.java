package com.gleejet.sun.roles;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.math.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.flash.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

public abstract class FlashEnemy extends BaseEnemy implements Pool.Poolable
{
    protected float alpha;
    protected TextureAtlas[] atlass;
    protected int curIndex;
    protected FlashPlayer[] flashPlayers;
    protected Vector2 pos;
    protected float scale;
    
    public FlashEnemy() {
        this.curIndex = 0;
        this.pos = new Vector2();
        this.alpha = 1.0f;
        this.scale = 0.33333334f;
        this.InitFlash();
        this.SetProperty();
        this.Clear();
    }
    
    @Override
    public void Clear() {
        super.Clear();
        this.currentHp = this.maxHp;
        this.UpdateHp();
        this.state = Constant.EnemyState.Move_To;
        this.curIndex = 0;
        this.flashPlayers[0].alphaMultiplier = 1.0f;
        this.flashPlayers[0].rePlay();
        this.setColor(Color.WHITE);
    }
    
    public void DefaultSet() {
    }
    
    @Override
    public void Die() {
        super.Die();
        this.currentHp = 0.0f;
        this.state = Constant.EnemyState.Dead;
        this.flashPlayers[this.curIndex].stop();
        this.curIndex = this.flashPlayers.length - 1;
        this.flashPlayers[this.curIndex].rePlay();
        this.flashPlayers[this.curIndex].setPosition(this.getX(), this.getY());
    }
    
    public float GetDisToPlayer() {
        final float x = this.getX();
        this.getY();
        final float n = this.getHeight() / 2.0f;
        final float right = this.player.getRight();
        this.player.getY();
        final float n2 = this.player.getHeight() / 2.0f;
        return Math.abs(x - right);
    }
    
    public float GetDrawHpPercent() {
        return 0.0f;
    }
    
    public float GetDrawHpStartX() {
        return 0.0f;
    }
    
    public float GetDrawHpY() {
        return this.getTop() - 4.0f;
    }
    
    public abstract void InitFlash();
    
    public boolean IsCanAttack() {
        final float getDisToPlayer = this.GetDisToPlayer();
        return getDisToPlayer >= this.minAttackDistance * 50.0f && getDisToPlayer <= this.maxAttackDistance * 50.0f;
    }
    
    public void MoveAway() {
        this.state = Constant.EnemyState.Move_Away;
        this.flashPlayers[this.curIndex].stop();
        this.curIndex = 0;
        this.flashPlayers[this.curIndex].rePlay();
        this.flashPlayers[this.curIndex].SetFlipX(true);
        this.speed.x = -this.speed.x;
        this.isDirectRight = true;
    }
    
    public abstract void SetProperty();
    
    public void SwitchNext() {
        this.flashPlayers[this.curIndex].stop();
        ++this.curIndex;
        this.flashPlayers[this.curIndex].rePlay();
        this.flashPlayers[this.curIndex].setPosition(this.getX(), this.getY());
    }
    
    @Override
    public void UpdateHp() {
        this.spriteHpBack.setSize(this.getWidth() * this.GetDrawHpPercent(), 4.0f);
        this.spriteHpBack.setPosition(this.getX() + this.GetDrawHpStartX(), this.GetDrawHpY());
        this.spriteHpFront.setSize((this.getWidth() * this.GetDrawHpPercent() - 2.0f) * this.currentHp / this.maxHp, 2.0f);
        this.spriteHpFront.setPosition(this.getX() + this.GetDrawHpStartX() + 1.0f, this.GetDrawHpY() + 1.0f);
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.currentTime += n;
        float n2 = n;
        if (this.freezeTime > 0.0f) {
            n2 = n * this.freezeRate;
        }
        this.flashPlayers[this.curIndex].updateRunTime(n2);
        this.flashPlayers[this.curIndex].setPosition(this.getX(), this.getY());
    }
    
    @Override
    public void draw(final SpriteBatch spriteBatch, final float n) {
        super.draw(spriteBatch, n);
        spriteBatch.getColor();
        spriteBatch.setColor(this.getColor());
        final FlashPlayer curFlash = this.getCurFlash();
        curFlash.drawFlashRotation(spriteBatch, curFlash.getWidth() / 2.0f, curFlash.getHeight() / 2.0f, this.getRotation());
        spriteBatch.setColor(Color.WHITE);
    }
    
    public FlashPlayer getCurFlash() {
        return this.flashPlayers[this.curIndex];
    }
    
    @Override
    public void reset() {
        this.currentHp = this.maxHp;
        this.UpdateHp();
        this.state = Constant.EnemyState.Move_To;
        this.curIndex = 0;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            this.flashPlayers[i].SetFlipX(false);
        }
    }
    
    @Override
    public void setPlayer(final Player player) {
        this.player = player;
    }
}
