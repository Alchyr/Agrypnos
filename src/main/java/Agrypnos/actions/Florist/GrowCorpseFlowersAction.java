package Agrypnos.actions.Florist;

import Agrypnos.cards.Florist.Flowers.CorpseFlower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class GrowCorpseFlowersAction extends AbstractGameAction {

    public GrowCorpseFlowersAction() {
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        ArrayList<CorpseFlower> corpseFlowers = new ArrayList<>();


        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if (c instanceof CorpseFlower)
            {
                corpseFlowers.add((CorpseFlower)c);
            }
        }

        if (false) //Later - check for spring, allowing flowers to grow regardles of location
        {
            for (AbstractCard c : AbstractDungeon.player.drawPile.group)
            {
                if (c instanceof CorpseFlower)
                {
                    corpseFlowers.add((CorpseFlower)c);
                }
            }
            for (AbstractCard c : AbstractDungeon.player.discardPile.group)
            {
                if (c instanceof CorpseFlower)
                {
                    corpseFlowers.add((CorpseFlower)c);
                }
            }
        }


        for (CorpseFlower c : corpseFlowers)
        {
            AbstractDungeon.actionManager.addToBottom(c.getTriggerGrowthAction());
        }

        this.isDone = true;
    }
}
