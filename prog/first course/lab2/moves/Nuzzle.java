package moves;
import ru.ifmo.se.pokemon.*;
public class Nuzzle extends PhysicalMove{
    public Nuzzle(int power, int accuracy) {
        super(Type.ELECTRIC, power, accuracy);
    }
    protected void applyOppEffects(Pokemon opp) {
        Effect.paralyze(opp);
    }
    @Override
    public String describe() {
        return "атакует с помощью Nuzzle!";
    }
}
