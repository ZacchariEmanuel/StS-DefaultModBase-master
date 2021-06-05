package testMod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import testMod.OnHeal;
import testMod.util.ModUtil;

import java.util.ArrayList;


@SpirePatch(
        clz = AbstractCreature.class,
        method = "heal",
        paramtypez = {int.class}

)
class OnHealPatch{
    @SpirePostfixPatch
    public static void OnHealPatch(AbstractCreature __instance, int amount)
    {
        if(__instance.isPlayer){
            for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if(c instanceof OnHeal){
                    ((OnHeal) c).triggerOnHeal(amount);
                }
            }
            for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                if(c instanceof OnHeal){
                    ((OnHeal) c).triggerOnHeal(amount);
                }
            }
            for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
                if(c instanceof OnHeal){
                    ((OnHeal) c).triggerOnHeal(amount);
                }
            }
        }
    }
}
