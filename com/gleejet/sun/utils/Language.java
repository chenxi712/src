package com.gleejet.sun.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Language {
	public static enum Regions {
		English, Chinese
	}
	
	public static Regions region;
	
	private static int idx;
	
	static {
		region = Regions.Chinese;
		if (region == Regions.Chinese)
			idx = 1;
		else if (region == Regions.English)
			idx = 0;
	}
	
	public static String tapToFire () {
		String[] str = {"Tap to fire!",
				"点击开火"};
		return str[idx];
	}
	
	public static String tapToSwitchWeapon () {
		String[] str = {"Tap to switch weapon!", "选择武器"};
		return str[idx];
	}
	
	public static String tapToUseSkill () {
		String[] str = {"Tap to use skill!", 
				"点击使用技能"};
		return str[idx];
	}
	
	public static String tapToDestroyTower () {
		String[] str = {"Artillery can destroy enemy's \ndefensive towers effectively!", 
				"大炮可以有效的摧\n毁敌人的防御塔。"};
		return str[idx];
	}
	
	public static String tapToChooseEasy () {
		String[] str = {"Tap to choose easy mode!", 
				"选择容易模式"};
		return str[idx];
	}
	
	public static String tapToContinue () {
		String[] str = {"Tap to continue!", 
				"点击继续"};
		return str[idx];
	}
	
	public static String tapToSelectLv1 () {
		String[] str = {"Tap to select level 1!",
				"选择第一关"};
		return str[idx];
	}
	
	public static String tapStore () {
		String[] str = {"Tap store!",
				"点击商店"};
		return str[idx];
	}
	
	public static String tapWeaponStorage () {
		String[] str = {"Tap to open weapon storage!",
				"点击打开武器库"};
		return str[idx];
	}
	
	public static String tapUpgradeGun () {
		String[] str = {"Tap to upgrade \nmachine-gun!",
				"点击升级武器"};
		return str[idx];
	}
	
	public static String tapAndSelectWeapon () {
		String[] str = {"Tap and hold to select the \nweapon!",
				"点击并拖动来\n选择武器"};
		return str[idx];
	}
	
	public static String tapAndRemoveWeapon () {
		String[] str = {"Tap and hold to remove the \nweapon!",
				"点击并拖动来\n移除武器"};
		return str[idx];
	}
	
	public static String tapToAddArtillery () {
		String[] str = {"Tap and hold to add artillery.",
				"点击并拖动来添加大炮"};
		return str[idx];
	}
	
	public static String tapToArm() {
		String[] str = {"Tap to arm with shotgun. \n(Designed for attacking \nairforce)",
				"点击瞄准，用来\n打击空中单位。"};
		return str[idx];
	}
	
	public static String tapToNextLv () {
		String[] str = {"Next level!",
				"开始下关"};
		return str[idx];
	}
	
	public static String chooseStoreWeapon () {
		String[] str = {"Choose store of weapon!",
		"选择武器商店"};
		return str[idx];
	}
	
	public static String touchToBuyWeapon () {
		String[] str = {"Touch to buy a new weapon!", 
				"购买武器"};
		return str[idx];
	}
	
	public static String tapToReturn () {
		String[] str = {"Return!", "返回"};
		return str[idx];
	}
	
	public static String equipWeapon () {
		String[] str = {"Equip this powerful weapon \nfor every 30 minutes!", 
				"装备这个强大的\n武器30分钟"};
		return str[idx];
	}
	
	public static String touchToLightLabel () {
		String[] str = {"Touch to switch to \nlight weapon label!",
				"选择轻武器"};
		return str[idx];
	}
	
	public static String battleStart () {
		String[] str = {"Battle start!", "开始战斗"};
		return str[idx];
	}
	
	public static String wouldBuyMond1 () {
		String[] str = {"Would you like to spend $", "你要花  ￥"};
		return str[idx];
	}
	
	public static String wouldBuyMond2 () {
		String[] str = {" to buy ", " 来买  "};
		return str[idx];
	}
	
	public static String wouldBuyMond3 () {
		String[] str = {" diamond?", " 钻石吗？"};
		return str[idx];
	}
	
	public static String wouldBuyGold1 () {
		String[] str = {"Do you want to spend ", "你要花 "};
		return str[idx];
	}
	
	public static String wouldBuyGold2 () {
		String[] str = {"diamond to buy ", " 钻石来买 "};
		return str[idx];
	}
	
	public static String wouldBuyGold3 () {
		String[] str = {" gold?", " 金币吗？"};
		return str[idx];
	}
	
	public static String toBuyWeapon () {
		String[] str = {"Do you want to spend 100 diamond to buy \nthe weapon?",
				"要花100个钻石买武器吗？"};
		return str[idx];
	}
	
	public static String goldIsNotEnough () {
		String[] str = {"Gold is not enough. Would \nyou like to buy more?", "金币不足，\n去获取更多金币？"};
		return str[idx];
	}
	
	public static String mondIsNotEnough () {
		String[] str = {"Diamond is not enough. Would \nyou like to buy more?", "钻石不足，\n去获取更多钻石？"};
		return str[idx];
	}
}
