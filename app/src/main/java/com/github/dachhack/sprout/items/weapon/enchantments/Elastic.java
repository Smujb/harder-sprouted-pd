package com.github.dachhack.sprout.items.weapon.enchantments;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.mobs.Mob;
import com.github.dachhack.sprout.effects.Pushing;
import com.github.dachhack.sprout.items.weapon.Weapon;
import com.github.dachhack.sprout.items.weapon.melee.relic.RelicMeleeWeapon;
import com.github.dachhack.sprout.levels.Level;
import com.github.dachhack.sprout.sprites.ItemSprite;
import com.watabou.utils.Random;

public class Elastic extends Weapon.Enchantment {
    private static ItemSprite.Glowing PINK = new ItemSprite.Glowing( 0xFF00FF );

    @Override
    public boolean proc(Weapon weapon, Char attacker, Char defender, int damage ) {
        // lvl 0 - 20%
        // lvl 1 - 33%
        // lvl 2 - 43%
        int level = Math.max(0, weapon.level);

        if (Level.adjacent(attacker.pos, defender.pos)
                && Random.Int(level + 5) >= 4) {

            for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
                int ofs = Level.NEIGHBOURS8[i];
                if (attacker.pos - defender.pos == ofs) {
                    int newPos = attacker.pos + ofs;
                    if ((Level.passable[newPos] || Level.avoid[newPos])
                            && Actor.findChar(newPos) == null) {

                        Actor.addDelayed(new Pushing(attacker, attacker.pos,
                                newPos), -1);

                        attacker.pos = newPos;
                        // FIXME
                        if (attacker instanceof Mob) {
                            Dungeon.level.mobPress((Mob) attacker);
                        } else {
                            Dungeon.level.press(newPos, attacker);
                        }

                    }
                    break;
                }
            }

        }


        return true;
    }

    @Override
    public boolean proc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {
        return false;
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return PINK;
    }
}
