package Agrypnos.powers.Florist;

import Agrypnos.Agrypnos;
import Agrypnos.cards.Florist.Flowers.FlowerCard;
import Agrypnos.powers.PowerImages;

import com.megacrit.cardcrawl.localization.PowerStrings;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;

import java.util.ArrayList;


public class FertilizerPower extends AbstractPower {
    public AbstractCreature source;

    public static final String POWER_ID = Agrypnos.createID("FertilizerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = Agrypnos.makePath(PowerImages.FERTILIZER);

    public FertilizerPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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

    @Override
    public void atStartOfTurnPostDraw() {
        ArrayList<FlowerCard> FlowerCardsInHand = new ArrayList<FlowerCard>();

        for (AbstractCard c : AbstractDungeon.player.hand.group)
        {
            if (c instanceof FlowerCard)
                FlowerCardsInHand.add((FlowerCard)c);
        }

        if (FlowerCardsInHand.size() >= 1)
        {
            for (int i = 0; i < this.amount; i++)
            {
                AbstractDungeon.actionManager.addToBottom(FlowerCardsInHand.get(AbstractDungeon.cardRng.random(0, FlowerCardsInHand.size() - 1)).getTriggerGrowthAction());
            }
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }

        else if (this.amount > 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }
}