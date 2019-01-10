package Agrypnos.actions.General;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ConditionalExhaustSpecificCardAction extends AbstractGameAction{
    private AbstractCard targetCard;
    private CardGroup group;
    private float startingDuration;
    private int testValue;

    public ConditionalExhaustSpecificCardAction(AbstractCard targetCard, CardGroup group, int testValue) {
        this.targetCard = targetCard;
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.EXHAUST;
        this.group = group;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;
        this.testValue = testValue;
    }

    public void update() {
        if (this.duration == this.startingDuration && this.group.contains(this.targetCard) && this.targetCard.magicNumber == testValue) {
            this.group.moveToExhaustPile(this.targetCard);
            CardCrawlGame.dungeon.checkForPactAchievement();
            this.targetCard.exhaustOnUseOnce = false;
            this.targetCard.freeToPlayOnce = false;
        }

        this.tickDuration();
    }
}
