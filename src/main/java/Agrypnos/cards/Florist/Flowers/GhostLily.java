package Agrypnos.cards.Florist.Flowers;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.actions.Florist.GhostLilyAction;
import Agrypnos.cards.CardImages;
import Agrypnos.cards.Florist.Skills.Seed;
import Agrypnos.util.CardColorEnum;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GhostLily extends FlowerCard
{
    public static final String ID = Agrypnos.createID("GhostLily");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColorEnum.FLORIST_COLOR;

    private static final int COST = 1;
    private static final int UPG_COST = 0;
    private static final int REPLACE = 1;
    private static final int GROWTH = 1;

    private AbstractCard seedCard;


    public GhostLily() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, GROWTH);

        initialValue = REPLACE;
        this.magicNumber = this.baseMagicNumber = REPLACE;

        FlowerGrowth = GrowthType.magic;

        this.isEthereal = true;
        AlwaysRetainField.alwaysRetain.set(this, false);
        this.retain = false;

        seedCard = new Seed().makeCopy();
    }

    @Override
    public boolean UPGRADE_GROWTH() {
        return false;
    }

    @Override
    public int baseCost()
    {
        if (upgraded)
            return UPG_COST;
        return COST;
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        if (this.isEthereal) { //this causes the Ghost Lily to exhaust before other Ethereal cards. Required for it to work properly.
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new UseCardAction(this));
    }


    @Override
    public void triggerOnExhaust() {
        AbstractDungeon.actionManager.addToTop(new GhostLilyAction(seedCard, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new GhostLily();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.seedCard.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}