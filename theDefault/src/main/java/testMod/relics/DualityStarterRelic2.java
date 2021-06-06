package testMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import testMod.DefaultMod;
import testMod.powers.DarkPower;
import testMod.powers.LightPower;
import testMod.util.TextureLoader;

import static testMod.DefaultMod.makeRelicOutlinePath;
import static testMod.DefaultMod.makeRelicPath;

public class DualityStarterRelic2 extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     */

    // ID, images, text.
    public static final String ID = DefaultMod.makeID(DualityStarterRelic2.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("TwinFeathers.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public DualityStarterRelic2() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new LightPower(AbstractDungeon.player, AbstractDungeon.player, 4), 4));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new DarkPower(AbstractDungeon.player, AbstractDungeon.player, 4), 4));
    }



    // Description
    @Override
    public String getUpdatedDescription() {
            return DESCRIPTIONS[0];
    }

}
