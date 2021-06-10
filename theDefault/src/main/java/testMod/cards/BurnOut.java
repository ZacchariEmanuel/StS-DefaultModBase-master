package testMod.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import testMod.DefaultMod;
import testMod.characters.TheDefault;
import testMod.patches.LightDarkPatch;
import testMod.powers.LightPower;
import testMod.powers.SmokePower;
import testMod.util.ModUtil;

import static testMod.DefaultMod.makeCardPath;

public class BurnOut extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(BurnOut.class.getSimpleName());
    public static final String IMG = makeCardPath("Temp_Burnout.png"); //Default IMG
    // Use if you have a png for this card //public static final String IMG = makeCardPath("BurnOut.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;
    //private static final int UPGRADED_COST = 2;

    // /STAT DECLARATION/


    public BurnOut() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(upgraded)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightPower(p, p, this.magicNumber), this.magicNumber));

        int effect = ModUtil.getLightCount();

        for (int i = effect; i > 0; i--){
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, i, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            p.getPower(LightPower.POWER_ID).amount -= 1;
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //upgradeBaseCost(UPGRADED_COST);
            rawDescription = UPGRADE_DESCRIPTION;

            initializeDescription();
        }
    }
}
