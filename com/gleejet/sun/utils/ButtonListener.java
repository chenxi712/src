package com.gleejet.sun.utils;

import com.gleejet.sun.utils.action.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class ButtonListener extends InputListener
{
    protected boolean isDown;
    
    public ButtonListener() {
        this.isDown = false;
    }
    
    @Override
    public boolean touchDown(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
        final Actor listenerActor = inputEvent.getListenerActor();
        listenerActor.setOrigin(listenerActor.getWidth() / 2.0f, listenerActor.getHeight() / 2.0f);
        listenerActor.addAction(TouchAction.downAction());
        return this.isDown = true;
    }
    
    @Override
    public void touchDragged(final InputEvent inputEvent, final float n, final float n2, final int n3) {
        final Actor listenerActor = inputEvent.getListenerActor();
        if (listenerActor.hit(n, n2, true) == null) {
            this.isDown = false;
            listenerActor.addAction(TouchAction.upAction());
        }
        super.touchDragged(inputEvent, n, n2, n3);
    }
    
    @Override
    public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
        if (!this.isDown) {
            return;
        }
        inputEvent.getListenerActor().addAction(TouchAction.upAction());
        super.touchUp(inputEvent, n, n2, n3, n4);
    }
}
