package Agrypnos.actions.General;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.Iterator;
import java.util.UUID;

public class TempModifyBlockAction extends AbstractGameAction {
    UUID uuid;

    public TempModifyBlockAction(UUID targetUUID, int amount) {
        this.setValues(this.target, this.source, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.uuid = targetUUID;
    }

    public void update() {
        Iterator var1 = GetAllInBattleInstances.get(this.uuid).iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            c.block += this.amount;
            if (c.block < 0) {
                c.block = 0;
            }
        }

        this.isDone = true;
    }
}