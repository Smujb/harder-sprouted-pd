/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.github.dachhack.sprout.actors.hero;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.Badges;
import com.github.dachhack.sprout.Challenges;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.Journal;
import com.github.dachhack.sprout.ShatteredPixelDungeon;
import com.github.dachhack.sprout.items.Amulet;
import com.github.dachhack.sprout.items.Ankh;
import com.github.dachhack.sprout.items.Bomb;
import com.github.dachhack.sprout.items.BookOfDead;
import com.github.dachhack.sprout.items.BookOfLife;
import com.github.dachhack.sprout.items.BookOfTranscendence;
import com.github.dachhack.sprout.items.DewVial;
import com.github.dachhack.sprout.items.EasterEgg;
import com.github.dachhack.sprout.items.Egg;
import com.github.dachhack.sprout.items.Generator;
import com.github.dachhack.sprout.items.Generator.Category;
import com.github.dachhack.sprout.items.OrbOfZot;
import com.github.dachhack.sprout.items.OtilukesJournal;
import com.github.dachhack.sprout.items.Palantir;
import com.github.dachhack.sprout.items.ShadowDragonEgg;
import com.github.dachhack.sprout.items.TomeOfMastery;
import com.github.dachhack.sprout.items.Towel;
import com.github.dachhack.sprout.items.Whistle;
import com.github.dachhack.sprout.items.armor.ClothArmor;
import com.github.dachhack.sprout.items.armor.PlateArmor;
import com.github.dachhack.sprout.items.artifacts.CloakOfShadows;
import com.github.dachhack.sprout.items.bags.AnkhChain;
import com.github.dachhack.sprout.items.bags.KeyRing;
import com.github.dachhack.sprout.items.bags.PotionBandolier;
import com.github.dachhack.sprout.items.bags.ScrollHolder;
import com.github.dachhack.sprout.items.bags.SeedPouch;
import com.github.dachhack.sprout.items.bags.WandHolster;
import com.github.dachhack.sprout.items.food.Food;
import com.github.dachhack.sprout.items.journalpages.DragonCave;
import com.github.dachhack.sprout.items.journalpages.JournalPage;
import com.github.dachhack.sprout.items.journalpages.SafeSpotPage;
import com.github.dachhack.sprout.items.journalpages.Sokoban1;
import com.github.dachhack.sprout.items.journalpages.Sokoban2;
import com.github.dachhack.sprout.items.journalpages.Sokoban3;
import com.github.dachhack.sprout.items.journalpages.Sokoban4;
import com.github.dachhack.sprout.items.journalpages.Town;
import com.github.dachhack.sprout.items.journalpages.Vault;
import com.github.dachhack.sprout.items.misc.Spectacles;
import com.github.dachhack.sprout.items.nornstone.BlueNornStone;
import com.github.dachhack.sprout.items.nornstone.GreenNornStone;
import com.github.dachhack.sprout.items.nornstone.PurpleNornStone;
import com.github.dachhack.sprout.items.nornstone.YellowNornStone;
import com.github.dachhack.sprout.items.potions.MegaExperiencePotion;
import com.github.dachhack.sprout.items.potions.PotionOfHaste;
import com.github.dachhack.sprout.items.potions.PotionOfHealing;
import com.github.dachhack.sprout.items.potions.PotionOfMindVision;
import com.github.dachhack.sprout.items.potions.PotionOfStrength;
import com.github.dachhack.sprout.items.rings.RingOfWealth;
import com.github.dachhack.sprout.items.scrolls.Scroll;
import com.github.dachhack.sprout.items.scrolls.ScrollOfIdentify;
import com.github.dachhack.sprout.items.scrolls.ScrollOfMagicMapping;
import com.github.dachhack.sprout.items.scrolls.ScrollOfMagicalInfusion;
import com.github.dachhack.sprout.items.scrolls.ScrollOfMirrorImage;
import com.github.dachhack.sprout.items.scrolls.ScrollOfPsionicBlast;
import com.github.dachhack.sprout.items.scrolls.ScrollOfRemoveCurse;
import com.github.dachhack.sprout.items.scrolls.ScrollOfUpgrade;
import com.github.dachhack.sprout.items.wands.Wand;
import com.github.dachhack.sprout.items.wands.WandOfAvalanche;
import com.github.dachhack.sprout.items.wands.WandOfDisintegration;
import com.github.dachhack.sprout.items.wands.WandOfFirebolt;
import com.github.dachhack.sprout.items.wands.WandOfFlock;
import com.github.dachhack.sprout.items.wands.WandOfLightning;
import com.github.dachhack.sprout.items.wands.WandOfMagicMissile;
import com.github.dachhack.sprout.items.wands.WandOfTelekinesis;
import com.github.dachhack.sprout.items.weapon.enchantments.Slashing;
import com.github.dachhack.sprout.items.weapon.melee.Chainsaw;
import com.github.dachhack.sprout.items.weapon.melee.Dagger;
import com.github.dachhack.sprout.items.weapon.melee.Glaive;
import com.github.dachhack.sprout.items.weapon.melee.ShortSword;
import com.github.dachhack.sprout.items.weapon.melee.Spork;
import com.github.dachhack.sprout.items.weapon.melee.WarHammer;
import com.github.dachhack.sprout.items.weapon.melee.relic.AresSword;
import com.github.dachhack.sprout.items.weapon.melee.relic.CromCruachAxe;
import com.github.dachhack.sprout.items.weapon.melee.relic.LokisFlail;
import com.github.dachhack.sprout.items.weapon.melee.relic.NeptunusTrident;
import com.github.dachhack.sprout.items.weapon.melee.relic.RelicMeleeWeapon;
import com.github.dachhack.sprout.items.weapon.missiles.Boomerang;
import com.github.dachhack.sprout.items.weapon.missiles.Dart;
import com.github.dachhack.sprout.items.weapon.missiles.JupitersWrath;
import com.github.dachhack.sprout.items.weapon.missiles.Tamahawk;
import com.github.dachhack.sprout.plants.BlandfruitBush;
import com.github.dachhack.sprout.plants.Blindweed;
import com.github.dachhack.sprout.plants.Dewcatcher;
import com.github.dachhack.sprout.plants.Dreamfoil;
import com.github.dachhack.sprout.plants.Earthroot;
import com.github.dachhack.sprout.plants.Fadeleaf;
import com.github.dachhack.sprout.plants.Firebloom;
import com.github.dachhack.sprout.plants.Flytrap;
import com.github.dachhack.sprout.plants.Icecap;
import com.github.dachhack.sprout.plants.Phaseshift;
import com.github.dachhack.sprout.plants.Plant;
import com.github.dachhack.sprout.plants.Sorrowmoss;
import com.github.dachhack.sprout.plants.Starflower;
import com.github.dachhack.sprout.plants.Stormvine;
import com.github.dachhack.sprout.plants.Sungrass;
import com.github.dachhack.sprout.utils.GLog;
import com.watabou.utils.Bundle;

