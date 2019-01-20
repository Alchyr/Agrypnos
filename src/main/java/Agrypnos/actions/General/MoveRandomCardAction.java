package Agrypnos.actions.General;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;
import java.util.function.Predicate;

public class MoveRandomCardAction extends AbstractGameAction {
    private AbstractPlayer p;
    private CardGroup source;
    private CardGroup destination;
    private Predicate<AbstractCard> predicate;

    public MoveRandomCardAction(CardGroup destination, CardGroup source, Predicate<AbstractCard> predicate, int amount) {
        this.p = AbstractDungeon.player;
        this.destination = destination;
        this.source = source;
        this.predicate = predicate;
        this.setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public MoveRandomCardAction(CardGroup destination, CardGroup source, Predicate<AbstractCard> predicate) {
        this(destination, source, predicate, 1);
    }

    public MoveRandomCardAction(CardGroup destination, CardGroup source, int amount) {
        this(destination, source, (c) -> {
            return true;
        }, amount);
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            Iterator sourceCards;
            AbstractCard card;

            CardGroup tempCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            sourceCards = this.source.group.iterator();

            while(sourceCards.hasNext()) {
                card = (AbstractCard)sourceCards.next();
                if (this.predicate.test(card)) {
                    tempCards.addToRandomSpot(card);
                }
            }

            if (tempCards.size() == 0) {
                this.isDone = true;
                return;
            }
            else
            {
                for (int i = 0; i < Math.min(tempCards.size(), amount); i++)
                {
                    AbstractCard c = tempCards.group.get(i);

                    if (this.destination == this.p.hand && this.p.hand.size() == BaseMod.MAX_HAND_SIZE) {
                        this.source.moveToDiscardPile(c);
                        this.p.createHandIsFullDialog();
                    } else {
                        c.unhover();
                        c.lighten(true);
                        c.setAngle(0.0F);
                        c.drawScale = 0.12F;
                        c.targetDrawScale = 0.75F;
                        c.current_x = CardGroup.DRAW_PILE_X;
                        c.current_y = CardGroup.DRAW_PILE_Y;
                        this.source.removeCard(c);
                        this.destination.addToTop(c);
                        AbstractDungeon.player.hand.refreshHandLayout();
                        AbstractDungeon.player.hand.glowCheck();
                        AbstractDungeon.player.hand.applyPowers();
                    }
                }
            }
        }
        this.tickDuration();
    }
}
