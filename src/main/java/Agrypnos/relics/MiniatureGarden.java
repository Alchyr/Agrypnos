package Agrypnos.relics;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.Relic;
import Agrypnos.actions.Florist.DiscardAllFlowersAction;
import Agrypnos.abstracts.FlowerCard;

import Agrypnos.actions.General.AddActionToBottomAction;
import Agrypnos.characters.TheFlorist;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class MiniatureGarden extends Relic
{
    public static final String ID = Agrypnos.createID("Miniature Garden");


    public MiniatureGarden() {
        super(ID, "MiniatureGarden",
                RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new MiniatureGarden();
    }

    public boolean canSpawn()
    {
        return AbstractDungeon.player instanceof TheFlorist;
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
        AbstractDungeon.actionManager.addToBottom(new AddActionToBottomAction(new DiscardAllFlowersAction()));
    }
}