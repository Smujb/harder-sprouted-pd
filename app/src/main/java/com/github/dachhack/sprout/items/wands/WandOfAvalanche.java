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
package com.github.dachhack.sprout.items.wands;

import com.github.dachhack.sprout.Assets;
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.ResultDescriptions;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.buffs.Paralysis;
import com.github.dachhack.sprout.actors.buffs.Strength;
import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.effects.CellEmitter;
import com.github.dachhack.sprout.effects.MagicMissile;
import com.github.dachhack.sprout.effects.Speck;
import com.github.dachhack.sprout.levels.Level;
import com.github.dachhack.sprout.mechanics.Ballistica;
import com.github.dachhack.sprout.utils.BArray;
import com.github.dachhack.sprout.utils.GLog;
import com.github.dachhack.sprout.utils.Utils;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class WandOfAvalanche extends Wand {

	{
		name = "Wand of Avalanche";
	}

	@Override
	public int max(int lvl) {
		return 10 + 9*lvl;
	}

	@Override
	public int min(int lvl) {
		return 6 + 3*lvl;
	}

	@Override
	protected void onZap(int cell) {

		Sample.INSTANCE.play(Assets.SND_ROCKS);

		int level = level();

		Ballistica.distance = Math.min(Ballistica.distance, 8 + level);

		int size = 2;
		PathFinder.buildDistanceMap(cell, BArray.not(Level.solid, null), size);

		for (int i = 0; i < Level.getLength(); i++) {

			int d = PathFinder.distance[i];

			if (d < Integer.MAX_VALUE) {

				Char ch = Actor.findChar(i);
				if (ch != null) {

					ch.sprite.flash();
					
					 int damage = damageRoll();
			         if (Dungeon.hero.buff(Strength.class) != null){ damage *= (int) 4f; Buff.detach(Dungeon.hero, Strength.class);}
			         if (ch == Dungeon.hero) {
			         	damage = Random.Int(damage/2) + 1;
					 }
					 ch.damage(damage, this);
	

					if (ch.isAlive() && Random.Int(2 + d) < 2 && !(ch instanceof Hero)) {//Doesn't self-paralyze
						Buff.prolong(ch, Paralysis.class, Random.IntRange(5, 10));
					}
				}

				CellEmitter.get(i).start(Speck.factory(Speck.ROCK), 0.07f,
						3 + (size - d));
				Camera.main.shake(3, 0.07f * (Math.max(1, level() - d)));
			}
		}

		if (!curUser.isAlive()) {
			Dungeon.fail(Utils.format(ResultDescriptions.ITEM, name));
			GLog.n("You killed yourself with your own Wand of Avalanche...");
		}
	}

	@Override
	protected void fx(int cell, Callback callback) {
		MagicMissile.earth(curUser.sprite.parent, curUser.pos, cell, callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}

	@Override
	public String desc() {
		return "When a discharge of this wand hits a wall (or any other solid obstacle) it causes "
				+ "an avalanche of stones, damaging and stunning all creatures in the affected area." +
				"It will deal reduced damage at distance, but may harm the player when close up." +
				"\n\n" + statsDesc(levelKnown);
	}
}
