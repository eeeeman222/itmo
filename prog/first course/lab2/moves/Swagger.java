package moves;
import ru.ifmo.se.pokemon.*;
public class Swagger extends StatusMove {
    public Swagger(int power, int acc) {
        super(Type.NORMAL, power, acc);
    }
    protected void applyOppEffects(Pokemon opp) {
        Effect.confuse(opp);
        Effect Swagger_E = new Effect().chance(1).turns(2).stat(Stat.ATTACK, -1);
    }
    @Override
    protected java.lang.String describe() {
        return "использует Swagger!!!";
    }
}
