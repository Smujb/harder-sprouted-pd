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

import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.items.food.MysteryMeat;
import com.github.dachhack.sprout.sprites.CrabSprite;
import com.watabou.utils.Random;

public class Crab extends Mob {

	{
		name = "sewer crab";
		spriteClass = CrabSprite.class;

		HP = HT = 15+(adjustForDepth(0)*Random.NormalIntRange(1, 3));
		defenseSkill = 5+ adjustForDepth(1);
		baseSpeed = 2f;

		EXP = 3;
		maxLvl = 9;

		loot = new MysteryMeat();
		lootChance = 0.5f;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(3, 6+ adjustForDepth(0));
	}

	@Override
	public int attackSkill(Char target) {
		return defaultAccuracy(target);
	}

	@Override
	public int dr() {
		return 4;
	}

	@Override
	public String defenseVerb() {
		return "parried";
	}

	@Override
	public String description() {
		return "These huge crabs are at the top of the food chain in the sewers. "
				+ "They are extremely fast and their thick carapace can withstand "
				+ "heavy blows.";
	}
}
