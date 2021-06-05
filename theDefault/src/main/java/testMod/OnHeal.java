package testMod;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;

public interface OnHeal {
    public void triggerOnHeal(int amount);
}
