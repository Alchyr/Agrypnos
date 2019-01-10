package Agrypnos.cards.Florist.Flowers;

import Agrypnos.Agrypnos;
import Agrypnos.actions.Florist.ResetFlowerGrowthAction;
import Agrypnos.actions.Florist.TriggerGrowthAction;
import Agrypnos.cards.CardImages;
import Agrypnos.util.CardColorEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;

public class Zinnia extends FlowerCard
{
    public static final String ID = Agrypnos.createID("Zinnia");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColorEnum.FLORIST_GREEN;

    private static final int COST = 1;
    private static final int BUFF = 6;
    private static final int UPGRADE_PLUS_BUFF = 3;
    private static final int GROWTH = 1;


    public Zinnia() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        initialValue = BUFF;
        FlowerGrowth = GrowthType.magic;

        this.magicNumber = this.baseMagicNumber = initialValue;

        this.exhaust = true;
    }

    @Override
    public int baseCost()
    {
        return COST;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseDexterityPower(p, this.magicNumber), this.magicNumber));

        if (ResetOnPlay)
            AbstractDungeon.actionManager.addToBottom(new ResetFlowerGrowthAction(this));
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard()
    {
        super.triggerOnEndOfTurnForPlayingCard();
        AbstractDungeon.actionManager.addToBottom(getTriggerGrowthAction());
    }

    @Override
    public TriggerGrowthAction getTriggerGrowthAction()
    {
        return new TriggerGrowthAction(this, GROWTH);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Zinnia();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_BUFF);
            initialValue = baseMagicNumber;
            this.initializeDescription();
        }
    }
}