package Agrypnos.cards.Florist.Skills;

import Agrypnos.Agrypnos;
import Agrypnos.actions.Florist.TriggerGrowthAction;
import Agrypnos.actions.General.UpdateHandAction;
import Agrypnos.cards.CardImages;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.util.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Watering extends CustomCard
{
    public static final String ID = Agrypnos.createID("Watering");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColorEnum.FLORIST_COLOR;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    public Watering() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<FlowerCard> FlowerCardsInHand = new ArrayList<>();

        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if (c instanceof FlowerCard)
                FlowerCardsInHand.add((FlowerCard)c);
        }
        if (FlowerCardsInHand.size() > 0)
        {
            for (FlowerCard c : FlowerCardsInHand)
            {
                AbstractDungeon.actionManager.addToBottom(new TriggerGrowthAction(c.getTriggerGrowthAction(), true, FlowerCard.growthFlash));
            }
            AbstractDungeon.actionManager.addToBottom(new UpdateHandAction());
        }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new Watering();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADED_COST);
            this.initializeDescription();
        }
    }
}