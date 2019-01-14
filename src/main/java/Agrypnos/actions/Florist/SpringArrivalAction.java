package Agrypnos.actions.Florist;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class SpringArrivalAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private AbstractPlayer p;
    private int energyOnUse;
    private int bonusSeeds;
    private AbstractCard seedCard;

    public SpringArrivalAction(AbstractPlayer p, boolean freeToPlayOnce, int energyOnUse, int bonusSeeds, AbstractCard seedCard) {
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.bonusSeeds = bonusSeeds;
        this.seedCard = seedCard;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        effect += bonusSeeds;

        if (effect > 0) {
            for (int i = 0; i < effect; ++i) {
                AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(seedCard, 1));
            }

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}