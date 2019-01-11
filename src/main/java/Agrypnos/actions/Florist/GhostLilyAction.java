package Agrypnos.actions.Florist;

import Agrypnos.abstracts.FlowerCard;
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
    }

    @Override
    public void update() {
        ArrayList<FlowerCard> FlowerCardsInHand = new ArrayList<>();

        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if (c instanceof FlowerCard)
                FlowerCardsInHand.add((FlowerCard)c);
        }
        if (FlowerCardsInHand.size() > 0)
        {
            for (FlowerCard c : FlowerCardsInHand)
            {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));

                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(seedCard, amount));
            }
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(seedCard, amount));

        this.isDone = true;
    }
}
