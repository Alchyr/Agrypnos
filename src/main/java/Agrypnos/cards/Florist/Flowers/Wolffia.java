package Agrypnos.cards.Florist.Flowers;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.actions.Florist.ResetFlowerGrowthAction;
import Agrypnos.actions.Florist.TriggerGrowthAction;
import Agrypnos.cards.CardImages;
import Agrypnos.powers.Crossover.RTSPowers;
import Agrypnos.powers.Crossover.SlimeGoop;
import Agrypnos.powers.Crossover.YohaneSin;
import Agrypnos.powers.Other.Affliction;
import Agrypnos.util.CardColorEnum;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;

import java.util.ArrayList;

public class Wolffia extends FlowerCard
{
    public static final String ID = Agrypnos.createID("Wolffia");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLOWER_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColorEnum.FLORIST_COLOR;

    private static final int COST = 2;
    private static final int DEBUFF = 2;
    private static final int UPGRADE_PLUS_DEBUFF = 2;
    private static final int GROWTH = 1;


    public Wolffia() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET, GROWTH);

        this.FlowerGrowth = GrowthType.magic;
        initialValue = DEBUFF;
        this.baseMagicNumber = this.magicNumber = DEBUFF;

        this.exhaust = true;
    }

    @Override
    public boolean UPGRADE_GROWTH() {
        return false;
    }

    @Override
    public int baseCost()
    {
        return COST;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if (AbstractDungeon.player.hand.contains(this))
        {
            AbstractDungeon.actionManager.addToBottom(new TriggerGrowthAction(getTriggerGrowthAction(), true, growthFlash));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.magicNumber > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new CollectorCurseEffect(m.hb.cX, m.hb.cY), 2.0F));

            ArrayList<AbstractPower> debuffPowers = new ArrayList<>();

            debuffPowers.add(new WeakPower(m, 1, false));
            debuffPowers.add(new VulnerablePower(m, 1, false));
            debuffPowers.add(new StrengthPower(m, -1));
            debuffPowers.add(new PoisonPower(m, p, 1));
            debuffPowers.add(new Affliction(m, 1));
            if (Loader.isModLoaded("Yohane"))
            {
                debuffPowers.add(YohaneSin.getNewSinPower(m, 1));
            }
            if (Loader.isModLoaded("Slimebound"))
            {
                debuffPowers.add(SlimeGoop.getNewSlimedPower(m, p, 1));
            }
            if (Loader.isModLoaded("ReplayTheSpireMod"))
            {
                debuffPowers.add(RTSPowers.getNewNecroticPoisonPower(m, p, 1));
                debuffPowers.add(RTSPowers.getNewLanguidPower(m, 1));
            }


            for (int i = 0; i < this.magicNumber; i++)
            {
                AbstractPower debuffPower = debuffPowers.get(AbstractDungeon.cardRandomRng.random(debuffPowers.size() - 1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, debuffPower, debuffPower.amount));
            }
        }

        if (ResetOnPlay)
            AbstractDungeon.actionManager.addToBottom(new ResetFlowerGrowthAction(this));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Wolffia();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_DEBUFF);
            initialValue = this.baseMagicNumber;
            this.initializeDescription();
        }
    }
}