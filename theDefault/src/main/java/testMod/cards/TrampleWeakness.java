package testMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import javafx.scene.effect.Light;
import testMod.DefaultMod;
import testMod.characters.TheDefault;
import testMod.powers.DarkPower;
import testMod.powers.LightPower;

import java.util.ArrayList;
import java.util.Iterator;

import static testMod.DefaultMod.makeCardPath;

public class TrampleWeakness extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(TrampleWeakness.class.getSimpleName());
    public static final String IMG = makeCardPath("TrampleWeakness.png"); //Default Image

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = -2;
    private static final int UPGRADED_COST = -2;
    // /STAT DECLARATION/

    public TrampleWeakness() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.purgeOnUse = true;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }

    public AbstractCard makeCopy() {
        return new TrampleWeakness();
    }

    // CHOICE CARD FUNCTIONALITY

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        stanceChoices.add(new Trample());
        stanceChoices.add(new Weakness());
        if (this.upgraded) {
            Iterator var4 = stanceChoices.iterator();

            while (var4.hasNext()) {
                AbstractCard c = (AbstractCard) var4.next();
                c.upgrade();
            }
        }

        this.addToBot(new ChooseOneAction(stanceChoices));
    }

    // /CHOICE CARD FUNCTIONALITY /
}


class Trample extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Trample.class.getSimpleName());
    public static final String IMG = makeCardPath("Trample.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_WHITE;

    private static final int COST = 1;
    private static final int MAGICNUMBER = 2;
    private static final int DAMAGE = 4;


    // /STAT DECLARATION/


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeDamage(2);
            initializeDescription();
        }
    }

    public Trample() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.baseMagicNumber = MAGICNUMBER;
        this.magicNumber = MAGICNUMBER;
    }

    // Adds the card to your hand when you select it as your choice
    public void onChoseThisOption() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new LightPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(makeCopy(), true));
    }

    public AbstractCard makeCopy(){
        AbstractCard card = new Trample();
        if(this.upgraded)
            card.upgrade();
        return card;
    }
}


class Weakness extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Weakness.class.getSimpleName());
    public static final String IMG = makeCardPath("Weakness.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BLACK;

    private static final int COST = 1;
    private static final int MAGICNUMBER = 2;
    private static final int DAMAGE = 4;


    // /STAT DECLARATION/


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            upgradeDamage(2);
            initializeDescription();
        }
    }

    public Weakness() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.baseMagicNumber = MAGICNUMBER;
        this.magicNumber = MAGICNUMBER;
    }

    // Adds the card to your hand when you select it as your choice
    public void onChoseThisOption() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new DarkPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(makeCopy(), true));
    }

    public AbstractCard makeCopy() {
        AbstractCard card = new Weakness();
        if(this.upgraded)
            card.upgrade();
        return card;
    }
}

/* localization/card text template, goes in Card-Strings.json

  "testMod:TrampleWeakness": {
    "NAME": "Trample Weakness",
    "DESCRIPTION": "Choose  [#fae9e8]Trample[] or [#8f918e]Weakness[]."
  },
  "testMod:Trample": {
    "NAME": "Trample",
    "DESCRIPTION": "Deal !D! Damage and apply !M! Vulnerable."
  },
  "testMod:Weakness": {
    "NAME": "Weakness",
    "DESCRIPTION": "Deal !D! Damage and apply !M! Weak.
  },
 */