package com.gleejet.sun.utils.ui;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.gleejet.sun.common.*;

public class MyLabel extends Actor
{
    Color colorTran;
    Constant.FontType fontType;
    String strText;
    
    public MyLabel() {
        this(null, Constant.FontType.common);
    }
    
    public MyLabel(final String s) {
        this(s, Constant.FontType.common);
    }
    
    public MyLabel(final String strText, final Constant.FontType fontType) {
        this.colorTran = new Color(0.0f, 0.0f, 0.0f, 0.0f);
        this.strText = strText;
        this.fontType = fontType;
    }
    
    public static Action comboAction(final Actor actor) {
        return Actions.sequence(Actions.scaleTo(1.6f, 1.6f, 0.2f), Actions.run(new Runnable() {
            @Override
            public void run() {
                actor.remove();
                Pools.free(actor);
            }
        }));
    }
    
    public static MyLabel createLabel(final Group group, final String text, final float n, final float n2) {
        final MyLabel myLabel = new MyLabel();
        myLabel.setText(text);
        myLabel.setFontType(Constant.FontType.common);
        myLabel.setPosition(n, n2);
        group.addActor(myLabel);
        return myLabel;
    }
    
    public static MyLabel createLabel(final MyGroup myGroup, final String text, final float n, final float n2) {
        final MyLabel myLabel = new MyLabel();
        myLabel.setText(text);
        myLabel.setFontType(Constant.FontType.common);
        myLabel.setPosition(n, n2);
        myGroup.addActor(myLabel);
        return myLabel;
    }
    
    public static MyLabel createStar(final Group group, final String text, final float n, final float n2) {
        final MyLabel myLabel = new MyLabel();
        myLabel.setText(text);
        myLabel.setFontType(Constant.FontType.star);
        myLabel.setPosition(n, n2);
        group.addActor(myLabel);
        return myLabel;
    }
    
    public static MyLabel createStar(final MyGroup myGroup, final String text, final float n, final float n2) {
        final MyLabel myLabel = new MyLabel();
        myLabel.setText(text);
        myLabel.setFontType(Constant.FontType.star);
        myLabel.setPosition(n, n2);
        myGroup.addActor(myLabel);
        return myLabel;
    }
    
    public static Action damageAction(final Actor actor) {
        return Actions.sequence(Actions.parallel(Actions.moveBy(0.0f, MathUtils.random(30, 50), 0.35f), Actions.scaleTo(0.7f, 0.7f, 0.35f), Actions.sequence(Actions.delay(0.2f), Actions.fadeOut(0.15f))), Actions.run(new Runnable() {
            @Override
            public void run() {
                actor.remove();
                Pools.free(actor);
            }
        }));
    }
    
    public static MyLabel obtain(final Group group, final String text, final Constant.FontType fontType, final float n, final float n2) {
        final MyLabel myLabel = Pools.obtain(MyLabel.class);
        myLabel.myclear();
        myLabel.setText(text);
        myLabel.setFontType(fontType);
        myLabel.setPosition(n, n2);
        myLabel.setColor(Color.WHITE);
        if (fontType == Constant.FontType.damage) {
            myLabel.setScale(1.8f);
            myLabel.addAction(damageAction(myLabel));
        }
        else if (fontType == Constant.FontType.combo) {
            myLabel.setScale(1.0f);
            myLabel.setVisible(false);
        }
        if (group != null) {
            group.addActor(myLabel);
        }
        return myLabel;
    }
    
    public void draw(final SpriteBatch spriteBatch, float rotation) {
        if (this.strText == null || this.strText.equals("")) {
            return;
        }
        final Color color = spriteBatch.getColor();
        spriteBatch.setColor(this.getColor());
        rotation = this.getRotation();
        if (this.getScaleX() == 1.0f && this.getScaleY() == 1.0f && rotation == 0.0f) {
            FontHandle.draw(spriteBatch, this.fontType, this.strText, this.getX(), this.getY());
        }
        else {
            FontHandle.draw(spriteBatch, this.fontType, this.strText, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getScaleX(), this.getScaleY());
        }
        spriteBatch.setColor(color);
    }
    
    public float getFontHeight() {
        return FontHandle.getHeight(this.fontType);
    }
    
    public float getFontWidth() {
        return FontHandle.getWidth(this.strText, this.fontType);
    }
    
    public void myclear() {
        this.clear();
        this.strText = null;
        this.fontType = Constant.FontType.damage;
        this.setScale(1.0f, 1.0f);
    }
    
    public void setComboNumber(final String strText) {
        this.strText = strText;
        this.clearActions();
        this.setVisible(true);
        this.setScale(1.0f);
        this.setColor(Color.WHITE);
        this.addAction(Actions.sequence(Actions.scaleTo(1.5f, 1.5f, 0.2f), Actions.delay(0.4f), Actions.color(this.colorTran, 0.3f)));
    }
    
    public void setComboShow() {
        this.clearActions();
        this.setVisible(true);
        this.setScale(1.5f);
        this.setColor(Color.WHITE);
        this.addAction(Actions.sequence(Actions.delay(0.6f), Actions.color(this.colorTran, 0.3f)));
    }
    
    public void setFontType(final Constant.FontType fontType) {
        this.fontType = fontType;
    }
    
    public void setText(final String strText) {
        this.strText = strText;
    }
}
