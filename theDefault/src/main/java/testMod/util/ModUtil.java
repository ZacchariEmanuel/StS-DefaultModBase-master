package testMod.util;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import testMod.OnHeal;
import testMod.effects.ColoredHealEffect;
import testMod.effects.ColoredHealPanelEffect;
import testMod.patches.SoulHealthPatch;
import testMod.powers.DarkPower;
import testMod.powers.LightPower;

import java.lang.reflect.Field;

public class ModUtil {
    public static class COLORS{
        public static final Color SOUL_HEAL = Color.valueOf("9b27e3");
        public static final Color SOUL_HEAL_TEXT = Color.valueOf("461266");
        public static final Color LIGHT = Color.valueOf("feffd9");
        public static final Color DARK = Color.valueOf("8f918e");
        public static final Color SMOKE_WORD = Color.valueOf("404140");
    }
    public static boolean isNoSoulHealth(){
        //Needed to avoid Type "'java/lang/Object' (current frame, stack[0]) is not assignable to integer" in SoulHealthPatch.SoulHealthRenderTextPatch
        return SoulHealthPatch.SoulHealth.get(AbstractDungeon.player) == 0;
    }

    public static int getLightCount(){
        if(AbstractDungeon.player == null || !AbstractDungeon.player.hasPower(LightPower.POWER_ID))
            return 0;
        else
            return AbstractDungeon.player.getPower(LightPower.POWER_ID).amount;
    }
    public static int getDarkCount(){
        if(AbstractDungeon.player == null || !AbstractDungeon.player.hasPower(DarkPower.POWER_ID))
            return 0;
        else
            return AbstractDungeon.player.getPower(DarkPower.POWER_ID).amount;
    }

    public static void SoulHeal(int healAmount) {
        if (Settings.isEndless && AbstractDungeon.player.hasBlight("FullBelly")) {
            healAmount /= 2;
            if (healAmount < 1) {
                healAmount = 1;
            }
        }

        if (AbstractDungeon.player.isDying) {
            return;
        }

        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (AbstractDungeon.player.isPlayer) {
                healAmount = r.onPlayerHeal(healAmount);
            }
        }

        for (AbstractPower p : AbstractDungeon.player.powers) {
            healAmount = p.onHeal(healAmount);
        }

        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if(c instanceof OnHeal){
                ((OnHeal) c).triggerOnHeal(healAmount);
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if(c instanceof OnHeal){
                ((OnHeal) c).triggerOnHeal(healAmount);
            }
        }
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if(c instanceof OnHeal){
                ((OnHeal) c).triggerOnHeal(healAmount);
            }
        }

        int SoulHealth = SoulHealthPatch.SoulHealth.get(AbstractDungeon.player) + healAmount;
        if (AbstractDungeon.player.currentHealth + SoulHealth > AbstractDungeon.player.maxHealth) {
            SoulHealth = AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;
        }
        SoulHealthPatch.SoulHealth.set(AbstractDungeon.player,SoulHealth);

        if (healAmount > 0) {

            ////////////////////////////////////////////////////////////////////////////////////////
            //TODO:Find a better solution to this
            Float exposedHpIconX = 0F;
            try {
                //Extract hpIconX because it is private :(
                Field privateFloat = TopPanel.class.getDeclaredField("hpIconX");
                privateFloat.setAccessible(true);
                exposedHpIconX = (Float) privateFloat.get(AbstractDungeon.topPanel);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            } //I am not proud of this
            AbstractDungeon.topLevelEffectsQueue.add(new ColoredHealPanelEffect(exposedHpIconX,COLORS.SOUL_HEAL));
            ////////////////////////////////////////////////////////////////////////////////////////

            AbstractDungeon.effectsQueue.add(new ColoredHealEffect(AbstractDungeon.player.hb.cX - AbstractDungeon.player.animX, AbstractDungeon.player.hb.cY, healAmount, COLORS.SOUL_HEAL, COLORS.DARK));
            AbstractDungeon.player.healthBarUpdatedEvent();
        }
    }
}
