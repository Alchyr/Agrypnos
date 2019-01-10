package Agrypnos.actions.General;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.Collections;
import java.util.Iterator;

public class HiddenApplyPowerAction extends AbstractGameAction {
    private AbstractPower powerToApply;
    private float startingDuration;

    public HiddenApplyPowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isFast) {
        if (Settings.FAST_MODE) {
            this.startingDuration = 0.1F;
        } else if (isFast) {
            this.startingDuration = Settings.ACTION_DUR_FASTER;
        } else {
            this.startingDuration = Settings.ACTION_DUR_FAST;
        }

        this.setValues(target, source, stackAmount);
        this.duration = this.startingDuration;
        this.powerToApply = powerToApply;
        if (AbstractDungeon.player.hasRelic("Snake Skull") && source != null && source.isPlayer && target != source && powerToApply.ID.equals("Poison")) {
            AbstractDungeon.player.getRelic("Snake Skull").flash();
            ++this.powerToApply.amount;
            ++this.amount;
        }

        if (powerToApply.ID.equals("Corruption")) {
            Iterator var7 = AbstractDungeon.player.hand.group.iterator();

            AbstractCard c;
            while(var7.hasNext()) {
                c = (AbstractCard)var7.next();
                if (c.type == AbstractCard.CardType.SKILL) {
                    c.modifyCostForCombat(-9);
                }
            }

            var7 = AbstractDungeon.player.drawPile.group.iterator();

            while(var7.hasNext()) {
                c = (AbstractCard)var7.next();
                if (c.type == AbstractCard.CardType.SKILL) {
                    c.modifyCostForCombat(-9);
                }
            }

            var7 = AbstractDungeon.player.discardPile.group.iterator();

            while(var7.hasNext()) {
                c = (AbstractCard)var7.next();
                if (c.type == AbstractCard.CardType.SKILL) {
                    c.modifyCostForCombat(-9);
                }
            }

            var7 = AbstractDungeon.player.exhaustPile.group.iterator();

            while(var7.hasNext()) {
                c = (AbstractCard)var7.next();
                if (c.type == AbstractCard.CardType.SKILL) {
                    c.modifyCostForCombat(-9);
                }
            }
        }

        this.actionType = ActionType.POWER;
        this.attackEffect = AttackEffect.NONE;
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.duration = 0.0F;
            this.startingDuration = 0.0F;
            this.isDone = true;
        }

    }

    public HiddenApplyPowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply) {
        this(target, source, powerToApply, -1);
    }

    public HiddenApplyPowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount) {
        this(target, source, powerToApply, stackAmount, false);
    }

    public void update() {
        if (this.shouldCancelAction()) {
            this.isDone = true;
        } else {
            if (this.duration == this.startingDuration) {
                if (this.powerToApply instanceof NoDrawPower && this.target.hasPower(this.powerToApply.ID)) {
                    this.isDone = true;
                    return;
                }

                if (this.source != null) {
                    Iterator var1 = this.source.powers.iterator();

                    while(var1.hasNext()) {
                        AbstractPower pow = (AbstractPower)var1.next();
                        pow.onApplyPower(this.powerToApply, this.target, this.source);
                    }
                }

                if (AbstractDungeon.player.hasRelic("Champion Belt") && this.source != null && this.source.isPlayer && this.target != this.source && this.powerToApply.ID.equals("Vulnerable") && !this.target.hasPower("Artifact")) {
                    AbstractDungeon.player.getRelic("Champion Belt").onTrigger(this.target);
                }

                if (this.target instanceof AbstractMonster && this.target.isDeadOrEscaped()) {
                    this.duration = 0.0F;
                    this.isDone = true;
                    return;
                }

                if (AbstractDungeon.player.hasRelic("Ginger") && this.target.isPlayer && this.powerToApply.ID.equals("Weakened")) {
                    AbstractDungeon.player.getRelic("Ginger").flash();
                    return;
                }

                if (AbstractDungeon.player.hasRelic("Turnip") && this.target.isPlayer && this.powerToApply.ID.equals("Frail")) {
                    AbstractDungeon.player.getRelic("Turnip").flash();
                    return;
                }

                if (this.target.hasPower("Artifact") && this.powerToApply.type == AbstractPower.PowerType.DEBUFF) {
                    CardCrawlGame.sound.play("NULLIFY_SFX");
                    this.target.getPower("Artifact").flashWithoutSound();
                    this.target.getPower("Artifact").onSpecificTrigger();
                    return;
                }

                boolean hasBuffAlready = false;
                Iterator var6 = this.target.powers.iterator();

                label148:
                while(true) {
                    AbstractPower p;
                    do {
                        do {
                            if (!var6.hasNext()) {
                                if (this.powerToApply.type == AbstractPower.PowerType.DEBUFF) {
                                    this.target.useFastShakeAnimation(0.5F);
                                }

                                if (!hasBuffAlready) {
                                    this.target.powers.add(this.powerToApply);
                                    Collections.sort(this.target.powers);
                                    this.powerToApply.onInitialApplication();

                                    AbstractDungeon.onModifyPower();
                                    if (this.target.isPlayer) {
                                        int buffCount = 0;
                                        Iterator var8 = this.target.powers.iterator();

                                        while(var8.hasNext()) {
                                            p = (AbstractPower)var8.next();
                                            if (p.type == AbstractPower.PowerType.BUFF) {
                                                ++buffCount;
                                            }
                                        }

                                        if (buffCount >= 10) {
                                            UnlockTracker.unlockAchievement("POWERFUL");
                                        }
                                    }
                                }
                                break label148;
                            }

                            p = (AbstractPower)var6.next();
                        } while(!p.ID.equals(this.powerToApply.ID));
                    } while(p.ID.equals("Night Terror"));

                    p.stackPower(this.amount);
                    p.flash();

                    p.updateDescription();
                    hasBuffAlready = true;
                    AbstractDungeon.onModifyPower();
                }
            }

            this.tickDuration();
        }
    }
}
