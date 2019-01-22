package Agrypnos.actions.General;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AddActionToBottomAction extends AbstractGameAction {
    private AbstractGameAction ActionToAdd;

    public AddActionToBottomAction(AbstractGameAction actionToAdd)
    {
        this.ActionToAdd = actionToAdd;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        AbstractDungeon.actionManager.addToBottom(ActionToAdd);

        this.isDone = true;
    }
}
