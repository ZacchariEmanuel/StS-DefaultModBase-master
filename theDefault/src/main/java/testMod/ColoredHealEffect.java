package testMod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.HealNumberEffect;

public class ColoredHealEffect extends AbstractGameEffect {
    private static final float X_JITTER;
    private static final float Y_JITTER;
    private static final float OFFSET_Y;

    public ColoredHealEffect(float x, float y, int amount,Color color1, Color color2) {
        int roll = MathUtils.random(0, 2);
        if (roll == 0) {
            CardCrawlGame.sound.play("HEAL_1");
        } else if (roll == 1) {
            CardCrawlGame.sound.play("HEAL_2");
        } else {
            CardCrawlGame.sound.play("HEAL_3");
        }

        AbstractDungeon.effectsQueue.add(new HealNumberEffect(x, y, amount));

        for (int i = 0; i < 18; ++i) {
            AbstractDungeon.effectsQueue.add(new ColoredHealVerticalLineEffect(x + MathUtils.random(-X_JITTER * 1.5F, X_JITTER * 1.5F), y + OFFSET_Y + MathUtils.random(-Y_JITTER, Y_JITTER),color1, color2));
        }

    }

    public void update() {
        this.isDone = true;
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }

    static {
        X_JITTER = 120.0F * Settings.scale;
        Y_JITTER = 120.0F * Settings.scale;
        OFFSET_Y = -50.0F * Settings.scale;
    }
}

class ColoredHealVerticalLineEffect extends AbstractGameEffect{

        private float x;
        private float y;
        private float vY;
        private float staggerTimer;
        private TextureAtlas.AtlasRegion img;
        private TextureAtlas.AtlasRegion img2;

        public ColoredHealVerticalLineEffect(float x, float y,Color color1, Color color2) {
            this.img = ImageMaster.STRIKE_LINE;
            this.img2 = ImageMaster.STRIKE_LINE_2;
            this.duration = MathUtils.random(0.6F, 1.3F);
            this.startingDuration = this.duration;
            this.x = x;
            this.y = y;
            this.staggerTimer = MathUtils.random(0.0F, 0.5F);
            float tmp = MathUtils.random(5.0F, 20.0F);
            this.vY = tmp * tmp * Settings.scale;
            this.rotation = 90.0F;
            if (MathUtils.randomBoolean()) {
                this.color = color1.cpy();
            } else {
                this.color = color2;
            }

            this.color.a = 0.0F;
            this.renderBehind = MathUtils.randomBoolean(0.3F);
        }

        public void update() {
            if (this.staggerTimer > 0.0F) {
                this.staggerTimer -= Gdx.graphics.getDeltaTime();
            } else {
                this.y += this.vY * Gdx.graphics.getDeltaTime();
                this.scale = Settings.scale * this.duration / this.startingDuration;
                this.duration -= Gdx.graphics.getDeltaTime();
                Color var10000;
                if (this.duration / this.startingDuration > 0.5F) {
                    this.color.a = 1.0F - this.duration / this.startingDuration;
                    var10000 = this.color;
                    var10000.a += MathUtils.random(0.0F, 0.2F);
                } else {
                    this.color.a = this.duration / this.startingDuration;
                    var10000 = this.color;
                    var10000.a += MathUtils.random(0.0F, 0.2F);
                }

                if (this.duration < 0.0F) {
                    this.isDone = true;
                    this.color.a = 0.0F;
                }

            }
        }

        public void render(SpriteBatch sb) {
            if (!(this.staggerTimer > 0.0F)) {
                sb.setBlendFunction(770, 1);
                sb.setColor(this.color);
                sb.draw(this.img, this.x - (float)this.img.packedWidth / 2.0F, this.y - (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth / 2.0F, (float)this.img.packedHeight / 2.0F, (float)this.img.packedWidth, (float)this.img.packedHeight, this.scale * MathUtils.random(0.7F, 2.0F), this.scale * 0.8F, this.rotation + MathUtils.random(-2.0F, 2.0F));
                sb.setColor(new Color(1.0F, 1.0F, 0.7F, this.color.a));
                sb.draw(this.img2, this.x - (float)this.img2.packedWidth / 2.0F, this.y - (float)this.img2.packedHeight / 2.0F, (float)this.img2.packedWidth / 2.0F, (float)this.img2.packedHeight / 2.0F, (float)this.img2.packedWidth, (float)this.img2.packedHeight, this.scale * 1.5F, this.scale * MathUtils.random(1.2F, 2.4F), this.rotation);
                sb.draw(this.img2, this.x - (float)this.img2.packedWidth / 2.0F, this.y - (float)this.img2.packedHeight / 2.0F, (float)this.img2.packedWidth / 2.0F, (float)this.img2.packedHeight / 2.0F, (float)this.img2.packedWidth, (float)this.img2.packedHeight, this.scale * 1.5F, this.scale * MathUtils.random(1.2F, 2.4F), this.rotation);
                sb.setBlendFunction(770, 771);
            }
        }

        public void dispose() {
        }
}
