package testMod.util;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import com.megacrit.cardcrawl.vfx.combat.HealPanelEffect;
import testMod.ColoredHealEffect;
import testMod.ColoredHealPanelEffect;
import testMod.patches.SoulHealthPatch;

import java.lang.reflect.Field;

public class ModUtil {
    public static class COLORS{
        public static final Color SOUL_HEAL = Color.valueOf("9b27e3");
        public static final Color LIGHT = Color.valueOf("fae9e8");
        public static final Color DARK = Color.valueOf("8f918e");
        public static final Color SMOKE_WORD = Color.valueOf("8f918e");
    }
    public static boolean isNoSoulHealth(){
        //Needed to avoid Type "'java/lang/Object' (current frame, stack[0]) is not assignable to integer" in SoulHealthPatch.SoulHealthRenderTextPatch
        return SoulHealthPatch.SoulHealth.get(AbstractDungeon.player) == 0;
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
