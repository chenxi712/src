package com.gleejet.sun.roles.build;

import com.gleejet.sun.common.*;
import com.gleejet.sun.roles.*;
import com.badlogic.gdx.math.*;

public abstract class Build extends FlashEnemy
{
    protected void CheckState(final boolean b) {
        switch (this.state) {
            default: {
                this.translate(-Constant.tranSpeed, 0.0f);
                break;
            }
            case Move_To: {
                this.translate(-Constant.tranSpeed, 0.0f);
                if (800.0f - this.getX() > 0.3f * this.getWidth()) {
                    if (b) {
                        this.state = Constant.EnemyState.Connect;
                    }
                    else {
                        this.state = Constant.EnemyState.Attack;
                    }
                    this.getCurFlash().play();
                    return;
                }
                break;
            }
            case Connect: {
                this.translate(-Constant.tranSpeed, 0.0f);
                if (this.flashPlayers[this.curIndex].GetPlayPercent() >= 0.95f) {
                    this.state = Constant.EnemyState.Attack;
                    this.SwitchNext();
                    return;
                }
                break;
            }
            case Attack: {
                this.translate(-Constant.tranSpeed, 0.0f);
                if (this.getX() - this.player.getRight() < 0.0f && this.getCurFlash().GetPlayPercent() >= 0.95f) {
                    this.state = Constant.EnemyState.Dead;
                    this.player.beAttacked(this.attackDamage * 5.0f);
                    this.player.beCollision(1, 1.5f);
                    this.Die();
                    Global.isAllKill = false;
                    return;
                }
                break;
            }
            case Dead: {
                this.translate(-Constant.tranSpeed, 0.0f);
                if (this.flashPlayers[this.curIndex].isEnd()) {
                    if (!this.flashPlayers[this.curIndex].isStop()) {
                        this.flashPlayers[this.curIndex].stop();
                        System.out.println("end");
                    }
                    this.deadHandle();
                    return;
                }
                break;
            }
        }
    }
    
    @Override
    public void Clear() {
        super.Clear();
        this.state = Constant.EnemyState.Move_To;
    }
    
    public float GetBulletStartX() {
        return this.getX() + MathUtils.random(15, 18);
    }
    
    public float GetBulletStartY() {
        return this.getY() + this.getHeight() * 0.3f;
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
    }
}
