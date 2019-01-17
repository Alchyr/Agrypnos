package Agrypnos.cards.Florist.Skills;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.actions.General.ExhaustConditionalCardsAction;
import Agrypnos.cards.CardImages;
import Agrypnos.util.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NaturePurity extends CustomCard
{
    public static final String ID = Agrypnos.createID("NaturePurity");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColorEnum.FLORIST_COLOR;

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final int ENERGY_PER_CARD = 1;

    public NaturePurity() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int nonFlowerCount = 0;
        for (AbstractCard c : p.hand.group)
        {
            if (!(c instanceof FlowerCard) && !c.equals(this))
                nonFlowerCount++;
        }
        AbstractDungeon.actionManager.addToBottom(new ExhaustConditionalCardsAction(p.hand, (c)->!(c instanceof FlowerCard), -1));
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(nonFlowerCount * ENERGY_PER_CARD));
    }

    @Override
    public AbstractCard makeCopy() {
        return new NaturePurity();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADE_COST);
            this.initializeDescription();
        }
    }
}