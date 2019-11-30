package com.github.dachhack.sprout.items.potions;

import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.items.Item;

import java.util.ArrayList;

public class MegaExperiencePotion extends Item {
    {
        name = "Mega Potion of Experience";

        bones = true;
    }

    public static final String AC_DRINK = "DRINK";
    public void apply(Hero hero) {
        for (int i = 0; i < 80; i++) {
            hero.earnExp(hero.maxExp() - hero.exp);
        }
    }
    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = super.actions(hero);
        actions.add(AC_DRINK);
        return actions;
    }

    @Override
    public void execute(final Hero hero, String action) {
        if (action.equals(AC_DRINK)) {
            apply(hero);
        } else {
            super.execute(hero,action);
        }
    }
}
