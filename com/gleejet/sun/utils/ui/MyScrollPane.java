package com.gleejet.sun.utils.ui;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;

public class MyScrollPane extends WidgetGroup
{
    float amountX;
    float amountY;
    float areaHeight;
    float areaWidth;
    boolean cancelTouchFocus;
    private boolean clamp;
    private boolean disableX;
    private boolean disableY;
    int draggingPointer;
    float fadeAlpha;
    float fadeAlphaSeconds;
    float fadeDelay;
    float fadeDelaySeconds;
    private boolean fadeScrollBars;
    boolean flickScroll;
    private ActorGestureListener flickScrollListener;
    float flingTime;
    float flingTimer;
    private boolean forceScrollX;
    private boolean forceScrollY;
    final Rectangle hKnobBounds;
    final Rectangle hScrollBounds;
    boolean hScrollOnBottom;
    public boolean isOut;
    final Vector2 lastPoint;
    float maxX;
    float maxY;
    private float overscrollDistance;
    private float overscrollSpeedMax;
    private float overscrollSpeedMin;
    private boolean overscrollX;
    private boolean overscrollY;
    private final Rectangle scissorBounds;
    boolean scrollX;
    boolean scrollY;
    private boolean scrollbarsOnTop;
    private boolean smoothScrolling;
    private ScrollPaneStyle style;
    boolean touchScrollH;
    boolean touchScrollV;
    final Rectangle vKnobBounds;
    final Rectangle vScrollBounds;
    boolean vScrollOnRight;
    float velocityX;
    float velocityY;
    float visualAmountX;
    float visualAmountY;
    private Actor widget;
    private final Rectangle widgetAreaBounds;
    private final Rectangle widgetCullingArea;
    
    public MyScrollPane(final Actor actor) {
        this(actor, new ScrollPaneStyle());
    }
    
    public MyScrollPane(final Actor actor, final Skin skin) {
        this(actor, skin.get(ScrollPaneStyle.class));
    }
    
    public MyScrollPane(final Actor actor, final Skin skin, final String s) {
        this(actor, skin.get(s, ScrollPaneStyle.class));
    }
    
