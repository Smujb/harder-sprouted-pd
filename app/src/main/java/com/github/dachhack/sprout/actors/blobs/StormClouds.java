package com.github.dachhack.sprout.actors.blobs;

import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.effects.BlobEmitter;
import com.github.dachhack.sprout.effects.Speck;
import com.github.dachhack.sprout.items.weapon.melee.relic.NeptunusTrident;
import com.github.dachhack.sprout.levels.Level;
import com.github.dachhack.sprout.levels.Terrain;
import com.github.dachhack.sprout.scenes.GameScene;

public class StormClouds extends Blob {

    @Override
    protected void evolve() {
        super.evolve();

        int cell;
        for (int i = 0; i < LENGTH; i++) {
            cell = i;
            if (off[cell] > 0) {
                if (NeptunusTrident.checkFloodable(cell)) {
                    Dungeon.level.map[cell] = Terrain.WATER;
                    Level.water[cell] = true;
                    GameScene.updateMap();
                    Dungeon.observe();
                }
            }
        }
    }

    @Override
    public void use( BlobEmitter emitter ) {
        super.use( emitter );
        emitter.pour( Speck.factory( Speck.STORM ), 0.4f );
    }

    @Override
    public String tileDesc() {
        return "A storm cloud is swirling here";
    }

}
