package Agrypnos.cards.Florist;

import Agrypnos.Agrypnos;
import Agrypnos.actions.Florist.ResetXFlowerGrowthAction;
import Agrypnos.cards.CardImages;
import Agrypnos.cards.Florist.Flowers.FlowerCard;
import Agrypnos.util.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class MortarAndPestle extends CustomCard
{
    public static final String ID = Agrypnos.createID("MortarAndPestle");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = CardColorEnum.FLORIST_GREEN;

    private static final int COST = 0;
    private static final int HEAL = 3;
    private static final int UPGRADE_HEAL = 2;

    private static final int EXHAUST_COUNT = 1;

    private int exhaustCount;

    public MortarAndPestle() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = baseMagicNumber = HEAL;

        exhaustCount = EXHAUST_COUNT;

        tags.add(CardTags.HEALING);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean hasFlowerCard = false;

        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof FlowerCard)
            {
                hasFlowerCard = true;
                break;
            }
        }
        if (hasFlowerCard) {
            return true;
        }
        cantUseMessage = "This card cannot be used without any Flowers.";
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ResetXFlowerGrowthAction(p,p,exhaustCount,false));
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new MortarAndPestle();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_HEAL);
            this.initializeDescription();
        }
    }
}
