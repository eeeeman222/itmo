package moves;

import ru.ifmo.se.pokemon.*;

public class Psychic extends SpecialMove {
    public psychic(double power, double acc) {
        super(Type.PSYCHIC, power, acc);
    }
    @Override
    protected void applyOppEffects(Pokemon opp) {
        Effect ShadowBall_E = new Effect().chance(0.1).turns(1).stat(Stat.DEFENSE, -1);
        opp.addEffect(ShadowBall_E);
    }
    @Override
    protected String describe() {
        return "атакует с помощью Psychic!";
    }
}
