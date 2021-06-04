package testMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import testMod.powers.CommonPower;
import testMod.powers.SmokePower;

public class SmokeXAction extends AbstractGameAction {
    private boolean freeToPlayOnce;
    private int magicNumber;
    private AbstractPlayer p;
    private int energyOnUse;
    //private boolean upgraded;

    public SmokeXAction(final AbstractPlayer p, final AbstractMonster m,
                        final int magicNumber, final boolean upgraded,
                        final DamageInfo.DamageType damageTypeForTurn, final boolean freeToPlayOnce,
                        final int energyOnUse) {
        this.freeToPlayOnce = false;
        this.p = p;
        this.magicNumber = magicNumber;
        this.freeToPlayOnce = freeToPlayOnce;
        actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        //this.upgraded = upgraded;
    }
    
    @Override
    public void update() {
        int Xamount = EnergyPanel.totalCount;
        if (energyOnUse != -1) {
            Xamount = energyOnUse;
        }
        if (p.hasRelic(ChemicalX.ID)) {
            Xamount += 2;
            p.getRelic(ChemicalX.ID).flash();
        }
        if (Xamount > 0) {
            for (int i = 0; i < Xamount; ++i) {
                
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                        new SmokePower(p, p, magicNumber), magicNumber,
                        AttackEffect.BLUNT_LIGHT));
            }
            if (!freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        }
        isDone = true;
    }
}
