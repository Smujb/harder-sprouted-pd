package com.github.dachhack.sprout.items.potions;

import com.github.dachhack.sprout.actors.hero.Hero;

public class MegaExperiencePotion extends Potion {
    {
        name = "Mega Potion of Experience";

        bones = true;
    }

    @Override
    public void apply(Hero hero) {
        setKnown();
        for (int i = 0; i < 80; i++) {
            hero.earnExp(hero.maxExp() - hero.exp);
        }
    }
}
