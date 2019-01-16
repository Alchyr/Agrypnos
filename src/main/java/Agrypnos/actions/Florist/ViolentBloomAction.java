package Agrypnos.actions.Florist;

import Agrypnos.abstracts.FlowerCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.EntanglePower;
import com.megacrit.cardcrawl.relics.VelvetChoker;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class ViolentBloomAction extends AbstractGameAction
{
    private ArrayList<AbstractCard> NotFlowerCards = new ArrayList<>();

    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPlayer p;
    private DamageInfo damage;

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ResetFlowerGrowthAction");
        TEXT = uiStrings.TEXT;
    }
    public ViolentBloomAction(AbstractCreature target, AbstractCreature source, DamageInfo damage) {
        this.p = AbstractDungeon.player;
        this.target = target;
        this.source = source;
        this.damage = damage;
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.CARD_MANIPULATION;
    }


    public void update() {
        FlowerCard resetCard = null;

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
                for(FlowerCard c : FlowerCardsInHand) {
                    resetCard = c;
                    AbstractDungeon.actionManager.addToBottom(new ResetFlowerGrowthAction(c));
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
                            resetCard = (FlowerCard)c;
                            AbstractDungeon.actionManager.addToBottom(new ResetFlowerGrowthAction((FlowerCard)c));
                        }
                    }
                }
                else
                {
                    this.p.hand.group.removeAll(this.NotFlowerCards);

                    if (this.p.hand.group.size() > 1) {
                        AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, false, false, false, false);
                        this.tickDuration();
                        return;
                    }
                    else if (this.p.hand.group.size() == 1) {
                        if (this.p.hand.getTopCard() instanceof FlowerCard)
                        {
                            resetCard = (FlowerCard)this.p.hand.getTopCard();
                            AbstractDungeon.actionManager.addToBottom(new ResetFlowerGrowthAction((FlowerCard)this.p.hand.getTopCard()));
                        }
                        this.returnCards();
                    }
                }
            }
        }

        //resetCard should be set at this point. If not, it should have returned and set isdone to true
        //Otherwise, it went into card select. Now testing that.

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                if (c instanceof FlowerCard) {
                    resetCard = (FlowerCard)c;
                    AbstractDungeon.actionManager.addToBottom(new ResetFlowerGrowthAction((FlowerCard)c));
                }
                this.p.hand.addToTop(c);
            }

            this.returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
        }

        //If code gets here, reset card should 100% be set. but, still check

        if (resetCard != null)
        {
            for (int i = 0; i < resetCard.GrowthCount(); i++)
            {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ExplosionSmallEffect(target.hb.cX, target.hb.cY)));
                AbstractDungeon.actionManager.addToBottom(
                        new com.megacrit.cardcrawl.actions.common.DamageAction(target, damage, AttackEffect.SHIELD));
            }
        }

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