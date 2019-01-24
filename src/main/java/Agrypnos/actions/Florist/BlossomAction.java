package Agrypnos.actions.Florist;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.actions.General.PlayCardAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;

import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MayhemPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;
import java.util.Iterator;

public class BlossomAction extends AbstractGameAction {
    private ArrayList<AbstractCard> NotFlowerCards = new ArrayList<>();

    private boolean freeToPlayOnce;
    private AbstractPlayer p;
    private int energyOnUse;
    private int bonusGrowth;

    private FlowerCard FlowerToGrow;

    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("GrowFlowersAction");
        TEXT = uiStrings.TEXT;
    }

    public BlossomAction(AbstractPlayer p, boolean freeToPlayOnce, int energyOnUse, int bonusGrowth) {
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.bonusGrowth = bonusGrowth;
        this.FlowerToGrow = null;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            if (this.p.hand.size() == 0) {
                this.isDone = true;
                return;
            }

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
                        //Agrypnos.logger.info("Blossom: Flower chosen automatically. " + c.cardID);
                        this.FlowerToGrow = (FlowerCard)c;
                        this.tickDuration();
                        return;
                    }
                }
            }

            this.p.hand.group.removeAll(this.NotFlowerCards);

            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                this.tickDuration();
                return;
            }
            else if (this.p.hand.group.size() == 1) {
                if (this.p.hand.getTopCard() instanceof FlowerCard)
                {
                    this.FlowerToGrow = (FlowerCard)this.p.hand.getTopCard();
                }

                this.returnCards();

                this.tickDuration();
                return;
            }
            else { //0 non flower cards in hand
                this.returnCards();
                this.isDone = true;
                return;
            }
        }
        else //duration has been ticked
        {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
                {
                    if (c instanceof  FlowerCard) {
                        this.FlowerToGrow = (FlowerCard)c;
                    }
                    this.p.hand.addToTop(c);
                }

                this.returnCards();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            }
        }

        //FlowerCardToGrow should be set now

        if (FlowerToGrow != null)
        {
            //Agrypnos.logger.info("Growing Flower: " + FlowerToGrow.cardID);

            int effect = EnergyPanel.totalCount;
            if (this.energyOnUse != -1) {
                effect = this.energyOnUse;
            }

            if (this.p.hasRelic("Chemical X")) {
                effect += 2;
                this.p.getRelic("Chemical X").flash();
            }

            effect += bonusGrowth;

            if (effect > 0) {
                for (int i = 1; i < effect; i++)
                {
                    AbstractDungeon.actionManager.addToBottom(FlowerToGrow.getTriggerGrowthAction());
                }
                AbstractDungeon.actionManager.addToBottom(new TriggerGrowthAction(FlowerToGrow.getTriggerGrowthAction(), true, FlowerCard.growthFlash));

                if (!Settings.FAST_MODE) {
                    AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    AbstractDungeon.actionManager.addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
                }
            }

            AbstractDungeon.actionManager.addToBottom(new PlayCardAction(FlowerToGrow, AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng), false));

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }


        this.isDone = true;
    }

    private void returnCards() {
        Iterator NotFlowerCardIterator = this.NotFlowerCards.iterator();

        while(NotFlowerCardIterator.hasNext()) {
            AbstractCard c = (AbstractCard)NotFlowerCardIterator.next();
            if (!this.p.hand.contains(c))
                this.p.hand.addToTop(c);
        }

        this.p.hand.refreshHandLayout();
    }
}