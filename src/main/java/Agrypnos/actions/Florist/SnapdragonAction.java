package Agrypnos.actions.Florist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class SnapdragonAction extends AbstractGameAction {
    private boolean freeToPlayOnce = false;
    private AbstractPlayer p;
    private int energyOnUse = -1;
    private int bonusHits = 0;
    private DamageInfo.DamageType damageType;

    public SnapdragonAction(AbstractPlayer p, int damage, DamageInfo.DamageType damageType, boolean freeToPlayOnce, int energyOnUse, int bonusHits) {
        this.amount = damage;
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.bonusHits = bonusHits;
        this.damageType = damageType;
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

        effect += bonusHits;

        if (effect > 0) {
            for(int i = 0; i < effect; ++i) {
                AbstractDungeon.actionManager.addToBottom(
                        new com.megacrit.cardcrawl.actions.common.DamageAction(AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng),
                                new DamageInfo(p, this.amount, this.damageType),
                                AbstractGameAction.AttackEffect.FIRE));
            }

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
