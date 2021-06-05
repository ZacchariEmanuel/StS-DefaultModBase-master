package testMod.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import testMod.DefaultMod;
import testMod.OnHeal;
import testMod.actions.DrawPileThisToHandAction;
import testMod.actions.SoulHealAction;
import testMod.characters.TheDefault;
import testMod.powers.SmokePower;

import static testMod.DefaultMod.makeCardPath;

public class VampireBat extends AbstractDynamicCard implements OnHeal {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(VampireBat.class.getSimpleName());
    public static final String IMG = makeCardPath("VampireBat.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 0;

    private static final int DAMAGE = 3;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static final int MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;


    // /STAT DECLARATION/


    public VampireBat() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC;

    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new SoulHealAction(p,magicNumber));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);

            initializeDescription();
        }
    }

    @Override
    public void triggerOnHeal(int amount) {
        addToBot((AbstractGameAction)new DrawPileThisToHandAction(this));
    }
}
