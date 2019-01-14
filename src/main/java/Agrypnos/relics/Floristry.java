package Agrypnos.relics;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.Relic;
import Agrypnos.actions.General.MoveRandomCardAction;
import Agrypnos.abstracts.FlowerCard;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.*;

public class Floristry extends Relic
{
    public static final String ID = Agrypnos.createID("Floristry");

    public Floristry() {
        super(ID, "Floristry",
                RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Floristry();
    }

    @Override
    public boolean canSpawn() {
        return false;
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.actionManager.addToBottom(new MoveRandomCardAction(AbstractDungeon.player.hand, AbstractDungeon.player.drawPile, (c) -> c instanceof FlowerCard, 1));
    }
}
