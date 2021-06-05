package testMod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import testMod.effects.DodgedWordEffect;
import testMod.powers.SmokePower;

import java.util.ArrayList;

@SpirePatch(
        clz = AbstractPlayer.class,
        method = "damage",
        paramtypez={
                DamageInfo.class
        }
)
public class SmokePatch{
    //static final UIStrings PlayerUIStrings = CardCrawlGame.languagePack.getUIString("AbstractPlayer");
    @SpirePrefixPatch
    public static void damage(AbstractPlayer __instance, DamageInfo info) {
        if(__instance.hasPower(SmokePower.POWER_ID)){
            if(__instance.getPower(SmokePower.POWER_ID).amount >= info.output) {
                info.output = 0;
                __instance.getPower(SmokePower.POWER_ID).flash();
                AbstractDungeon.effectList.add(new DodgedWordEffect(AbstractDungeon.player, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Dodged"));//PlayerUIStrings.TEXT[0]));
            }
        }
    }
}

/*@SpirePatch(
        clz = AbstractPlayer.class,
        method = "damage",
        paramtypez={
                DamageInfo.class
        }
)
class SmokeInstrumentPatch{
    @SpireInstrumentPatch
    public static ExprEditor EditHealthTextRenderer()
    {

        return new ExprEditor() {
            public void edit(MethodCall m) throws CannotCompileException
            {

                if (m.getClassName().equals("com.megacrit.cardcrawl.characters.AbstractPlayer") //Search for this class being called
                        && m.getMethodName().equals("decrementBlock"))  //Search for this method of that class being called
                    m.replace(
                            "{ if(hasPower(testMod.powers.SmokePower.POWER_ID) && getPower(testMod.powers.SmokePower.POWER_ID).amount >= info.output){"+     //If the AbstractCreature is not the player, then leave the health bar text as is
                                    "damageAmount = info.output = 0;" +
                                    "hadBlock = false;" +
                                    "getPower(testMod.powers.SmokePower.POWER_ID).flash();" +
                                    "com.megacrit.cardcrawl.dungeons.AbstractDungeon.effectList.add(new testMod.effects.DodgedWordEffect(com.megacrit.cardcrawl.dungeons.AbstractDungeon.player, com.megacrit.cardcrawl.dungeons.AbstractDungeon.player.hb.cX, com.megacrit.cardcrawl.dungeons.AbstractDungeon.player.hb.cY, \"Dodged\"));}"+
                                    "else{$_ = $proceed($$);}}");
            }
        };
    }
}*/

/*
@SpirePatch(
        clz = AbstractPlayer.class,
        method = "damage",
        paramtypez={
                DamageInfo.class
        }
)
class SmokeInsertPatch{
    @SpireInsertPatch(
            locator = Locator.class,
            localvars = {"damageAmount","hadBlock"}
    )
    public static void Insert(AbstractPlayer __instance, DamageInfo info, int damageAmount, boolean hadBlock) {
        if(__instance.hasPower(SmokePower.POWER_ID)){
            if(__instance.getPower(SmokePower.POWER_ID).amount >= info.output) {
                //damageAmount = 0;
                //info.output = 0;
                hadBlock = false;
                //__instance.getPower(SmokePower.POWER_ID).flash();
                //AbstractDungeon.effectList.add(new DodgedWordEffect(AbstractDungeon.player, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Dodged"));//PlayerUIStrings.TEXT[0]));
            }
        }
    }

    private static class Locator extends SpireInsertLocator {

        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {

            //Matcher firstMatcher = new  Matcher.FieldAccessMatcher(AbstractPlayer.class,"currentHealth");
            Matcher finalMatcher = new  Matcher.MethodCallMatcher(AbstractPlayer.class,"hasPower");

            //ArrayList<Matcher> initialMatchers = new ArrayList<Matcher>();
            //initialMatchers.add(firstMatcher);

            int[] results = LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
            return results;
        }
    }
}

*/