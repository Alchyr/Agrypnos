package Agrypnos.cards.Florist.Flowers;

import Agrypnos.Agrypnos;
import Agrypnos.actions.Florist.ResetFlowerGrowthAction;
import Agrypnos.actions.Florist.TriggerGrowthAction;
import Agrypnos.actions.General.HiddenApplyPowerAction;
import Agrypnos.cards.CardImages;
import Agrypnos.powers.Florist.QueenOfTheNightPower;
import Agrypnos.util.CardColorEnum;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class QueenOfTheNight extends FlowerCard
{
    public static final String ID = Agrypnos.createID("QueenOfTheNight");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColorEnum.FLORIST_GREEN;

    private static final int COST = -2;
    private static final int PROTECT = 4;
    private static final int UPGRADE_PLUS_PROTECT = 2;
    private static final int GROWTH = 2;
    private static final int UPGRADE_PLUS_GROWTH = 1;


    private boolean triggered = false;


    public QueenOfTheNight() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        initialValue = PROTECT;

        this.FlowerGrowth = GrowthType.block;

        this.block = this.baseBlock = PROTECT;
        this.magicNumber = this.baseMagicNumber = GROWTH;
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
    public void onMoveToDiscard() {
        if (ResetOnPlay)
            AbstractDungeon.actionManager.addToBottom(new ResetFlowerGrowthAction(this));
    }

    @Override
    public void triggerWhenDrawn() {
        if (!AbstractDungeon.player.hasPower(QueenOfTheNightPower.POWER_ID) && !triggered)
        {
            triggered = true;
            AbstractDungeon.actionManager.addToTop(new HiddenApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new QueenOfTheNightPower(AbstractDungeon.player), 1, true));
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (!AbstractDungeon.player.hasPower(QueenOfTheNightPower.POWER_ID) && !triggered)
        {
            triggered = true;
            AbstractDungeon.actionManager.addToTop(new HiddenApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new QueenOfTheNightPower(AbstractDungeon.player), 1, true));
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
        return new TriggerGrowthAction(this, magicNumber);
    }

    @Override
    public AbstractCard makeCopy() {
        return new QueenOfTheNight();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_PLUS_PROTECT);
            this.initialValue = this.baseBlock;
            this.upgradeMagicNumber(UPGRADE_PLUS_GROWTH);
            this.initializeDescription();
        }
    }
}