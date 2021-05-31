package testMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javafx.scene.effect.Light;
import testMod.DefaultMod;
import testMod.characters.TheDefault;
import testMod.powers.DarkPower;
import testMod.powers.LightPower;

import java.util.ArrayList;
import java.util.Iterator;

import static testMod.DefaultMod.makeCardPath;

public class PartAndParcel extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(PartAndParcel.class.getSimpleName());
    public static final String IMG = makeCardPath("PartAndParcel.png"); //Default Image

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = -2;
    private static final int UPGRADED_COST = -2;
    // /STAT DECLARATION/

    public PartAndParcel() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.purgeOnUse = true;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }

    public AbstractCard makeCopy() {
        return new PartAndParcel();
    }

    // CHOICE CARD FUNCTIONALITY

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        stanceChoices.add(new Part());
        stanceChoices.add(new Parcel());
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


class Part extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Part.class.getSimpleName());
    public static final String IMG = makeCardPath("Part.png"); //Default IMG
    // Use if you have a png for this card //public static final String IMG = makeCardPath("Part.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_WHITE;

    private static final int COST = 1;

    private static final int DAMAGE = 11;
    private static final int DISCARD = 1;
    private static final int UPGRADE_PLUS_DMG = 4;

    // /STAT DECLARATION/

    public Part() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DiscardAction(p, p, DISCARD, false));
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }


    // Adds the card to your hand when you select it as your choice
    public void onChoseThisOption() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new LightPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(makeCopy(), true));
    }

    public AbstractCard makeCopy() {
        AbstractCard card = new Part();
        if (this.upgraded)
            card.upgrade();
        return card;
    }
}


class Parcel extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Parcel.class.getSimpleName());
    public static final String IMG = makeCardPath("Parcel.png"); //Default IMG
    private static final CardStrings cardStrings;
    // Use if you have a png for this card //public static final String IMG = makeCardPath("Parcel.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BLACK;

    private static final int COST = 1;

    private static final int DAMAGE = 6;
    private static final int DRAW = 1;
    private static final int UPGRADE_DRAW = 1;

    // /STAT DECLARATION/

    public Parcel() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = DRAW;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        this.addToBot(new DrawCardAction(p, magicNumber,false));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_DRAW);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    // Adds the card to your hand when you select it as your choice
    @Override
    public void onChoseThisOption() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new DarkPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(makeCopy(), true));
    }

    public AbstractCard makeCopy() {
        AbstractCard card = new Parcel();
        if (this.upgraded)
            card.upgrade();
        return card;
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

}

/* localization/card text template, goes in Card-Strings.json

  "testMod:PartAndParcel": {
    "NAME": "PartAndParcel",
    "DESCRIPTION": "Choose  [#f2f2f2]Part[] or [#a6a6a6]Parcel[]."
  },
  "testMod:Part": {
    "NAME": "Part",
    "DESCRIPTION": "Discard !M!, Deal !D! Damage."
  },
  "testMod:Parcel": {
    "NAME": "Parcel",
    "DESCRIPTION": "Deal !D! Damage, Draw !M!."
  },
 */