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
    public static void ApplyInitialLightDark(AbstractPlayer __instance)
    {
        int initialLight = LightDarkPatch.InitialLightAmount.get(__instance);
        int initialDark = LightDarkPatch.InitialDarkAmount.get(__instance);
            if(initialLight > 0)
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new LightPower(__instance, __instance, initialLight), initialLight));
            if(initialDark > 0)
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new DarkPower(__instance, __instance, initialDark), initialDark));

    }
}
