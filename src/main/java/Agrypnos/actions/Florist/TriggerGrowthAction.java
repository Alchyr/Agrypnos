package Agrypnos.actions.Florist;

import Agrypnos.abstracts.FlowerCard;
import Agrypnos.powers.Florist.NoGrowPower;
import Agrypnos.powers.Florist.WinterPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;


public class TriggerGrowthAction extends AbstractGameAction {
    private FlowerCard target;
    private int growth;
    private boolean flash;
    private Color flashColor;

    public TriggerGrowthAction(FlowerCard target, int growth, boolean flash, Color color)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.target = target;
        this.growth = growth;
        this.flash = flash;
        this.flashColor = color;
    }
    public TriggerGrowthAction(FlowerCard target, int growth, boolean flash) {
        this(target, growth, flash, null);
    }
    public TriggerGrowthAction(FlowerCard target, int growth) {
        this(target, growth, false, null);
    }

    public TriggerGrowthAction(TriggerGrowthAction base, boolean flash, Color color)
    {
        this(base.target, base.growth, flash, color);
    }


    @Override
    public void update()
    {
        if (growth != 0)
        {
            Agrypnos.Agrypnos.logger.info("Triggering growth of " + target.cardID + " - Growth: " + growth);

            if (AbstractDungeon.player.hasPower(NoGrowPower.POWER_ID))
            {
                Agrypnos.Agrypnos.logger.info("Growth prevented by No Grow Power.");
                this.isDone = true;
                return;
            }
            else if (AbstractDungeon.player.hasPower(WinterPower.POWER_ID))
            {
                Agrypnos.Agrypnos.logger.info("Growth prevented by Winter Power.");
                this.isDone = true;
                return;
            }

            switch (target.FlowerGrowth)
            {
                case damage:
                    target.baseDamage += growth;
                    if (target.baseDamage < 0)
                        target.baseDamage = 0;

                    target.grown = true;
                    break;
                case block:
                    target.baseBlock += growth;
                    if (target.baseBlock < 0)
                        target.baseBlock = 0;

                    target.grown = true;
                    break;
                case magic:
                    target.baseMagicNumber += growth;
                    if (target.baseMagicNumber < 0)
                        target.baseMagicNumber = 0;

                    target.magicNumber = target.baseMagicNumber;

                    target.grown = true;
                    break;
                case draw:
                    target.baseDraw += growth;
                    if (target.baseDraw < 0)
                        target.baseDraw = 0;

                    target.grown = true;
                    break;
                case heal:
                    target.baseHeal += growth;
                    if (target.baseHeal < 0)
                        target.baseHeal = 0;

                    target.grown = true;
                    break;
                case permanentdamage:
                    target.baseDamage += growth;
                    if (target.baseDamage < 0)
                        target.baseDamage = 0;

                    target.misc = target.baseDamage;
                    target.grown = true;


                    Iterator cardIterator = AbstractDungeon.player.masterDeck.group.iterator();

                    AbstractCard c;
                    while(cardIterator.hasNext()) {
                        c = (AbstractCard)cardIterator.next();
                        if (c.uuid.equals(target.uuid)) {
                            c.baseDamage += growth;
                            if (c.baseDamage < 0)
                                c.baseDamage = 0;

                            c.misc = c.baseDamage;
                            c.isDamageModified = false;
                        }
                    }
                    break;
            }

            target.applyPowers();
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.glowCheck();

            if (flashColor != null) {
                target.flash(flashColor);
            }
            else if (flash)
            {
                target.flash();
            }

            AbstractDungeon.actionManager.addToBottom(new OnFlowerGrowthAction(target));
        }

        this.isDone = true;

    }
}