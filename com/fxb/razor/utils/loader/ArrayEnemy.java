package com.fxb.razor.utils.loader;

import com.badlogic.gdx.utils.*;
import com.fxb.razor.roles.*;

public class ArrayEnemy
{
    Array<BaseEnemy> arrBuildEnemy;
    Array<Array<BaseEnemy>> arrMoveEnemy;
    
    public ArrayEnemy() {
        this.arrMoveEnemy = new Array<Array<BaseEnemy>>();
        this.arrBuildEnemy = new Array<BaseEnemy>();
    }
}
