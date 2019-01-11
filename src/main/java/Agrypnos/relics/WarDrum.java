package Agrypnos.relics;

import Agrypnos.Agrypnos;
import Agrypnos.abstracts.Relic;

import com.megacrit.cardcrawl.actions.common.ApplyPoisonOnRandomMonsterAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;


public class WarDrum extends Relic
{
    public static final String ID = Agrypnos.createID("War Drum");



    public static int LastCardsPlayed = 0;

    public static int ThisTurnPlayed = 0;

    public static boolean FirstTurn = false;


    public WarDrum() {
        super(ID, "WarDrum",
                RelicTier.RARE, LandingSound.HEAVY);

        setCounter(0);
    }


    @Override
    public AbstractRelic makeCopy() {
        return new WarDrum();
    }

    @Override
    public void atBattleStart() {
        ThisTurnPlayed = 0;
        FirstTurn = true;//prevent energy gain on first turn
    }

    @Override
    public void atTurnStart() {
        if (LastCardsPlayed == ThisTurnPlayed && !FirstTurn)
        {
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
        }
        if (FirstTurn)
        {
            FirstTurn = false;
        }
        LastCardsPlayed = ThisTurnPlayed;
        ThisTurnPlayed = 0;

        setCounter(LastCardsPlayed);

        if (counter == 0) {
            beginPulse();
        }
    }

    @Override
    public void onPlayerEndTurn() {
        if (pulse)
            stopPulse();
    }

    @Override
    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
        super.onUseCard(targetCard, useCardAction);

        if (targetCard.type == AbstractCard.CardType.ATTACK && targetCard.target == AbstractCard.CardTarget.ENEMY)
        {
            useCardAction.target = AbstractDungeon.getRandomMonster();
        }
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        ThisTurnPlayed++;
        setCounter(LastCardsPlayed - ThisTurnPlayed); //if it goes below 0 it should become invisible
    }

    @Override
    public void onVictory() {
        LastCardsPlayed = ThisTurnPlayed;
        setCounter(LastCardsPlayed);
    }
}
