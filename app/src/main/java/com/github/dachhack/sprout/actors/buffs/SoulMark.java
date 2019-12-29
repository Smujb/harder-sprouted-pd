package com.github.dachhack.sprout.actors.buffs;

import com.github.dachhack.sprout.ui.BuffIndicator;

public class SoulMark extends FlavourBuff {

    public static final float DURATION	= 10f;

    @Override
    public int icon() {
        return BuffIndicator.CORRUPT;
    }

    /*@Override
    public void fx(boolean on) {
        if (on) target.sprite.add(CharSprite.State.MARKED);
        else target.sprite.remove(CharSprite.State.MARKED);
    }*/

    @Override
    public String toString() {
        return "soul mark";
    }
}
