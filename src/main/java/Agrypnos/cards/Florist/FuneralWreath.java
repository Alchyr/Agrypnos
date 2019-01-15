package Agrypnos.cards.Florist;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.cards.CardImages;
import Agrypnos.util.CardColorEnum;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

public class FuneralWreath extends CustomCard
{
    public static final String ID = Agrypnos.createID("FuneralWreath");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Agrypnos.makePath(CardImages.FLORIST_ATTACK);

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = CardColorEnum.FLORIST_COLOR;

    private static final int BASE_COST = 9;
    private static final int DAMAGE = 9;
    private static final int UPGRADE_DAMAGE = 4;


    public FuneralWreath() {
        super(ID, NAME, IMG, BASE_COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.isMultiDamage = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int bellCooldown = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof FlowerCard)
            {
                AbstractDungeon.actionManager.addToBottom(
                        new VFXAction(new ShockWaveEffect(p.hb.cX, p.hb.cY, Color.DARK_GRAY, ShockWaveEffect.ShockWaveType.ADDITIVE))
                );
                if (bellCooldown == 0)
                {
                    AbstractDungeon.actionManager.addToBottom(
                            new SFXAction("BELL", 0.075f)
                    );
                    bellCooldown = 2;
                }
                else
                {
                    bellCooldown--;
                }
                AbstractDungeon.actionManager.addToBottom(
                        new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(p, multiDamage, this.damageTypeForTurn,
                                AbstractGameAction.AttackEffect.NONE, false));
                AbstractDungeon.actionManager.addToBottom(
                        new WaitAction(0.1f));
            }
        }
        if (!upgraded)
        {
            AbstractDungeon.actionManager.addToBottom(new ExhaustAction(p, p, 5000, true));
        }
        else //i'm sure nobody will have over 5000 cards. Probably.
        {
            AbstractDungeon.actionManager.addToBottom(new DiscardAction(p, p, 5000, true));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new FuneralWreath();
    }

    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        int flowerCount = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof FlowerCard)
            {
                flowerCount++;
            }
        }
        this.setCostForTurn(this.cost - flowerCount);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int flowerCount = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof FlowerCard)
            {
                flowerCount++;
            }
        }
        this.setCostForTurn(this.cost - flowerCount);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}