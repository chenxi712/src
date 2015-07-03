package com.gleejet.sun.common;

import com.gleejet.sun.objects.*;
import com.gleejet.sun.roles.*;
import com.gleejet.sun.roles.air.*;
import com.gleejet.sun.roles.boss.*;
import com.gleejet.sun.roles.build.*;
import com.gleejet.sun.roles.ground.*;

public class TypeHandle
{
    public static BaseEnemy createEnemy(final Constant.EnemyType enemyType) {
        switch (enemyType) {
            default: {
                return null;
            }
            case Archer1: {
                Archer.setCreateLevel(1);
                return new Archer();
            }
            case Archer2: {
                Archer.setCreateLevel(2);
                return new Archer();
            }
            case Archer3: {
                Archer.setCreateLevel(3);
                return new Archer();
            }
            case Lancer1: {
                Lancer.setCreateLevel(1);
                return new Lancer();
            }
            case Lancer2: {
                Lancer.setCreateLevel(2);
                return new Lancer();
            }
            case Lancer3: {
                Lancer.setCreateLevel(3);
                return new Lancer();
            }
            case Airforce1: {
                Airforce.setCreateLevel(1);
                return new Airforce();
            }
            case Airforce2: {
                Airforce.setCreateLevel(2);
                return new Airforce();
            }
            case Airforce3: {
                Airforce.setCreateLevel(3);
                return new Airforce();
            }
            case Dragon1: {
                Dragon.setCreateLevel(1);
                return new Dragon();
            }
            case Dragon2: {
                Dragon.setCreateLevel(2);
                return new Dragon();
            }
            case Dragon3: {
                Dragon.setCreateLevel(3);
                return new Dragon();
            }
            case Bomber1: {
                Bomber.setCreateLevel(1);
                return new Bomber();
            }
            case Bomber2: {
                Bomber.setCreateLevel(2);
                return new Bomber();
            }
            case Bomber3: {
                Bomber.setCreateLevel(3);
                return new Bomber();
            }
            case Artillery1: {
                Artillery.setCreateLevel(1);
                return new Artillery();
            }
            case Artillery2: {
                Artillery.setCreateLevel(2);
                return new Artillery();
            }
            case Artillery3: {
                Artillery.setCreateLevel(3);
                return new Artillery();
            }
            case Tower1: {
                Tower.setCreateLevel(1);
                return new Tower();
            }
            case Tower2: {
                Tower.setCreateLevel(2);
                return new Tower();
            }
            case Tower3: {
                Tower.setCreateLevel(3);
                return new Tower();
            }
            case Selfdes1: {
                Selfdes.setCreateLevel(1);
                return new Selfdes();
            }
            case Selfdes2: {
                Selfdes.setCreateLevel(2);
                return new Selfdes();
            }
            case Selfdes3: {
                Selfdes.setCreateLevel(3);
                return new Selfdes();
            }
            case CanTower1: {
                CanTower.setCreateLevel(1);
                return new CanTower();
            }
            case CanTower2: {
                CanTower.setCreateLevel(2);
                return new CanTower();
            }
            case CanTower3: {
                CanTower.setCreateLevel(3);
                return new CanTower();
            }
            case Flamecar: {
                Flamecar.setCreateLevel(1);
                return new Flamecar();
            }
            case Freezecar: {
                Freezecar.setCreateLevel(1);
                return new Freezecar();
            }
            case Stonecar: {
                Stonecar.setCreateLevel(1);
                return new Stonecar();
            }
            case Bear1: {
                return new Bear1();
            }
            case Bear2: {
                return new Bear2();
            }
            case Wasp1: {
                return new Wasp1();
            }
            case Wasp2: {
                return new Wasp2();
            }
            case Rinocer1: {
                return new Rinocer1();
            }
            case Rinocer2: {
                return new Rinocer2();
            }
            case Dinosaur1: {
                return new Dinosaur1();
            }
            case Dinosaur2: {
                return new Dinosaur2();
            }
            case BDragon1: {
                return new BDragon1();
            }
            case BDragon2: {
                return new BDragon2();
            }
            case Mine1:
            case Mine2:
            case Mine3:
            case Box1:
            case Box2:
            case Box3:
            case Spikeweed1:
            case Spikeweed2:
            case Spikeweed3: {
                final StillEnemy stillEnemy = new StillEnemy();
                stillEnemy.setType(enemyType);
                return stillEnemy;
            }
            case Flag: {
                final Flag flag = new Flag();
                flag.setType(enemyType);
                return flag;
            }
        }
    }
    
    public static boolean isSameType(final Constant.EnemyType enemyType, final Constant.EnemyType enemyType2) {
        boolean b = false;
        if (enemyType.toString().substring(0, enemyType.toString().length() - 1).equals(enemyType.toString().substring(0, enemyType2.toString().length() - 1))) {
            b = true;
        }
        return b;
    }
    
