package Agrypnos.powers.Florist;

import Agrypnos.Agrypnos;
import Agrypnos.cards.Florist.Thorn;
import Agrypnos.powers.PowerImages;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class RoseGardenPower extends AbstractPower {

    public static final String POWER_ID = Agrypnos.createID("RoseThorns");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = Agrypnos.makePath(PowerImages.ROSETHORNS);

    public static final int ROSE_THORNS_DAMAGE = 4;

    public static AbstractCard thornCard;

    private DamageInfo thornsInfo;

    public RoseGardenPower(AbstractCreature owner, int numStacks) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = numStacks;
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.thornsInfo = new DamageInfo(this.owner, this.ROSE_THORNS_DAMAGE, DamageInfo.DamageType.THORNS);

        thornCard = new Thorn().makeCopy();
    }

    public void stackPower(int stackAmount) {
        if (this.amount == -1) {
        } else {
            this.fontScale = 8.0F;
            this.amount += stackAmount;
            this.updateDescription();
        }
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            this.flash();
            this.amount--;
            this.updateDescription();
            AbstractDungeon.actionManager.addToTop(new DamageAction(info.owner, this.thornsInfo, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
            AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(thornCard, 1));
            if (this.amount <= 0)
            {
                AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(owner, owner, this));
            }

        }

        return damageAmount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
