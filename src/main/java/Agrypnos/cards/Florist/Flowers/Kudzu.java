package Agrypnos.cards.Florist.Flowers;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.actions.Florist.ResetFlowerGrowthAction;
import Agrypnos.actions.Florist.TriggerGrowthAction;
import Agrypnos.actions.General.ExhaustConditionalCardsAction;
import Agrypnos.cards.CardImages;
import Agrypnos.util.CardColorEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

import java.util.ArrayList;

public class Kudzu extends FlowerCard
{
    public static final String ID = Agrypnos.createID("Kudzu");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_ATTACK);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColorEnum.FLORIST_COLOR;

    private static final int COST = 3;
    private static final int DAMAGE = 20;
    private static final int GROWTH = 7;
    private static final int UPGRADE_PLUS_GROWTH = 2;
    private static final int EXHAUST = 2;
    private static final int UPGRADE_PLUS_EXHAUST = 1;


    public Kudzu() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, GROWTH);

        this.FlowerGrowth = GrowthType.damage;
        initialValue = DAMAGE;
        this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = EXHAUST;
    }

    @Override
    public boolean UPGRADE_GROWTH() {
        return true;
    }

    @Override
    public int baseCost()
    {
        return COST;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int daggers = this.damage / 10 + (this.damage % 10 == 0 ? 0 : 1);

        /*int[] damages = new int[daggers];
        for (int i = 0; i < daggers; i++)
            damages[i] = 10;

        if (this.damage % 10 != 0)
            damages[daggers - 1] = this.damage % 10;*/

        for (int i = 0; i < daggers; i++)
        {
            if (m != null) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ThrowDaggerEffect(m.hb.cX, m.hb.cY)));
            }
            /*AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m,
                            new DamageInfo(p, damages[i], this.damageTypeForTurn),
                            AbstractGameAction.AttackEffect.NONE
                    )
            );*/
        }
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.NONE
                )
        );

        if (ResetOnPlay)
            AbstractDungeon.actionManager.addToBottom(new ResetFlowerGrowthAction(this));
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard()
    {
        super.triggerOnEndOfTurnForPlayingCard();

        int exhaustAmount = this.magicNumber;
        int nonFlowerCards = 0;

        //first exhaust flowers

        ArrayList<FlowerCard> FlowerCardsInHand = new ArrayList<>();

        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if (!c.equals(this))
            {
                if (c instanceof FlowerCard)
                {
                    FlowerCardsInHand.add((FlowerCard)c);
                }
                else
                {
                    nonFlowerCards++;
                }
            }
        }

        while (exhaustAmount > 0 && FlowerCardsInHand.size() > 0)
        {
            exhaustAmount--;
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(FlowerCardsInHand.remove(AbstractDungeon.cardRandomRng.random(FlowerCardsInHand.size() - 1)), AbstractDungeon.player.hand));

            AbstractDungeon.actionManager.addToBottom(new TriggerGrowthAction(getTriggerGrowthAction(), true, growthFlash));
        }

        //any remaining exhaust is used on random cards other than this card
        if (exhaustAmount > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new ExhaustConditionalCardsAction(AbstractDungeon.player.hand, (c)->(!c.equals(this)) , exhaustAmount));
            //the number of exhausted cards for second one is the minimum between non flower cards and exhaustAmount
            for (int i = 0; i < Math.min(exhaustAmount, nonFlowerCards); i++)
            {
                AbstractDungeon.actionManager.addToBottom(new TriggerGrowthAction(getTriggerGrowthAction(), true, growthFlash));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Kudzu();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeGrowth(UPGRADE_PLUS_GROWTH);
            this.upgradeMagicNumber(UPGRADE_PLUS_EXHAUST);
            initialValue = baseDamage;
            this.initializeDescription();
        }
    }
}