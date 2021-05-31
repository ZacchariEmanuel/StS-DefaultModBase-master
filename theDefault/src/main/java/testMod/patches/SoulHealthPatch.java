package testMod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;


import java.util.ArrayList;

@SpirePatch(
        clz= AbstractPlayer.class,
        method=SpirePatch.CLASS
)
public class SoulHealthPatch
{
    public static SpireField<Integer> SoulHealth = new SpireField<>(() -> 5);
    public static final SpireField<Color> SoulHealthColor = new SpireField<>(() -> Color.valueOf("9b27e3"));
}

@SpirePatch(
    clz = AbstractCreature.class,
    method = "renderHealthText"
)
class SoulHealthRenderTextPatch{
    @SpireInstrumentPatch
    public static ExprEditor EditHealthTextRenderer()
    {
        return new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException
                {
                    if (m.getClassName().equals("com.megacrit.cardcrawl.helpers.FontHelper") //Search for this class being called
                            && m.getMethodName().equals("renderFontCentered"))  //Search for this method of that class being called
                        m.replace(
                                "{ if(!this.isPlayer) $_ = $proceed($$); "+     //If the AbstractCreature is not the player, then leave the health bar text as is
                                "else{"+    //If the AbstractCreature IS the player, then we edit the function
                                "$3 = this.currentHealth + \"[#"+   //Change the third argument of the function to the players health
                                "\"+testMod.patches.SoulHealthPatch.SoulHealthColor.get(com.megacrit.cardcrawl.dungeons.AbstractDungeon.player).toString().substring(0,6)+\""+ //Set the color (ignoring the last alpha bits)
                                "\"+"+
                                "Integer.toHexString(Math.max(0, Math.min(255, (int)(this.healthHideTimer * 0x100))))" //Set alpha bits relative to healthHideTimer
                                +"+\"](+\" + testMod.patches.SoulHealthPatch.SoulHealth.get(com.megacrit.cardcrawl.dungeons.AbstractDungeon.player) + \")[]/\" + this.maxHealth;" //finish with the soul health text then right the rest of the health text normally
                                +" $_ = $proceed($$); }}");

                }
        };
    }
}

@SpirePatch(
        clz = AbstractCreature.class,
        method = "renderRedHealthBar"
)
class SoulHealthRenderPatch{
    @SpireInsertPatch(
            locator=Locator.class
    )
    public static void Insert(AbstractCreature __instance, SpriteBatch sb, float x, float y, float ___HEALTH_BAR_OFFSET_Y, float ___HEALTH_BAR_HEIGHT) {
        if(__instance.isPlayer) {
            Color originalColor = sb.getColor(); //Store original color

            sb.setColor(SoulHealthPatch.SoulHealthColor.get(AbstractDungeon.player));
            float soulHealthWidth = __instance.hb.width * (__instance.currentHealth + SoulHealthPatch.SoulHealth.get(AbstractDungeon.player)) / __instance.maxHealth;
            if (SoulHealthPatch.SoulHealth.get(AbstractDungeon.player) > 0) {
                sb.draw(ImageMaster.HEALTH_BAR_L, x - ___HEALTH_BAR_HEIGHT, y + ___HEALTH_BAR_OFFSET_Y, ___HEALTH_BAR_HEIGHT, ___HEALTH_BAR_HEIGHT);
            }
            sb.draw(ImageMaster.HEALTH_BAR_B, x, y + ___HEALTH_BAR_OFFSET_Y, soulHealthWidth, ___HEALTH_BAR_HEIGHT);
            sb.draw(ImageMaster.HEALTH_BAR_R, x + soulHealthWidth, y + ___HEALTH_BAR_OFFSET_Y, ___HEALTH_BAR_HEIGHT, ___HEALTH_BAR_HEIGHT);

            //Set healthbar color back to what it was before this patch

            sb.setColor(originalColor);
        }
    }

    // ModTheSpire searches for a nested class that extends SpireInsertLocator
    // This class will be the Locator for the @SpireInsertPatch
    // When a Locator is not specified, ModTheSpire uses the default behavior for the @SpireInsertPatch
    private static class Locator extends SpireInsertLocator {
        // This is the abstract method from SpireInsertLocator that will be used to find the line
        // numbers you want this patch inserted at
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            // finalMatcher is the line that we want to insert our patch before -
            // in this example we are using a `MethodCallMatcher` which is a type
            // of matcher that matches a method call based on the type of the calling
            // object and the name of the method being called. Here you can see that
            // we're expecting the `end` method to be called on a `SpireBatch`

            //Matcher firstMatcher = new Matcher.MethodCallMatcher(SpriteBatch.class, "draw");
            //Matcher finalMatcher = new  Matcher.FieldAccessMatcher(ImageMaster.class,"HEALTH_BAR_R");
            Matcher finalMatcher = new  Matcher.FieldAccessMatcher(AbstractCreature.class,"currentHealth");

            ArrayList<Matcher> initialMatchers = new ArrayList<Matcher>();
            //initialMatchers.add(firstMatcher);

            // the `new ArrayList<Matcher>()` specifies the prerequisites before the line can be matched -
            // the LineFinder will search for all the prerequisites in order before it will match the finalMatcher -
            // since we didn't specify any prerequisites here, the LineFinder will simply find the first expression
            // that matches the finalMatcher.
            int[] results = LineFinder.findInOrder(ctMethodToPatch, initialMatchers, finalMatcher);
            return results;
        }
    }
}