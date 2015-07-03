package com.gleejet.sun.objects.maingun;

import com.gleejet.sun.common.*;
import com.gleejet.sun.utils.ui.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class MainGunShow extends MyGroup
{
    private Group groupGun;
    private MyImage imgDrawf;
    private MyImage imgGunBody;
    private MyImage imgGunFront;
    private MyImage imgGunFront2;
    private MyImage imgGunShelf;
    private MyImage imgPlatform;
    
    public MainGunShow(final int n) {
        this.groupGun = new Group();
        float n2 = 0.0f;
        switch (n) {
            case 0: {
                this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-1"));
                this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-1"));
                this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-1"));
                this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-1"));
                this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("qiangjia-1"));
                this.imgDrawf.setPosition(5.0f, 17.0f);
                this.imgPlatform.setPosition(0.0f, 0.0f);
                this.imgGunShelf.setPosition(49.0f, 3.0f);
                this.imgGunFront.setPosition(60.0f, 12.0f);
                this.imgGunBody.setPosition(0.0f, 0.0f);
                this.groupGun.addActor(this.imgGunFront);
                this.groupGun.addActor(this.imgGunBody);
                this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
                this.groupGun.setOrigin(24.0f, 12.0f);
                this.groupGun.setPosition(36.0f, 18.0f);
                this.addActor(this.imgPlatform);
                this.addActor(this.groupGun);
                this.addActor(this.imgGunShelf);
                this.addActor(this.imgDrawf);
                this.setSize(120.0f, 70.0f);
                break;
            }
            case 1: {
                this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-2"));
                this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-2"));
                this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-2"));
                this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-2"));
                this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("qiangjia-2"));
                this.imgDrawf.setPosition(15.0f, 18.0f);
                this.imgPlatform.setPosition(-4.0f, 0.0f);
                this.imgGunShelf.setPosition(60.0f, 3.0f);
                this.imgGunFront.setPosition(50.0f, 4.0f);
                this.imgGunBody.setPosition(0.0f, 0.0f);
                this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
                this.groupGun.setOrigin(24.0f, 12.0f);
                this.groupGun.setPosition(47.0f, 23.0f);
                this.addActor(this.imgPlatform);
                this.addActor(this.groupGun);
                this.addActor(this.imgGunShelf);
                this.addActor(this.imgDrawf);
                this.setSize(120.0f, 70.0f);
                this.groupGun.addActor(this.imgGunFront);
                this.groupGun.addActor(this.imgGunBody);
                break;
            }
            case 2: {
                this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-3"));
                this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-3"));
                this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-3"));
                this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-3"));
                this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("zhijia-3"));
                this.imgDrawf.setPosition(18.0f, 14.0f);
                this.imgPlatform.setPosition(0.0f, 0.0f);
                this.imgGunShelf.setPosition(49.0f, 3.0f);
                this.imgGunFront.setPosition(55.0f, 13.0f);
                this.imgGunBody.setPosition(0.0f, 0.0f);
                this.groupGun.addActor(this.imgGunFront);
                this.groupGun.addActor(this.imgGunBody);
                this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
                this.groupGun.setOrigin(24.0f, 12.0f);
                this.groupGun.setPosition(48.0f, 16.0f);
                this.addActor(this.imgPlatform);
                this.addActor(this.imgGunShelf);
                this.addActor(this.imgDrawf);
                this.addActor(this.groupGun);
                this.setSize(120.0f, 70.0f);
                break;
            }
            case 3: {
                this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-4"));
                this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-4"));
                this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-4"));
                this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-4"));
                this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("qiangjia-4"));
                this.imgDrawf.setPosition(11.0f, 17.0f);
                this.imgPlatform.setPosition(0.0f, 0.0f);
                this.imgGunShelf.setPosition(66.0f, 6.0f);
                this.imgGunFront.setPosition(28.0f, -8.0f);
                this.imgGunBody.setPosition(0.0f, 0.0f);
                this.groupGun.addActor(this.imgGunBody);
                this.groupGun.addActor(this.imgGunFront);
                this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
                this.groupGun.setOrigin(24.0f, 12.0f);
                this.groupGun.setPosition(55.0f, 24.0f);
                this.addActor(this.imgPlatform);
                this.addActor(this.groupGun);
                this.addActor(this.imgGunShelf);
                this.addActor(this.imgDrawf);
                this.setSize(120.0f, 70.0f);
                break;
            }
            case 4: {
                this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-5"));
                this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-5"));
                this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-5"));
                this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-5"));
                this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("qiangjia-5"));
                this.imgDrawf.setPosition(13.0f, 23.0f);
                this.imgPlatform.setPosition(0.0f, 0.0f);
                this.imgGunShelf.setPosition(56.0f, 24.0f);
                this.imgGunFront.setPosition(42.0f, 0.0f);
                this.imgGunBody.setPosition(0.0f, 0.0f);
                this.groupGun.addActor(this.imgGunFront);
                this.groupGun.addActor(this.imgGunBody);
                this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
                this.groupGun.setOrigin(24.0f, 12.0f);
                this.groupGun.setPosition(45.0f, 34.0f);
                this.addActor(this.imgDrawf);
                this.addActor(this.groupGun);
                this.addActor(this.imgGunShelf);
                this.addActor(this.imgPlatform);
                this.setSize(120.0f, 70.0f);
                n2 = -8.0f;
                break;
            }
            case 5: {
                this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-6"));
                this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-6"));
                this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-6"));
                this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-6"));
                this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("qiangjia-6"));
                this.imgDrawf.setPosition(18.0f, 14.0f);
                this.imgPlatform.setPosition(0.0f, 0.0f);
                this.imgGunShelf.setPosition(55.0f, 0.0f);
                this.imgGunFront.setPosition(62.0f, 11.0f);
                this.imgGunBody.setPosition(0.0f, 0.0f);
                this.groupGun.addActor(this.imgGunFront);
                this.groupGun.addActor(this.imgGunBody);
                this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
                this.groupGun.setOrigin(24.0f, 12.0f);
                this.groupGun.setPosition(48.0f, 17.0f);
                this.addActor(this.imgDrawf);
                this.addActor(this.imgPlatform);
                this.addActor(this.groupGun);
                this.addActor(this.imgGunShelf);
                this.setSize(120.0f, 70.0f);
                break;
            }
            case 6: {
                this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-7"));
                this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-7"));
                this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-7"));
                this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-7"));
                this.imgGunFront2 = new MyImage(Assets.atlasMainGun.findRegion("qiangtou2-7"));
                this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("zhijia-7"));
                this.imgDrawf.setPosition(12.0f, 12.0f);
                this.imgPlatform.setPosition(0.0f, 0.0f);
                this.imgGunShelf.setPosition(53.0f, 0.0f);
                this.imgGunFront.setPosition(60.0f, 5.0f);
                this.imgGunFront2.setPosition(60.0f, 25.0f);
                this.imgGunBody.setPosition(0.0f, 0.0f);
                this.groupGun.addActor(this.imgGunFront);
                this.groupGun.addActor(this.imgGunFront2);
                this.groupGun.addActor(this.imgGunBody);
                this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
                this.groupGun.setOrigin(24.0f, 12.0f);
                this.groupGun.setPosition(52.0f, 18.0f);
                this.addActor(this.imgDrawf);
                this.addActor(this.imgPlatform);
                this.addActor(this.groupGun);
                this.addActor(this.imgGunShelf);
                this.setSize(120.0f, 70.0f);
                break;
            }
            case 7: {
                this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-8"));
                this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-8"));
                this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-8"));
                this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-8"));
                this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("zhijia-8"));
                this.imgDrawf.setPosition(6.0f, 31.0f);
                this.imgPlatform.setPosition(0.0f, 0.0f);
                this.imgGunShelf.setPosition(55.0f, 23.0f);
                this.imgGunFront.setPosition(56.0f, 4.0f);
                this.imgGunBody.setPosition(0.0f, 0.0f);
                this.groupGun.addActor(this.imgGunBody);
                this.groupGun.addActor(this.imgGunFront);
                this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
                this.groupGun.setOrigin(24.0f, 12.0f);
                this.groupGun.setPosition(32.0f, 44.0f);
                this.addActor(this.imgDrawf);
                this.addActor(this.imgPlatform);
                this.addActor(this.groupGun);
                this.addActor(this.imgGunShelf);
                this.setSize(120.0f, 70.0f);
                n2 = -28.0f;
                break;
            }
            case 8: {
                this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-9"));
                this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-9"));
                this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-9"));
                this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-9"));
                this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("zhijia-9"));
                this.imgDrawf.setPosition(6.0f, 15.0f);
                this.imgPlatform.setPosition(0.0f, 0.0f);
                this.imgGunShelf.setPosition(53.0f, -12.0f);
                this.imgGunFront.setPosition(9.0f, -3.0f);
                this.imgGunBody.setPosition(0.0f, 0.0f);
                this.groupGun.addActor(this.imgGunBody);
                this.groupGun.addActor(this.imgGunFront);
                this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
                this.groupGun.setOrigin(24.0f, 12.0f);
                this.groupGun.setPosition(50.0f, 15.0f);
                this.addActor(this.imgDrawf);
                this.addActor(this.imgPlatform);
                this.addActor(this.groupGun);
                this.addActor(this.imgGunShelf);
                this.setSize(120.0f, 70.0f);
                this.setPosition(66.0f, 132.0f);
                break;
            }
            case 9: {
                this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-10"));
                this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-10"));
                this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-10"));
                this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-10"));
                this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("zhijia-10"));
                this.imgDrawf.setPosition(21.0f, 20.0f);
                this.imgPlatform.setPosition(0.0f, 0.0f);
                this.imgGunShelf.setPosition(71.0f, -1.0f);
                this.imgGunFront.setPosition(59.0f, 8.0f);
                this.imgGunBody.setPosition(0.0f, 0.0f);
                this.groupGun.addActor(this.imgGunFront);
                this.groupGun.addActor(this.imgGunBody);
                this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
                this.groupGun.setOrigin(24.0f, 12.0f);
                this.groupGun.setPosition(53.0f, 24.0f);
                this.addActor(this.imgDrawf);
                this.addActor(this.imgPlatform);
                this.addActor(this.groupGun);
                this.addActor(this.imgGunShelf);
                this.setSize(120.0f, 70.0f);
                break;
            }
            case 10: {
                this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-11"));
                this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-11"));
                this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-11"));
                this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-11"));
                this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("zhijia-11"));
                this.imgDrawf.setPosition(16.0f, 12.0f);
                this.imgPlatform.setPosition(0.0f, 0.0f);
                this.imgGunShelf.setPosition(50.0f, 1.0f);
                this.imgGunFront.setPosition(33.0f, -4.5f);
                this.imgGunBody.setPosition(0.0f, 0.0f);
                this.groupGun.addActor(this.imgGunBody);
                this.groupGun.addActor(this.imgGunFront);
                this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
                this.groupGun.setOrigin(24.0f, 12.0f);
                this.groupGun.setPosition(52.0f, 25.0f);
                this.addActor(this.imgDrawf);
                this.addActor(this.imgPlatform);
                this.addActor(this.groupGun);
                this.addActor(this.imgGunShelf);
                this.setSize(120.0f, 70.0f);
                break;
            }
            case 11: {
                this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-12"));
                this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-12"));
                this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-12"));
                this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-12"));
                this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("zhijia-12"));
                this.imgDrawf.setPosition(6.0f, 12.0f);
                this.imgPlatform.setPosition(0.0f, 0.0f);
                this.imgGunShelf.setPosition(50.0f, 1.0f);
                this.imgGunFront.setPosition(29.0f, -20.5f);
                this.imgGunBody.setPosition(0.0f, 0.0f);
                this.groupGun.addActor(this.imgGunBody);
                this.groupGun.addActor(this.imgGunFront);
                this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
                this.groupGun.setOrigin(24.0f, 12.0f);
                this.groupGun.setPosition(45.0f, 20.0f);
                this.addActor(this.imgDrawf);
                this.addActor(this.imgPlatform);
                this.addActor(this.groupGun);
                this.addActor(this.imgGunShelf);
                this.setSize(120.0f, 70.0f);
                break;
            }
            case 12: {
                this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-13"));
                this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-13"));
                this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-13"));
                this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-13"));
                this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("zhijia-13"));
                this.imgDrawf.setPosition(4.0f, 28.0f);
                this.imgPlatform.setPosition(0.0f, 0.0f);
                this.imgGunShelf.setPosition(51.0f, 0.0f);
                this.imgGunFront.setPosition(29.0f, -15.0f);
                this.imgGunBody.setPosition(0.0f, 0.0f);
                this.groupGun.addActor(this.imgGunBody);
                this.groupGun.addActor(this.imgGunFront);
                this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
                this.groupGun.setOrigin(24.0f, 12.0f);
                this.groupGun.setPosition(49.0f, 22.0f);
                this.addActor(this.imgDrawf);
                this.addActor(this.imgPlatform);
                this.addActor(this.groupGun);
                this.addActor(this.imgGunShelf);
                this.setSize(120.0f, 70.0f);
                break;
            }
        }
        this.setPosition(342.0f + 0.0f, 287.0f + n2);
    }
}
