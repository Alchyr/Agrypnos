package Agrypnos.powers.Florist;

import Agrypnos.Agrypnos;
import Agrypnos.powers.PowerImages;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class CompostPower extends TwoAmountPower {
    public AbstractCreature source;

    public static final String POWER_ID = Agrypnos.createID("CompostPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = Agrypnos.makePath(PowerImages.COMPOST);


    public static final int EXHAUST_FOR_TRIGGER = 4;

    public CompostPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount2 = amount;
        this.amount = EXHAUST_FOR_TRIGGER;
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
    }

    @Override
    public void stackPower(int stackAmount) {
        if (this.amount2 == -1) {
        } else {
            this.fontScale = 8.0F;
            this.amount2 += stackAmount;
        }
    }

    @Override
    public void onExhaust(AbstractCard card) {
        super.onExhaust(card);
        this.amount -= 1;
        if (this.amount <= 0)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
                    new FertilizerPower(owner, owner, this.amount2), this.amount2));
            this.amount = EXHAUST_FOR_TRIGGER;
        }
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[3];
        }
        else if (this.amount > 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2] + this.amount2 + DESCRIPTIONS[3];
        }
    }
}
