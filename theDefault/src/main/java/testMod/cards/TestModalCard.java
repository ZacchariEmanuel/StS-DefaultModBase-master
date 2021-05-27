package testMod.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import testMod.DefaultMod;
import testMod.characters.TheDefault;

import java.util.List;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static testMod.DefaultMod.makeCardPath;

public class TestModalCard extends CustomCard implements ModalChoice.Callback
{
    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(TestModalCard.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png"); //Default IMG
    // Use if you have a png for this card //public static final String IMG = makeCardPath("TestBlock.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;
    private static final int COST = -2;
    private static final int UPGRADED_COST = -2;

    // /STAT DECLARATION/

    // MODAL CARD
    private static final String ChoiceAID = DefaultMod.makeID(TestDefend.class.getSimpleName());
    private static final String ChoiceBID = DefaultMod.makeID(TestStrike.class.getSimpleName());
    private ModalChoice modal;

    // /MODAL CARD/



    public TestModalCard()
    {

        super(ID, languagePack.getCardStrings(ID).NAME, IMG, COST, languagePack.getCardStrings(ID).DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        modal = new ModalChoiceBuilder()
                .setCallback(this) // Sets callback of all the below options to this
                .setColor(CardColor.BLUE) // Sets color of any following cards to red
                .addOption(languagePack.getCardStrings(ChoiceAID).NAME,languagePack.getCardStrings(ChoiceAID).DESCRIPTION, CardTarget.NONE)
                .setColor(CardColor.GREEN) // Sets color of any following cards to green
                .addOption(languagePack.getCardStrings(ChoiceBID).NAME,languagePack.getCardStrings(ChoiceBID).DESCRIPTION, CardTarget.NONE)
                .create();
    }

    // Uses the titles and descriptions of the option cards as tooltips for this card
    @Override
    public List<TooltipInfo> getCustomTooltips()
    {
        return modal.generateTooltips();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        modal.open();
    }

    // This is called when one of the option cards us chosen
    @Override
    public void optionSelected(AbstractPlayer p, AbstractMonster m, int i)
    {
        AbstractCard c;
        switch (i) {
            case 0:
                c = CardLibrary.getCard(ChoiceAID).makeCopy();
                c.upgraded = this.upgraded;
                break;
            case 1:
                c = CardLibrary.getCard(ChoiceBID).makeCopy();
                c.name = "Testing Renaming a Card";
                c.upgraded = this.upgraded;
                break;
            default:
                return;
        }

        this.purgeOnUse = true;

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, true));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded) {
            upgradeName();
        }
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new TestModalCard();
    }
}