import static com.github.dachhack.sprout.items.Generator.Category.SEED;

public enum HeroClass {

	WARRIOR("warrior"), MAGE("mage"), ROGUE("rogue"), HUNTRESS("huntress");

	private String title;

	private HeroClass(String title) {
		this.title = title;
	}

	public static final String[] WAR_PERKS = {
			"Warriors start with an Ankh",
			"Warriors start with 11 points of Strength.",
			"Warriors start with a unique short sword. This sword can be later \"reforged\" to upgrade another melee weapon.",
			"Warriors are less proficient with missile weapons.",
			"Any piece of food restores more health when eaten.",
			"Potions of Strength are identified from the beginning.", };

	public static final String[] MAG_PERKS = {
			"Mages start with a unique Wand of Magic Missile. This wand can be later \"disenchanted\" to upgrade another wand.",
			"Mages recharge their wands faster.",
			"When eaten, any piece of food restores 1 charge for all wands in the inventory.",
			"Mages can use wands as a melee weapon.",
			"Dew blessings are more effective for mages.",
			"Scrolls of Identify are identified from the beginning." };

	public static final String[] ROG_PERKS = {
			"Rogues start with a unique Cloak of Shadows.",
			"Rogues identify a type of a ring on equipping it.",
			"Rogues are proficient with light armor, dodging better while wearing one.",
			"Rogues are proficient in detecting hidden doors and traps.",
			"Rogues can go without food longer.",
			"Rogues are able to make special bombs.",
			"Scrolls of Magic Mapping are identified from the beginning." };

