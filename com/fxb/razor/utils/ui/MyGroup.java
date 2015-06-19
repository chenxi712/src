package com.fxb.razor.utils.ui;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;

public class MyGroup extends Actor
{
    Array<Actor> arrChildern;
    Color tempColor;
    
    public MyGroup() {
        this.arrChildern = new Array<Actor>();
        this.tempColor = new Color();
    }
    
    private void drawChildern(final SpriteBatch spriteBatch, final float n) {
        for (int i = 0; i < this.arrChildern.size; ++i) {
            final Actor actor = this.arrChildern.get(i);
            actor.getOriginX();
            actor.getOriginY();
            actor.setOrigin(this.getOriginX(), this.getOriginY());
            final float scaleX = actor.getScaleX();
            final float scaleY = actor.getScaleY();
            actor.setScale(this.getScaleX() * scaleX, this.getScaleY() * scaleY);
            final float n2 = this.getX() + (actor.getOriginX() + actor.getX() - this.getOriginX()) * (this.getScaleX() - 1.0f);
            final float n3 = this.getY() + (actor.getOriginY() + actor.getY() - this.getOriginY()) * (this.getScaleY() - 1.0f);
            actor.translate(n2, n3);
            final Color color = actor.getColor();
            this.tempColor.set(actor.getColor());
            final Color color2 = this.getColor();
            actor.setColor(color.r * color2.r, color.g * color2.g, color.b * color2.b, color.a * color2.a);
            actor.rotate(this.getRotation());
            if (actor.isVisible()) {
                actor.draw(spriteBatch, n);
            }
            actor.rotate(-this.getRotation());
            actor.setColor(this.tempColor);
            actor.translate(-n2, -n3);
            actor.setScale(scaleX, scaleY);
        }
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        for (int i = 0; i < this.arrChildern.size; ++i) {
            this.arrChildern.get(i).act(n);
        }
    }
    
    public void addActor(final Actor actor) {
        this.arrChildern.add(actor);
    }
    
    @Override
    public void draw(final SpriteBatch spriteBatch, final float n) {
        super.draw(spriteBatch, n);
        if (this.isVisible()) {
            this.drawChildern(spriteBatch, n);
        }
    }
    
    public boolean myRemoveActor(final Actor actor) {
        boolean b = true;
        if (!this.arrChildern.removeValue(actor, true)) {
            b = false;
        }
        return b;
    }
}
