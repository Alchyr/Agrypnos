package Agrypnos.relics;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.abstracts.Relic;
import Agrypnos.actions.Florist.GrowRandomFlowersAction;
import Agrypnos.actions.General.MoveRandomCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Floriculture extends Relic
{
    public static final String ID = Agrypnos.createID("Floriculture");

    public Floriculture() {
        super(ID, "Floriculture",
                RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Floriculture();
    }


    public void obtain()
    {
        if (AbstractDungeon.player.hasRelic("Agrypnos:Floristry")) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); i++) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals("Agrypnos:Floristry")) {
                    this.counter = AbstractDungeon.player.getRelic("Agrypnos:Floristry").counter;
                    instantObtain(AbstractDungeon.player, i, true);

                    break;
                }
            }
        } else {
            super.obtain();
        }
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

    @Override
    public void atTurnStartPostDraw() {
        AbstractDungeon.actionManager.addToBottom(new GrowRandomFlowersAction(AbstractDungeon.player.hand, 1));
    }
}
