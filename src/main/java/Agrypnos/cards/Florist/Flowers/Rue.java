package Agrypnos.cards.Florist.Flowers;

import Agrypnos.Agrypnos;
import Agrypnos.actions.Florist.ResetFlowerGrowthAction;
import Agrypnos.actions.Florist.TriggerGrowthAction;
import Agrypnos.actions.General.RemoveRandomDebuffAction;
import Agrypnos.cards.CardImages;
import Agrypnos.util.CardColorEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;

public class Rue extends FlowerCard
{
    public static final String ID = Agrypnos.createID("Rue");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColorEnum.FLORIST_GREEN;

    private static final int COST = 1;
    private static final int UPG_COST = 0;
    private static final int REMOVAL = 1;
    private static final int GROWTH = 1;


    public Rue() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        initialValue = REMOVAL;
        FlowerGrowth = GrowthType.magic;

        this.magicNumber = this.baseMagicNumber = REMOVAL;

        this.exhaust = true;
    }

    @Override
    public int baseCost()
    {
        if (upgraded)
            return UPG_COST;
        return COST;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int debuffCount = 0;

        for (AbstractPower power : p.powers) {
            if (power.type == AbstractPower.PowerType.DEBUFF) {
                debuffCount++;
            }
        }

        int artifactAmount = this.magicNumber - debuffCount;
        debuffCount = Math.min(this.magicNumber, debuffCount);

        for (int i = 0; i < debuffCount; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new RemoveRandomDebuffAction(p));
        }
        if (artifactAmount > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, artifactAmount), artifactAmount));
        }

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
        return new Rue();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPG_COST);
            this.initializeDescription();
        }
    }
}