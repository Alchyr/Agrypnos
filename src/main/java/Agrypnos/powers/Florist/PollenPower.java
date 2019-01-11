package Agrypnos.powers.Florist;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.FlowerCard;
import Agrypnos.powers.PowerImages;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPoisonOnRandomMonsterAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PollenPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = Agrypnos.createID("PollenPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = Agrypnos.makePath(PowerImages.POLLEN);

    public PollenPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.source = source;
    }

    public static void onFlowerGrowth(FlowerCard trigger)
    {
        AbstractPower PlayerPollen = AbstractDungeon.player.getPower(POWER_ID);

        if (PlayerPollen != null)
        {
            int amt = PlayerPollen.amount;
            AbstractDungeon.actionManager.addToBottom(new ApplyPoisonOnRandomMonsterAction(AbstractDungeon.player, amt, true, AbstractGameAction.AttackEffect.POISON));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}