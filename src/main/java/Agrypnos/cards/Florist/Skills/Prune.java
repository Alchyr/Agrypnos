package Agrypnos.cards.Florist.Skills;

import Agrypnos.Agrypnos;
import Agrypnos.actions.Florist.ResetXFlowerGrowthAction;
import Agrypnos.cards.CardImages;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.util.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.FastDrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Prune extends CustomCard {
    public static final String ID = Agrypnos.createID("Prune");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColorEnum.FLORIST_COLOR;

    private static final int COST = 0;
    private static final int DRAW = 2;

    public Prune() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = DRAW;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof FlowerCard)
                return super.canUse(p, m);
        }
        cantUseMessage = "This card cannot be used without any Flowers.";
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ResetXFlowerGrowthAction(p,p,1, !upgraded));

        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Prune();
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