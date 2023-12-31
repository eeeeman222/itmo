package pokemons;
import ru.ifmo.se.pokemon.*;
import moves.Thunderbolt;
import moves.Wild_Charge;
import moves.Nuzzle;
public class Pikachu extends Pokemon {
    public pikachu(String name, int lvl) {
        //pokemon_info
        super(name, lvl);
        super.setType(Type.ELECTRIC);
        super.setStats(35, 55, 40, 50, 50, 90);

        //pokemon_moves

        WildCharge wildcharge = new WildCharge(90, 100);
        Thunderbolt thunderbolt = new Thunderbolt(90, 100);
        Nuzzle nuzzle = new Nuzzle(20, 100);
        super.setMove(wild_charge, thunderbolt, nuzzle);
    }
}
