package moves;

import ru.ifmo.se.pokemon.*;

public class DoubleTeam extends StatusMove {
    public doubleTeam(double power, double acc) {
        super(Type.NORMAL, power, acc);
    }
    @Override
    protected void applySelfEffects(Pokemon me) {
        Effect DoubleTeamE = new Effect().chance(1).turns(1).stat(Stat.EVASION, +1);
        me.addEffect(DoubleTeamE);
    }
    @Override
    protected String describe() {
        return "применяет Double Team и повышает уклонение противника.";
    }
}
