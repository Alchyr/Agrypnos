package Agrypnos.actions.General;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.Iterator;
import java.util.UUID;

public class CardFlashAction extends AbstractGameAction {
    UUID targetUUID;

    public CardFlashAction(UUID targetUUID)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.targetUUID = targetUUID;
    }

    @Override
    public void update()
    {
        Iterator var1 = GetAllInBattleInstances.get(this.targetUUID).iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            c.flash();
        }

        this.isDone = true;
    }
}