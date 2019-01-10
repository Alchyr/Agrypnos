package Agrypnos.cards.Florist;

import Agrypnos.Agrypnos;
import Agrypnos.cards.CardImages;
import Agrypnos.util.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.WindyParticleEffect;

import java.util.Iterator;

public class FloralScent extends CustomCard
{
    public static final String ID = Agrypnos.createID("FloralScent");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_SKILL);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColorEnum.FLORIST_GREEN;

    private static final int COST = 0;
    private static final int DEBUFF = 2;
    private static final int UPGRADE_PLUS_DEBUFF = 1;

    private static final int UPGRADE_SELF_DEBUFF = 1;


    public FloralScent() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);

        this.magicNumber = this.baseMagicNumber = DEBUFF;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new WindyParticleEffect(), 0.5f));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("WIND", 1.5f));

        Iterator monsters = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        AbstractMonster mo;
        while(monsters.hasNext()) {
            mo = (AbstractMonster)monsters.next();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new WeakPower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        }

        if (upgraded)
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WeakPower(p, UPGRADE_SELF_DEBUFF, false), UPGRADE_SELF_DEBUFF));
        }
        else
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WeakPower(p, this.magicNumber, false), this.magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new FloralScent();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_DEBUFF);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}