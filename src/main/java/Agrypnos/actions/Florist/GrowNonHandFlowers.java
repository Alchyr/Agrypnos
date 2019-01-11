package Agrypnos.actions.Florist;

import Agrypnos.abstracts.FlowerCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GrowNonHandFlowers extends AbstractGameAction {
    public GrowNonHandFlowers()
    {
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.player.drawPile.group)
        {
            if (c instanceof FlowerCard)
            {
                AbstractDungeon.actionManager.addToBottom(((FlowerCard)c).getTriggerGrowthAction());
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group)
        {
            if (c instanceof FlowerCard)
            {
                AbstractDungeon.actionManager.addToBottom(((FlowerCard)c).getTriggerGrowthAction());
            }
        }

        //Create "SunEffect" that flashes like a sun or something in center of room

        this.isDone = true;
    }
}
