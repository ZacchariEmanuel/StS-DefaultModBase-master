package testMod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.SmokePuffEffect;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import testMod.DefaultMod;
import testMod.effects.DodgedWordEffect;
import testMod.util.TextureLoader;

import static testMod.DefaultMod.makePowerPath;

public class SmokePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID(SmokePower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    static final UIStrings PlayerUIStrings = CardCrawlGame.languagePack.getUIString("AbstractPlayer");
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84_Smoke.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32_Smoke.png"));

    public SmokePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = String.format(DESCRIPTIONS[0],amount) ;
    }

    @Override
    public AbstractPower makeCopy() {
        return new SmokePower(owner, source, amount);
    }



    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount <= this.amount) {
            damageAmount = 0;
            this.flash();
            AbstractDungeon.effectList.add(new DodgedWordEffect(AbstractDungeon.player, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, PlayerUIStrings.TEXT[0]));
            //AbstractDungeon.effectList.add(new SmokePuffEffect(AbstractDungeon.player.drawX,AbstractDungeon.player.drawY));
        }
        return damageAmount;
    }

    @Override
    public void atStartOfTurn() {
        addToBot((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        this.amount = 0;
    }



}
