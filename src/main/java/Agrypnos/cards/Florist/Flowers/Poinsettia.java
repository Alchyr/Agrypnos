package Agrypnos.cards.Florist.Flowers;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.actions.Florist.PoinsettiaAction;
import Agrypnos.actions.Florist.TriggerGrowthAction;
import Agrypnos.cards.CardImages;
import Agrypnos.util.CardColorEnum;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Poinsettia extends FlowerCard
{
    public static final String ID = Agrypnos.createID("Poinsettia");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColorEnum.FLORIST_COLOR;

    private static final int COST = 1;
    private static final int EXHAUST = 3;
    private static final int BLOCK = 4;
    private static final int UPGRADE_BLOCK = 2;
    private static final int GROWTH = 1;


    public Poinsettia() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, GROWTH);

        initialValue = BLOCK;
        this.baseBlock = BLOCK;
        FlowerGrowth = GrowthType.block;

        this.magicNumber = this.baseMagicNumber = EXHAUST;
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
        AbstractDungeon.actionManager.addToBottom(new PoinsettiaAction(this.magicNumber, this.block));
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard()
    {
        super.triggerOnEndOfTurnForPlayingCard();
        AbstractDungeon.actionManager.addToBottom(new TriggerGrowthAction(getTriggerGrowthAction(), true, growthFlash));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Poinsettia();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK);
            this.initialValue = this.baseBlock;
            this.initializeDescription();
        }
    }
}