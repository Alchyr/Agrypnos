package Agrypnos.cards.Florist.Flowers;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.actions.General.AddActionToBottomAction;
import Agrypnos.actions.General.ExhaustToDiscardAction;
import Agrypnos.actions.Florist.ResetFlowerGrowthAction;
import Agrypnos.actions.Florist.TriggerGrowthAction;
import Agrypnos.cards.CardImages;
import Agrypnos.util.CardColorEnum;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Hologram;
import com.megacrit.cardcrawl.cards.green.Bane;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Carnation extends FlowerCard
{
    public static final String ID = Agrypnos.createID("Carnation");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColorEnum.FLORIST_COLOR;

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int GROWTH = 1;
    private static final int UPGRADE_PLUS_GROWTH = 1;


    private static final int RETURN_CAP = 2; //to prevent softlocks with certain interactions, ex hubris Black Hole
    private int returnLimit = RETURN_CAP;


    public Carnation() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, GROWTH);

        initialValue = BLOCK;
        FlowerGrowth = GrowthType.block;

        this.baseBlock = BLOCK;
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
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.GainBlockAction(p, p, this.block));
        this.returnLimit = RETURN_CAP;

        if (ResetOnPlay)
            AbstractDungeon.actionManager.addToBottom(new ResetFlowerGrowthAction(this));
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        this.returnLimit = RETURN_CAP;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
    }

    @Override
    public void triggerOnExhaust() {
        returnLimit--;
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));

        if (ResetOnPlay)
            AbstractDungeon.actionManager.addToBottom(new ResetFlowerGrowthAction(this));

        if (returnLimit > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new WaitAction(1.0f)); //have to wait for exhaust animation to end
            AbstractDungeon.actionManager.addToBottom(new ExhaustToDiscardAction(this));
        }
        else
        {
            Agrypnos.logger.info("CARNATION: Return from Exhaust canceled to prevent infinite cycle");
            this.returnLimit = RETURN_CAP;
        }

    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard()
    {
        super.triggerOnEndOfTurnForPlayingCard();
        AbstractDungeon.actionManager.addToBottom(new TriggerGrowthAction(getTriggerGrowthAction(), true, growthFlash));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Carnation();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeGrowth(UPGRADE_PLUS_GROWTH);
            this.upgradeBlock(UPGRADE_PLUS_BLOCK);
            initialValue = baseBlock;
        }
    }

}