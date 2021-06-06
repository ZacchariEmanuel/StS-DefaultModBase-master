package testMod.cards;


import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import testMod.DefaultMod;
import testMod.cards.AbstractDynamicCard;
import testMod.characters.TheDefault;
import testMod.powers.DarkPower;

import static testMod.DefaultMod.makeCardPath;

public class DarkBlock extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(DarkBlock.class.getSimpleName());
    public static final String IMG = makeCardPath("DarkBlock.png"); //Default IMG

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int BLOCK = 0;
    private static final int MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;


    // /STAT DECLARATION/


    public DarkBlock() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        block = baseBlock = BLOCK;
        magicNumber = baseMagicNumber = MAGIC;

    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = block;
        if(p.hasPower(DarkPower.POWER_ID))
            effect += p.getPower(DarkPower.POWER_ID).amount * magicNumber;

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, effect));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);

            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
