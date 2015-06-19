package com.fxb.razor.utils.ui;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.graphics.g2d.*;

public class AutoLineLabel extends Label
{
    float textWidth;
    
    public AutoLineLabel(final String info, final LabelStyle labelStyle, final float textWidth) {
        super(info, labelStyle);
        this.textWidth = textWidth;
        this.setInfo(info);
    }
    
    public static AutoLineLabel createRoman(final Group group, final String s, final float n, final float n2, final float n3) {
        final AutoLineLabel autoLineLabel = new AutoLineLabel(s, UiHandle.getLabelStyle15(), n3);
        autoLineLabel.setPosition(n, n2);
        group.addActor(autoLineLabel);
        return autoLineLabel;
    }
    
    public static AutoLineLabel createRoman(final MyGroup myGroup, final String s, final float n, final float n2, final float n3) {
        final AutoLineLabel autoLineLabel = new AutoLineLabel(s, UiHandle.getLabelStyle15(), n3);
        autoLineLabel.setPosition(n, n2);
        myGroup.addActor(autoLineLabel);
        return autoLineLabel;
    }
    
    public static AutoLineLabel createRoman16(final Group group, final String s, final float n, final float n2, final float n3) {
        final AutoLineLabel autoLineLabel = new AutoLineLabel(s, UiHandle.getLabelStyle14(), n3);
        autoLineLabel.setPosition(n, n2);
        group.addActor(autoLineLabel);
        return autoLineLabel;
    }
    
    public static AutoLineLabel createRoman16(final MyGroup myGroup, final String s, final float n, final float n2, final float n3) {
        final AutoLineLabel autoLineLabel = new AutoLineLabel(s, UiHandle.getLabelStyle14(), n3);
        autoLineLabel.setPosition(n, n2);
        myGroup.addActor(autoLineLabel);
        return autoLineLabel;
    }
    
    public static AutoLineLabel createRomanBlue(final Group group, final String s, final float n, final float n2, final float n3) {
        final AutoLineLabel autoLineLabel = new AutoLineLabel(s, UiHandle.getLabelStyle15(), n3);
        autoLineLabel.setPosition(n, n2);
        group.addActor(autoLineLabel);
        return autoLineLabel;
    }
    
    public void setInfo(final String s) {
        final StringBuilder sb = new StringBuilder();
        float n = 0.0f;
        final BitmapFont font = this.getStyle().font;
        final String[] split = s.split(" ");
        for (int i = 0; i < split.length; ++i) {
            final float width = font.getBounds(split[i]).width;
            float n2 = n;
            if (n + width >= this.textWidth) {
                sb.append('\n');
                n2 = 0.0f;
            }
            final float n3 = n2 + width;
            sb.append(split[i]);
            n = n3;
            if (i != split.length - 1) {
                n = n3 + font.getBounds(" ").width;
                sb.append(' ');
            }
        }
        super.setText(sb.toString());
    }
}
