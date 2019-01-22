package Agrypnos.actions.Florist;

import Agrypnos.abstracts.FlowerCard;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class GhostLilyAction extends AbstractGameAction {
    private AbstractCard seedCard;

    public GhostLilyAction(AbstractCard seedCard, int amount)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.amount = amount;
        this.seedCard = seedCard;

        this.seedCard.retain = true;
    }

    @Override
    public void update() {
        int handSpace = BaseMod.MAX_HAND_SIZE;

        //Agrypnos.Agrypnos.logger.info("Ghost Lily: Conversion rate = " + this.amount);

        ArrayList<FlowerCard> FlowerCardsInHand = new ArrayList<>();

        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            handSpace--;
            //Agrypnos.Agrypnos.logger.info("Card in hand: " + c.cardID);
            if (c instanceof FlowerCard)
            {
                FlowerCardsInHand.add((FlowerCard)c);
                //Agrypnos.Agrypnos.logger.info("This is a Flower.");
            }
        }
        if (!FlowerCardsInHand.isEmpty())
        {
            Agrypnos.Agrypnos.logger.info("Ghost Lily: Converting " + FlowerCardsInHand.size() + " Flowers to Seeds.");
            for (FlowerCard c : FlowerCardsInHand)
            {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));

                handSpace++; //since this will be exhausted
                if (handSpace >= 1)
                {
                    seedCard.retain = true;
                }

                for (int i = 0; i < this.amount; i++)
                {
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(seedCard.makeStatEquivalentCopy(), 1));
                    handSpace--;
                    if (handSpace <= 0)
                    {
                        seedCard.retain = false;
                        handSpace = 0;
                    }
                }
            }
        }

        for (int i = 0; i < this.amount; i++) //one additional time for the Ghost Lily itself
        {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(seedCard.makeStatEquivalentCopy(), 1));
            handSpace--;
            if (handSpace <= 0)
            {
                seedCard.retain = false;
                handSpace = 0;
            }
        }

        this.isDone = true;
    }
}
