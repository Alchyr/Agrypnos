package Agrypnos.actions.Florist;

import Agrypnos.abstracts.FlowerCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.RitualDagger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Iterator;

public class GeneModificationAction extends AbstractGameAction
{
    private ArrayList<AbstractCard> NotFlowerCards = new ArrayList<>();

    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("UpgradeFlowerGrowthAction");
        TEXT = uiStrings.TEXT;
    }

    public GeneModificationAction(AbstractCreature source, int amount) {
        this.p = AbstractDungeon.player;
        this.source = source;
        this.amount = amount;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }


    public void update() {
        FlowerCard upgradeCard = null;

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

            if (FlowerCardsInHand.size() == 1) {
                for(FlowerCard c : FlowerCardsInHand) {
                    upgradeCard = c;
                    upgradeCard.upgradeGrowth(this.amount);
                }
            }
            else
            {
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

                    while(handIterator.hasNext()) {
                        AbstractCard c = (AbstractCard)handIterator.next();
                        if (c instanceof FlowerCard) {
                            upgradeCard = (FlowerCard)c;
                            upgradeCard.upgradeGrowth(this.amount);
                        }
                    }
                }
                else
                {
                    this.p.hand.group.removeAll(this.NotFlowerCards);

                    if (this.p.hand.group.size() > 1) {
                        AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                        this.tickDuration();
                        return;
                    }
                    else if (this.p.hand.group.size() == 1) {
                        if (this.p.hand.getTopCard() instanceof FlowerCard)
                        {
                            upgradeCard = (FlowerCard)this.p.hand.getTopCard();
                            upgradeCard.upgradeGrowth(this.amount);
                        }
                        this.returnCards();
                    }
                }
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                if (c instanceof FlowerCard) {
                    upgradeCard = (FlowerCard)c;
                    upgradeCard.upgradeGrowth(this.amount);
                }
                this.p.hand.addToTop(c);
            }

            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
        }

        if (upgradeCard != null)
        {
            Iterator cardIterator = AbstractDungeon.player.masterDeck.group.iterator();

            AbstractCard c;
            while(cardIterator.hasNext()) {
                c = (AbstractCard)cardIterator.next();
                if (c.uuid.equals(upgradeCard.uuid) && c instanceof FlowerCard) {
                    ((FlowerCard)c).upgradeGrowth(this.amount);
                }
            }

            upgradeCard.flash(FlowerCard.growthFlash);
        }

        AbstractDungeon.player.hand.refreshHandLayout();
        AbstractDungeon.player.hand.glowCheck();

        this.isDone = true;
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