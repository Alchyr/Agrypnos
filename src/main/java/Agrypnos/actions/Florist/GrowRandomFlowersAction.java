package Agrypnos.actions.Florist;

import Agrypnos.abstracts.FlowerCard;
import Agrypnos.actions.General.CardFlashAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class GrowRandomFlowersAction extends AbstractGameAction {
    private CardGroup source;

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
            boolean[] flashed = new boolean[FlowerCards.size()];
            for (boolean b : flashed)
                b = false;

            for (int i = 0; i < this.amount; i++)
            {
                int randIndex = AbstractDungeon.cardRng.random(0, FlowerCards.size() - 1);
                AbstractDungeon.actionManager.addToBottom(FlowerCards.get(randIndex).getTriggerGrowthAction());
                flashed[randIndex] = true;
            }

            for (int i = 0; i < FlowerCards.size(); i++) //each card will only flash once.
            {
                if (flashed[i])
                {
                    AbstractDungeon.actionManager.addToBottom(new CardFlashAction(FlowerCards.get(i).uuid, FlowerCard.growthFlash));
                }
            }
        }


        this.isDone = true;
    }
}
