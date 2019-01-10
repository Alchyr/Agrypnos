package Agrypnos.actions.General;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

public class HiddenRemoveSpecificPowerAction extends AbstractGameAction {
    private String powerToRemove;
    private AbstractPower powerInstance;
    private static final float DURATION = 0.1F;

    public HiddenRemoveSpecificPowerAction(AbstractCreature target, AbstractCreature source, String powerToRemove) {
        this.setValues(target, source, this.amount);
        this.actionType = ActionType.DEBUFF;
        this.duration = DURATION;
        this.powerToRemove = powerToRemove;
    }

    public HiddenRemoveSpecificPowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerInstance) {
        this.setValues(target, source, this.amount);
        this.actionType = ActionType.DEBUFF;
        this.duration = DURATION;
        this.powerInstance = powerInstance;
    }

    public void update() {
        if (this.duration == DURATION) {
            if (this.target.isDeadOrEscaped()) {
                this.isDone = true;
                return;
            }

            AbstractPower removeMe = null;
            if (this.powerToRemove != null) {
                removeMe = this.target.getPower(this.powerToRemove);
            } else if (this.powerInstance != null && this.target.powers.contains(this.powerInstance)) {
                removeMe = this.powerInstance;
            }

            if (removeMe != null) {
                removeMe.onRemove();
                this.target.powers.remove(removeMe);
                AbstractDungeon.onModifyPower();
                Iterator var2 = AbstractDungeon.player.orbs.iterator();

                while(var2.hasNext()) {
                    AbstractOrb o = (AbstractOrb)var2.next();
                    o.updateDescription();
                }
            } else {
                this.duration = 0.0F;
            }
        }

        this.tickDuration();
    }
}