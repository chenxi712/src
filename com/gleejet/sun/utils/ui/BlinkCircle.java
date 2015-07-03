package com.gleejet.sun.utils.ui;

import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class BlinkCircle extends MyImage
{
    public static Action blink() {
        return Actions.forever(Actions.sequence(Actions.scaleTo(0.85f, 0.85f, 0.3f, Interpolation.linear), Actions.scaleTo(1.15f, 1.15f, 0.3f, Interpolation.linear)));
    }
    
    public static BlinkCircle create(final Stage stage, final Actor actor) {
        final BlinkCircle blinkCircle = new BlinkCircle();
        final Vector2 vector2 = new Vector2(0.0f, 0.0f);
        actor.localToStageCoordinates(vector2);
        blinkCircle.setOrigin(blinkCircle.getWidth() / 2.0f, blinkCircle.getHeight() / 2.0f);
        blinkCircle.addAction(blink());
        blinkCircle.setPosition(vector2.x + (actor.getWidth() - blinkCircle.getWidth()) / 2.0f, vector2.y + (actor.getHeight() - blinkCircle.getHeight()) / 2.0f);
        blinkCircle.setTouchable(Touchable.disabled);
        stage.addActor(blinkCircle);
        return blinkCircle;
    }
}
