package Agrypnos.actions.Florist;

import Agrypnos.actions.General.CardFlashAction;
import Agrypnos.cards.Florist.Flowers.FlowerCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;


public class TriggerGrowthAction extends AbstractGameAction {
    FlowerCard target;
    int growth;

    public TriggerGrowthAction(FlowerCard target, int growth)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.target = target;
        this.growth = growth;
    }

    @Override
    public void update()
    {
        if (growth != 0)
        {
            Agrypnos.Agrypnos.logger.info("Triggering growth of " + target.cardID);
            switch (target.FlowerGrowth)
            {
                case damage:
                    target.baseDamage += growth;
                    if (target.baseDamage < 0)
                        target.baseDamage = 0;

                    target.grown = true;
                    break;
                case block:
                    target.baseBlock += growth;
                    if (target.baseBlock < 0)
                        target.baseBlock = 0;

                    target.grown = true;
                    break;
                case magic:
                    target.baseMagicNumber += growth;
                    if (target.baseMagicNumber < 0)
                        target.baseMagicNumber = 0;

                    target.magicNumber = target.baseMagicNumber;

                    target.grown = true;
                    break;
                case draw:
                    target.baseDraw += growth;
                    if (target.baseDraw < 0)
                        target.baseDraw = 0;

                    target.grown = true;
                    break;
                case heal:
                    target.baseHeal += growth;
                    if (target.baseHeal < 0)
                        target.baseHeal = 0;

                    target.grown = true;
                    break;
                case permanentdamage:
                    target.baseDamage += growth;
                    if (target.baseDamage < 0)
                        target.baseDamage = 0;

                    target.grown = true;


                    Iterator cardIterator = AbstractDungeon.player.masterDeck.group.iterator();

                    AbstractCard c;
                    while(cardIterator.hasNext()) {
                        c = (AbstractCard)cardIterator.next();
                        if (c.uuid.equals(target.uuid) && c instanceof FlowerCard) {
                            c.baseDamage += growth;
                            c.isDamageModified = false;
                        }
                    }
                    break;
            }

            target.applyPowers();

            AbstractDungeon.player.hand.refreshHandLayout();

            AbstractDungeon.actionManager.addToBottom(new OnFlowerGrowthAction(target));
            AbstractDungeon.actionManager.addToBottom(new CardFlashAction(target.uuid));
        }

        this.isDone = true;

    }
}