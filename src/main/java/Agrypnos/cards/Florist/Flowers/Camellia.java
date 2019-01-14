package Agrypnos.cards.Florist.Flowers;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.actions.Florist.ResetFlowerGrowthAction;
import Agrypnos.actions.Florist.TriggerGrowthAction;
import Agrypnos.cards.CardImages;
import Agrypnos.util.CardColorEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class Camellia extends FlowerCard
{
    public static final String ID = Agrypnos.createID("Camellia");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_ATTACK);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColorEnum.FLORIST_COLOR;

    private static final int COST = 1;
    private static final int DAMAGE = 3;
    private static final int UPGRADE_PLUS_DAMAGE = 1;
    private static final int GROWTH = 1;


    public Camellia() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, GROWTH);

        this.baseDamage = DAMAGE;
        this.initialValue = DAMAGE;
        this.FlowerGrowth = GrowthType.damage;
    }

    @Override
    public boolean UPGRADE_GROWTH() {
        return false;
    }

    @Override
    public int baseCost()
    {
        return COST;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = this.damage; i > 0; i--) {
            AbstractDungeon.actionManager.addToBottom(
                    new com.megacrit.cardcrawl.actions.common.DamageAction(m,
                    new DamageInfo(p, i, this.damageTypeForTurn),
                    Math.random() > .6 ? AbstractGameAction.AttackEffect.SLASH_DIAGONAL : (Math.random() > .5 ? AbstractGameAction.AttackEffect.SLASH_VERTICAL : AbstractGameAction.AttackEffect.SLASH_HORIZONTAL)
                )
            );
        }

        if (ResetOnPlay)
            AbstractDungeon.actionManager.addToBottom(new ResetFlowerGrowthAction(this));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Camellia();
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard()
    {
        super.triggerOnEndOfTurnForPlayingCard();
        AbstractDungeon.actionManager.addToBottom(new TriggerGrowthAction(getTriggerGrowthAction(), true, growthFlash));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DAMAGE);
            this.initialValue = baseDamage;
            this.initializeDescription();
        }
    }
}