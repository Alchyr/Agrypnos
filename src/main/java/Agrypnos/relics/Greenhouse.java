package Agrypnos.relics;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.Relic;
import Agrypnos.actions.Florist.GrowRandomFlowersAction;
import Agrypnos.characters.TheFlorist;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class Greenhouse extends Relic
{
    public static final String ID = Agrypnos.createID("Greenhouse");


    public Greenhouse() {
        super(ID, "Greenhouse",
                RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void onPlayerEndTurn() {
        int energy = EnergyPanel.totalCount;

        if (energy > 0)
        {
            AbstractDungeon.actionManager.addToTop(new GrowRandomFlowersAction(AbstractDungeon.player.hand, energy));
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Greenhouse();
    }

    public boolean canSpawn()
    {
        return AbstractDungeon.player instanceof TheFlorist;
    }


}