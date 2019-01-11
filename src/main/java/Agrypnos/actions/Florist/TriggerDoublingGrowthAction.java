package Agrypnos.actions.Florist;

import Agrypnos.actions.General.CardFlashAction;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.powers.Florist.NoGrowPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.PowerIconShowEffect;

public class TriggerDoublingGrowthAction extends AbstractGameAction {
    FlowerCard target;

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
            Agrypnos.Agrypnos.logger.info("Growth prevented by NoGrowPower.");
            this.isDone = true;
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new PowerIconShowEffect(AbstractDungeon.player.getPower(NoGrowPower.POWER_ID)), 0.1f));
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
                AbstractDungeon.actionManager.addToBottom(new TriggerGrowthAction(target, target.magicNumber * 2));
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new CardFlashAction(target.uuid, FlowerCard.growthFlash));

        this.isDone = true;

    }
}