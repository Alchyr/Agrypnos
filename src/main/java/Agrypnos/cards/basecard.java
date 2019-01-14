/*
package Agrypnos.cards;

import Agrypnos.Agrypnos;
import Agrypnos.util.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class basecard extends CustomCard
{
    public static final String ID = Agrypnos.createID("card id");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.something);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.rarity;
    private static final CardTarget TARGET = CardTarget.target;
    private static final CardType TYPE = CardType.type;
    public static final CardColor COLOR = CardColorEnum.FLORIST_COLOR;

    private static final int COST = x;

    public constructor() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new UseCardAction(this));
    }

    @Override
    public AbstractCard makeCopy() {
        return new card();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            do other necessary upgrades
            this.initializeDescription();
        }
    }
}

*/