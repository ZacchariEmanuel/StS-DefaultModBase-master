//Kinda stuck on this one rn
//TODO: Figure out how to make revoke mechanic work

package testMod;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import testMod.cards.AbstractDynamicCard;

public class AbstractSplitChild extends AbstractDynamicCard {
    public static Class<AbstractDynamicCard> ParentType;
    public AbstractSplitChild(String id, String img, int cost, CardType type, CardColor color, CardRarity rarity, CardTarget target,Class<AbstractDynamicCard> ParentType) {
        super(id, img, cost, type, color, rarity, target);
        this.ParentType = ParentType;
    }

    @Override
    public void upgrade() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    public void Revoke() throws InstantiationException, IllegalAccessException {
        if(AbstractDungeon.player.hand.contains(this)){
            AbstractDungeon.player.hand.removeCard(this);
            AbstractDungeon.player.hand.addToHand(ParentType.newInstance());
        }
    }
}
