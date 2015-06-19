package com.fxb.razor.roles.ground;

import com.fxb.razor.roles.*;
import com.badlogic.gdx.math.*;
import com.fxb.razor.common.*;

public abstract class Ground extends FlashEnemy
{
    protected void CheckState(final boolean b, final boolean b2, int i) {
        switch (this.state) {
            case Move_To: {
                this.translate(this.speed.x - Constant.tranSpeed, this.speed.y);
                if (this.GetDisToPlayer() <= this.maxAttackDistance * 50.0f) {
                    if (b) {
                        this.state = Constant.EnemyState.Connect;
                    }
                    else {
                        this.state = Constant.EnemyState.Attack;
                    }
                    this.SwitchNext();
                    return;
                }
                BaseEnemy baseEnemy;
                for (i = 0; i < Global.groupEnemy.getChildren().size; ++i) {
                    baseEnemy = (BaseEnemy)Global.groupEnemy.getChildren().get(i);
                    MathUtils.random(-25.0f, -20.0f);
                    if (baseEnemy.getState() == Constant.EnemyState.Attack && this.isSameType(this, baseEnemy) && this.getX() - baseEnemy.getX() <= 0.4f * baseEnemy.getWidth()) {
                        if (b) {
                            this.state = Constant.EnemyState.Connect;
                        }
                        else {
                            this.state = Constant.EnemyState.Attack;
                        }
                        this.SwitchNext();
                        return;
                    }
                }
                break;
            }
            case Connect: {
                this.translate(-Constant.tranSpeed, this.speed.y);
                if (this.flashPlayers[this.curIndex].GetPlayPercent() >= 0.95f) {
                    this.state = Constant.EnemyState.Attack;
                    this.SwitchNext();
                    return;
                }
                break;
            }
            case Attack: {
                this.translate(-Constant.tranSpeed, this.speed.y);
                if (b2) {
                    if (this.GetDisToPlayer() < this.minAttackDistance * 50.0f && this.getCurFlash().GetPlayPercent() >= 0.95f) {
                        this.MoveAway();
                        return;
                    }
                    break;
                }
                else {
                    if (this.player.getRight() - this.getX() > 30.0f) {
                        this.Die();
                        this.player.beAttacked(this.attackDamage * 5.0f);
                        this.player.beCollision(2, 1.5f);
                        Global.isAllKill = false;
                        return;
                    }
                    break;
                }
            }
            case Move_Away: {
                this.translate(this.speed.x - 0.35f, this.speed.y);
                if (this.getX() > 810.0f) {
                    this.deadHandle();
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
                    }
                    this.deadHandle();
                    return;
                }
                break;
            }
        }
    }
    
    @Override
    public void Die() {
        super.Die();
        SoundHandle.playForFootDie();
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        if (this.getRight() < -10.0f) {
            this.deadHandle();
        }
    }
    
    public boolean isSameType(final BaseEnemy baseEnemy, final BaseEnemy baseEnemy2) {
        final Constant.EnemyType type = baseEnemy.getType();
        final Constant.EnemyType type2 = baseEnemy2.getType();
        if (type != type2) {
            if (baseEnemy2.GetCategray() != Constant.EnemyCategray.Build) {}
            final String string = type.toString();
            final String string2 = type2.toString();
            if (!string.substring(0, string.length() - 1).equals(string2.substring(0, string2.length() - 1))) {
                return false;
            }
        }
        return true;
    }
}
