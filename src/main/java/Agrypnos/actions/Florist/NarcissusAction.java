package Agrypnos.actions.Florist;

import Agrypnos.actions.General.ExhaustConditionalCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;

public class NarcissusAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private AbstractCard copyCard = null;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("NarcissusAction");
        TEXT = uiStrings.TEXT;
    }

    public NarcissusAction(int amount) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
    }

    public void update() {
        Iterator cardIterator;
        AbstractCard c;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.group.size() == 0)
            {
                this.isDone = true;
                return;
            }
            else if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);
                this.tickDuration();
                return;
            }
            else
            {
                copyCard = this.p.hand.getTopCard();
                copyCard.superFlash();

                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(copyCard.makeStatEquivalentCopy(), amount));

                this.isDone = true;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            cardIterator = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while(cardIterator.hasNext()) {
                copyCard = (AbstractCard)cardIterator.next();
                copyCard.superFlash();
                this.p.hand.addToTop(copyCard);
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();

            //copyCard is selected
            int exhaustCount = Math.min(AbstractDungeon.player.hand.group.size() - 1, amount);

            AbstractDungeon.actionManager.addToBottom(new ExhaustConditionalCardsAction(AbstractDungeon.player.hand, (card)->!card.equals(copyCard), exhaustCount));

            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(copyCard.makeStatEquivalentCopy(), amount));

            this.isDone = true;
        }

        this.tickDuration();
    }
}