package Agrypnos.actions.General;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;
import java.util.function.Predicate;

public class ExhaustConditionalCardsAction extends AbstractGameAction {
    private CardGroup source;
    private Predicate<AbstractCard> predicate;

    public ExhaustConditionalCardsAction(CardGroup source, Predicate<AbstractCard> predicate, int amount)
    {
        this.source = source;
        this.predicate = predicate;
        this.amount = amount;
    }
    public ExhaustConditionalCardsAction(CardGroup source, Predicate<AbstractCard> predicate)
    {
        this(source, predicate, 1);
    }

    public void update() {
        Iterator sourceCards;
        AbstractCard card;

        CardGroup removeCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        sourceCards = this.source.group.iterator();

        while(sourceCards.hasNext()) {
            card = (AbstractCard)sourceCards.next();
            if (this.predicate.test(card)) {
                removeCards.addToRandomSpot(card);
            }
        }

        if (removeCards.size() == 0) {
            this.isDone = true;
        }
        else
        {
            for (int i = 0; i < Math.min(removeCards.size(), amount); i++)
            {
                AbstractCard c = removeCards.getTopCard();
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, source));
            }
            this.isDone = true;
        }
    }
}
