package moves;
import ru.ifmo.se.pokemon.*;
public class WildCharge extends PhysicalMove{
    public wildCharge(double power, double acc){
        super(Type.ELECTRIC, power, acc);
    }
    @Override
    protected double calcBaseDamage(Pokemon pokemon, Pokemon pokemon1) {
        return super.calcBaseDamage(pokemon, pokemon1);
    }
    protected void applySelfDamage(Pokemon me, double damage) {
        me.setMod(Stat.HP, (int) Math.round(damage * 0.5));
    }
    @Override
    protected java.lang.String describe() {
        return "использует Wild Charge!!!;";
    }
}
