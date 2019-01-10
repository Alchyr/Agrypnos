package Agrypnos.cards.Florist.Flowers;

import Agrypnos.Agrypnos;
import Agrypnos.actions.Florist.ResetFlowerGrowthAction;
import Agrypnos.actions.Florist.TriggerGrowthAction;
import Agrypnos.cards.CardImages;
import Agrypnos.cards.Florist.Thorn;
import Agrypnos.util.CardColorEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Rose extends FlowerCard
{
    public static final String ID = Agrypnos.createID("Rose");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_ATTACK);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColorEnum.FLORIST_GREEN;

    private static final int COST = 2;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DAMAGE = 4;
    private static final int GROWTH = 3;
    private static final int UPGRADE_PLUS_GROWTH = 2;

    private static final int DAMAGE_PER_THORN = 10;


    public static AbstractCard thornCard;


    public Rose() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        initialValue = DAMAGE;
        this.FlowerGrowth = GrowthType.damage;

        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = GROWTH;

        thornCard = new Thorn().makeCopy();
    }

    @Override
    public int baseCost()
    {
        return COST;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_DIAGONAL
                )
        );

        AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(thornCard, (int)(this.damage / DAMAGE_PER_THORN)));

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
        return new TriggerGrowthAction(this, magicNumber);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Rose();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DAMAGE);
            initialValue = baseDamage;
            this.upgradeMagicNumber(UPGRADE_PLUS_GROWTH);
            this.initializeDescription();
        }
    }
}