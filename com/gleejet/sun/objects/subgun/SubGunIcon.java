package com.gleejet.sun.objects.subgun;

import com.badlogic.gdx.scenes.scene2d.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.utils.ui.*;
import com.badlogic.gdx.graphics.g2d.*;

public class SubGunIcon extends Group
{
    float coldTime;
    MyImage imgIcon;
    MyImage imgMond;
    MyImage imgShade;
    MyLabel labelColdTime;
    
    public SubGunIcon() {
        this.coldTime = 0.0f;
        this.imgIcon = UiHandle.addItem(this, Assets.atlasUiGame, "bianyang", 0.0f, 0.0f);
        this.imgMond = UiHandle.addItem(this, Assets.atlasUiGame, "baoshi1", 32.0f, 68.0f);
        (this.imgShade = UiHandle.addItem(this, Assets.atlasUiGame, "circle", 2.0f, 2.0f)).setSize(this.imgIcon.getWidth() - 4.0f, this.imgIcon.getHeight() - 4.0f);
        this.imgShade.setVisible(false);
        this.labelColdTime = MyLabel.createLabel(this, "5", 34.0f, 34.0f);
        this.setTransform(false);
        this.setSize(this.imgIcon.getWidth(), this.imgIcon.getHeight());
    }
    
    public void draw(final SpriteBatch spriteBatch, final float n) {
        super.draw(spriteBatch, n);
        if (this.coldTime > 0.0f) {
            return;
        }
    }
    
    public MyImage getIcon() {
        return this.imgIcon;
    }
    
    public void setColdTime(final float coldTime) {
        this.coldTime = coldTime;
        this.labelColdTime.setText("" + (int)(this.coldTime + 1.0f));
        this.labelColdTime.setPosition(this.imgShade.getWidth() / 2.0f - this.labelColdTime.getFontWidth() / 2.0f + 3.0f, this.imgShade.getHeight() / 2.0f - this.labelColdTime.getFontHeight() / 2.0f + 3.0f);
    }
    
    public void setIcon(final TextureRegion region) {
        this.imgIcon.setRegion(region);
    }
    
    public void setValid(final boolean b) {
        if (b) {
            this.imgShade.setVisible(false);
            this.labelColdTime.setVisible(false);
            this.imgMond.setRegion(Assets.atlasUiGame.findRegion("baoshi1"));
            return;
        }
        this.imgShade.setVisible(true);
        this.labelColdTime.setVisible(true);
        this.imgMond.setRegion(Assets.atlasUiGame.findRegion("baoshi2"));
    }
}
