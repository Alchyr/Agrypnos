package Agrypnos.powers.Florist;

import Agrypnos.Agrypnos;
import Agrypnos.actions.Florist.GrowCorpseFlowersAction;
import Agrypnos.powers.PowerImages;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class CorpseFlowerPower extends AbstractPower implements InvisiblePower {
    public static final String POWER_ID = Agrypnos.createID("CorpseFlowerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static final String IMG = Agrypnos.makePath(PowerImages.NONE);

    public CorpseFlowerPower(final AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(IMG);
        this.updateDescription();
    }

    @Override
    public void onExhaust(AbstractCard card) {
        AbstractDungeon.actionManager.addToBottom(new GrowCorpseFlowersAction());
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}