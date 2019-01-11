package Agrypnos.actions.Florist;

import Agrypnos.abstracts.FlowerCard;
import Agrypnos.powers.Florist.PollenPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class OnFlowerGrowthAction extends AbstractGameAction {
    FlowerCard target;

    public OnFlowerGrowthAction(FlowerCard target) {
        this.actionType = ActionType.SPECIAL;
        this.target = target;
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hasPower(PollenPower.POWER_ID))
        {
            PollenPower.onFlowerGrowth(target);
        }

        isDone = true;
    }
}