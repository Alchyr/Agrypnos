package Agrypnos.cards.Florist.Skills;

import Agrypnos.Agrypnos;
import Agrypnos.actions.Florist.TriggerGrowthAction;
import Agrypnos.actions.General.UpdateHandAction;
import Agrypnos.cards.CardImages;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.util.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.OfferingEffect;

import java.util.ArrayList;

public class NaturalNutrition extends CustomCard
{
    public static final String ID = Agrypnos.createID("NaturalNutrition");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColorEnum.FLORIST_GREEN;

    private static final int COST = 0;

    private static final int HP_COST = 3;

    public NaturalNutrition() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = HP_COST;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new OfferingEffect(), 0.5F));
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, magicNumber));

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
                if (upgraded)
                {
                    AbstractDungeon.actionManager.addToBottom(c.getTriggerGrowthAction());
                    AbstractDungeon.actionManager.addToBottom(new TriggerGrowthAction(c.getTriggerGrowthAction(), true, FlowerCard.growthFlash));
                }
                else
                {
                    AbstractDungeon.actionManager.addToBottom(new TriggerGrowthAction(c.getTriggerGrowthAction(), true, FlowerCard.growthFlash));
                }
            }

            AbstractDungeon.actionManager.addToBottom(new UpdateHandAction());
        }
    }

    // Which card to return when making a copy of this card.
    @Override
    public AbstractCard makeCopy() {
        return new NaturalNutrition();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}