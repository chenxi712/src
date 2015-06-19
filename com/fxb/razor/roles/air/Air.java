package com.fxb.razor.roles.air;

import com.fxb.razor.roles.*;
import com.fxb.razor.common.*;

public abstract class Air extends FlashEnemy
{
    protected float airMoveTime;
    protected boolean isMoveUp;
    protected boolean isStartDead;
    
    public Air() {
        this.isStartDead = false;
        this.airMoveTime = 1.0f;
    }
    
    @Override
    public void Die() {
        super.Die();
        SoundHandle.playForDragonDie();
    }
    
    @Override
    public void MoveAway() {
        this.state = Constant.EnemyState.Move_Away;
        this.flashPlayers[this.curIndex].stop();
        this.curIndex = 0;
        this.flashPlayers[this.curIndex].rePlay();
        this.flashPlayers[this.curIndex].setPosition(this.getX(), this.getY());
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        if (this.getRight() < -5.0f || this.getY() > 485.0f) {
            this.deadHandle();
            Global.isAllKill = false;
        }
    }
}
