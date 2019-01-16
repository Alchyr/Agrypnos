package Agrypnos.patches;

import Agrypnos.powers.Other.Affliction;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch(
        clz = ApplyPowerAction.class,
        method = "update"
)
public class AfflictionPatch {
    @SpireInsertPatch(
        locator=Locator.class,
        localvars={"target", "powerToApply", "duration"}
    )
    public static SpireReturn Insert(ApplyPowerAction __powerApplication, AbstractCreature target, AbstractPower powerToApply, @ByRef float[] duration)
    {
        if (target.hasPower(Affliction.POWER_ID) && powerToApply.type == AbstractPower.PowerType.BUFF) {
            AbstractDungeon.actionManager.addToTop(new TextAboveCreatureAction(target, "Negated"));
            duration[0] -= Gdx.graphics.getDeltaTime();
            CardCrawlGame.sound.play("NULLIFY_SFX");
            target.getPower(Affliction.POWER_ID).flashWithoutSound();
            target.getPower(Affliction.POWER_ID).onSpecificTrigger();
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }


    private static class Locator extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "add");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
