package Agrypnos.cards.Florist.Flowers;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.actions.Florist.ResetFlowerGrowthAction;
import Agrypnos.actions.General.HiddenApplyPowerAction;
import Agrypnos.cards.CardImages;
import Agrypnos.powers.Florist.CorpseFlowerPower;
import Agrypnos.util.CardColorEnum;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.StartupCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class CorpseFlower extends FlowerCard implements StartupCard {
    public static final String ID = Agrypnos.createID("CorpseFlower");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLOWER_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColorEnum.FLORIST_COLOR;

    private static final int COST = 2;
    private static final int POISON = 8;
    private static final int UPGRADE_PLUS_POISON = 3;
    private static final int GROWTH = 2;
    private static final int UPGRADE_PLUS_GROWTH = 1;


    private boolean triggered;


    public CorpseFlower() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, GROWTH);

        triggered = false;

        this.magicNumber = this.baseMagicNumber = POISON;
        initialValue = POISON;
        this.FlowerGrowth = GrowthType.magic;
    }

    @Override
    public boolean UPGRADE_GROWTH() {
        return true;
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

    public void applyPowers()
    {
        super.applyPowers();

        if (!AbstractDungeon.player.hasPower(CorpseFlowerPower.POWER_ID) && !triggered)
        {
            triggered = true;
            AbstractDungeon.actionManager.addToBottom(new HiddenApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new CorpseFlowerPower(AbstractDungeon.player)));
        }
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
            this.upgradeGrowth(UPGRADE_PLUS_GROWTH);
            initialValue = baseMagicNumber;
            this.initializeDescription();
        }
    }
}