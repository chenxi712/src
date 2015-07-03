package com.gleejet.sun.utils.ui;

import com.badlogic.gdx.scenes.scene2d.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.stages.dialogs.*;
import com.gleejet.sun.utils.*;

public class CoinMond
{
    public static class GroupCoinHead extends MyGroup
    {
        MyImage imgBg;
        MyLabel labelCoin;
        
        public GroupCoinHead(final int n) {
            this.imgBg = UiHandle.addItem(this, Assets.atlasInstruction, "jinbi_head", 0.0f, 0.0f);
            (this.labelCoin = new MyLabel(StrHandle.get(n))).setSize(80.0f, 27.0f);
            this.labelCoin.setPosition(52.0f, 14.0f);
            this.addActor(this.labelCoin);
            this.setSize(this.imgBg.getWidth(), this.imgBg.getHeight());
            this.setPosition(222.0f, 415.0f);
        }
        
        public static GroupCoinHead createCoinHead(final Group group) {
            final GroupCoinHead groupCoinHead = new GroupCoinHead((int)Global.totalCoinNum);
            groupCoinHead.setPosition(120.0f, 366.0f);
            group.addActor(groupCoinHead);
            return groupCoinHead;
        }
        
        public static GroupCoinHead createCoinHead(final Stage stage, final InputListener inputListener) {
            final GroupCoinHead groupCoinHead = new GroupCoinHead((int)Global.totalCoinNum);
            groupCoinHead.addListener(inputListener);
            stage.addActor(groupCoinHead);
            return groupCoinHead;
        }
        
        public void setCoinNum(final int n) {
            this.labelCoin.setText(StrHandle.get(n));
        }
        
        public void touchHandle(final Stage stage, final DialogStore dialogStore) {
            dialogStore.selectItem(2);
            dialogStore.openDialog(stage);
        }
    }
    
    public static class GroupMondHead extends MyGroup
    {
        MyImage imgBg;
        MyLabel labelMond;
        
        public GroupMondHead(final int n) {
            this.imgBg = UiHandle.addItem(this, Assets.atlasInstruction, "baoshi_head", 0.0f, 0.0f);
            (this.labelMond = new MyLabel(StrHandle.get(n))).setSize(70.0f, 25.0f);
            this.labelMond.setPosition(45.0f, 10.0f);
            this.addActor(this.labelMond);
            this.setSize(this.imgBg.getWidth(), this.imgBg.getHeight());
            this.setPosition(425.0f, 418.0f);
        }
        
        public static GroupMondHead createMondHead(final Group group) {
            final GroupMondHead groupMondHead = new GroupMondHead((int)Global.totalMondNum);
            groupMondHead.setPosition(326.0f, 369.0f);
            group.addActor(groupMondHead);
            return groupMondHead;
        }
        
        public static GroupMondHead createMondHead(final Stage stage, final InputListener inputListener) {
            final GroupMondHead groupMondHead = new GroupMondHead((int)Global.totalMondNum);
            groupMondHead.addListener(inputListener);
            stage.addActor(groupMondHead);
            return groupMondHead;
        }
        
        public void setMondNum(final int n) {
            this.labelMond.setText(StrHandle.get(n));
        }
        
        public void touchHandle(final Stage stage, final DialogStore dialogStore) {
            dialogStore.selectItem(1);
            dialogStore.openDialog(stage);
        }
    }
}
