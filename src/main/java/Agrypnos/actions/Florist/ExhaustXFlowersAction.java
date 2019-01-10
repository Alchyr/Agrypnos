package Agrypnos.actions.Florist;

import Agrypnos.cards.Florist.Flowers.FlowerCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;

public class ExhaustXFlowersAction extends AbstractGameAction
{
    ArrayList<AbstractCard> NotFlowerCards = new ArrayList<>();

    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean isRandom;
    public static int numExhaust;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustFlowersAction");
        TEXT = uiStrings.TEXT;
    }

    public ExhaustXFlowersAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom) {
        this.p = (AbstractPlayer)target;
        this.isRandom = isRandom;
        this.setValues(target, source, amount);
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }


    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            }

            ArrayList<FlowerCard> FlowerCardsInHand = new ArrayList<>();

            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof FlowerCard)
                    FlowerCardsInHand.add((FlowerCard) c);
            }

            if (FlowerCardsInHand.size() <= this.amount) {
                this.amount = FlowerCardsInHand.size();
                numExhaust = this.amount;

                for(FlowerCard c : FlowerCardsInHand) {
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                }

                this.isDone = true;

                return;
            }

            if (!this.isRandom) {
                numExhaust = this.amount;

                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (!(c instanceof FlowerCard)) {
                        this.NotFlowerCards.add(c);
                    }
                }
                if (this.NotFlowerCards.size() == this.p.hand.group.size()) {
                    this.isDone = true;
                    return;
                }

                //There is only one possible option, no need for choice
                if (this.p.hand.group.size() - this.NotFlowerCards.size() == 1) {
                    Iterator handIterator = this.p.hand.group.iterator();

                    while (handIterator.hasNext()) {
                        AbstractCard c = (AbstractCard)handIterator.next();
                        if (c instanceof  FlowerCard) {
                            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                            this.isDone = true;
                            return;
                        }
                    }
                }

                this.p.hand.group.removeAll(this.NotFlowerCards);

                if (this.p.hand.group.size() > 1) {
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, false, false, false, false);
                    this.tickDuration();
                    return;
                }
                else if (this.p.hand.group.size() == 1) {
                    if (this.p.hand.getTopCard() instanceof FlowerCard)
                    {
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(AbstractDungeon.player.hand.getTopCard(), AbstractDungeon.player.hand));
                    }

                    this.returnCards();
                    this.isDone = true;
                }

                this.tickDuration();
                return;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                if (c instanceof  FlowerCard) {
                    AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand));
                }
            }

            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        this.tickDuration();
    }


    private void returnCards() {
        Iterator NotFlowerCardIterator = this.NotFlowerCards.iterator();

        while(NotFlowerCardIterator.hasNext()) {
            AbstractCard c = (AbstractCard)NotFlowerCardIterator.next();
            this.p.hand.addToTop(c);
        }

        this.p.hand.refreshHandLayout();
    }
}