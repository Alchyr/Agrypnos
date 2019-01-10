package Agrypnos.actions.General;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class UpdateHandAction extends AbstractGameAction {
    @Override
    public void update() {
        AbstractDungeon.player.hand.refreshHandLayout();
        AbstractDungeon.player.hand.applyPowers();

        this.isDone = true;
    }
}
