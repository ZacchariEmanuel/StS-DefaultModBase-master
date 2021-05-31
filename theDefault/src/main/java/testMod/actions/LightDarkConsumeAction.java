package testMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.beyond.Donu;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import testMod.patches.LightDarkPatch;

public class LightDarkConsumeAction extends AbstractGameAction {
        private int increaseLightAmount;
        private int increaseDarkAmount;
        private DamageInfo info;
        private static final float DURATION = 0.1F;

        public LightDarkConsumeAction(AbstractCreature target, DamageInfo info, int increaseLightAmount, int increaseDarkAmount) {
            this.info = info;
            this.setValues(target, info);
            this.increaseLightAmount = increaseLightAmount;
            this.increaseDarkAmount = increaseDarkAmount;
            this.actionType = ActionType.DAMAGE;
            this.duration = 0.1F;
        }

        public void update() {
            if (this.duration == 0.1F && this.target != null) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.NONE));
                this.target.damage(this.info);
                if ((((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {
                    int PriorLightAmount = LightDarkPatch.InitialLightAmount.get(AbstractDungeon.player);
                    int PriorDarkAmount = LightDarkPatch.InitialDarkAmount.get(AbstractDungeon.player);
                    LightDarkPatch.InitialLightAmount.set(AbstractDungeon.player, PriorLightAmount + increaseLightAmount);
                    LightDarkPatch.InitialDarkAmount.set(AbstractDungeon.player, PriorDarkAmount + increaseDarkAmount);
                }

                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }
            }

            this.tickDuration();
        }

}
