package com.fxb.razor.objects;

import com.fxb.razor.roles.*;
import com.fxb.razor.utils.ui.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.fxb.razor.stages.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class Flag0 extends StillEnemy
{
    Group group;
    MyImage imgFlag;
    MyImage imgPole;
    
    public Flag0() {
        this.group = new Group();
        this.imgPole = new MyImage(Assets.atlasUiGame.findRegion("qigan"));
        this.imgFlag = new MyImage(Assets.atlasUiGame.findRegion("qizi"));
        this.imgPole.setPosition(0.0f, 0.0f);
        this.imgFlag.setPosition(0.0f, this.imgPole.getHeight() / 2.0f - this.imgFlag.getHeight());
        this.group.addActor(this.imgPole);
        this.group.addActor(this.imgFlag);
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.group.act(n);
        this.group.setPosition(this.getX(), this.getY());
    }
    
    @Override
    public void draw(final SpriteBatch spriteBatch, final float n) {
        super.draw(spriteBatch, n);
        this.group.draw(spriteBatch, n);
    }
    
    public Group getGroup() {
        return this.group;
    }
    
    public void riseUp() {
        this.imgFlag.addAction(Actions.sequence(Actions.moveTo(0.0f, this.imgPole.getHeight() - this.imgFlag.getHeight(), 0.4f), Actions.delay(0.3f), Actions.run(new Runnable() {
            @Override
            public void run() {
                ((GameStage)Flag0.this.getStage()).levelWin();
            }
        })));
    }
}
