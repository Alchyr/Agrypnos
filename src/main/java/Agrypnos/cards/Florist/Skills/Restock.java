package Agrypnos.cards.Florist.Skills;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.actions.General.MoveRandomCardAction;
import Agrypnos.cards.CardImages;
import Agrypnos.util.CardColorEnum;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.defect.SeekAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Restock extends CustomCard {
    public static final String ID = Agrypnos.createID("Restock");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColorEnum.FLORIST_COLOR;

    private static final int COST = 1;
    private static final int DRAW = 3;
    private static final int UPG_DRAW = 1;

    public Restock() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = DRAW;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof FlowerCard)
            {
                cantUseMessage = "This card cannot be used with a Flower in your hand.";
                return false;
            }
        }

        return true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MoveRandomCardAction(AbstractDungeon.player.hand, AbstractDungeon.player.drawPile, (c) -> c instanceof FlowerCard, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Restock();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPG_DRAW);
            this.initializeDescription();
        }
    }
}