package com.fxb.razor.stages.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.utils.*;
import com.fxb.razor.utils.ui.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.fxb.razor.common.*;

public class DialogHero extends BaseDialog
{
    TextureAtlas atlasHero;
    TextureAtlas atlasStart;
    private ButtonListener btnListener;
    MyImage[] imgHeros;
    ScrollPane pan;
    Table table;
    
    public DialogHero() {
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == DialogHero.this.imgClose) {
                        DialogHandle.closeDialog(DialogHero.this, 0.35f);
                    }
                }
            }
        };
        this.atlasHero = Global.manager.get("ui/ui_hero.pack", TextureAtlas.class);
        this.atlasStart = Global.manager.get("ui/ui_start.pack", TextureAtlas.class);
        this.imgBg = this.addItem(this, this.atlasStart, "waikuang", 0.0f, 0.0f);
        this.initClose(this.btnListener);
        this.addItem(this, this.atlasHero, "hero", 166.0f, 235.0f);
        this.initPan();
        this.imgHeros = new MyImage[Global.arrMainGunGet.size];
        for (int i = 0; i < this.imgHeros.length; ++i) {
            this.imgHeros[i] = new MyImage(this.atlasHero.findRegion(Global.arrMainGunGet.get(i)));
            this.table.add(this.imgHeros[i]);
        }
        int j = 0;
        final int size = Global.arrMainGunGet.size;
        while (j < 13) {
            final String s = Constant.strMainOrders[j];
            if (!Global.arrMainGunGet.contains(s, false)) {
                this.table.add(UiHandle.addItem((Group)null, this.atlasHero, StrHandle.get(s, "_b"), 0.0f, 0.0f));
            }
            ++j;
        }
        (this.imgShade = new MyShade()).setColor(MyShade.colorHalfTran);
        this.setSizeOrigin();
    }
    
    private void initPan() {
        this.table = new Table();
        this.table.defaults().space(3.0f);
        this.table.defaults().padBottom(5.0f);
        final ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.hScroll = new TextureRegionDrawable(this.atlasHero.findRegion("tiao"));
        scrollPaneStyle.vScroll = new TextureRegionDrawable(this.atlasHero.findRegion("tiao"));
        final NinePatch ninePatch = new NinePatch(this.atlasHero.findRegion("huadonganniu"), 14, 14, 5, 5);
        scrollPaneStyle.hScrollKnob = new NinePatchDrawable(ninePatch);
        scrollPaneStyle.vScrollKnob = new NinePatchDrawable(ninePatch);
        (this.pan = new ScrollPane(this.table, scrollPaneStyle)).setFadeScrollBars(false);
        this.pan.setScrollingDisabled(false, true);
        this.pan.setWidth(615.0f);
        this.pan.setHeight(213.0f);
        this.pan.setPosition(28.0f, 32.0f);
        this.addActor(this.pan);
    }
    
    private class GroupLock extends Group
    {
        MyImage imgCover;
        MyImage imgHero;
        
        public GroupLock(final String s) {
            this.imgHero = UiHandle.addItem(this, DialogHero.this.atlasHero, s, 0.0f, 0.0f);
            (this.imgCover = UiHandle.addItem(this, Assets.atlasLoad, "white", 0.0f, 0.0f)).setSize(this.imgHero.getWidth(), this.imgHero.getHeight());
            this.imgCover.setColor(0.3f, 0.3f, 0.3f, 0.95f);
            this.setSize(this.imgHero.getWidth(), this.imgHero.getHeight());
        }
    }
}
