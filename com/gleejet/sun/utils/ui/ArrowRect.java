package com.gleejet.sun.utils.ui;

import com.gleejet.sun.common.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class ArrowRect extends Group
{
    MyImage[] imgArrows;
    
    public ArrowRect() {
        this.imgArrows = new MyImage[4];
        for (int i = 0; i < this.imgArrows.length; ++i) {
            (this.imgArrows[i] = new MyImage(Assets.atlasInstruction.findRegion("zhizhen" + (i + 1)))).setOrigin(this.imgArrows[i].getWidth() / 2.0f, this.imgArrows[i].getHeight() / 2.0f);
            this.addActor(this.imgArrows[i]);
        }
    }
    
    public static Action blink() {
        return Actions.forever(Actions.sequence(Actions.scaleTo(0.85f, 0.85f, 0.3f, Interpolation.linear), Actions.scaleTo(1.15f, 1.15f, 0.3f, Interpolation.linear)));
    }
    
    public static ArrowRect createArrowRect(final Group group, final Actor actor) {
        final ArrowRect arrowRect = new ArrowRect();
        arrowRect.init(actor.getWidth(), actor.getHeight());
        final Vector2 vector2 = new Vector2();
        actor.localToParentCoordinates(vector2);
        arrowRect.setPosition(vector2.x - (arrowRect.getWidth() - actor.getWidth()) / 2.0f, vector2.y - (arrowRect.getHeight() - actor.getHeight()) / 2.0f);
        group.addActor(arrowRect);
        return arrowRect;
    }
    
    public static ArrowRect createArrowRect(final Stage stage, final float n, final float n2, final float n3, final float n4) {
        final ArrowRect arrowRect = new ArrowRect();
        arrowRect.init(n, n2);
        arrowRect.setPosition(n3, n4);
        stage.addActor(arrowRect);
        return arrowRect;
    }
    
    public static ArrowRect createArrowRect(final Stage stage, final Actor actor) {
        final ArrowRect arrowRect = new ArrowRect();
        arrowRect.init(actor.getWidth(), actor.getHeight());
        final Vector2 vector2 = new Vector2();
        actor.localToStageCoordinates(vector2);
        arrowRect.setPosition(vector2.x - (arrowRect.getWidth() - actor.getWidth()) / 2.0f, vector2.y - (arrowRect.getHeight() - actor.getHeight()) / 2.0f);
        stage.addActor(arrowRect);
        return arrowRect;
    }
    
    public void init(final float n, final float n2) {
        this.imgArrows[0].setPosition(0.0f, n2 / 2.0f + this.imgArrows[1].getHeight() - this.imgArrows[0].getHeight() / 2.0f);
        this.imgArrows[1].setPosition(this.imgArrows[0].getWidth() + n / 2.0f - this.imgArrows[1].getWidth() / 2.0f, 0.0f);
        this.imgArrows[2].setPosition(this.imgArrows[0].getWidth() + n, this.imgArrows[0].getY());
        this.imgArrows[3].setPosition(this.imgArrows[1].getX(), this.imgArrows[1].getHeight() + n2);
        this.setSize(this.imgArrows[2].getRight(), this.imgArrows[3].getTop());
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.addAction(blink());
        this.setTouchable(Touchable.disabled);
    }
    
    public void setTarget(final Actor actor) {
        final Vector2 vector2 = new Vector2();
        actor.localToStageCoordinates(vector2);
        this.clearActions();
        this.init(actor.getWidth(), actor.getHeight());
        this.setPosition(vector2.x - (this.getWidth() - actor.getWidth()) / 2.0f, vector2.y - (this.getHeight() - actor.getHeight()) / 2.0f);
    }
}
