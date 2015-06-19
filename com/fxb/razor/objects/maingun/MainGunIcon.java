package com.fxb.razor.objects.maingun;

import com.fxb.razor.utils.ui.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.graphics.g2d.*;

public class MainGunIcon extends MyGroup
{
    MyImage[] imgBullets;
    MyImage imgDied;
    MyImage imgHighlight;
    MyImage imgIcon;
    TextureRegion regionAlive;
    TextureRegion regionDead;
    
    public MainGunIcon() {
        (this.imgHighlight = UiHandle.addItem(this, Assets.atlasUiGame, "xuanzhongwaikuang", 22.0f, 2.0f)).setVisible(false);
        (this.imgIcon = new MyImage()).setPosition(25.0f, 19.0f);
        this.addActor(this.imgIcon);
        this.imgBullets = new MyImage[10];
        for (int i = 0; i < this.imgBullets.length; ++i) {
            this.imgBullets[i] = UiHandle.addItem(this, Assets.atlasUiGame, "zhidan4", i * 5 + 4 + 25, 6.0f);
        }
        (this.imgDied = UiHandle.addItem(this, Assets.atlasUiGame, "died", 36.0f, 30.0f)).setVisible(false);
        this.setSize(119.0f, 85.0f);
    }
    
    public MyImage getIcon() {
        return this.imgIcon;
    }
    
    public void revive() {
        this.imgDied.setVisible(false);
        this.imgIcon.setRegion(this.regionAlive);
        this.setTouchable(Touchable.enabled);
    }
    
    public void setDead() {
        this.imgIcon.setRegion(this.regionDead);
        this.imgDied.setVisible(true);
        this.setTouchable(Touchable.disabled);
    }
    
    public void setDural(final float n) {
        final TextureAtlas.AtlasRegion region = Assets.atlasUiGame.findRegion("zhidan3");
        final TextureAtlas.AtlasRegion region2 = Assets.atlasUiGame.findRegion("zhidan4");
        final int n2 = (int)(n / 0.1f);
        for (int i = 0; i < this.imgBullets.length; ++i) {
            final MyImage myImage = this.imgBullets[i];
            TextureAtlas.AtlasRegion region3;
            if (i < n2) {
                region3 = region2;
            }
            else {
                region3 = region;
            }
            myImage.setRegion(region3);
        }
    }
    
    public void setLightVisible(final boolean visible) {
        this.imgHighlight.setVisible(visible);
    }
    
    public void setRegionAlive(final TextureRegion regionAlive) {
        this.regionAlive = regionAlive;
        this.imgIcon.setRegion(this.regionAlive);
    }
    
    public void setRegionDead(final TextureRegion regionDead) {
        this.regionDead = regionDead;
    }
}
