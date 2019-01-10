package Agrypnos.cards.Florist.Flowers;

import Agrypnos.Agrypnos;
import Agrypnos.actions.Florist.ResetFlowerGrowthAction;
import Agrypnos.actions.Florist.TriggerGrowthAction;
import Agrypnos.actions.General.HiddenApplyPowerAction;
import Agrypnos.cards.CardImages;
import Agrypnos.powers.Florist.CorpseFlowerPower;
import Agrypnos.util.CardColorEnum;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.DeadlyPoison;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class CorpseFlower extends FlowerCard implements StartupCard {
    public static final String ID = Agrypnos.createID("CorpseFlower");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColorEnum.FLORIST_GREEN;

    private static final int COST = 2;
    private static final int POISON = 7;
    private static final int UPGRADE_PLUS_POISON = 2;
    private static final int GROWTH = 4;
    private static final int UPGRADE_PLUS_GROWTH = 2;


    private int growth;


    public CorpseFlower() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = POISON;
        initialValue = POISON;
        this.FlowerGrowth = GrowthType.magic;

        growth = GROWTH;

        this.rawDescription = DESCRIPTION + growth + EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public boolean atBattleStartPreDraw()
    {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new HiddenApplyPowerAction(p, p, new CorpseFlowerPower(p)));
        return false;
    }

    @Override
    public int baseCost()
    {
        return COST;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new PoisonPower(mo, p, this.magicNumber), this.magicNumber, AbstractGameAction.AttackEffect.POISON));
        }
        if (ResetOnPlay)
            AbstractDungeon.actionManager.addToBottom(new ResetFlowerGrowthAction(this));
    }

    @Override
    public TriggerGrowthAction getTriggerGrowthAction() {
        return new TriggerGrowthAction(this, growth);
    }

    @Override
    public AbstractCard makeCopy() {
        return new CorpseFlower();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_POISON);
            initialValue = baseMagicNumber;
            growth += UPGRADE_PLUS_GROWTH;
            this.rawDescription = DESCRIPTION + growth + EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }
    }
}