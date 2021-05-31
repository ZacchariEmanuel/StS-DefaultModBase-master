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
import testMod.DefaultMod;
import testMod.characters.TheDefault;
import testMod.powers.DarkPower;
import testMod.powers.LightPower;

import java.util.ArrayList;
import java.util.Iterator;

import static testMod.DefaultMod.makeCardPath;

public class LightDarkPower extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(LightDarkPower.class.getSimpleName());
    public static final String IMG = makeCardPath("LightDarkPowerSkill.png"); //Default Image

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = -2;
    private static final int UPGRADED_COST = -2;
    // /STAT DECLARATION/

    public LightDarkPower() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.purgeOnUse = true;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }

    public AbstractCard makeCopy() {
        return new LightDarkPower();
    }

    // CHOICE CARD FUNCTIONALITY

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        stanceChoices.add(new LightPowerPower());
        stanceChoices.add(new DarkPowerPower());
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


class LightPowerPower extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(LightPowerPower.class.getSimpleName());
    public static final String IMG = makeCardPath("LightPowerPower.png"); //Default IMG
    // Use if you have a png for this card //public static final String IMG = makeCardPath("LightPower.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BLACK;

    private static final int COST = 1;
    private static final int MAGIC = 2;
    private static final int MAGIC_PLUS_UPGRADE = 1;

    // /STAT DECLARATION/

    public LightPowerPower() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new LightPower(AbstractDungeon.player, AbstractDungeon.player, magicNumber), magicNumber));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(MAGIC_PLUS_UPGRADE);
            initializeDescription();
        }
    }


    // Choice Functionality //
    //////////////////////////

    // Adds the card to your hand when you select it as your choice
    public void onChoseThisOption() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new LightPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(makeCopy(), true));
    }

    public AbstractCard makeCopy() {
        AbstractCard card = new LightPowerPower();
        if (this.upgraded)
            card.upgrade();
        return card;
    }
}


class DarkPowerPower extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(DarkPowerPower.class.getSimpleName());
    public static final String IMG = makeCardPath("DarkPowerPower.png"); //Default IMG
    // Use if you have a png for this card //public static final String IMG = makeCardPath("DarkPower.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_BLACK;

    private static final int COST = 1;
    private static final int MAGIC = 2;
    private static final int MAGIC_PLUS_UPGRADE = 1;

    // /STAT DECLARATION/

    public DarkPowerPower() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new DarkPower(AbstractDungeon.player, AbstractDungeon.player, magicNumber), magicNumber));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(MAGIC_PLUS_UPGRADE);
            initializeDescription();
        }
    }


    // Choice Functionality //
    //////////////////////////

    // Adds the card to your hand when you select it as your choice
    @Override
    public void onChoseThisOption() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new DarkPower(AbstractDungeon.player, AbstractDungeon.player, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(makeCopy(), true));
    }

    public AbstractCard makeCopy() {
        AbstractCard card = new DarkPowerPower();
        if (this.upgraded)
            card.upgrade();
        return card;
    }
}

/* localization/card text template, goes in Card-Strings.json

  "testMod:LightDarkPower": {
    "NAME": "LightDarkPower",
    "DESCRIPTION": "Choose  [#f2f2f2]LightPower[] or [#a6a6a6]DarkPower[]."
  },
  "testMod:LightPower": {
    "NAME": "LightPowerPower",
    "DESCRIPTION": "Deal !D! Damage."
  },
  "testMod:DarkPower": {
    "NAME": "DarkPowerPower",
    "DESCRIPTION": "Gain !B! Block."
  },
 */