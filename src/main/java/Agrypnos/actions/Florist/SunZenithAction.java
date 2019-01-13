package Agrypnos.actions.Florist;

import Agrypnos.abstracts.FlowerCard;
import Agrypnos.actions.General.ReduceCostUntilEndOfTurnAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class SunZenithAction extends AbstractGameAction
{
    private AbstractPlayer p;

    public SunZenithAction(int amount) {
        this.p = (AbstractPlayer)target;
        this.amount = amount;
        this.actionType = ActionType.CARD_MANIPULATION;
    }


    public void update() {
        ArrayList<FlowerCard> FlowerCardsInHand = new ArrayList<>();

        for (AbstractCard c : p.hand.group) {
            if (c instanceof FlowerCard)
                FlowerCardsInHand.add((FlowerCard) c);
        }

        if (FlowerCardsInHand.size() == 0)
        {
            this.isDone = true;
            return;
        }
        else
        {
            int mostGrow, mostGrowIndex = 0;

            mostGrow = FlowerCardsInHand.get(0).GrowthCount();

            for (int i = 0; i < FlowerCardsInHand.size(); i++)
            {
                if (FlowerCardsInHand.get(i).GrowthCount() > mostGrow)
                {
                    mostGrow = FlowerCardsInHand.get(i).GrowthCount();
                    mostGrowIndex = i;
                }
            }

            AbstractDungeon.actionManager.addToBottom(new ReduceCostUntilEndOfTurnAction(FlowerCardsInHand.get(mostGrowIndex).uuid, this.amount));
        }

        this.isDone = true;
    }
}