package Agrypnos.powers.Crossover;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import yohanemod.powers.Sin;

public class YohaneSin {
    public static AbstractPower getNewSinPower(AbstractCreature owner, int amount)
    {
        return new Sin(owner, amount);
    }
}
