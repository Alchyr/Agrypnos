package Agrypnos.powers.Crossover;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.mod.replay.powers.LanguidPower;
import com.megacrit.cardcrawl.mod.replay.powers.NecroticPoisonPower;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class RTSPowers {
    public static AbstractPower getNewNecroticPoisonPower(AbstractCreature owner, AbstractCreature source, int amount)
    {
        return new NecroticPoisonPower(owner, source, amount);
    }

    public static AbstractPower getNewLanguidPower(AbstractCreature owner, int amount)
    {
        return new LanguidPower(owner, amount, false);
    }
}
