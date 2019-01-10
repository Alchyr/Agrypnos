package Agrypnos.abstracts;


import basemod.abstracts.CustomRelic;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import Agrypnos.util.TextureLoader;


public abstract class Relic extends CustomRelic {
    public Relic(String setId, String textureID, RelicTier tier, LandingSound sfx) {
        super(setId, TextureLoader.getTexture("img/Agrypnos/relics/" + textureID + ".png"), tier, sfx);

        Texture outline = TextureLoader.getTexture("img/Agrypnos/relics/" + textureID + "-outline.png");

        outlineImg = outline;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        try{
            return this.getClass().newInstance();
        }catch(IllegalAccessException | InstantiationException e){
            throw new RuntimeException("Failed to auto-generate makeCopy for relic: " + this.relicId);
        }
    }
}