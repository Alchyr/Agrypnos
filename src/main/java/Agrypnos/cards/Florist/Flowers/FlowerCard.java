package Agrypnos.cards.Florist.Flowers;

import Agrypnos.actions.Florist.TriggerGrowthAction;
import Agrypnos.cards.Florist.CatastrophicGrowth;
import Agrypnos.cards.Florist.NaturalNutrition;
import Agrypnos.cards.Florist.Watering;
import Agrypnos.powers.Florist.MorningSunPower;
import Agrypnos.util.CustomTags;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public abstract class FlowerCard extends CustomCard {
    static public boolean ResetOnPlay = true;

    public int initialValue;
    public boolean grown;

    public GrowthType FlowerGrowth;

    private int MorningSunTurnCostReduction = 0; //tracks whether Morning Sun has already been activated this turn
    private int MorningSunCostReduction = 0; //tracks whether Morning Sun has already been activated this turn

    public enum GrowthType
    {
        damage,
        block,
        magic,
        heal,
        draw,
        permanentdamage
    }

    public FlowerCard(String ID, String NAME, String IMG, int COST, String DESCRIPTION, CardType TYPE, CardColor COLOR, CardRarity RARITY, CardTarget TARGET) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        AlwaysRetainField.alwaysRetain.set(this, true);
        initialValue = 0;
        this.tags.add(CustomTags.FLOWER);
    }

    public abstract int baseCost();

    @Override
    public void applyPowers() {
        super.applyPowers();

        if (MorningSunTurnCostReduction != 0)
        {
            modifyCost(MorningSunTurnCostReduction, MorningSunCostReduction);
            MorningSunTurnCostReduction = 0;
            MorningSunCostReduction = 0;
        }

        if (!grown && MorningSunTurnCostReduction == 0 && cost > 0 && AbstractDungeon.player.hasPower(MorningSunPower.POWER_ID))
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

    public abstract TriggerGrowthAction getTriggerGrowthAction();

    @Override
    public void triggerOnEndOfTurnForPlayingCard()
    {
        super.triggerOnEndOfTurnForPlayingCard();
    }
}
