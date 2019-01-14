package Agrypnos.cards.Florist.Skills;

import Agrypnos.Agrypnos;
import Agrypnos.cards.CardImages;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.util.CardColorEnum;

import basemod.helpers.CardTags;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;

import java.util.ArrayList;
import java.util.Iterator;

public class Seed extends CustomCard
{
    public static final String ID = Agrypnos.createID("Seed");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColorEnum.FLORIST_COLOR;

    private static final int COST = -2;
    private static final int BLOCK = 4;

    public Seed() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.baseBlock = BLOCK;

        AlwaysRetainField.alwaysRetain.set(this, true);

        this.isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new UseCardAction(this));
    }


    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        cantUseMessage = "This card cannot be played.";
        return false;
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard()
    {
        dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    }

    @Override
    public void triggerOnExhaust() {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));

        Agrypnos.logger.info("Getting random flower.");

        ArrayList<String> FlowerCardKeys = new ArrayList<>();
        Iterator<AbstractCard> i = CardLibrary.getAllCards().iterator();

        while (i.hasNext())
        {
            AbstractCard c = i.next();
            if (c instanceof FlowerCard && !(c.tags.contains(CardTags.HEALING)))
            {
                switch (c.rarity) //much more likely to generate more common cards
                {
                    case BASIC:
                    case COMMON:
                        FlowerCardKeys.add(c.cardID);
                        FlowerCardKeys.add(c.cardID);
                        FlowerCardKeys.add(c.cardID);
                        FlowerCardKeys.add(c.cardID);
                        break;
                    case UNCOMMON:
                        FlowerCardKeys.add(c.cardID);
                        FlowerCardKeys.add(c.cardID);
                        FlowerCardKeys.add(c.cardID);
                        break;
                    case RARE:
                        FlowerCardKeys.add(c.cardID);
                    default:
                        break;
                }
            }
        }

        AbstractCard randomFlower = CardLibrary.getCard(FlowerCardKeys.get(AbstractDungeon.cardRandomRng.random(FlowerCardKeys.size() - 1))).makeCopy();

        if (upgraded)
            randomFlower.upgrade();

        Agrypnos.logger.info("Result: " + randomFlower.cardID);

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(randomFlower, 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Seed();
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
