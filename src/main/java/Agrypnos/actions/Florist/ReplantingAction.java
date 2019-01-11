package Agrypnos.actions.Florist;

import Agrypnos.abstracts.FlowerCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Setup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;

public class ReplantingAction extends AbstractGameAction
{
    ArrayList<AbstractCard> NotFlowerCards = new ArrayList<>();

    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean isRandom;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ResetFlowerGrowthAction");
        TEXT = uiStrings.TEXT;
    }

    public ReplantingAction(AbstractCreature target, AbstractCreature source, boolean isRandom) {
        this.p = (AbstractPlayer)target;
        this.setValues(target, source, 1);
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.isRandom = isRandom;
    }


    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            }

            ArrayList<FlowerCard> FlowerCardsInHand = new ArrayList<>();

            for (AbstractCard c : p.hand.group) {
                if (c instanceof FlowerCard)
                    FlowerCardsInHand.add((FlowerCard) c);
            }

            if (FlowerCardsInHand.size() == 1) {
                if (FlowerCardsInHand.get(0).cost >= 0)
                {
                    AbstractDungeon.actionManager.addToBottom(new ResetFlowerGrowthAction(FlowerCardsInHand.get(0)));
                    FlowerCardsInHand.get(0).freeToPlayOnce = true;
                }

                this.isDone = true;

                return;
            }

            if (!this.isRandom) {
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (!(c instanceof FlowerCard) || c.cost < 0) {
                        this.NotFlowerCards.add(c);
                    }
                }
                if (this.NotFlowerCards.size() == this.p.hand.group.size()) {
                    this.isDone = true;
                    return;
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
                        AbstractDungeon.actionManager.addToBottom(new ResetFlowerGrowthAction((FlowerCard)this.p.hand.getTopCard()));
                    }

                    this.returnCards();
                    this.isDone = true;
                }

                this.tickDuration();
                return;
            }
            else
            {
                for (int i = 0; i < amount; i++)
                {
                    if (FlowerCardsInHand.size() > 0) {
                        FlowerCard c = FlowerCardsInHand.remove(AbstractDungeon.cardRng.random(FlowerCardsInHand.size() - 1));

                        AbstractDungeon.actionManager.addToBottom(new ResetFlowerGrowthAction(c));
                        c.freeToPlayOnce = true;
                    }
                }
                this.isDone = true;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                if (c instanceof  FlowerCard) {
                    AbstractDungeon.actionManager.addToBottom(new ResetFlowerGrowthAction((FlowerCard)c));
                    c.freeToPlayOnce = true;
                }
                this.p.hand.addToTop(c);
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
