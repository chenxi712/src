package com.gleejet.sun.objects.maingun;

import com.gleejet.sun.common.*;
import com.gleejet.sun.utils.ui.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.graphics.g2d.*;

public class GroupHp extends Group
{
    MyImage[] imgBullets;
    MyImage imgDrawf;
    MyImage imgHp;
    MyImage imgInnerBorder;
    MyImage imgOutBorder;
    
    public GroupHp() {
        (this.imgDrawf = new MyImage(Assets.atlasUiGame.findRegion("ren1"))).setPosition(0.0f, 0.0f);
        (this.imgOutBorder = new MyImage(Assets.atlasUiGame.findRegion("xuetiaomukuang"))).setPosition(66.0f, 6.0f);
        (this.imgInnerBorder = new MyImage(Assets.atlasUiGame.findRegion("xuetiaotikuang"))).setPosition(67.0f, 43.0f);
        (this.imgHp = new MyImage(Assets.atlasUiGame.findRegion("xuetiao"))).setWidth(50.0f);
        this.imgHp.setPosition(69.0f, 44.0f);
        this.addActor(this.imgOutBorder);
        this.addActor(this.imgHp);
        this.addActor(this.imgInnerBorder);
        this.imgBullets = new MyImage[10];
        for (int i = 0; i < this.imgBullets.length; ++i) {
            (this.imgBullets[i] = new MyImage(Assets.atlasUiGame.findRegion("zhidan2"))).setPosition(i * 8 + 99, 6.0f);
            this.addActor(this.imgBullets[i]);
        }
        this.addActor(this.imgDrawf);
        this.setPosition(19.0f, 385.0f);
        this.setVisible(false);
        this.setTransform(false);
    }
    
    public void setDural(final float n) {
        final TextureAtlas.AtlasRegion region = Assets.atlasUiGame.findRegion("zhidan1");
        final TextureAtlas.AtlasRegion region2 = Assets.atlasUiGame.findRegion("zhidan2");
        final int n2 = (int)(n / 0.1f);
        for (int i = 0; i < this.imgBullets.length; ++i) {
            if (i < n2) {
                this.imgBullets[i].setRegion(region2);
            }
            else {
                this.imgBullets[i].setRegion(region);
            }
        }
    }
    
    public void setHpRate(final float n) {
        this.imgHp.setWidth((this.imgInnerBorder.getWidth() - 4.0f) * n);
    }
    
    public void setIcon(final TextureRegion region) {
        this.imgDrawf.setRegion(region);
    }
}
