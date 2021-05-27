package testMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
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

import java.util.ArrayList;
import java.util.Iterator;

import static testMod.DefaultMod.makeCardPath;

public class TestModalCard3 extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(TestModalCard3.class.getSimpleName());
    public static final String IMG = makeCardPath("TestImage.png"); //Default IMG

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = -2;
    private static final int UPGRADED_COST = -2;
    // /STAT DECLARATION/

    public TestModalCard3() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.purgeOnUse = true;
    }
    
    //CHOICE CARD FUNCTIONALITY 

    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> stanceChoices = new ArrayList();
        stanceChoices.add(new ChooseLeft());
        stanceChoices.add(new ChooseRight());
        if (this.upgraded) {
            Iterator var4 = stanceChoices.iterator();

            while(var4.hasNext()) {
                AbstractCard c = (AbstractCard)var4.next();
                c.upgrade();
            }
        }

        this.addToBot(new ChooseOneAction(stanceChoices));
    }
    

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
        }
    }

    public AbstractCard makeCopy() {
        return new TestModalCard3();
    }
}



class ChooseLeft extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(ChooseLeft.class.getSimpleName());
    public static final String IMG = makeCardPath("TestImage.png");
    // Use if you have a png for this card //public static final String IMG = makeCardPath("ChooseLeft.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 3;

    // /STAT DECLARATION/


    public ChooseLeft() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    public void onChoseThisOption() {
        AbstractCard c = makeCopy();
        if(upgraded) c.upgrade();

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, true));
    }

    public AbstractCard makeCopy() {
        return new ChooseLeft();
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
}

class ChooseRight extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(ChooseRight.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png"); //Default IMG
    // Use if you have a png for this card //public static final String IMG = makeCardPath("ChooseRight.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;

    // /STAT DECLARATION/


    public ChooseRight() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }

    // Adds the card to your hand when you select it as an option
    @Override
    public void onChoseThisOption() {
        AbstractCard c = makeCopy();
        if(upgraded) c.upgrade();

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, true));
    }

    public AbstractCard makeCopy() {
        return new ChooseRight();
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

/*
  "testMod:TestModalCard3": {
    "NAME": "Test Modal Card 3",
    "DESCRIPTION": "Choose  [#f2f2f2]left[] or [#242424]right[]."
  },
  "testMod:ChooseLeft": {
    "NAME": "Choose Left",
    "DESCRIPTION": "Deal !D! Damage."
  },
  "testMod:ChooseRight": {
    "NAME": "Choose Right",
    "DESCRIPTION": "Gain !B! Block."
  },
 */