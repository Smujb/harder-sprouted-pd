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
package com.github.dachhack.sprout.actors.mobs;

import java.util.ArrayList;
import java.util.HashSet;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.blobs.ToxicGas;
import com.github.dachhack.sprout.actors.buffs.Amok;
import com.github.dachhack.sprout.actors.buffs.Blindness;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.buffs.Burning;
import com.github.dachhack.sprout.actors.buffs.Charm;
import com.github.dachhack.sprout.actors.buffs.Poison;
import com.github.dachhack.sprout.actors.buffs.Sleep;
import com.github.dachhack.sprout.actors.buffs.Terror;
import com.github.dachhack.sprout.actors.buffs.Vertigo;
import com.github.dachhack.sprout.actors.hero.HeroClass;
import com.github.dachhack.sprout.actors.mobs.npcs.OtilukeNPC;
import com.github.dachhack.sprout.effects.CellEmitter;
import com.github.dachhack.sprout.effects.Pushing;
import com.github.dachhack.sprout.effects.Speck;
import com.github.dachhack.sprout.items.misc.AutoPotion.AutoHealPotion;
import com.github.dachhack.sprout.items.scrolls.ScrollOfPsionicBlast;
import com.github.dachhack.sprout.items.weapon.enchantments.Death;
import com.github.dachhack.sprout.items.weapon.enchantments.Leech;
import com.github.dachhack.sprout.items.weapon.melee.relic.RelicMeleeWeapon;
import com.github.dachhack.sprout.items.weapon.missiles.JupitersWrath;
import com.github.dachhack.sprout.levels.Level;
import com.github.dachhack.sprout.mechanics.Ballistica;
import com.github.dachhack.sprout.scenes.GameScene;
import com.github.dachhack.sprout.sprites.ZotSprite;
import com.github.dachhack.sprout.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Zot extends Mob {

	private static final int JUMP_DELAY = 5;

	public static final float TIME_TO_TELEPORT = 3f;

	private boolean canJump = false;
	{
		name = "Zot";
		spriteClass = ZotSprite.class;
		baseSpeed = 2f;
		EXP = 20;

		seeThroughInvisibility = true;

		scalesWithHeroLevel = true;
	}
	private int HPAtLastJump = HP = HT = 40000;

	private int timeToJump = JUMP_DELAY;
	
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(250, 500);
	}

	@Override
	public int dr() {
		return 200;
	}

	@Override
	public int attackSkill(Char target) {
		if (enemy.invisible > 0) {
			return super.attackSkill(target)/2;
		} else {
			return super.attackSkill(target);
		}
	}

	@Override
	public int defenseSkill(Char enemy) {
		return super.defenseSkill(enemy)/2;
	}

	@Override
	protected boolean act() {
		int regen = Dungeon.hero.buff(AutoHealPotion.class) != null ? 200 : Random.Int(200,500);

		boolean blinded = this.buff(Blindness.class) != null;
		
		if (paralysed | blinded) {
			yell("ARRRGH!");
			
			if(!checkEyes()){
				ArrayList<Integer> spawnPoints = new ArrayList<Integer>();

				for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
					int p = Dungeon.hero.pos + Level.NEIGHBOURS8[i];
					if (Actor.findChar(p) == null
							&& (Level.passable[p] || Level.avoid[p])) {
						spawnPoints.add(p);
					}
				}

				if (spawnPoints.size() > 0) {
					MagicEye eye = new MagicEye();
					eye.pos = Random.element(spawnPoints);

					GameScene.add(eye);
					Actor.addDelayed(new Pushing(eye, pos, eye.pos), -1);
				}
			}
			
			if (HP < HT) {
				sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
				HP = HP + Math.min(HT - HP, regen);
			}
		}

		
		boolean result = super.act();
		if (HP < HT & !Level.fieldOfView[this.pos]) {//Only heals when not visible
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			HP = HP + Math.min(HT - HP, regen);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void die(Object cause) {
		
		Dungeon.level.locked=false;
		GameScene.bossSlain();
		
		for (Mob mob : (Iterable<Mob>) Dungeon.level.mobs.clone()) {
			if (mob instanceof ZotPhase || mob instanceof MagicEye) {
				mob.die(cause);
				mob.destroy();
				mob.sprite.killAndErase();
			}
		}
		
		super.die(cause);
		yell("...");
		OtilukeNPC.spawnAt(pos);
	}

	@Override
	protected boolean getCloser(int target) {
		if (canJump && Level.fieldOfView[target]) {
			jump();
			return true;
		} else {
			return super.getCloser(target);
		}
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return Ballistica.cast(pos, enemy.pos, false, true) == enemy.pos;
	}

	@Override
	protected boolean doAttack(Char enemy) {
		if (HPAtLastJump - HP > 5000) {
			canJump = true;
		}

		if (enemy.invisible > 0) {
			GLog.n("Zot: Fool. I can still see you!");
		}

		if (canJump) {
			jump();
			return true;
		} else {
			return super.doAttack(enemy);
		}
	}

	private void jump() {
		
		if (!checkPhases()){
			ArrayList<Integer> spawnPoints = new ArrayList<Integer>();

			for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
				int p = pos + Level.NEIGHBOURS8[i];
				if (Actor.findChar(p) == null
						&& (Level.passable[p] || Level.avoid[p])) {
					spawnPoints.add(p);
				}
			}

			if (spawnPoints.size() > 0) {
				ZotPhase zot = new ZotPhase();
				zot.pos = Random.element(spawnPoints);

				GameScene.add(zot);
				Actor.addDelayed(new Pushing(zot, pos, zot.pos), -1);
			}
		}
		
		int newPos;
		do {
			newPos = Random.Int(Level.getLength());
		} while (!Level.fieldOfView[newPos] || !Level.passable[newPos]
				|| Level.adjacent(newPos, enemy.pos)
				|| Actor.findChar(newPos) != null);

		sprite.move(pos, newPos);
		move(newPos);

		if (Dungeon.visible[newPos] && Level.distance(pos, enemy.pos) < 8) {
			CellEmitter.get(newPos).burst(Speck.factory(Speck.WOOL), 10);
			Sample.INSTANCE.play(Assets.SND_PUFF);
		} else {
			canJump = false;

			HPAtLastJump = HP;
		}
		if (enemy != null) {
			MagicEye.spawnAroundChance(this.pos);
		}

		spend(TIME_TO_TELEPORT);
	}
	
	private boolean checkPhases() {
		boolean check = false;
		int phases = 0;
		for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
			if (mob instanceof ZotPhase) {
				phases++;
				if (Dungeon.hero.heroClass != HeroClass.HUNTRESS && phases > 6) {
					check = true;
				} else if (phases > 10) {
					check = true;
				}
			}
		}
		return check;
	}
	
	private boolean checkEyes() {
		boolean check = false;
		int phases = 0;
		for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
			if (mob instanceof MagicEye) {
				phases++;
				if (Dungeon.hero.heroClass != HeroClass.HUNTRESS && phases > 20) {
					check = true;
				} else if (phases > 30) {
					check = true;
				}
			}
		}
		return check;
	}
	
	@Override
	public void damage(int dmg, Object src) {

		
		if(Dungeon.hero.heroClass == HeroClass.HUNTRESS && !checkPhases()){
			ArrayList<Integer> spawnPoints = new ArrayList<Integer>();

			for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
				int p = Dungeon.hero.pos + Level.NEIGHBOURS8[i];
				if (Actor.findChar(p) == null
						&& (Level.passable[p] || Level.avoid[p])) {
					spawnPoints.add(p);
				}
			}

			if (spawnPoints.size() > 0) {
				MagicEye eye = new MagicEye();
				eye.pos = Random.element(spawnPoints);

				GameScene.add(eye);
				Actor.addDelayed(new Pushing(eye, pos, eye.pos), -1);
			}
		}
		
		super.damage(dmg, src);
	}

	@Override
	public void notice() {
		super.notice();
		yell("...");
	}

	@Override
	public String description() {
		return "Zot.";
	}

	private final static String HPATLASTJUMP = "last_jump";

	@Override
	public void storeInBundle(Bundle bundle) {
		bundle.put(HPATLASTJUMP, HPAtLastJump);
		super.storeInBundle(bundle);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		HPAtLastJump = bundle.getInt(HPATLASTJUMP);
		super.restoreFromBundle(bundle);
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Poison.class);
		RESISTANCES.add(Death.class);
		IMMUNITIES.add(Leech.class);
		IMMUNITIES.add(Death.class);
		IMMUNITIES.add(Terror.class);
		IMMUNITIES.add(Amok.class);
		IMMUNITIES.add(Charm.class);
		IMMUNITIES.add(Sleep.class);
		IMMUNITIES.add(Burning.class);
		IMMUNITIES.add(ToxicGas.class);
		IMMUNITIES.add(ScrollOfPsionicBlast.class);
		IMMUNITIES.add(Vertigo.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
