package Agrypnos.util;

import Agrypnos.abstracts.FlowerCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class GrowthVariable extends DynamicVariable {
    @Override
    public String key()
    {
        return "growth";
        // What you put in your localization file between ! to show your value. Eg, !myKey!.
    }

    @Override
    public boolean isModified(AbstractCard card)
    {
        if (card instanceof FlowerCard)
        {
            return ((FlowerCard) card).upgradePreview;
        }
        return false;
    }

    /* Feature for newer version, uncomment later
    @Override
    public void setIsModified(AbstractCard card, boolean v)
    {
        if (card instanceof FlowerCard)
        {
            ((FlowerCard) card).upgradePreview = v;
        }
    }*/

    @Override
    public int value(AbstractCard card)
    {
        if (card instanceof FlowerCard)
        {
            return Math.abs(((FlowerCard)card).growth);
        }
        return -1;
    }

    @Override
    public int baseValue(AbstractCard card)
    {
        if (card instanceof FlowerCard)
        {
            return Math.abs(((FlowerCard)card).growth);
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card)
    {
        if (card instanceof FlowerCard)
        {
            return ((FlowerCard)card).UPGRADE_GROWTH();
        }
        return false;
    }
}
