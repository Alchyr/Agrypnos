package Agrypnos.abstracts;

import Agrypnos.actions.Florist.TriggerGrowthAction;
import Agrypnos.powers.Florist.MorningSunPower;
import Agrypnos.powers.Florist.SummerPower;
import Agrypnos.util.CustomTags;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class FlowerCard extends CustomCard {
    static public boolean ResetOnPlay = true;

    public int initialValue;
    public boolean grown;

    public GrowthType FlowerGrowth;

    public static Color growthFlash = Color.GREEN;
    public static Color resetFlash = Color.RED;

    private int MorningSunTurnCostReduction = 0; //tracks whether Morning Sun has already been activated this turn
    private int MorningSunCostReduction = 0; //tracks whether Morning Sun has already been activated this turn

    public int baseGrowth;
    public int growth;

    public boolean upgradePreview = false;

    public abstract boolean UPGRADE_GROWTH();

    public enum GrowthType
    {
        damage,
        block,
        magic,
        heal,
        draw,
        permanentdamage
    }

    public FlowerCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET, int GROWTH) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.growth = this.baseGrowth = GROWTH;
        AlwaysRetainField.alwaysRetain.set(this, true);
        initialValue = 0;
        this.tags.add(CustomTags.FLOWER);
    }

    public abstract int baseCost();

    public int GrowthCount()
    {
        if (growth == 0)
            return 0;

        switch (FlowerGrowth)
        {
            case damage:
            case permanentdamage:
                return Math.abs(baseDamage - initialValue) / growth;
            case block:
                return Math.abs(baseBlock - initialValue) / growth;
            case magic:
                return Math.abs(baseMagicNumber - initialValue) / growth;
            case draw:
                return Math.abs(baseDraw - initialValue) / growth;
            case heal:
                return Math.abs(baseHeal - initialValue) / growth;
            default:
                return 0;
        }
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard AbstractCopy = super.makeStatEquivalentCopy();

        if (AbstractCopy instanceof FlowerCard)
        {
            ((FlowerCard) AbstractCopy).initialValue = this.initialValue;
            ((FlowerCard) AbstractCopy).grown = this.grown;
            ((FlowerCard) AbstractCopy).growth = this.growth;
            ((FlowerCard) AbstractCopy).FlowerGrowth = this.FlowerGrowth;
            ((FlowerCard) AbstractCopy).magicNumber = this.magicNumber;
            ((FlowerCard) AbstractCopy).MorningSunCostReduction = this.MorningSunCostReduction;
            ((FlowerCard) AbstractCopy).MorningSunTurnCostReduction = this.MorningSunTurnCostReduction;
        }

        return AbstractCopy;
    }

    public void upgradeGrowth(int GrowthIncrease)
    {
        this.baseGrowth += GrowthIncrease;
        this.growth += GrowthIncrease;
    }

    public TriggerGrowthAction getTriggerGrowthAction()
    {
        return new TriggerGrowthAction(this, growth);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();

        this.growth = baseGrowth;

        if (AbstractDungeon.player.hasPower(SummerPower.POWER_ID))
        {
            this.growth += AbstractDungeon.player.getPower(SummerPower.POWER_ID).amount;
        }

        if (MorningSunTurnCostReduction != 0)
        {
            modifyCost(MorningSunTurnCostReduction, MorningSunCostReduction);
            MorningSunTurnCostReduction = 0;
            MorningSunCostReduction = 0;
        }

        if (!grown && cost > 0 && AbstractDungeon.player.hasPower(MorningSunPower.POWER_ID))
        {
            MorningSunTurnCostReduction = AbstractDungeon.player.getPower(MorningSunPower.POWER_ID).amount;
            MorningSunTurnCostReduction = Math.min(MorningSunTurnCostReduction, this.costForTurn); //cap by the cost

            MorningSunCostReduction = modifyCost(-MorningSunTurnCostReduction);
        }
    }

    private int modifyCost(int amt)
    {
        if (this.costForTurn >= 0) {
            int diff = this.costForTurn;
            this.costForTurn += amt;
            if (this.costForTurn < 0) {
                this.costForTurn = 0;
            }
            diff = diff - costForTurn;

            if (this.costForTurn != this.cost) {
                this.isCostModified = true;
            }

            this.cost -= diff;
            if (this.cost < 0)
            {
                diff += this.cost;
                this.cost -= this.cost;
            }
            return diff;
        }
        return 0;
    }
    private void modifyCost(int turnCostChange, int costChange)
    {
        if (this.costForTurn >= 0) {
            this.costForTurn += turnCostChange;
            if (this.costForTurn < 0) {
                this.costForTurn = 0;
            }
            this.cost += costChange;
            if (this.cost < 0) {
                this.cost = 0;
            }

            if (this.cost == this.costForTurn)
            {
                this.isCostModified = false;
            }
        }
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard()
    {
        super.triggerOnEndOfTurnForPlayingCard();
    }
}
