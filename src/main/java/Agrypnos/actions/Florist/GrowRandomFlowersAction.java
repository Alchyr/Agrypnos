package Agrypnos.actions.Florist;

import Agrypnos.cards.Florist.Flowers.FlowerCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class GrowRandomFlowersAction extends AbstractGameAction {
    CardGroup source;

    public GrowRandomFlowersAction(CardGroup source, int amount)
    {
        this.source = source;
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        ArrayList<FlowerCard> FlowerCards = new ArrayList<FlowerCard>();

        for (AbstractCard c : source.group)
        {
            if (c instanceof FlowerCard)
                FlowerCards.add((FlowerCard)c);
        }

        if (FlowerCards.size() >= 1)
        {
            for (int i = 0; i < this.amount; i++)
            {
                AbstractDungeon.actionManager.addToBottom(FlowerCards.get(AbstractDungeon.cardRng.random(0, FlowerCards.size() - 1)).getTriggerGrowthAction());
            }
        }


        this.isDone = true;
    }
}