	public static final String[] HUN_PERKS = {
			"Huntresses do bonus damage with missile weapons.",
			"Huntresses start with a unique upgradeable boomerang.",
			"Huntresses are proficient with missile weapons, getting bonus damage from excess strength.",
			"Huntresses have a chance to regain multiple used missile weapons from an enemy.",
			"Huntresses sense neighbouring monsters even if they are hidden behind obstacles.",
			"Potions of Mind Vision are identified from the beginning." };

	public void initHero(Hero hero) {

		hero.heroClass = this;

		if (Badges.isUnlocked(masteryBadge()) || Dungeon.testing) {
			new TomeOfMastery().collect();
		}

		initCommon(hero);

		switch (this) {
		case WARRIOR:
			initWarrior(hero);
			break;

		case MAGE:
			initMage(hero);
			break;

		case ROGUE:
			initRogue(hero);
			break;

		case HUNTRESS:
			initHuntress(hero);
			break;
		}



			//new OtilukesJournal().collect();
			//Dungeon.limitedDrops.journal.drop();
		

		hero.updateAwareness();
	}

	private static void initCommon(Hero hero) {
		OtilukesJournal jn = new OtilukesJournal();
		jn.identify().collect();
		Dungeon.limitedDrops.journal.drop();
		if (!Dungeon.isChallenged(Challenges.NO_ARMOR))
			(hero.belongings.armor = new ClothArmor()).upgrade(3).identify();

		if (!Dungeon.isChallenged(Challenges.NO_FOOD))
			new Food().identify().collect();
		new ScrollOfIdentify().setKnown();
		if (Dungeon.testing) {
			playtest(hero);
		}
		//new Chainsaw().enchantBuzz().identify().collect();
		//DewVial vial = new DewVial();
		//vial.collect();
		//vial.fill();
	}

	public Badges.Badge masteryBadge() {
		switch (this) {
		case WARRIOR:
			return Badges.Badge.MASTERY_WARRIOR;
		case MAGE:
			return Badges.Badge.MASTERY_MAGE;
		case ROGUE:
			return Badges.Badge.MASTERY_ROGUE;
		case HUNTRESS:
			return Badges.Badge.MASTERY_HUNTRESS;
		}
		return null;
	}

	private static void initWarrior(Hero hero) {
		hero.STR = hero.STR + 1;

		(hero.belongings.weapon = new ShortSword()).upgrade(3).identify();
		Dart darts = new Dart(8);
		darts.identify().collect();
		    	
		Dungeon.quickslot.setSlot(0, darts);
		
		KeyRing keyring = new KeyRing(); keyring.collect();

		new Ankh().collect();
		new PotionOfStrength().setKnown();
		
		//playtest(hero);
	}

	private static void initMage(Hero hero) {


		WandOfMagicMissile wand = new WandOfMagicMissile();
		wand.identify().upgrade(3);
		hero.belongings.weapon = wand;
		//new ScrollOfUpgrade().quantity(5).identify().collect();
		KeyRing keyring = new KeyRing(); keyring.collect();
		
		Dungeon.quickslot.setSlot(0, wand);

		new ScrollOfIdentify().setKnown();
		
		//playtest(hero);
	}

