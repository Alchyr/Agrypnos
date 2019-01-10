package Agrypnos.actions.Florist;

import Agrypnos.actions.General.CardFlashAction;
import Agrypnos.cards.Florist.Flowers.FlowerCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

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

        AbstractDungeon.actionManager.addToBottom(new CardFlashAction(target.uuid));

        this.isDone = true;

    }
}