package com.fxb.razor.stages.dialogs;

import com.badlogic.gdx.utils.*;
import com.fxb.razor.utils.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.common.*;
import com.fxb.razor.utils.ui.*;

public class DialogEnemyKill extends BaseDialog
{
    TextureAtlas altasStart;
    Array<GroupKillItem> arrKillItem;
    TextureAtlas atlasEnemyKill;
    private ButtonListener btnListener;
    ScrollPane pan;
    Table table;
    
    public DialogEnemyKill() {
        this.arrKillItem = new Array<GroupKillItem>();
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(InputEvent inputEvent, float n, float n2, int n3, int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == DialogEnemyKill.this.imgClose) {
                        DialogHandle.closeDialog(DialogEnemyKill.this, 0.35f);
                    }
                }
            }
        };
        this.atlasEnemyKill = Global.manager.get("ui/ui_enemy_kill.pack", TextureAtlas.class);
        this.altasStart = Global.manager.get("ui/ui_start.pack", TextureAtlas.class);
        this.imgBg = this.addItem(this, this.altasStart, "waikuang", 0.0f, 0.0f);
        this.initClose(this.btnListener);
        this.addItem(this, this.atlasEnemyKill, "title", 166.0f, 235.0f);
        this.initPan();
        this.initTable();
        this.tablePack();
        (this.imgShade = new MyShade()).setColor(MyShade.colorHalfTran);
        this.setSizeOrigin();
    }
    
    private void initPan() {
        this.table = new Table();
        this.table.defaults().space(3.0f);
        this.table.defaults().padBottom(10.0f);
        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.hScroll = new TextureRegionDrawable(this.atlasEnemyKill.findRegion("tiao"));
        scrollPaneStyle.hScrollKnob = new TextureRegionDrawable(this.atlasEnemyKill.findRegion("huadonganniu"));
        scrollPaneStyle.vScroll = new TextureRegionDrawable(this.atlasEnemyKill.findRegion("tiao"));
        scrollPaneStyle.vScrollKnob = new TextureRegionDrawable(this.atlasEnemyKill.findRegion("huadonganniu"));
        (this.pan = new ScrollPane(this.table, scrollPaneStyle)).setFadeScrollBars(false);
        this.pan.setScrollingDisabled(false, true);
        this.pan.setWidth(615.0f);
        this.pan.setHeight(213.0f);
        this.pan.setPosition(28.0f, 35.0f);
        this.addActor(this.pan);
    }
    
    private void tablePack() {
        this.table.clear();
        for (int i = 0; i < this.arrKillItem.size; ++i) {
            this.table.add(this.arrKillItem.get(i));
        }
    }
    
    public void initTable() {
        for (int i = 0; i < Constant.enemyTypes.length - 11; ++i) {
            Constant.EnemyType enemyType = Constant.enemyTypes[i];
            if (PreferHandle.isTypeRecord(enemyType)) {
                this.arrKillItem.add(new GroupKillItem(enemyType));
            }
        }
    }
    
    private class GroupKillItem extends Group
    {
        MyImage imgBg;
        MyImage imgLabelBg;
        MyImage imgName;
        MyImage imgType;
        int killNum;
        MyLabel labelCount;
        Constant.EnemyType type;
        
        public GroupKillItem(Constant.EnemyType enemyType) {
            String lowerCase = enemyType.toString().toLowerCase();
            this.imgBg = DialogEnemyKill.this.addItem(this, DialogEnemyKill.this.atlasEnemyKill, "beijing", 0.0f, 0.0f);
            (this.imgType = DialogEnemyKill.this.addItem(this, DialogEnemyKill.this.atlasEnemyKill, lowerCase, 53.0f, 96.0f)).setPosition((139.0f - this.imgType.getWidth()) / 2.0f, 60.0f + (125.0f - this.imgType.getHeight()) / 2.0f);
            if (this.imgType == null) {
            	 Debug.log("name:" + lowerCase);
            	
            }
            Debug.log("name:" +  lowerCase);
            DialogEnemyKill.this.addItem(this, DialogEnemyKill.this.atlasEnemyKill, lowerCase + "_t", 22.0f, 10.0f);
            this.imgLabelBg = DialogEnemyKill.this.addItem(this, DialogEnemyKill.this.atlasEnemyKill, "tongjikuang", 35.0f, 41.0f);
            int intValue;
            if (Global.mapTypeKill.get(enemyType) == null) {
                intValue = 0;
            }
            else {
                intValue = Global.mapTypeKill.get(enemyType);
            }
            this.killNum = intValue;
            (this.labelCount = MyLabel.createLabel(this, Integer.toString(this.killNum), 63.0f, 46.0f)).setX(this.imgLabelBg.getX() + 2.0f + (this.imgLabelBg.getWidth() - this.labelCount.getFontWidth()) / 2.0f);
            this.setSize(this.imgBg.getWidth(), this.imgBg.getHeight());
        }
        
        public int getKillNum() {
            return this.killNum;
        }
    }
}
