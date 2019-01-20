package Agrypnos.actions.Florist;

import Agrypnos.actions.General.CardFlashAction;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.powers.Florist.NoGrowPower;
import Agrypnos.powers.Florist.WinterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.PowerIconShowEffect;

import java.util.Iterator;

public class TriggerDoublingGrowthAction extends AbstractGameAction {
    private FlowerCard target;

    public TriggerDoublingGrowthAction(FlowerCard target)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.target = target;
    }

    @Override
    public void update()
    {
        Agrypnos.Agrypnos.logger.info("Triggering doubling growth of " + target.cardID);

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
                target.baseDamage += target.baseDamage - target.initialValue;
                if (target.baseDamage != target.initialValue)
                    target.grown = true;

                target.applyPowers();
                break;
            case block:
                target.baseBlock += target.baseBlock - target.initialValue;
                if (target.baseBlock != target.initialValue)
                    target.grown = true;

                target.applyPowers();
                break;
            case magic:
                target.baseMagicNumber += target.baseMagicNumber - target.initialValue;
                if (target.baseMagicNumber != target.initialValue)
                    target.grown = true;

                target.applyPowers();
                break;
            case draw:
                target.baseDraw += target.baseDraw - target.initialValue;
                if (target.baseDraw != target.initialValue)
                    target.grown = true;

                target.applyPowers();
                break;
            case heal:
                target.baseHeal += target.baseHeal - target.initialValue;
                if (target.baseHeal != target.initialValue)
                    target.grown = true;

                target.applyPowers();
                break;
            case permanentdamage:
                int growth = target.baseDamage - target.initialValue;
                target.baseDamage += growth;
                target.misc = target.baseDamage;

                if (target.baseDamage != target.initialValue)
                    target.grown = true;


                Iterator cardIterator = AbstractDungeon.player.masterDeck.group.iterator();

                AbstractCard c;
                while(cardIterator.hasNext()) {
                    c = (AbstractCard)cardIterator.next();
                    if (c.uuid.equals(target.uuid)) {
                        c.baseDamage += growth;
                        c.misc = c.baseDamage;
                        c.isDamageModified = false;
                    }
                }

                target.applyPowers();
                break;
        }

        AbstractDungeon.player.hand.refreshHandLayout();
        AbstractDungeon.player.hand.glowCheck();

        AbstractDungeon.actionManager.addToBottom(new CardFlashAction(target.uuid, FlowerCard.growthFlash));

        AbstractDungeon.actionManager.addToBottom(new OnFlowerGrowthAction(target));

        this.isDone = true;

    }
}