    public MyScrollPane(final Actor widget, final ScrollPaneStyle style) {
        this.hScrollBounds = new Rectangle();
        this.vScrollBounds = new Rectangle();
        this.hKnobBounds = new Rectangle();
        this.vKnobBounds = new Rectangle();
        this.widgetAreaBounds = new Rectangle();
        this.widgetCullingArea = new Rectangle();
        this.scissorBounds = new Rectangle();
        this.vScrollOnRight = true;
        this.hScrollOnBottom = true;
        this.lastPoint = new Vector2();
        this.fadeScrollBars = true;
        this.smoothScrolling = true;
        this.fadeAlphaSeconds = 1.0f;
        this.fadeDelaySeconds = 1.0f;
        this.cancelTouchFocus = true;
        this.flickScroll = true;
        this.overscrollX = true;
        this.overscrollY = true;
        this.flingTime = 1.0f;
        this.overscrollDistance = 50.0f;
        this.overscrollSpeedMin = 30.0f;
        this.overscrollSpeedMax = 200.0f;
        this.clamp = true;
        this.draggingPointer = -1;
        this.isOut = false;
        if (style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = style;
        this.setWidget(widget);
        this.setWidth(150.0f);
        this.setHeight(150.0f);
        this.addCaptureListener(new InputListener() {
            private float handlePosition;
            
            @Override
            public boolean mouseMoved(final InputEvent inputEvent, final float n, final float n2) {
                if (!MyScrollPane.this.flickScroll) {
                    MyScrollPane.this.resetFade();
                }
                return false;
            }
            
            @Override
            public boolean touchDown(final InputEvent inputEvent, float amountY, float amountX, final int n, final int n2) {
                int n3 = -1;
                if (MyScrollPane.this.draggingPointer != -1) {
                    return false;
                }
                if (n == 0 && n2 != 0) {
                    return false;
                }
                MyScrollPane.this.getStage().setScrollFocus(MyScrollPane.this);
                if (!MyScrollPane.this.flickScroll) {
                    MyScrollPane.this.resetFade();
                }
                if (MyScrollPane.this.fadeAlpha == 0.0f) {
                    return false;
                }
                if (MyScrollPane.this.scrollX && MyScrollPane.this.hScrollBounds.contains(amountY, amountX)) {
                    inputEvent.stop();
                    MyScrollPane.this.resetFade();
                    if (MyScrollPane.this.hKnobBounds.contains(amountY, amountX)) {
                        MyScrollPane.this.lastPoint.set(amountY, amountX);
                        this.handlePosition = MyScrollPane.this.hKnobBounds.x;
                        MyScrollPane.this.touchScrollH = true;
                        MyScrollPane.this.draggingPointer = n;
                        return true;
                    }
                    final MyScrollPane this$0 = MyScrollPane.this;
                    amountX = MyScrollPane.this.amountX;
                    final float areaWidth = MyScrollPane.this.areaWidth;
                    if (amountY >= MyScrollPane.this.hKnobBounds.x) {
                        n3 = 1;
                    }
                    this$0.setScrollX(n3 * areaWidth + amountX);
                    return true;
                }
                else {
                    if (!MyScrollPane.this.scrollY || !MyScrollPane.this.vScrollBounds.contains(amountY, amountX)) {
                        return false;
                    }
                    inputEvent.stop();
                    MyScrollPane.this.resetFade();
                    if (MyScrollPane.this.vKnobBounds.contains(amountY, amountX)) {
                        MyScrollPane.this.lastPoint.set(amountY, amountX);
                        this.handlePosition = MyScrollPane.this.vKnobBounds.y;
                        MyScrollPane.this.touchScrollV = true;
                        MyScrollPane.this.draggingPointer = n;
                        return true;
                    }
                    final MyScrollPane this$ = MyScrollPane.this;
                    amountY = MyScrollPane.this.amountY;
                    final float areaHeight = MyScrollPane.this.areaHeight;
                    if (amountX < MyScrollPane.this.vKnobBounds.y) {
                        n3 = 1;
                    }
                    this$.setScrollY(n3 * areaHeight + amountY);
                    return true;
                }
            }
            
            @Override
            public void touchDragged(final InputEvent inputEvent, final float n, final float n2, final int n3) {
                if (n3 == MyScrollPane.this.draggingPointer) {
                    if (MyScrollPane.this.touchScrollH) {
                        final float handlePosition = this.handlePosition + (n - MyScrollPane.this.lastPoint.x);
                        this.handlePosition = handlePosition;
                        final float min = Math.min(MyScrollPane.this.hScrollBounds.x + MyScrollPane.this.hScrollBounds.width - MyScrollPane.this.hKnobBounds.width, Math.max(MyScrollPane.this.hScrollBounds.x, handlePosition));
                        final float n4 = MyScrollPane.this.hScrollBounds.width - MyScrollPane.this.hKnobBounds.width;
                        if (n4 != 0.0f) {
                            MyScrollPane.this.setScrollPercentX((min - MyScrollPane.this.hScrollBounds.x) / n4);
                        }
                        MyScrollPane.this.lastPoint.set(n, n2);
                        return;
                    }
                    if (MyScrollPane.this.touchScrollV) {
                        final float handlePosition2 = this.handlePosition + (n2 - MyScrollPane.this.lastPoint.y);
                        this.handlePosition = handlePosition2;
                        final float min2 = Math.min(MyScrollPane.this.vScrollBounds.y + MyScrollPane.this.vScrollBounds.height - MyScrollPane.this.vKnobBounds.height, Math.max(MyScrollPane.this.vScrollBounds.y, handlePosition2));
                        final float n5 = MyScrollPane.this.vScrollBounds.height - MyScrollPane.this.vKnobBounds.height;
                        if (n5 != 0.0f) {
                            MyScrollPane.this.setScrollPercentY(1.0f - (min2 - MyScrollPane.this.vScrollBounds.y) / n5);
                        }
                        MyScrollPane.this.lastPoint.set(n, n2);
                    }
                }
            }
            
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                if (n3 != MyScrollPane.this.draggingPointer) {
                    return;
                }
                MyScrollPane.this.cancel();
            }
        });
        this.addListener(this.flickScrollListener = new ActorGestureListener() {
            @Override
            public void fling(final InputEvent inputEvent, final float velocityX, final float n, final int n2) {
                if (Math.abs(velocityX) > 150.0f) {
                    MyScrollPane.this.flingTimer = MyScrollPane.this.flingTime;
                    MyScrollPane.this.velocityX = velocityX;
                    MyScrollPane.this.cancelTouchFocusedChild(inputEvent);
                }
                if (Math.abs(n) > 150.0f) {
                    MyScrollPane.this.flingTimer = MyScrollPane.this.flingTime;
                    MyScrollPane.this.velocityY = -n;
                    MyScrollPane.this.cancelTouchFocusedChild(inputEvent);
                }
            }
            
            @Override
            public boolean handle(final Event event) {
                if (super.handle(event)) {
                    if (((InputEvent)event).getType() == InputEvent.Type.touchDown) {
                        MyScrollPane.this.flingTimer = 0.0f;
                    }
                    return true;
                }
                return false;
            }
            
            @Override
            public void pan(final InputEvent inputEvent, final float n, final float n2, final float n3, final float n4) {
                if (!MyScrollPane.this.isOut) {
                    MyScrollPane.this.resetFade();
                    final MyScrollPane this$0 = MyScrollPane.this;
                    this$0.amountX -= n3;
                    final MyScrollPane this$ = MyScrollPane.this;
                    this$.amountY += n4;
                    MyScrollPane.this.clamp();
                }
            }
        });
        this.addListener(new InputListener() {
            @Override
            public boolean scrolled(final InputEvent inputEvent, final float n, final float n2, final int n3) {
                MyScrollPane.this.resetFade();
                if (MyScrollPane.this.scrollY) {
                    MyScrollPane.this.setScrollY(MyScrollPane.this.amountY + MyScrollPane.this.getMouseWheelY() * n3);
                }
                else if (MyScrollPane.this.scrollX) {
                    MyScrollPane.this.setScrollX(MyScrollPane.this.amountX + MyScrollPane.this.getMouseWheelX() * n3);
                }
                return true;
            }
        });
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        final boolean panning = this.flickScrollListener.getGestureDetector().isPanning();
        if (this.fadeAlpha > 0.0f && this.fadeScrollBars && !panning && !this.touchScrollH && !this.touchScrollV) {
            this.fadeDelay -= n;
            if (this.fadeDelay <= 0.0f) {
                this.fadeAlpha = Math.max(0.0f, this.fadeAlpha - n);
            }
        }
        if (this.flingTimer > 0.0f) {
            this.resetFade();
            final float n2 = this.flingTimer / this.flingTime;
            this.amountX -= this.velocityX * n2 * n;
            this.amountY -= this.velocityY * n2 * n;
            this.clamp();
            if (this.amountX == -this.overscrollDistance) {
                this.velocityX = 0.0f;
            }
            if (this.amountX >= this.maxX + this.overscrollDistance) {
                this.velocityX = 0.0f;
            }
            if (this.amountY == -this.overscrollDistance) {
                this.velocityY = 0.0f;
            }
            if (this.amountY >= this.maxY + this.overscrollDistance) {
                this.velocityY = 0.0f;
            }
            this.flingTimer -= n;
            if (this.flingTimer <= 0.0f) {
                this.velocityX = 0.0f;
                this.velocityY = 0.0f;
            }
        }
        if (this.smoothScrolling && this.flingTimer <= 0.0f && !this.touchScrollH && !this.touchScrollV && !panning) {
            if (this.visualAmountX != this.amountX) {
                if (this.visualAmountX < this.amountX) {
                    this.visualScrollX(Math.min(this.amountX, this.visualAmountX + Math.max(150.0f * n, (this.amountX - this.visualAmountX) * 5.0f * n)));
                }
                else {
                    this.visualScrollX(Math.max(this.amountX, this.visualAmountX - Math.max(150.0f * n, (this.visualAmountX - this.amountX) * 5.0f * n)));
                }
            }
            if (this.visualAmountY != this.amountY) {
                if (this.visualAmountY < this.amountY) {
                    this.visualScrollY(Math.min(this.amountY, this.visualAmountY + Math.max(150.0f * n, (this.amountY - this.visualAmountY) * 5.0f * n)));
                }
                else {
                    this.visualScrollY(Math.max(this.amountY, this.visualAmountY - Math.max(150.0f * n, (this.visualAmountY - this.amountY) * 5.0f * n)));
                }
            }
        }
        else {
            if (this.visualAmountX != this.amountX) {
                this.visualScrollX(this.amountX);
            }
            if (this.visualAmountY != this.amountY) {
                this.visualScrollY(this.amountY);
            }
        }
        if (!panning) {
            if (this.overscrollX && this.scrollX) {
                if (this.amountX < 0.0f) {
                    this.resetFade();
                    this.amountX += (this.overscrollSpeedMin + (this.overscrollSpeedMax - this.overscrollSpeedMin) * -this.amountX / this.overscrollDistance) * n;
                    if (this.amountX > 0.0f) {
                        this.scrollX(0.0f);
                    }
                }
                else if (this.amountX > this.maxX) {
                    this.resetFade();
                    this.amountX -= (this.overscrollSpeedMin + (this.overscrollSpeedMax - this.overscrollSpeedMin) * -(this.maxX - this.amountX) / this.overscrollDistance) * n;
                    if (this.amountX < this.maxX) {
                        this.scrollX(this.maxX);
                    }
                }
            }
            if (this.overscrollY && this.scrollY) {
                if (this.amountY < 0.0f) {
                    this.resetFade();
                    this.amountY += (this.overscrollSpeedMin + (this.overscrollSpeedMax - this.overscrollSpeedMin) * -this.amountY / this.overscrollDistance) * n;
                    if (this.amountY > 0.0f) {
                        this.scrollY(0.0f);
                    }
                }
                else if (this.amountY > this.maxY) {
                    this.resetFade();
                    this.amountY -= (this.overscrollSpeedMin + (this.overscrollSpeedMax - this.overscrollSpeedMin) * -(this.maxY - this.amountY) / this.overscrollDistance) * n;
                    if (this.amountY < this.maxY) {
                        this.scrollY(this.maxY);
                    }
                }
            }
        }
    }
    
