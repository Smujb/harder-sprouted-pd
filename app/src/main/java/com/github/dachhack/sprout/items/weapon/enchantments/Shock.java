/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.github.dachhack.sprout.items.weapon.enchantments;

import java.util.ArrayList;
import java.util.HashSet;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.effects.Lightning;
import com.github.dachhack.sprout.effects.particles.SparkParticle;
import com.github.dachhack.sprout.items.wands.Wand;
import com.github.dachhack.sprout.items.weapon.Weapon;
import com.github.dachhack.sprout.items.weapon.melee.relic.RelicMeleeWeapon;
import com.github.dachhack.sprout.levels.Level;
import com.github.dachhack.sprout.levels.traps.LightningTrap;
import com.github.dachhack.sprout.utils.BArray;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Shock extends Weapon.Enchantment {

	private static final String TXT_SHOCKING = "Shocking %s";

	public boolean proc(Wand wand, Char attacker, Char defender, int damage) {
		int level = Math.max( 0, wand.level );

		if (Random.Int( level + 3 ) >= 2) {

			affected.clear();

			arcs.clear();
			arc(attacker, defender, 4);

			affected.remove(defender); //defender isn't hurt by lightning
			for (Char ch : affected) {
				ch.damage((int) Math.ceil(damage / 3f), this);
			}

			attacker.sprite.parent.add(new Lightning(points, nPoints, null));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean proc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {
		return false;
	}
	
	@Override
	public boolean proc(Weapon weapon, Char attacker, Char defender, int damage) {
		// lvl 0 - 25%
		// lvl 1 - 40%
		// lvl 2 - 50%
		/*int level = Math.max(0, weapon.level);

		if (Random.Int(level + 4) >= 3) {

			points[0] = attacker.pos;
			nPoints = 1;

			affected.clear();
			affected.add(attacker);

			hit(defender, Random.Int(1, damage / 2));

			attacker.sprite.parent.add(new Lightning(points, nPoints, null));

			return true;

		} else {

			return false;

		}*/
		int level = Math.max( 0, weapon.level );

		if (Random.Int( level + 3 ) >= 2) {

			affected.clear();

			arcs.clear();
			arc(attacker, defender, 2);

			affected.remove(defender); //defender isn't hurt by lightning
			for (Char ch : affected) {
				ch.damage((int) Math.ceil(damage / 3f), this);
			}

			attacker.sprite.parent.add(new Lightning(points, nPoints, null));
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String name(String weaponName) {
		return String.format(TXT_SHOCKING, weaponName);
	}

	ArrayList<Char> affected = new ArrayList<Char>();

	private int[] points = new int[20];
	private int nPoints;

	private ArrayList<Lightning> arcs = new ArrayList<>();

	private void arc( Char attacker, Char defender, int dist ) {

		affected.add(defender);

		defender.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
		defender.sprite.flash();

		PathFinder.buildDistanceMap( defender.pos, BArray.not(Level.solid, null ), dist );
		for (int i = 0; i < PathFinder.distance.length; i++) {
			if (PathFinder.distance[i] < Integer.MAX_VALUE) {
				Char n = Actor.findChar(i);
				if (n != null && n != attacker && !affected.contains(n)) {
					points[nPoints++] = n.pos;
					affected.add(n);
					arc(attacker, n,  dist);
				}
			}
		}
	}

	/*private void hit(Char ch, int damage, int range) {

		if (damage < 1) {
			return;
		}

		affected.add(ch);
		ch.damage(Level.water[ch.pos] && !ch.flying ? (int) (damage * 2)
				: damage, LightningTrap.LIGHTNING);

		ch.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
		ch.sprite.flash();

		points[nPoints++] = ch.pos;

		HashSet<Char> ns = new HashSet<Char>();
		for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
			Char n = Actor.findChar(ch.pos + Level.NEIGHBOURS8[i]);
			if (n != null && !affected.contains(n)) {
				ns.add(n);
			}
		}

		if (ns.size() > 0) {
			hit(Random.element(ns), Random.Int(damage / 2, damage));
		}
	}*/
}
