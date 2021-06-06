package testMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import testMod.DefaultMod;
import testMod.powers.DarkPower;
import testMod.powers.LightPower;
import testMod.util.ModUtil;
import testMod.util.TextureLoader;

import static testMod.DefaultMod.makeRelicOutlinePath;
import static testMod.DefaultMod.makeRelicPath;

public class WhiteFeather extends CustomRelic {


    public static final String ID = DefaultMod.makeID(WhiteFeather.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("feather.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("feather_outline.png"));

    public WhiteFeather() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }


    @Override
    public void onPlayerEndTurn() {
        flash();
        //this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        if(ModUtil.getLightCount() > 0){
            this.addToBot(new DamageAllEnemiesAction((AbstractCreature)null, DamageInfo.createDamageMatrix(ModUtil.getLightCount(), true), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            //this.stopPulse();
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
            return DESCRIPTIONS[0];
    }

}
