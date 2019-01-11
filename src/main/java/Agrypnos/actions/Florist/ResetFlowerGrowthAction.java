package Agrypnos.actions.Florist;

import Agrypnos.abstracts.FlowerCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class ResetFlowerGrowthAction extends AbstractGameAction {
    FlowerCard target;

    public ResetFlowerGrowthAction(FlowerCard target)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.target = target;
    }

    @Override
    public void update()
    {
        Agrypnos.Agrypnos.logger.info("Resetting growth of " + target.cardID);
        switch (target.FlowerGrowth)
        {
            case damage:
                target.baseDamage = target.initialValue;
                target.grown = false;
                target.applyPowers();
                break;
            case block:
                target.baseBlock = target.initialValue;
                target.grown = false;
                target.applyPowers();
                break;
            case magic:
                target.baseMagicNumber = target.initialValue;
                target.grown = false;
                target.applyPowers();
                break;
            case draw:
                target.baseDraw = target.initialValue;
                target.grown = false;
                target.applyPowers();
                break;
            case heal:
                target.baseHeal = target.initialValue;
                target.grown = false;
                target.applyPowers();
                break;
            case permanentdamage:
                target.baseDamage = target.initialValue;
                target.grown = false;
                target.applyPowers();


                Iterator cardIterator = AbstractDungeon.player.masterDeck.group.iterator();

                AbstractCard c;
                while(cardIterator.hasNext()) {
                    c = (AbstractCard)cardIterator.next();
                    if (c.uuid.equals(target.uuid) && c instanceof FlowerCard) {
                        c.applyPowers();
                        c.baseDamage = target.initialValue;
                        c.isDamageModified = false;
                    }
                }
                break;
        }
        target.flash(FlowerCard.resetFlash);
        this.isDone = true;
    }
}