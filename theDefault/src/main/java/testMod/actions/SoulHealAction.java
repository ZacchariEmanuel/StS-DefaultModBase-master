package testMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import testMod.powers.CommonPower;
import testMod.util.ModUtil;

public class SoulHealAction extends AbstractGameAction {

        public SoulHealAction(AbstractCreature source, int amount) {
            this.setValues(AbstractDungeon.player, source, amount);
            this.startDuration = this.duration;
            if (Settings.FAST_MODE) {
                this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
            }

            this.actionType = ActionType.HEAL;
        }

        public SoulHealAction(AbstractCreature source, int amount, float duration) {
            this(source, amount);
            this.duration = this.startDuration = duration;
        }

        public void update() {
            if (this.duration == this.startDuration) {
                ModUtil.SoulHeal(amount);
            }

            this.tickDuration();
        }
}