    @Override
    public void addActor(final Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }
    
    @Override
    public void addActorAfter(final Actor actor, final Actor actor2) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }
    
    @Override
    public void addActorAt(final int n, final Actor actor) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }
    
    @Override
    public void addActorBefore(final Actor actor, final Actor actor2) {
        throw new UnsupportedOperationException("Use ScrollPane#setWidget.");
    }
    
    public void cancel() {
        this.draggingPointer = -1;
        this.touchScrollH = false;
        this.touchScrollV = false;
        this.flickScrollListener.getGestureDetector().cancel();
    }
    
    void cancelTouchFocusedChild(final InputEvent inputEvent) {
        if (this.cancelTouchFocus) {
            final Stage stage = this.getStage();
            if (stage != null) {
                stage.cancelTouchFocus(this.flickScrollListener, this);
            }
        }
    }
    
    void clamp() {
        if (!this.clamp) {
            return;
        }
        float n;
        if (this.overscrollX) {
            n = MathUtils.clamp(this.amountX, -this.overscrollDistance, this.maxX + this.overscrollDistance);
        }
        else {
            n = MathUtils.clamp(this.amountX, 0.0f, this.maxX);
        }
        this.scrollX(n);
        float n2;
        if (this.overscrollY) {
            n2 = MathUtils.clamp(this.amountY, -this.overscrollDistance, this.maxY + this.overscrollDistance);
        }
        else {
            n2 = MathUtils.clamp(this.amountY, 0.0f, this.maxY);
        }
        this.scrollY(n2);
    }
    
    @Override
    public void draw(final SpriteBatch spriteBatch, float x) {
        if (this.widget == null) {
            return;
        }
        this.validate();
        this.applyTransform(spriteBatch, this.computeTransform());
        if (this.scrollX) {
            this.hKnobBounds.x = this.hScrollBounds.x + (int)((this.hScrollBounds.width - this.hKnobBounds.width) * this.getScrollPercentX());
        }
        if (this.scrollY) {
            this.vKnobBounds.y = this.vScrollBounds.y + (int)((this.vScrollBounds.height - this.vKnobBounds.height) * (1.0f - this.getScrollPercentY()));
        }
        final float y = this.widgetAreaBounds.y;
        float n;
        if (!this.scrollY) {
            n = y - (int)this.maxY;
        }
        else {
            n = y - (int)(this.maxY - this.visualAmountY);
        }
        float n2 = n;
        if (!this.fadeScrollBars) {
            n2 = n;
            if (this.scrollbarsOnTop) {
                n2 = n;
                if (this.scrollX) {
                    float minHeight = 0.0f;
                    if (this.style.hScrollKnob != null) {
                        minHeight = this.style.hScrollKnob.getMinHeight();
                    }
                    float max = minHeight;
                    if (this.style.hScroll != null) {
                        max = Math.max(minHeight, this.style.hScroll.getMinHeight());
                    }
                    n2 = n + max;
                }
            }
        }
        float x2 = this.widgetAreaBounds.x;
        if (this.scrollX) {
            x2 -= (int)this.visualAmountX;
        }
        this.widget.setPosition(x2, n2);
        if (this.widget instanceof Cullable) {
            this.widgetCullingArea.x = -this.widget.getX() + this.widgetAreaBounds.x;
            this.widgetCullingArea.y = -this.widget.getY() + this.widgetAreaBounds.y;
            this.widgetCullingArea.width = this.widgetAreaBounds.width;
            this.widgetCullingArea.height = this.widgetAreaBounds.height;
            ((Cullable)this.widget).setCullingArea(this.widgetCullingArea);
        }
        this.getStage().calculateScissors(this.widgetAreaBounds, this.scissorBounds);
        final Color color = this.getColor();
        spriteBatch.setColor(color.r, color.g, color.b, color.a * x);
        if (this.style.background != null) {
            this.style.background.draw(spriteBatch, 0.0f, 0.0f, this.getWidth(), this.getHeight());
        }
        spriteBatch.flush();
        if (ScissorStack.pushScissors(this.scissorBounds)) {
            this.drawChildren(spriteBatch, x);
            ScissorStack.popScissors();
        }
        spriteBatch.setColor(color.r, color.g, color.b, color.a * x * Interpolation.fade.apply(this.fadeAlpha / this.fadeAlphaSeconds));
        if (this.scrollX && this.scrollY && this.style.corner != null) {
            final Drawable corner = this.style.corner;
            x = this.hScrollBounds.x;
            corner.draw(spriteBatch, this.hScrollBounds.width + x, this.hScrollBounds.y, this.vScrollBounds.width, this.vScrollBounds.y);
        }
        if (this.scrollX) {
            if (this.style.hScroll != null) {
                this.style.hScroll.draw(spriteBatch, this.hScrollBounds.x, this.hScrollBounds.y, this.hScrollBounds.width, this.hScrollBounds.height);
            }
            if (this.style.hScrollKnob != null) {
                this.style.hScrollKnob.draw(spriteBatch, this.hKnobBounds.x, this.hKnobBounds.y, this.hKnobBounds.width, this.hKnobBounds.height);
            }
        }
        if (this.scrollY) {
            if (this.style.vScroll != null) {
                this.style.vScroll.draw(spriteBatch, this.vScrollBounds.x, this.vScrollBounds.y, this.vScrollBounds.width, this.vScrollBounds.height);
            }
            if (this.style.vScrollKnob != null) {
                this.style.vScrollKnob.draw(spriteBatch, this.vKnobBounds.x, this.vKnobBounds.y, this.vKnobBounds.width, this.vKnobBounds.height);
            }
        }
        this.resetTransform(spriteBatch);
    }
    
    public float getMaxX() {
        return this.maxX;
    }
    
    public float getMaxY() {
        return this.maxY;
    }
    
    @Override
    public float getMinHeight() {
        return 0.0f;
    }
    
    @Override
    public float getMinWidth() {
        return 0.0f;
    }
    
    protected float getMouseWheelX() {
        return Math.max(this.areaWidth * 0.9f, this.maxX * 0.1f) / 4.0f;
    }
    
    protected float getMouseWheelY() {
        return Math.max(this.areaHeight * 0.9f, this.maxY * 0.1f) / 4.0f;
    }
    
    @Override
    public float getPrefHeight() {
        if (this.widget instanceof Layout) {
            float prefHeight = ((Layout)this.widget).getPrefHeight();
            if (this.style.background != null) {
                prefHeight += this.style.background.getTopHeight() + this.style.background.getBottomHeight();
            }
            return prefHeight;
        }
        return 150.0f;
    }
    
    @Override
    public float getPrefWidth() {
        if (this.widget instanceof Layout) {
            float prefWidth = ((Layout)this.widget).getPrefWidth();
            if (this.style.background != null) {
                prefWidth += this.style.background.getLeftWidth() + this.style.background.getRightWidth();
            }
            return prefWidth;
        }
        return 150.0f;
    }
    
    public float getScrollBarHeight() {
        if (this.style.hScrollKnob == null || !this.scrollX) {
            return 0.0f;
        }
        return this.style.hScrollKnob.getMinHeight();
    }
    
    public float getScrollBarWidth() {
        if (this.style.vScrollKnob == null || !this.scrollY) {
            return 0.0f;
        }
        return this.style.vScrollKnob.getMinWidth();
    }
    
    public float getScrollPercentX() {
        return MathUtils.clamp(this.amountX / this.maxX, 0.0f, 1.0f);
    }
    
    public float getScrollPercentY() {
        return MathUtils.clamp(this.amountY / this.maxY, 0.0f, 1.0f);
    }
    
    public float getScrollX() {
        return this.amountX;
    }
    
    public float getScrollY() {
        return this.amountY;
    }
    
    public ScrollPaneStyle getStyle() {
        return this.style;
    }
    
    public float getVelocityX() {
        if (this.flingTimer <= 0.0f) {
            return 0.0f;
        }
        final float n = this.flingTimer / this.flingTime;
        final float n2 = n * (n * n);
        return this.velocityX * n2 * n2 * n2;
    }
    
    public float getVelocityY() {
        return this.velocityY;
    }
    
    public float getVisualScrollX() {
        if (!this.scrollX) {
            return 0.0f;
        }
        return this.visualAmountX;
    }
    
    public float getVisualScrollY() {
        if (!this.scrollY) {
            return 0.0f;
        }
        return this.visualAmountY;
    }
    
    public Actor getWidget() {
        return this.widget;
    }
    
    @Override
    public Actor hit(final float n, final float n2, final boolean b) {
        if (n >= 0.0f && n < this.getWidth() && n2 >= 0.0f && n2 < this.getHeight()) {
            if (this.scrollX) {
                final Actor actor = this;
                if (this.hScrollBounds.contains(n, n2)) {
                    return actor;
                }
            }
            if (this.scrollY) {
                final Actor actor = this;
                if (this.vScrollBounds.contains(n, n2)) {
                    return actor;
                }
            }
            return super.hit(n, n2, b);
        }
        return null;
    }
    
    public boolean isDragging() {
        return this.draggingPointer != -1;
    }
    
    public boolean isFlinging() {
        return this.flingTimer > 0.0f;
    }
    
    public boolean isForceScrollX() {
        return this.forceScrollX;
    }
    
    public boolean isForceScrollY() {
        return this.forceScrollY;
    }
    
    public boolean isPanning() {
        return this.flickScrollListener.getGestureDetector().isPanning();
    }
    
    public boolean isScrollX() {
        return this.scrollX;
    }
    
    public boolean isScrollY() {
        return this.scrollY;
    }
    
    @Override
    public void layout() {
        final Drawable background = this.style.background;
        final Drawable hScrollKnob = this.style.hScrollKnob;
        final Drawable vScrollKnob = this.style.vScrollKnob;
        float leftWidth = 0.0f;
        float rightWidth = 0.0f;
        float topHeight = 0.0f;
        float bottomHeight = 0.0f;
        if (background != null) {
            leftWidth = background.getLeftWidth();
            rightWidth = background.getRightWidth();
            topHeight = background.getTopHeight();
            bottomHeight = background.getBottomHeight();
        }
        final float width = this.getWidth();
        final float height = this.getHeight();
        float minHeight = 0.0f;
        if (hScrollKnob != null) {
            minHeight = hScrollKnob.getMinHeight();
        }
        float max = minHeight;
        if (this.style.hScroll != null) {
            max = Math.max(minHeight, this.style.hScroll.getMinHeight());
        }
        float minWidth = 0.0f;
        if (vScrollKnob != null) {
            minWidth = vScrollKnob.getMinWidth();
        }
        float max2 = minWidth;
        if (this.style.vScroll != null) {
            max2 = Math.max(minWidth, this.style.vScroll.getMinWidth());
        }
        this.areaWidth = width - leftWidth - rightWidth;
        this.areaHeight = height - topHeight - bottomHeight;
        if (this.widget != null) {
            float n;
            float n2;
            if (this.widget instanceof Layout) {
                final Layout layout = (Layout)this.widget;
                n = layout.getPrefWidth();
                n2 = layout.getPrefHeight();
            }
            else {
                n = this.widget.getWidth();
                n2 = this.widget.getHeight();
            }
            this.scrollX = (this.forceScrollX || (n > this.areaWidth && !this.disableX));
            this.scrollY = (this.forceScrollY || (n2 > this.areaHeight && !this.disableY));
            final boolean fadeScrollBars = this.fadeScrollBars;
            if (!fadeScrollBars) {
                if (this.scrollY) {
                    this.areaWidth -= max2;
                    if (!this.scrollX && n > this.areaWidth && !this.disableX) {
                        this.scrollX = true;
                    }
                }
                if (this.scrollX) {
                    this.areaHeight -= max;
                    if (!this.scrollY && n2 > this.areaHeight && !this.disableY) {
                        this.scrollY = true;
                        this.areaWidth -= max2;
                    }
                }
            }
            this.widgetAreaBounds.set(leftWidth, bottomHeight, this.areaWidth, this.areaHeight);
            if (fadeScrollBars) {
                if (this.scrollX) {
                    this.areaHeight -= max;
                }
                if (this.scrollY) {
                    this.areaWidth -= max2;
                }
            }
            else if (this.scrollbarsOnTop) {
                if (this.scrollX) {
                    final Rectangle widgetAreaBounds = this.widgetAreaBounds;
                    widgetAreaBounds.height += max;
                }
                if (this.scrollY) {
                    final Rectangle widgetAreaBounds2 = this.widgetAreaBounds;
                    widgetAreaBounds2.width += max2;
                }
            }
            else {
                if (this.scrollX) {
                    if (this.hScrollOnBottom) {
                        final Rectangle widgetAreaBounds3 = this.widgetAreaBounds;
                        widgetAreaBounds3.y += max;
                    }
                    else {
                        this.widgetAreaBounds.y = 0.0f;
                    }
                }
                if (this.scrollY) {
                    if (this.vScrollOnRight) {
                        this.widgetAreaBounds.x = 0.0f;
                    }
                    else {
                        final Rectangle widgetAreaBounds4 = this.widgetAreaBounds;
                        widgetAreaBounds4.x += max2;
                    }
                }
            }
            float max3;
            if (this.disableX) {
                max3 = width;
            }
            else {
                max3 = Math.max(this.areaWidth, n);
            }
            float max4;
            if (this.disableY) {
                max4 = height;
            }
            else {
                max4 = Math.max(this.areaHeight, n2);
            }
            this.maxX = max3 - this.areaWidth;
            this.maxY = max4 - this.areaHeight;
            if (fadeScrollBars) {
                if (this.scrollX) {
                    this.maxY -= max;
                }
                if (this.scrollY) {
                    this.maxX -= max2;
                }
            }
            this.scrollX(MathUtils.clamp(this.amountX, 0.0f, this.maxX));
            this.scrollY(MathUtils.clamp(this.amountY, 0.0f, this.maxY));
            if (this.scrollX) {
                if (hScrollKnob != null) {
                    float n3;
                    if (this.style.hScroll != null) {
                        n3 = this.style.hScroll.getMinHeight();
                    }
                    else {
                        n3 = hScrollKnob.getMinHeight();
                    }
                    float n4;
                    if (this.vScrollOnRight) {
                        n4 = leftWidth;
                    }
                    else {
                        n4 = leftWidth + max2;
                    }
                    float n5;
                    if (this.hScrollOnBottom) {
                        n5 = bottomHeight;
                    }
                    else {
                        n5 = height - topHeight - n3;
                    }
                    this.hScrollBounds.set(n4, n5, this.areaWidth, n3);
                    this.hKnobBounds.width = Math.max(hScrollKnob.getMinWidth(), (int)(this.hScrollBounds.width * this.areaWidth / max3));
                    this.hKnobBounds.height = hScrollKnob.getMinHeight();
                    this.hKnobBounds.x = this.hScrollBounds.x + (int)((this.hScrollBounds.width - this.hKnobBounds.width) * this.getScrollPercentX());
                    this.hKnobBounds.y = this.hScrollBounds.y;
                }
                else {
                    this.hScrollBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                    this.hKnobBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                }
            }
            if (this.scrollY) {
                if (vScrollKnob != null) {
                    float n6;
                    if (this.style.vScroll != null) {
                        n6 = this.style.vScroll.getMinWidth();
                    }
                    else {
                        n6 = vScrollKnob.getMinWidth();
                    }
                    if (this.hScrollOnBottom) {
                        bottomHeight = height - topHeight - this.areaHeight;
                    }
                    float n7;
                    if (this.vScrollOnRight) {
                        n7 = width - rightWidth - n6;
                    }
                    else {
                        n7 = leftWidth;
                    }
                    this.vScrollBounds.set(n7, bottomHeight, n6, this.areaHeight);
                    this.vKnobBounds.width = vScrollKnob.getMinWidth();
                    this.vKnobBounds.height = Math.max(vScrollKnob.getMinHeight(), (int)(this.vScrollBounds.height * this.areaHeight / max4));
                    if (this.vScrollOnRight) {
                        this.vKnobBounds.x = width - rightWidth - vScrollKnob.getMinWidth();
                    }
                    else {
                        this.vKnobBounds.x = leftWidth;
                    }
                    this.vKnobBounds.y = this.vScrollBounds.y + (int)((this.vScrollBounds.height - this.vKnobBounds.height) * (1.0f - this.getScrollPercentY()));
                }
                else {
                    this.vScrollBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                    this.vKnobBounds.set(0.0f, 0.0f, 0.0f, 0.0f);
                }
            }
            this.widget.setSize(max3, max4);
            if (this.widget instanceof Layout) {
                ((Layout)this.widget).validate();
            }
        }
    }
    
    @Override
    public boolean removeActor(final Actor actor) {
        if (actor != this.widget) {
            return false;
        }
        this.setWidget(null);
        return true;
    }
    
    void resetFade() {
        this.fadeAlpha = this.fadeAlphaSeconds;
        this.fadeDelay = this.fadeDelaySeconds;
    }
    
    public void scrollTo(float amountY, final float n, float n2, final float n3) {
        float amountX;
        if (amountY + n2 > this.areaWidth + (amountX = this.amountX)) {
            amountX = amountY + n2 - this.areaWidth;
        }
        n2 = amountX;
        if (amountY < amountX) {
            n2 = amountY;
        }
        this.scrollX(MathUtils.clamp(n2, 0.0f, this.maxX));
        n2 = (amountY = this.amountY);
        if (n2 > this.maxY - n - n3 + this.areaHeight) {
            amountY = this.maxY - n - n3 + this.areaHeight;
        }
        n2 = amountY;
        if (amountY < this.maxY - n) {
            n2 = this.maxY - n;
        }
        this.scrollY(MathUtils.clamp(n2, 0.0f, this.maxY));
    }
    
    public void scrollToCenter(float n, float n2, float amountY, final float n3) {
        float amountX;
        if (n + amountY > this.areaWidth + (amountX = this.amountX)) {
            amountX = n + amountY - this.areaWidth;
        }
        amountY = amountX;
        if (n < amountX) {
            amountY = n;
        }
        this.scrollX(MathUtils.clamp(amountY, 0.0f, this.maxX));
        amountY = this.amountY;
        n2 = this.maxY - n2 + this.areaHeight / 2.0f - n3 / 2.0f;
        Label_0117: {
            if (amountY >= n2 - this.areaHeight / 4.0f) {
                n = amountY;
                if (amountY <= this.areaHeight / 4.0f + n2) {
                    break Label_0117;
                }
            }
            n = n2;
        }
        this.scrollY(MathUtils.clamp(n, 0.0f, this.maxY));
    }
    
    protected void scrollX(final float amountX) {
        this.amountX = amountX;
    }
    
    protected void scrollY(final float amountY) {
        this.amountY = amountY;
    }
    
    public void setCancelTouchFocus(final boolean cancelTouchFocus) {
        this.cancelTouchFocus = cancelTouchFocus;
    }
    
    public void setClamp(final boolean clamp) {
        this.clamp = clamp;
    }
    
    public void setFadeScrollBars(final boolean fadeScrollBars) {
        if (this.fadeScrollBars == fadeScrollBars) {
            return;
        }
        if (!(this.fadeScrollBars = fadeScrollBars)) {
            this.fadeAlpha = this.fadeAlphaSeconds;
        }
        this.invalidate();
    }
    
    public void setFlickScroll(final boolean flickScroll) {
        if (this.flickScroll == flickScroll) {
            return;
        }
        this.flickScroll = flickScroll;
        if (flickScroll) {
            this.addListener(this.flickScrollListener);
        }
        else {
            this.removeListener(this.flickScrollListener);
        }
        this.invalidate();
    }
    
    public void setFlingTime(final float flingTime) {
        this.flingTime = flingTime;
    }
    
    public void setForceScroll(final boolean forceScrollX, final boolean forceScrollY) {
        this.forceScrollX = forceScrollX;
        this.forceScrollY = forceScrollY;
    }
    
    public void setOverscroll(final boolean overscrollX, final boolean overscrollY) {
        this.overscrollX = overscrollX;
        this.overscrollY = overscrollY;
    }
    
    public void setScrollBarPositions(final boolean hScrollOnBottom, final boolean vScrollOnRight) {
        this.hScrollOnBottom = hScrollOnBottom;
        this.vScrollOnRight = vScrollOnRight;
    }
    
    public void setScrollPercentX(final float n) {
        this.scrollX(this.maxX * MathUtils.clamp(n, 0.0f, 1.0f));
    }
    
    public void setScrollPercentY(final float n) {
        this.scrollY(this.maxY * MathUtils.clamp(n, 0.0f, 1.0f));
    }
    
    public void setScrollX(final float n) {
        this.scrollX(MathUtils.clamp(n, 0.0f, this.maxX));
    }
    
    public void setScrollY(final float n) {
        this.scrollY(MathUtils.clamp(n, 0.0f, this.maxY));
    }
    
    public void setScrollbarsOnTop(final boolean scrollbarsOnTop) {
        this.scrollbarsOnTop = scrollbarsOnTop;
        this.invalidate();
    }
    
    public void setScrollingDisabled(final boolean disableX, final boolean disableY) {
        this.disableX = disableX;
        this.disableY = disableY;
    }
    
    public void setSmoothScrolling(final boolean smoothScrolling) {
        this.smoothScrolling = smoothScrolling;
    }
    
    public void setStyle(final ScrollPaneStyle style) {
        if (style == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        this.style = style;
        this.invalidateHierarchy();
    }
    
    public void setVelocityX(final float velocityX) {
        this.velocityX = velocityX;
    }
    
    public void setVelocityY(final float velocityY) {
        this.velocityY = velocityY;
    }
    
    public void setWidget(final Actor widget) {
        if (widget == this) {
            throw new IllegalArgumentException("widget cannot be same object");
        }
        if (this.widget != null) {
            super.removeActor(this.widget);
        }
        if ((this.widget = widget) != null) {
            super.addActor(widget);
        }
    }
    
    public void setupFadeScrollBars(final float fadeAlphaSeconds, final float fadeDelaySeconds) {
        this.fadeAlphaSeconds = fadeAlphaSeconds;
        this.fadeDelaySeconds = fadeDelaySeconds;
    }
    
    public void setupOverscroll(final float overscrollDistance, final float overscrollSpeedMin, final float overscrollSpeedMax) {
        this.overscrollDistance = overscrollDistance;
        this.overscrollSpeedMin = overscrollSpeedMin;
        this.overscrollSpeedMax = overscrollSpeedMax;
    }
    
    public void updateVisualScroll() {
        this.visualAmountX = this.amountX;
        this.visualAmountY = this.amountY;
    }
    
    protected void visualScrollX(final float visualAmountX) {
        this.visualAmountX = visualAmountX;
    }
    
    protected void visualScrollY(final float visualAmountY) {
        this.visualAmountY = visualAmountY;
    }
    
    public static class ScrollPaneStyle
    {
        public Drawable background;
        public Drawable corner;
        public Drawable hScroll;
        public Drawable hScrollKnob;
        public Drawable vScroll;
        public Drawable vScrollKnob;
        
        public ScrollPaneStyle() {
        }
        
        public ScrollPaneStyle(final Drawable background, final Drawable hScroll, final Drawable hScrollKnob, final Drawable vScroll, final Drawable vScrollKnob) {
            this.background = background;
            this.hScroll = hScroll;
            this.hScrollKnob = hScrollKnob;
            this.vScroll = vScroll;
            this.vScrollKnob = vScrollKnob;
        }
        
        public ScrollPaneStyle(final ScrollPaneStyle scrollPaneStyle) {
            this.background = scrollPaneStyle.background;
            this.hScroll = scrollPaneStyle.hScroll;
            this.hScrollKnob = scrollPaneStyle.hScrollKnob;
            this.vScroll = scrollPaneStyle.vScroll;
            this.vScrollKnob = scrollPaneStyle.vScrollKnob;
        }
    }
}
