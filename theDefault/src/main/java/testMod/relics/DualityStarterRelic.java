package testMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import testMod.DefaultMod;
import testMod.powers.DarkPower;
import testMod.powers.LightPower;
import testMod.util.TextureLoader;

import static testMod.DefaultMod.makeRelicOutlinePath;
import static testMod.DefaultMod.makeRelicPath;

public class DualityStarterRelic extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID(DualityStarterRelic.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("TwinFeathers.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public DualityStarterRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    private int getLightCount(){
        if(AbstractDungeon.player == null || !AbstractDungeon.player.hasPower(LightPower.POWER_ID))
            return 0;
        else
            return AbstractDungeon.player.getPower(LightPower.POWER_ID).amount;
    }
    private int getDarkCount(){
        if(AbstractDungeon.player == null || !AbstractDungeon.player.hasPower(DarkPower.POWER_ID))
            return 0;
        else
            return AbstractDungeon.player.getPower(DarkPower.POWER_ID).amount;
    }

    @Override
    public void onPlayerEndTurn() {
        flash();
        //this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        if(getLightCount() > 0){
            this.addToBot(new DamageAllEnemiesAction((AbstractCreature)null, DamageInfo.createDamageMatrix(getLightCount(), true), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            //this.stopPulse();
        }

        //Block for Dark at end of turn
        /*if(getDarkCount() > 0) {
            this.addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, getDarkCount()));
        }*/
    }

    //Heal for Dark at end of battle
    @Override
    public void onVictory() {
        this.flash();
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractPlayer p = AbstractDungeon.player;
        if (p.currentHealth > 0) {
            p.heal(getDarkCount());
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
            return String.format(DESCRIPTIONS[0], getLightCount(), getDarkCount());
    }

}
