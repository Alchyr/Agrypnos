package Agrypnos.relics;

import Agrypnos.Agrypnos;
import Agrypnos.cards.Florist.Skills.Seed;
import Agrypnos.abstracts.Relic;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class FlowerPot extends Relic
{
    public static final String ID = Agrypnos.createID("Flower Pot");

    public FlowerPot() {
        super(ID, "FlowerPot",
                RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FlowerPot();
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Seed().makeCopy(), 1));
    }
}
