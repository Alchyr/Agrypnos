package Agrypnos.actions.General;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Hyperbeam;
import com.megacrit.cardcrawl.cards.red.Exhume;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

public class ExhaustToDiscardAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCard c;

    public ExhaustToDiscardAction(AbstractCard c) {
        this.p = AbstractDungeon.player;
        this.c = c;
        this.setValues(this.p, AbstractDungeon.player, 1);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.5f;
    }

    public void update() {
        if (this.p.exhaustPile.contains(c) && this.duration == 0.5f)
        {
            this.p.discardPile.addToTop(c);

            this.p.exhaustPile.removeCard(c);

            c.unfadeOut();
        }
        this.tickDuration();
    }
}
