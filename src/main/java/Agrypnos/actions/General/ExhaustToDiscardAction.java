package Agrypnos.actions.General;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExhaustToDiscardAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCard c;

    public ExhaustToDiscardAction(AbstractCard c) {
        this.p = AbstractDungeon.player;
        this.c = c;
        this.setValues(this.p, AbstractDungeon.player, 1);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        if (this.p.exhaustPile.contains(c) && this.duration == Settings.ACTION_DUR_FAST)
        {
            c.unfadeOut();

            this.p.discardPile.addToTop(c);

            c.applyPowers();

            this.p.exhaustPile.removeCard(c);

            c.unhover();

            c.fadingOut = false;
        }
        this.tickDuration();
    }
}
