package Agrypnos.cards.Florist.Flowers;

import Agrypnos.Agrypnos;
import Agrypnos.actions.Florist.ResetFlowerGrowthAction;
import Agrypnos.actions.Florist.TriggerGrowthAction;
import Agrypnos.cards.CardImages;
import Agrypnos.util.CardColorEnum;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.FastDrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Lotus extends FlowerCard
{
    public static final String ID = Agrypnos.createID("Lotus");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColorEnum.FLORIST_GREEN;

    private static final int COST = -2;

    private static final int LIFETIME = 4;
    private static final int GROWTH = -1;

    public Lotus() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        initialValue = LIFETIME;

        this.FlowerGrowth = GrowthType.magic;

        this.magicNumber = this.baseMagicNumber = LIFETIME;
    }

    @Override
    public int baseCost()
    {
        return 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new UseCardAction(this));
    }
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        cantUseMessage = "This card cannot be played.";
        return false;
    }

    @Override
    public void triggerOnExhaust() {
        AbstractDungeon.actionManager.addToBottom(new ResetFlowerGrowthAction(this)); //always resets on exhaust, because otherwise it's useless if brought back
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        if (upgraded)
        {
            AbstractDungeon.actionManager.addToBottom(new FastDrawCardAction(AbstractDungeon.player, 2));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new FastDrawCardAction(AbstractDungeon.player, 1));
        }
        if (magicNumber <= 0)
        {
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand, true));
        }
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
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
        return new Lotus();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}