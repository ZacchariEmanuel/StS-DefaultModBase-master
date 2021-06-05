package testMod.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import testMod.DefaultMod;
import testMod.actions.SoulHealAction;
import testMod.characters.TheDefault;
import testMod.powers.SmokePower;

import static testMod.DefaultMod.makeCardPath;

public class Masochism extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Masochism.class.getSimpleName());
    public static final String IMG = makeCardPath("Masochism.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int BLOCK = 10;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int MAGIC = 3;


    // /STAT DECLARATION/


    public Masochism() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        block = baseBlock = BLOCK;
        magicNumber = baseMagicNumber = MAGIC;
        this.exhaustOnUseOnce = true;

    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(p, p, magicNumber));
        AbstractDungeon.actionManager.addToBottom(new SoulHealAction(p, this.block));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);

            initializeDescription();
        }
    }
}
