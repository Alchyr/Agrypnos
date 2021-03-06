package Agrypnos.cards.Florist.Flowers;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.cards.CardImages;
import Agrypnos.util.CardColorEnum;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.colorless.RitualDagger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Myosotis extends FlowerCard
{
    public static final String ID = Agrypnos.createID("Myosotis");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLOWER_ATTACK);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColorEnum.FLORIST_COLOR;

    private static final int COST = 2;
    private static final int DAMAGE = 1;
    private static final int GROWTH = 2;
    private static final int UPGRADE_PLUS_GROWTH = 1;


    public Myosotis() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, GROWTH);

        initialValue = DAMAGE;
        FlowerGrowth = GrowthType.permanentdamage;

        this.baseDamage = DAMAGE;
        this.misc = DAMAGE;

        this.isEthereal = true;
        //AlwaysRetainField.alwaysRetain.set(this, false);
        //this.retain = false;
    }

    @Override
    public boolean UPGRADE_GROWTH() {
        return true;
    }

    @Override
    public int baseCost()
    {
        return COST;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(getTriggerGrowthAction());
        //add growth action first so that it grows even when used to kill last enemy

        //damage value should not be affected
        AbstractDungeon.actionManager.addToBottom(
                new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void onLoad(Integer baseGrowth) {
        if (this.baseDamage != this.misc)
        {
            this.damage = this.baseDamage = this.misc;
        }
        super.onLoad(baseGrowth);
    }

    public void applyPowers() {
        if (this.baseDamage != this.misc)
        {
            this.baseDamage = this.misc;
        }
        super.applyPowers();
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Myosotis();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeGrowth(UPGRADE_PLUS_GROWTH);

            this.initializeDescription();
        }
    }
}