	private static void initRogue(Hero hero) {
		(hero.belongings.weapon = new Dagger()).upgrade(3).identify();

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.misc1 = cloak).identify();
		hero.belongings.misc1.activate(hero);

		Dart darts = new Dart(10);
		darts.identify().collect();
		
		KeyRing keyring = new KeyRing(); keyring.collect();

		Dungeon.quickslot.setSlot(0, cloak);
		if (ShatteredPixelDungeon.quickSlots() > 1)
			Dungeon.quickslot.setSlot(1, darts);
		
		Bomb bomb = new Bomb(); bomb.collect();
		new ScrollOfMagicMapping().setKnown();
	}

	private static void initHuntress(Hero hero) {

		//hero.HP = (hero.HT -= 5);

		(hero.belongings.weapon = new Boomerang()).upgrade(3).identify();
		
		KeyRing keyring = new KeyRing(); keyring.collect();

		Dungeon.quickslot.setSlot(0, hero.belongings.weapon);

		new PotionOfMindVision().setKnown();
	}

	public static void playtest(Hero hero) {
		if (!Dungeon.playtest) {
			//Playtest
			//TomeOfMastery tome = new TomeOfMastery(); tome.collect();
			//new WandOfDisintegration().identify().reinforce().upgrade(15).collect();
			//new WandOfFirebolt().identify().reinforce().upgrade(15).collect();
			//new WandOfLightning().identify().reinforce().upgrade(15).collect();
			//new WandOfAvalanche().identify().reinforce().upgrade(15).collect();
			//new WandOfTelekinesis().identify().reinforce().upgrade(15).collect();
			//new WandHolster().collect();

			//new Tamahawk().quantity(40).collect();

			//new AnkhChain().collect();
			//new KeyRing().collect();

			//new SeedPouch().collect();

			//new PotionBandolier().collect();

			//new DewVial().fill().collect();

			//new BookOfDead().collect();
			//new BookOfLife().collect();
			//new BookOfTranscendence().collect();
			//new Amulet().collect();

			//(hero.belongings.misc2 = new RingOfWealth()).identify().upgrade(28);
			//hero.belongings.misc2.activate(hero);
			//new PotionOfHealing().quantity(50).identify().collect();

			//hero.HT=hero.HP=999;
			//hero.STR = hero.STR + 10;
			//PlateArmor armor1 = new PlateArmor();
			//armor1.reinforce().upgrade(15).identify().collect();
			// PlateArmor armor2 = new PlateArmor();
			// armor2.upgrade(14).identify().collect();

			//WarHammer hammer = new WarHammer();
			//hammer.reinforce().upgrade(15).identify().collect();

			//Glaive glaive = new Glaive();
			//glaive.reinforce().upgrade(15).identify().collect();

			Spork spork = new Spork();
			spork.enchantNom().upgrade(4).identify().collect();
			new Spork().enchant(new Slashing()).upgrade(4).identify().collect();
			if (hero.heroClass == HeroClass.HUNTRESS) {
				new Boomerang().enchant(new Slashing()).identify().collect();
			}

			new ScrollOfMirrorImage().collect();

			//spork.reinforce().upgrade(15).identify().collect();

			//Towel towel = new Towel();
			//towel.identify().collect();

			//Spectacles specs = new Spectacles();
			//specs.collect();
			//Dewcatcher.Seed seed3 = new Dewcatcher.Seed(); seed3.collect();
			//Flytrap.Seed seed1 = new Flytrap.Seed(); seed1.collect();
			//Phaseshift.Seed seed2 = new Phaseshift.Seed(); seed2.collect();
			//Starflower.Seed seed4 = new Starflower.Seed(); seed4.collect();
			//new NeptunusTrident().enchantNeptune().setTesting().identify().collect();
			//new LokisFlail().enchantLoki().setTesting().identify().collect();
			//new JupitersWrath().enchantJupiter().setTesting().identify().collect();
			//new CromCruachAxe().enchantLuck().setTesting().identify().collect();
			//new AresSword().enchantAres().setTesting().identify().collect();
			//ScrollHolder bag2 = new ScrollHolder();
			//bag2.collect();
			// armor = new PlateArmor(); armor.upgrade(130); armor.identify().collect();
			//OrbOfZot zot = new OrbOfZot(); zot.collect();
			//WarHammer hammer = new WarHammer();
			//hammer.identify().collect();
			// Wand wand = new WandOfDisintegration(); wand.upgrade(50); wand.collect();
			//Wand wand3 = new WandOfFlock();
			//wand3.upgrade(15).reinforce();
			//wand3.collect();
			// Wand wand2 = new WandOfTelekinesis(); wand2.upgrade(15); wand2.collect();
			//Wand wand3 = new WandOfTeleportation(); wand3.upgrade(15); wand3.collect();
			// Wand wand4 = new WandOfBlink(); wand4.upgrade(15); wand4.collect();
			//Wand wand2 = new WandOfFirebolt(); wand2.upgrade(15); wand2.collect();
			//Wand wand3 = new WandOfLightning(); wand3.upgrade(15); wand3.collect();
			//Ring ring = new RingOfHaste(); ring.upgrade(25); ring.collect();
			//ConchShell conch = new ConchShell(); conch.collect();
			//AncientCoin coin = new AncientCoin(); coin.collect();
			//TenguKey key = new TenguKey(); key.collect();
			JournalPage safe = new SafeSpotPage();
			safe.collect();
			JournalPage dol = new Town();
			dol.collect();
			JournalPage sk1 = new Sokoban1();
			sk1.collect();
			JournalPage sk2 = new Sokoban2();
			sk2.collect();
			JournalPage sk3 = new Sokoban3();
			sk3.collect();
			JournalPage sk4 = new Sokoban4();
			sk4.collect();
			//JournalPage sk5 = new Vault();
			//sk5.collect();
			//JournalPage dra = new DragonCave();
			//dra.collect();
			new MegaExperiencePotion().collect();
			//JournalPage town = new Town(); town.collect();
			//JournalPage cave = new DragonCave(); cave.collect();
			//NeptunusTrident trident = new NeptunusTrident(); trident.enchantNeptune(); trident.upgrade(200); trident.collect();
			//CromCruachAxe axe = new CromCruachAxe(); axe.enchantLuck(); axe.collect();
			//AresSword sword = new AresSword(); sword.enchantAres(); sword.collect();
			//JupitersWrath wraith = new JupitersWrath(); wraith.enchantJupiter(); wraith.collect();
			//LokisFlail flail = new LokisFlail(); flail.enchantLoki(); flail.collect();
			//JournalPage sk5 = new Town(); sk5.collect();
			//Wand wand = new WandOfAmok(); wand.upgrade(15); wand.collect();
			//Bone bone = new Bone(); bone.collect();
			//ConchShell conch = new ConchShell(); conch.collect();
			//AncientCoin coin = new AncientCoin(); coin.collect();
			//TenguKey key = new TenguKey(); key.collect();
			//CavesKey key2 = new CavesKey(); key2.collect();
			//SanChikarah san = new SanChikarah(); san.collect();
			//BookOfLife lbook = new BookOfLife(); lbook.collect();
			//BookOfDead dbook = new BookOfDead(); dbook.collect();
			// ReturnBeacon beacon = new ReturnBeacon(); beacon.collect();
			//SanChikarahDeath san = new SanChikarahDeath(); san.collect();
			// Blueberry berry = new Blueberry(60); berry.collect();
			// PotionOfMindVision potion4 = new PotionOfMindVision(); potion4.collect();
			//Dewcatcher.Seed seed3 = new Dewcatcher.Seed(); seed3.collect();
			// ActiveMrDestructo mrd = new ActiveMrDestructo(); mrd.collect();
			// ActiveMrDestructo2 mrd2 = new ActiveMrDestructo2(); mrd2.collect();
			// RingOfDisintegration ar = new RingOfDisintegration(); ar.collect();
			//RingOfFrost fr = new RingOfFrost(); fr.collect();
			//RingOfHaste ha = new RingOfHaste(); ha.upgrade(5); ha.collect();
			//PotionOfFrost pot = new PotionOfFrost(); pot.collect();
			//SteelHoneypot hpot = new SteelHoneypot(); hpot.collect();
			//Egg egg = new Egg(); egg.collect();
			//EasterEgg egg2 = new EasterEgg(); egg2.collect();
			//ShadowDragonEgg egg3 = new ShadowDragonEgg(); egg3.collect();
			//GoldenSkeletonKey key = new GoldenSkeletonKey(0); key.collect();
			//Flytrap.Seed seed1 = new Flytrap.Seed(); seed1.collect();
			//Phaseshift.Seed seed2 = new Phaseshift.Seed(); seed2.collect();
			//Starflower.Seed seed3 = new Starflower.Seed(); seed3.collect();
			//BlandfruitBush.Seed seed4 = new BlandfruitBush.Seed(); seed4.collect();

			//Chainsaw saw = new Chainsaw(); saw.enchantBuzz(); saw.collect();
			//PotionBandolier bag1 = new PotionBandolier(); bag1.collect();
			//ScrollHolder bag2 = new ScrollHolder(); bag2.collect();
			//AnkhChain chain = new AnkhChain(); chain.collect();
			//WandHolster holster = new WandHolster(); holster.collect();
			//AutoPotion apot = new AutoPotion(); apot.collect();
			//AdamantArmor aArmor = new AdamantArmor(); aArmor.collect();
			//AdamantWand aWand = new AdamantWand(); aWand.collect();
			//AdamantRing aRing = new AdamantRing(); aRing.collect();
			//AdamantWeapon aWeapon = new AdamantWeapon(); aWeapon.collect();
			//PotionOfLiquidFlame potion5 = new PotionOfLiquidFlame(); potion5.collect();

			//PuddingCup cup = new PuddingCup(); cup.collect();

			Dungeon.playtest = true;
			GLog.i("Playtest Activated");


			/*for (int i = 0; i < 50; i++) {
				//Scroll scroll = new ScrollOfMagicalInfusion();
				//scroll.identify().collect();
				Scroll scroll2 = new ScrollOfMagicMapping();
				scroll2.identify().collect();

				Scroll scroll3 = new ScrollOfIdentify();
				scroll3.identify().collect();
				Scroll scroll4 = new ScrollOfRemoveCurse();
				scroll4.identify().collect();
				Scroll scroll5 = new ScrollOfPsionicBlast();
				scroll5.identify().collect();

				new BlandfruitBush.Seed().collect();
				new Blindweed.Seed().collect();
				new Dewcatcher.Seed().collect();
				new Dreamfoil.Seed().collect();
				new Earthroot.Seed().collect();
				new Fadeleaf.Seed().collect();
				new Firebloom.Seed().collect();
				new Flytrap.Seed().collect();
				new Icecap.Seed().collect();
				new Phaseshift.Seed().collect();
				new Sorrowmoss.Seed().collect();
				new Starflower.Seed().collect();
				new Stormvine.Seed().collect();
				new Sungrass.Seed().collect();

			}*/
				
				/*
				for(int i=1; i<61; i++){
			        PotionOfExperience potion1 = new PotionOfExperience(); potion1.collect();
			       PotionOfInvisibility potion2 = new PotionOfInvisibility(); potion2.collect();
			      PotionOfHealing potion3 = new PotionOfHealing(); potion3.collect();
			       PotionOfMindVision potion4 = new PotionOfMindVision(); potion4.collect();
			      PotionOfLevitation potion6 = new PotionOfLevitation(); potion6.collect();
			      //PotionOfFrost potion6 = new PotionOfFrost(); potion6.collect();
			      PotionOfLiquidFlame potion5 = new PotionOfLiquidFlame(); potion5.collect();
			     // Bomb bomb = new Bomb(); bomb.collect();
			      //DarkGold darkgold = new DarkGold(); darkgold.collect();
			        }
				*/

		}
		
				/*
							 			      
			      
				         
				
				       
				  Blueberry berry = new Blueberry(10); berry.collect();
				  ClusterBomb cbomb = new ClusterBomb(); cbomb.collect();
				  DizzyBomb dbomb = new DizzyBomb(); dbomb.collect();
				  SmartBomb smbomb = new SmartBomb(); smbomb.collect();
				  SeekingBombItem sbomb = new SeekingBombItem(); sbomb.collect();
				  SeekingClusterBombItem scbomb = new SeekingClusterBombItem(); scbomb.collect();
				  
				  
				//  Bomb bomb = new Bomb(); bomb.collect();
				//DeathCap mush1 = new DeathCap(); mush1.collect();
				//GoldenJelly mush2 = new GoldenJelly(); mush2.collect();
				//BlueMilk mush3 = new BlueMilk(); mush3.collect();
				//JackOLantern mush4 = new JackOLantern(); mush4.collect();
				//Earthstar mush5 = new Earthstar(); mush5.collect();
				//Lichen mush6 = new Lichen(); mush6.collect();
				//PixieParasol mush7 = new PixieParasol(); mush7.collect();
				
				ActiveMrDestructo mrd = new ActiveMrDestructo(); mrd.collect();
				//OrbOfZot orb = new OrbOfZot(); orb.collect();
				        
				//Phaseshift.Seed seed = new Phaseshift.Seed(); seed.collect();
				//Phaseshift.Seed seed2 = new Phaseshift.Seed(); seed2.collect();
				//Starflower.Seed seed3 = new Starflower.Seed(); seed3.collect();
				
								
				//PotionOfLiquidFlame potion5 = new PotionOfLiquidFlame(); potion5.collect();
				//BookOfDead dbook = new BookOfDead(); dbook.collect();
				///BookOfLife lbook = new BookOfLife(); lbook.collect();
				//BookOfTranscendence tbook = new BookOfTranscendence(); tbook.collect();
				//SanChikarah san = new SanChikarah(); san.collect();	
				
		        //SewersKey key1 = new SewersKey(); key1.collect();
		        //PrisonKey key2 = new PrisonKey(); key2.collect();
		        //CavesKey key3 = new CavesKey(); key3.collect();
		        //CityKey key4 = new CityKey(); key4.collect();
		      // HallsKey key5 = new HallsKey(); key5.collect();
		        FullMoonberry berry2 = new FullMoonberry(10); berry2.collect();		
*/
	}
	
	public String title() {
		return title;
	}

	public String spritesheet() {

		switch (this) {
		case WARRIOR:
			return Assets.WARRIOR;
		case MAGE:
			return Assets.MAGE;
		case ROGUE:
			return Assets.ROGUE;
		case HUNTRESS:
			return Assets.HUNTRESS;
		}

		return null;
	}

	public String[] perks() {

		switch (this) {
		case WARRIOR:
			return WAR_PERKS;
		case MAGE:
			return MAG_PERKS;
		case ROGUE:
			return ROG_PERKS;
		case HUNTRESS:
			return HUN_PERKS;
		}

		return null;
	}

	private static final String CLASS = "class";

	public void storeInBundle(Bundle bundle) {
		bundle.put(CLASS, toString());
	}

	public static HeroClass restoreInBundle(Bundle bundle) {
		String value = bundle.getString(CLASS);
		return value.length() > 0 ? valueOf(value) : ROGUE;
	}
}
