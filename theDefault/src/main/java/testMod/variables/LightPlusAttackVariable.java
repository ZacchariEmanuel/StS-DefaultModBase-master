package testMod.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import testMod.powers.DarkPower;
import testMod.powers.LightPower;

import static testMod.DefaultMod.makeID;

public class LightPlusAttackVariable extends DynamicVariable
{
    @Override
    public String key()
    {
        return makeID("LIGHT_PLUS_ATTACK");
    }

    @Override
    public boolean isModified(AbstractCard card)
    {
        return card.isDamageModified;
    }

    @Override
    public int value(AbstractCard card)
    {
        if(!AbstractDungeon.player.hasPower(LightPower.POWER_ID))
            return card.damage;
        else
            return card.damage + AbstractDungeon.player.getPower(LightPower.POWER_ID).amount;
    }
    @Override
    public int baseValue(AbstractCard card)
    {
        if(!AbstractDungeon.player.hasPower(LightPower.POWER_ID))
            return card.baseDamage;
        else
            return card.baseDamage + AbstractDungeon.player.getPower(LightPower.POWER_ID).amount;
    }
    @Override
    public boolean upgraded(AbstractCard card)
    {
        return card.upgradedDamage;
    }
}