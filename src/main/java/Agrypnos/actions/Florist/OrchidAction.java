package Agrypnos.actions.Florist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class OrchidAction extends AbstractGameAction {
    private boolean freeToPlayOnce = false;
    private AbstractPlayer p;
    private AbstractCreature m;
    private int energyOnUse = -1;

    public OrchidAction(AbstractPlayer p, AbstractCreature m, int bonusLoss, boolean freeToPlayOnce, int energyOnUse) {
        this.amount = bonusLoss;
        this.p = p;
        this.m = m;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        effect += this.amount;

        if (effect > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StrengthPower(m, -effect), -effect, true, AbstractGameAction.AttackEffect.NONE));
            if (!m.hasPower("Artifact"))
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new GainStrengthPower(m, effect), effect, true, AbstractGameAction.AttackEffect.NONE));

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}