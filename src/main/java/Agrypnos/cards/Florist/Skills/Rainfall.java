package Agrypnos.cards.Florist.Skills;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.actions.Florist.GrowNonHandFlowersActions;
import Agrypnos.actions.Florist.TriggerGrowthAction;
import Agrypnos.actions.General.UpdateHandAction;
import Agrypnos.cards.CardImages;
import Agrypnos.util.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Rainfall extends CustomCard
{
    public static final String ID = Agrypnos.createID("Rainfall");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColorEnum.FLORIST_COLOR;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;

    public Rainfall() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GrowNonHandFlowersActions());

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
        return new Rainfall();
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