package Agrypnos.powers.Crossover;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import slimebound.powers.SlimedPower;

public class SlimeGoop {
    public static AbstractPower getNewSlimedPower(AbstractCreature owner, AbstractCreature source, int amount)
    {
        return new SlimedPower(owner, source, amount);
    }
}
