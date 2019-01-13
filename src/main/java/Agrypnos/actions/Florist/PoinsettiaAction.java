package Agrypnos.actions.Florist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;

public class PoinsettiaAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private boolean anyNumber;
    private boolean canPickZero;
    private int block;

    public PoinsettiaAction(int amount, int block) {
        this.amount = amount;
        this.canPickZero = false;
        this.anyNumber = true;
        this.canPickZero = true;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.EXHAUST;
        this.block = block;
    }

    public void update() {
        int numExhausted;

        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            }

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, this.anyNumber, this.canPickZero);
            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            Iterator exhaustCards = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            numExhausted = AbstractDungeon.handCardSelectScreen.selectedCards.group.size();

            while(exhaustCards.hasNext()) {
                AbstractCard c = (AbstractCard)exhaustCards.next();
                this.p.hand.moveToExhaustPile(c);
            }

            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;

            for (int i = 0; i < numExhausted; i++)
            {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
            }

            this.isDone = true;
        }

        this.tickDuration();
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
        TEXT = uiStrings.TEXT;
    }
}
