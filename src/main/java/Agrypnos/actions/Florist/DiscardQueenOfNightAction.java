package Agrypnos.actions.Florist;

import Agrypnos.cards.Florist.Flowers.QueenOfTheNight;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class DiscardQueenOfNightAction extends AbstractGameAction {

    public DiscardQueenOfNightAction() {
        this.actionType = ActionType.DISCARD;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            ArrayList<QueenOfTheNight> discardCards = new ArrayList<QueenOfTheNight>();

            for (AbstractCard c : AbstractDungeon.player.hand.group)
            {
                if (c instanceof QueenOfTheNight)
                {
                    discardCards.add((QueenOfTheNight)c);
                }
            }

            for (QueenOfTheNight c : discardCards)
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
