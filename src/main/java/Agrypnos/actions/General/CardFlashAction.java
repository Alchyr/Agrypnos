package Agrypnos.actions.General;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.Iterator;
import java.util.UUID;

public class CardFlashAction extends AbstractGameAction {
    private UUID targetUUID;
    private Color color;

    public CardFlashAction(UUID targetUUID, Color color)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.targetUUID = targetUUID;
        this.color = color;
    }
    public CardFlashAction(UUID targetUUID)
    {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.targetUUID = targetUUID;
        this.color = null;
    }

    @Override
    public void update()
    {
        Iterator var1 = GetAllInBattleInstances.get(this.targetUUID).iterator();

        while (var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();

            if (color != null) {
                c.flash(color);
            }
            else {
                c.flash();
            }
        }

        this.isDone = true;
    }
}