package testMod.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import testMod.DefaultMod;
import testMod.characters.TheDefault;
import testMod.powers.LightPower;

import static testMod.DefaultMod.makeCardPath;

public class LightAttack extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Special Strike: Deal 7 (*) damage times the energy you currently have.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(LightAttack.class.getSimpleName());
    public static final String IMG = makeCardPath("LightAttack.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 0;

    private static final int MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    public int specialDamage;

    // /STAT DECLARATION/

    public LightAttack() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = MAGIC;

        isMultiDamage = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = damage;
        if(p.hasPower(LightPower.POWER_ID))
            effect += p.getPower(LightPower.POWER_ID).amount * magicNumber;

        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, effect, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.FIRE));
        if(m!= null)
            AbstractDungeon.actionManager.addToBottom((new VFXAction(new LightningEffect(m.hb.cX, m.hb.cY))));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_MAGIC);

            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
