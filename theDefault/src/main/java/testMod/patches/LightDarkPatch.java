package testMod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import testMod.powers.DarkPower;
import testMod.powers.LightPower;

@SpirePatch(
        clz= AbstractPlayer.class,
        method=SpirePatch.CLASS
)
public class LightDarkPatch
{
    public static SpireField<Integer> InitialLightAmount = new SpireField<>(() -> 0);
    public static SpireField<Integer> InitialDarkAmount = new SpireField<>(() -> 0);

}

@SpirePatch(
    clz = AbstractPlayer.class,
    method = "applyStartOfCombatPreDrawLogic"

)
class LightDarkCombatStartPatch{
    @SpirePostfixPatch
    public static void ApplyInitialLightDark()
    {
        int initialLight = LightDarkPatch.InitialLightAmount.get(AbstractDungeon.player);
        int initialDark = LightDarkPatch.InitialDarkAmount.get(AbstractDungeon.player);
            if(initialLight > 0)
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LightPower(AbstractDungeon.player, AbstractDungeon.player, initialLight), initialLight));
            if(initialDark > 0)
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DarkPower(AbstractDungeon.player, AbstractDungeon.player, initialDark), initialDark));

    }
}