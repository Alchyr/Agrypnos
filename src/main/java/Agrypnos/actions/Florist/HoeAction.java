package Agrypnos.actions.Florist;

import Agrypnos.powers.Florist.FertilizerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class HoeAction extends AbstractGameAction {
    private int fertilizerAmount;
    private DamageInfo info;
    private static final float DURATION = 0.1F;

    public HoeAction(AbstractCreature target, DamageInfo info, int fertilizerAmount, AttackEffect effect) {
        this.info = info;
        this.setValues(target, info);
        this.fertilizerAmount = fertilizerAmount;
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
        this.attackEffect = effect;
    }

    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            this.target.damageFlash = true;
            this.target.damageFlashFrames = 4;
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect, false));

            this.target.damage(this.info);
            if ((((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead) {
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FertilizerPower(AbstractDungeon.player, AbstractDungeon.player, this.fertilizerAmount), this.fertilizerAmount));
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();
    }
}