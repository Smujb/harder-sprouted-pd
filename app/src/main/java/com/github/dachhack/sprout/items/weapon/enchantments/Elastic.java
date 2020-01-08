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
    private static final String TXT_ELASTIC = "Elastic %s";
    private static ItemSprite.Glowing PINK = new ItemSprite.Glowing( 0xFF00FF );

    @Override
    public boolean proc(Weapon weapon, Char attacker, Char defender, int damage ) {
        // lvl 0 - 20%
        // lvl 1 - 33%
        // lvl 2 - 43%
        int level = Math.max(0, weapon.level);

        if (Level.adjacent(defender.pos, attacker.pos)
                && Random.Int(level + 5) >= 4) {

            for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
                int ofs = Level.NEIGHBOURS8[i];
                if (defender.pos - attacker.pos == ofs) {
                    int newPos = defender.pos + ofs;
                    if ((Level.passable[newPos] || Level.avoid[newPos])
                            && Actor.findChar(newPos) == null) {

                        Actor.addDelayed(new Pushing(defender, defender.pos,
                                newPos), -1);

                        defender.pos = newPos;
                        // FIXME
                        if (defender instanceof Mob) {
                            Dungeon.level.mobPress((Mob) defender);
                        } else {
                            Dungeon.level.press(newPos, defender);
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
    public String name(String weaponName) {
        return String.format(TXT_ELASTIC, weaponName);
    }

    @Override
    public ItemSprite.Glowing glowing() {
        return PINK;
    }
}
