package Agrypnos.relics;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.Relic;
import Agrypnos.actions.General.MoveRandomCardAction;
import Agrypnos.abstracts.FlowerCard;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Floriculture extends Relic
{
    public static final String ID = Agrypnos.createID("Floriculture");

    public Floriculture() {
        super(ID, "Floriculture",
                RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Floriculture();
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new MoveRandomCardAction(AbstractDungeon.player.hand, AbstractDungeon.player.drawPile, (c) -> c instanceof FlowerCard, 1));
    }
}
