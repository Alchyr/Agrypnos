package Agrypnos.actions.Florist;

import Agrypnos.abstracts.FlowerCard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class DiscardAllFlowersAction extends AbstractGameAction {

    public DiscardAllFlowersAction() {
        this.actionType = ActionType.DISCARD;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            ArrayList<FlowerCard> discardCards = new ArrayList<FlowerCard>();

            for (AbstractCard c : AbstractDungeon.player.hand.group)
            {
                if (c instanceof FlowerCard)
                {
                    discardCards.add((FlowerCard)c);
                }
            }

            for (FlowerCard c : discardCards)
            {
                if (AbstractDungeon.player.hand.contains(c))
                {
                    AbstractDungeon.player.hand.moveToDiscardPile(c);
                    GameActionManager.incrementDiscard(false);
                    c.triggerOnManualDiscard();
                }
            }
        }

        this.tickDuration();
    }
}
