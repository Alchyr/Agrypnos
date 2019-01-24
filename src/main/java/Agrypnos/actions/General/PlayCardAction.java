package Agrypnos.actions.General;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PlayCardAction extends AbstractGameAction {
    private boolean exhaustCards;
    private AbstractCard card;

    public PlayCardAction(AbstractCard cardInHandToUse, AbstractCreature target, boolean exhausts) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.card = cardInHandToUse;
        this.target = target;
        this.exhaustCards = exhausts;
    }

    public void update() {
        card.freeToPlayOnce = true;
        card.exhaustOnUseOnce = this.exhaustCards;

        if (!card.canUse(AbstractDungeon.player, (AbstractMonster)this.target)) {
            if (this.exhaustCards) {
                AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
            } else {
                AbstractDungeon.actionManager.addToTop(new DiscardSpecificCardAction(card, AbstractDungeon.player.hand));
                AbstractDungeon.actionManager.addToTop(new WaitAction(0.2F));
            }
        } else {
            card.applyPowers();
            AbstractDungeon.actionManager.addToTop(new QueueCardAction(card, this.target));
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.1F));
        }

        AbstractDungeon.actionManager.addToTop(new UpdateHandAction());

        this.isDone = true;
    }
}