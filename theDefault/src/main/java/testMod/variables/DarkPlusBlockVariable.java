package testMod.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import testMod.powers.DarkPower;

import static testMod.DefaultMod.makeID;

public class DarkPlusBlockVariable extends DynamicVariable
{
    @Override
    public String key()
{
    return makeID("DARK_PLUS_BLOCK");
}

    @Override
    public boolean isModified(AbstractCard card)
    {
        return card.isBlockModified;
    }

    @Override
    public int value(AbstractCard card)
    {
        if(!AbstractDungeon.player.hasPower(DarkPower.POWER_ID))
            return card.block;
        else
            return card.block + AbstractDungeon.player.getPower(DarkPower.POWER_ID).amount;
    }
    @Override
    public int baseValue(AbstractCard card)
    {
        if(!AbstractDungeon.player.hasPower(DarkPower.POWER_ID))
            return card.baseBlock;
        else
            return card.baseBlock + AbstractDungeon.player.getPower(DarkPower.POWER_ID).amount;
    }
    @Override
    public boolean upgraded(AbstractCard card)
    {
        return card.upgradedBlock;
    }
}