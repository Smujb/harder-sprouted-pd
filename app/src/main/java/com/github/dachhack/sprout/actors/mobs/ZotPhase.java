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

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.ResultDescriptions;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.effects.particles.SparkParticle;
import com.github.dachhack.sprout.items.Generator;
import com.github.dachhack.sprout.levels.Level;
import com.github.dachhack.sprout.levels.traps.LightningTrap;
import com.github.dachhack.sprout.mechanics.Ballistica;
import com.github.dachhack.sprout.sprites.CharSprite;
import com.github.dachhack.sprout.sprites.ZotPhaseSprite;
import com.github.dachhack.sprout.utils.GLog;
import com.github.dachhack.sprout.utils.Utils;
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

import java.util.HashSet;

public class ZotPhase extends Mob implements Callback{

	private static final float TIME_TO_ZAP = 3f;

	private static final String TXT_LIGHTNING_KILLED = "%s's lightning bolt killed you...";

	{
		name = "Zot";
		spriteClass = ZotPhaseSprite.class;

		HP = HT = 2000;
		baseSpeed = 2f;

		EXP = 36;		

		loot = Generator.Category.SCROLL;
		lootChance = 0.33f;

		seeThroughInvisibility = true;

		scalesWithHeroLevel = true;
		
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(115, 200);
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
	public int dr() {
		return 100;
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return Ballistica.cast(pos, enemy.pos, false, true) == enemy.pos & Level.distance(pos,enemy.pos) < 5;
	}

	@Override
	protected boolean doAttack(Char enemy) {

		if (enemy.invisible > 0 & Random.Int(3) == 0) {
			GLog.n("Zot: Fool. I can still see you!");
		}

		if (Level.distance(pos, enemy.pos) <= 1) {

			return super.doAttack(enemy);

		} else {

			boolean visible = Level.fieldOfView[pos]
					|| Level.fieldOfView[enemy.pos];
			if (visible) {
				((ZotPhaseSprite) sprite).zap(enemy.pos);
			}

			spend(TIME_TO_ZAP);

			if (hit(this, enemy, true)) {
				int dmg = Random.Int(80, 120);
				if (Level.water[enemy.pos] && !enemy.flying) {
					dmg *= 2f;
				}
				enemy.damage(dmg, LightningTrap.LIGHTNING);

				enemy.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
				enemy.sprite.flash();

				if (enemy == Dungeon.hero) {

					Camera.main.shake(2, 0.3f);

					if (!enemy.isAlive()) {
						Dungeon.fail(Utils.format(ResultDescriptions.MOB,
								Utils.indefinite(name)));
						GLog.n(TXT_LIGHTNING_KILLED, name);
					}
				}
			} else {
				enemy.sprite
						.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
			}

			return !visible;
		}
	}

	@Override
	public void call() {
		next();
	}

	@Override
	public String description() {
		
		return (Random.Int(10) > 2) ? "Zot." : "???";
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(LightningTrap.Electricity.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
}