    public static void loadForType(final Constant.EnemyType enemyType) {
        switch (enemyType) {
            default: {}
            case Archer1: {
                Archer.loadElements(1);
            }
            case Archer2: {
                Archer.loadElements(2);
            }
            case Archer3: {
                Archer.loadElements(3);
            }
            case Lancer1: {
                Lancer.loadElements(1);
            }
            case Lancer2: {
                Lancer.loadElements(2);
            }
            case Lancer3: {
                Lancer.loadElements(3);
            }
            case Airforce1: {
                Airforce.loadElements(1);
            }
            case Airforce2: {
                Airforce.loadElements(2);
            }
            case Airforce3: {
                Airforce.loadElements(3);
            }
            case Dragon1: {
                Dragon.loadElements(1);
            }
            case Dragon2: {
                Dragon.loadElements(2);
            }
            case Dragon3: {
                Dragon.loadElements(3);
            }
            case Bomber1: {
                Bomber.loadElements(1);
            }
            case Bomber2: {
                Bomber.loadElements(2);
            }
            case Bomber3: {
                Bomber.loadElements(3);
            }
            case Artillery1: {
                Artillery.loadElements(1);
            }
            case Artillery2: {
                Artillery.loadElements(2);
            }
            case Artillery3: {
                Artillery.loadElements(3);
            }
            case Tower1: {
                Tower.loadElements(1);
            }
            case Tower2: {
                Tower.loadElements(2);
            }
            case Tower3: {
                Tower.loadElements(3);
            }
            case Selfdes1: {
                Selfdes.loadElements(1);
            }
            case Selfdes2: {
                Selfdes.loadElements(2);
            }
            case Selfdes3: {
                Selfdes.loadElements(3);
            }
            case CanTower1: {
                CanTower.loadElements(1);
            }
            case CanTower2: {
                CanTower.loadElements(2);
            }
            case CanTower3: {
                CanTower.loadElements(3);
            }
            case Flamecar: {
                Flamecar.loadElements(1);
            }
            case Freezecar: {
                Freezecar.loadElements(1);
            }
            case Stonecar: {
                Stonecar.loadElements(1);
            }
            case Bear1: {
                Bear1.loadElements(1);
            }
            case Bear2: {
                Bear2.loadElements(1);
            }
            case Wasp1: {
                Wasp1.loadElements(1);
            }
            case Wasp2: {
                Wasp2.loadElements(1);
            }
            case Rinocer1: {
                Rinocer1.loadElements(1);
            }
            case Rinocer2: {
                Rinocer2.loadElements(1);
            }
            case Dinosaur1: {
                Dinosaur1.loadElements(1);
            }
            case Dinosaur2: {
                Dinosaur2.loadElements(1);
            }
            case BDragon1: {
                BDragon1.loadElements(1);
            }
            case BDragon2: {
                BDragon2.loadElements(1);
            }
        }
    }
    
    public static void unloadForType(final Constant.EnemyType enemyType) {
        switch (enemyType) {
            default: {}
            case Archer1: {
                Archer.unloadElements(1);
            }
            case Archer2: {
                Archer.unloadElements(2);
            }
            case Archer3: {
                Archer.unloadElements(3);
            }
            case Lancer1: {
                Lancer.unloadElements(1);
            }
            case Lancer2: {
                Lancer.unloadElements(2);
            }
            case Lancer3: {
                Lancer.unloadElements(3);
            }
            case Airforce1: {
                Airforce.unloadElements(1);
            }
            case Airforce2: {
                Airforce.unloadElements(2);
            }
            case Airforce3: {
                Airforce.unloadElements(3);
            }
            case Dragon1: {
                Dragon.unloadElements(1);
            }
            case Dragon2: {
                Dragon.unloadElements(2);
            }
            case Dragon3: {
                Dragon.unloadElements(3);
            }
            case Bomber1: {
                Bomber.unloadElements(1);
            }
            case Bomber2: {
                Bomber.unloadElements(2);
            }
            case Bomber3: {
                Bomber.unloadElements(3);
            }
            case Artillery1: {
                Artillery.unloadElements(1);
            }
            case Artillery2: {
                Artillery.unloadElements(2);
            }
            case Artillery3: {
                Artillery.unloadElements(3);
            }
            case Tower1: {
                Tower.unloadElements(1);
            }
            case Tower2: {
                Tower.unloadElements(2);
            }
            case Tower3: {
                Tower.unloadElements(3);
            }
            case Selfdes1: {
                Selfdes.unloadElements(1);
            }
            case Selfdes2: {
                Selfdes.unloadElements(2);
            }
            case Selfdes3: {
                Selfdes.unloadElements(3);
            }
            case CanTower1: {
                CanTower.unloadElements(1);
            }
            case CanTower2: {
                CanTower.unloadElements(2);
            }
            case CanTower3: {
                CanTower.unloadElements(3);
            }
            case Flamecar: {
                Flamecar.unloadElements(1);
            }
            case Freezecar: {
                Freezecar.unloadElements(1);
            }
            case Stonecar: {
                Stonecar.unloadElements(1);
            }
            case Bear1: {
                Bear1.unloadElements(1);
            }
            case Bear2: {
                Bear2.unloadElements(1);
            }
            case Wasp1: {
                Wasp1.unloadElements(1);
            }
            case Wasp2: {
                Wasp2.unloadElements(1);
            }
            case Rinocer1: {
                Rinocer1.unloadElements(1);
            }
            case Rinocer2: {
                Rinocer2.unloadElements(1);
            }
            case Dinosaur1: {
                Dinosaur1.unloadElements(1);
            }
            case Dinosaur2: {
                Dinosaur2.unloadElements(1);
            }
            case BDragon1: {
                BDragon1.unloadElements(1);
            }
            case BDragon2: {
                BDragon2.unloadElements(1);
            }
        }
    }
}
