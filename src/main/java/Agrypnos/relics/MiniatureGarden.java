package Agrypnos.relics;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.Relic;
import Agrypnos.actions.Florist.DiscardAllFlowersAction;
import Agrypnos.cards.Florist.Flowers.FlowerCard;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class MiniatureGarden extends Relic
{
    public static final String ID = Agrypnos.createID("Miniature Garden");


    public MiniatureGarden() {
        super(ID, "MiniatureGarden",
                RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new MiniatureGarden();
    }

    @Override
    public void onEquip() {
        FlowerCard.ResetOnPlay = false;
    }

    @Override
    public void onUnequip() {
        FlowerCard.ResetOnPlay = true;
    }

    @Override
    public void onPlayerEndTurn() {
        AbstractDungeon.actionManager.addToBottom(new DiscardAllFlowersAction());
    }
}