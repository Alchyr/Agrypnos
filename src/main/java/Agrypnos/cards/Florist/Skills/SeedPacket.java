package Agrypnos.cards.Florist.Skills;

import Agrypnos.Agrypnos;
import Agrypnos.cards.CardImages;
import Agrypnos.util.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SeedPacket extends CustomCard
{
    public static final String ID = Agrypnos.createID("SeedPacket");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColorEnum.FLORIST_COLOR;

    private static final int COST = 2;
    private static final int FIND = 3;
    private static final int BLOCK = 3;
    private static final int UPGRADE_BLOCK = 1;


    private AbstractCard seedCard;


    public SeedPacket() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = FIND;
        this.baseBlock = this.block = BLOCK;

        seedCard = new Seed().makeCopy();

        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new com.megacrit.cardcrawl.actions.common.GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(seedCard, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new SeedPacket();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK);
            this.seedCard.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}