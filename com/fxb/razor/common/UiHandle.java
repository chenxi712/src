package com.fxb.razor.common;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.utils.*;
import com.fxb.razor.utils.ui.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class UiHandle
{
    public static final Color colorFont;
    
    static {
        colorFont = new Color(0.84313726f, 0.83137256f, 0.62352943f, 1.0f);
    }
    
    public static MyImage addItem(final Group group, final TextureAtlas textureAtlas, final String s, final float n, final float n2) {
        return addItem(group, textureAtlas, s, n, n2, null, null);
    }
    
    public static MyImage addItem(final Group group, final TextureAtlas textureAtlas, final String s, final float n, final float n2, final InputListener inputListener) {
        return addItem(group, textureAtlas, s, n, n2, inputListener, null);
    }
    
    public static MyImage addItem(final Group group, final TextureAtlas textureAtlas, final String s, final float n, final float n2, final InputListener inputListener, final String name) {
        final MyImage myImage = new MyImage(textureAtlas.findRegion(s));
        myImage.setPosition(n, n2);
        myImage.setName(name);
        if (inputListener != null) {
            myImage.addListener(inputListener);
        }
        if (group != null) {
            group.addActor(myImage);
        }
        return myImage;
    }
    
    public static MyImage addItem(final Array<MyImage> array, final TextureAtlas textureAtlas, final String s, final float n, final float n2) {
        return addItem(array, textureAtlas, s, n, n2, null, null);
    }
    
    public static MyImage addItem(final Array<MyImage> array, final TextureAtlas textureAtlas, final String s, final float n, final float n2, final InputListener inputListener, final String name) {
        final MyImage myImage = new MyImage(textureAtlas.findRegion(s));
        myImage.setPosition(n, n2);
        myImage.setName(name);
        if (inputListener != null) {
            myImage.addListener(inputListener);
        }
        array.add(myImage);
        return myImage;
    }
    
    public static MyImage addItem(final MyGroup myGroup, final TextureAtlas textureAtlas, final String s, final float n, final float n2) {
        return addItem(myGroup, textureAtlas, s, n, n2, null);
    }
    
    public static MyImage addItem(final MyGroup myGroup, final TextureAtlas textureAtlas, final String s, final float n, final float n2, final InputListener inputListener) {
        final MyImage myImage = new MyImage(textureAtlas.findRegion(s));
        myImage.setPosition(n, n2);
        if (inputListener != null) {
            myImage.addListener(inputListener);
        }
        if (myGroup != null) {
            myGroup.addActor(myImage);
        }
        return myImage;
    }
    
    public static Sprite addSprite(final Array<Sprite> array, final TextureAtlas textureAtlas, final String s, final float n, final float n2) {
        final Sprite sprite = new Sprite(textureAtlas.findRegion(s));
        sprite.setPosition(n, n2);
        array.add(sprite);
        return sprite;
    }
    
    public static MyImage createAim(final Stage stage) {
        final MyImage myImage = new MyImage(Assets.atlasUiGame.findRegion("miaozhunjiang"));
        myImage.setColor(0.7f, 0.7f, 0.7f, 0.65f);
        myImage.setSize(myImage.getWidth() * 0.7f, myImage.getHeight() * 0.7f);
        myImage.setOrigin(myImage.getWidth() / 2.0f, myImage.getHeight() / 2.0f);
        myImage.setVisible(false);
        stage.addActor(myImage);
        return myImage;
    }
    
    public static MyImage createBlood(final Stage stage) {
        final MyImage myImage = new MyImage(Assets.atlasUiGame.findRegion("xueping"));
        myImage.setSize(804.0f, 486.0f);
        myImage.setPosition(-2.0f, -2.0f);
        myImage.setVisible(false);
        myImage.setTouchable(Touchable.disabled);
        myImage.setColor(1.0f, 1.0f, 1.0f, 0.85f);
        myImage.setName("imgBlood");
        stage.addActor(myImage);
        return myImage;
    }
    
    public static Group createGroup(final Stage stage, final boolean b) {
        return createGroup(stage, b, 0.0f, 0.0f);
    }
    
    public static Group createGroup(final Stage stage, final boolean transform, final float n, final float n2) {
        final Group group = new Group();
        group.setPosition(n, n2);
        group.setTransform(transform);
        if (stage != null) {
            stage.addActor(group);
        }
        return group;
    }
    
    public static MyImage createHand(final Group group, final Actor actor) {
        final MyImage myImage = new MyImage(Assets.atlasInstruction.findRegion("shou"));
        final Vector2 vector2 = new Vector2();
        actor.localToParentCoordinates(vector2);
        myImage.setPosition(vector2.x + actor.getWidth() / 2.0f - 12.0f, vector2.y + actor.getHeight() / 2.0f - myImage.getHeight() + 8.0f);
        myImage.setTouchable(Touchable.disabled);
        handTouch(myImage);
        myImage.setName("imgHand");
        group.addActor(myImage);
        return myImage;
    }
    
    public static MyImage createHand(final Stage stage, final float n, final float n2) {
        final MyImage myImage = new MyImage(Assets.atlasInstruction.findRegion("shou"));
        myImage.setPosition(n, n2);
        myImage.setTouchable(Touchable.disabled);
        handTouch(myImage);
        myImage.setName("imgHand");
        stage.addActor(myImage);
        return myImage;
    }
    
    public static MyImage createHand(final Stage stage, final Actor actor) {
        final MyImage myImage = new MyImage(Assets.atlasInstruction.findRegion("shou"));
        final Vector2 vector2 = new Vector2();
        actor.localToStageCoordinates(vector2);
        myImage.setPosition(vector2.x + actor.getWidth() / 2.0f - 12.0f, vector2.y + actor.getHeight() / 2.0f - myImage.getHeight() + 8.0f);
        myImage.setTouchable(Touchable.disabled);
        handTouch(myImage);
        myImage.setName("imgHand");
        stage.addActor(myImage);
        return myImage;
    }
    
    public static Group createInsturctionArrow(final Stage stage, final float n, final float n2) {
        final Group group = new Group();
        float n3 = 0.0f + (addItem(group, Assets.atlasInstruction, "jiantou4", -13.5f, 0.0f).getHeight() - 1.0f);
        for (int i = 0; i < 3; ++i) {
            n3 += addItem(group, Assets.atlasInstruction, "jiantou2", 0.0f, n3).getHeight() - 1.0f;
        }
        final float n4 = n3 + (addItem(group, Assets.atlasInstruction, "jiantou6", 0.0f, n3).getHeight() - 1.0f) + 20.0f;
        float n5 = n4 + (addItem(group, Assets.atlasInstruction, "jiantou3", 0.0f, n4).getHeight() - 1.0f);
        for (int j = 0; j < 3; ++j) {
            n5 += addItem(group, Assets.atlasInstruction, "jiantou2", 0.0f, n5).getHeight() - 1.0f;
        }
        addItem(group, Assets.atlasInstruction, "jiantou1", -13.5f, n5);
        group.setPosition(n, n2);
        stage.addActor(group);
        return group;
    }
    
    public static Group createInsturctionBoard(final Group group, final String s, final Actor actor, final int n, final int n2) {
        final Group group2 = new Group();
        final MyImage addItem = addItem(group2, Assets.atlasInstruction, "kuang", 0.0f, 0.0f);
        createRomanLabel(group2, s, 140.0f, 75.0f);
        group2.setSize(addItem.getWidth(), addItem.getHeight());
        float x;
        if (n > 0) {
            x = actor.getRight() - 20.0f;
        }
        else {
            x = actor.getX() - group2.getWidth() + 20.0f;
        }
        group2.setX(x);
        float y;
        if (n2 > 0) {
            y = actor.getTop() - 20.0f;
        }
        else {
            y = actor.getY() - group2.getHeight() + 20.0f;
        }
        group2.setY(y);
        group.addActor(group2);
        final Actor actor2 = group.findActor("imgHand");
        if (actor2 != null) {
            actor2.toFront();
        }
        return group2;
    }
    
    public static Group createInsturctionBoard(final Stage stage, final String s, final float n, final float n2) {
        final Group group = new Group();
        final MyImage addItem = addItem(group, Assets.atlasInstruction, "kuang", 0.0f, 0.0f);
        createRomanLabel(group, s, 140.0f, 65.0f);
        group.setSize(addItem.getWidth(), addItem.getHeight());
        group.setPosition(n, n2);
        stage.addActor(group);
        return group;
    }
    
    public static Group createInsturctionBoard(final Stage stage, final String s, final Actor actor, final int n, final int n2) {
        final Group group = new Group();
        final MyImage addItem = addItem(group, Assets.atlasInstruction, "kuang", 0.0f, 0.0f);
        createRomanLabel(group, s, 140.0f, 75.0f);
        group.setSize(addItem.getWidth(), addItem.getHeight());
        float x;
        if (n > 0) {
            x = actor.getRight() - 20.0f;
        }
        else {
            x = actor.getX() - group.getWidth() + 20.0f;
        }
        group.setX(x);
        float y;
        if (n2 > 0) {
            y = actor.getTop() - 20.0f;
        }
        else {
            y = actor.getY() - group.getHeight() + 20.0f;
        }
        group.setY(y);
        stage.addActor(group);
        final Actor actor2 = stage.getRoot().findActor("imgHand");
        if (actor2 != null) {
            actor2.toFront();
        }
        return group;
    }
    
    public static MyImage createNobullets(final Stage stage) {
        final MyImage myImage = new MyImage(Assets.atlasUiGame.findRegion("nobullets"));
        myImage.setPosition(320.0f, 50.0f);
        myImage.setVisible(false);
        myImage.setName("imgNobullets");
        myImage.setTouchable(Touchable.disabled);
        stage.addActor(myImage);
        return myImage;
    }
    
    public static Label createRoman16Label(final Group group, final String s, final float n, final float n2) {
        final Label label = new Label(s, getLabelStyle14());
        label.setPosition(n, n2);
        group.addActor(label);
        return label;
    }
    
    public static Label createRoman16Label(final MyGroup myGroup, final String s, final float n, final float n2) {
        final Label label = new Label(s, getLabelStyle14());
        label.setPosition(n, n2);
        myGroup.addActor(label);
        return label;
    }
    
    public static Label createRomanLabel(final Group group, final String s, final float n, final float n2) {
        final Label label = new Label(s, getLabelStyle15());
        label.setPosition(n, n2);
        group.addActor(label);
        return label;
    }
    
    public static Label createRomanLabel(final MyGroup myGroup, final String s, final float n, final float n2) {
        final Label label = new Label(s, getLabelStyle15());
        label.setPosition(n, n2);
        myGroup.addActor(label);
        return label;
    }
    
    public static MyImage createWarning() {
        final MyImage myImage = new MyImage(Assets.atlasUiGame.findRegion("warning"));
        myImage.setPosition(320.0f, 50.0f);
        myImage.setVisible(false);
        myImage.setName("imgWarning");
        myImage.setTouchable(Touchable.disabled);
        Global.groupEffectPlayer.addActor(myImage);
        return myImage;
    }
    
    public static Label.LabelStyle getLabelStyle14() {
        final Label.LabelStyle labelStyle = new Label.LabelStyle(Assets.fontMs14, UiHandle.colorFont);
        labelStyle.fontColor = UiHandle.colorFont;
        return labelStyle;
    }
    
    public static Label.LabelStyle getLabelStyle15() {
        final Label.LabelStyle labelStyle = new Label.LabelStyle(Assets.fontMs16, UiHandle.colorFont);
        labelStyle.fontColor = UiHandle.colorFont;
        return labelStyle;
    }
    
    public static void handTouch(final MyImage myImage) {
        myImage.clearActions();
        myImage.translate(-5.0f, 5.0f);
        myImage.addAction(Actions.forever(Actions.sequence(Actions.moveBy(10.0f, -10.0f, 0.35f), Actions.moveBy(-10.0f, 10.0f, 0.35f))));
    }
    
    public static void setHandTarget(final Actor actor, final Actor actor2) {
        final Vector2 vector2 = new Vector2();
        actor2.localToStageCoordinates(vector2);
        actor.setPosition(vector2.x + actor2.getWidth() / 2.0f - 12.0f, vector2.y + actor2.getHeight() / 2.0f - actor.getHeight() + 8.0f);
    }
    
    public static void startShake(final Actor actor, final float n) {
        actor.clearActions();
        actor.setVisible(true);
        actor.addAction(Actions.forever(Actions.sequence(Actions.delay(n), Actions.visible(false), Actions.delay(n), Actions.visible(true))));
    }
    
    public static void stopShake(final Actor actor) {
        actor.clearActions();
        actor.setVisible(false);
    }
}
