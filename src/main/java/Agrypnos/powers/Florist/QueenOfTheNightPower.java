package Agrypnos.powers.Florist;

import Agrypnos.Agrypnos;
import Agrypnos.actions.Florist.DiscardQueenOfNightAction;
import Agrypnos.actions.General.HiddenRemoveSpecificPowerAction;
import Agrypnos.cards.Florist.Flowers.QueenOfTheNight;
import Agrypnos.powers.PowerImages;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class QueenOfTheNightPower extends AbstractPower implements InvisiblePower {
    public static final String POWER_ID = Agrypnos.createID("QueenOfTheNightPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = Agrypnos.makePath(PowerImages.NONE);

    public QueenOfTheNightPower(final AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 1;
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (damageAmount > 0)
        {
            int reduction = 0;
            boolean hasCard = false;

            for (AbstractCard c : AbstractDungeon.player.hand.group)
            {
                if (c instanceof QueenOfTheNight)
                {
                    reduction += c.magicNumber;
                    hasCard = true;
                }
            }
            if (!hasCard)
            {
                AbstractDungeon.actionManager.addToBottom(new HiddenRemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            }
            else
            {
                damageAmount -= reduction;
                if (damageAmount < 0)
                    damageAmount = 0;

                AbstractDungeon.actionManager.addToTop(new DiscardQueenOfNightAction());
                AbstractDungeon.actionManager.addToTop(new HiddenRemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            }
        }
        return damageAmount;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
