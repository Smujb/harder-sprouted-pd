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
package com.github.dachhack.sprout.plants;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.actors.hero.HeroSubClass;
import com.github.dachhack.sprout.actors.mobs.Mob;
import com.github.dachhack.sprout.effects.CellEmitter;
import com.github.dachhack.sprout.effects.Speck;
import com.github.dachhack.sprout.items.artifacts.TimekeepersHourglass;
import com.github.dachhack.sprout.items.potions.PotionOfMindVision;
import com.github.dachhack.sprout.items.scrolls.ScrollOfTeleportation;
import com.github.dachhack.sprout.scenes.InterlevelScene;
import com.github.dachhack.sprout.sprites.ItemSpriteSheet;
import com.github.dachhack.sprout.utils.GLog;
import com.watabou.noosa.Game;

public class Fadeleaf extends Plant {

	private static final String TXT_DESC = "Touching a Fadeleaf will teleport any creature "
			+ "to a random place on the current level.";

	{
		image = 6;
		plantName = "Fadeleaf";
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);

		if (ch instanceof Hero) {

			((Hero) ch).curAction = null;
			if (((Hero) ch).subClass == HeroSubClass.WARDEN){

				if (Dungeon.bossLevel()) {
					GLog.w( "No teleportation allowed!!!!" );
					return;

				}

				Buff buff = Dungeon.hero.buff(TimekeepersHourglass.timeFreeze.class);
				if (buff != null) buff.detach();

				InterlevelScene.mode = InterlevelScene.Mode.RETURN;
				InterlevelScene.returnDepth = Math.max(1, (Dungeon.depth - 1));
				InterlevelScene.returnPos = -2;
				Game.switchScene( InterlevelScene.class );

			} else {
				ScrollOfTeleportation.teleportHero((Hero) ch);
			}

		} else if (ch instanceof Mob) {

			int count = 10;
			int newPos;
			do {
				newPos = Dungeon.level.randomRespawnCell();
				if (count-- <= 0) {
					break;
				}
			} while (newPos == -1);

			if (newPos != -1) {

				ch.pos = newPos;
				ch.sprite.place(ch.pos);
				ch.sprite.visible = Dungeon.visible[pos];

			}

		}

		if (Dungeon.visible[pos]) {
			CellEmitter.get(pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
		}
	}

	@Override
	public String desc() {
		return TXT_DESC;
	}

	public static class Seed extends Plant.Seed {
		{
			plantName = "Fadeleaf";

			name = "seed of " + plantName;
			image = ItemSpriteSheet.SEED_FADELEAF;

			plantClass = Fadeleaf.class;
			alchemyClass = PotionOfMindVision.class;
		}

		@Override
		public String desc() {
			return TXT_DESC;
		}
	}
}
