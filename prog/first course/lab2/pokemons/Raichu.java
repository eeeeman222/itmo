package pokemons;
import ru.ifmo.se.pokemon.*;
import moves.Thunderbolt;
import moves.Wild_Charge;
import moves.Nuzzle;
import moves.Swagger;
public class Raichu extends Pokemon {
    public Raichu(String name, int lvl) {
        super(name, lvl);
        super.setType(Type.ELECTRIC);
        super.setStats(60, 90, 55, 90, 80, 110);
        //pokemon_moves
        Wild_Charge wildcharge = new WildCharge(90, 100);
        Thunderbolt thunderbolt = new Thunderbolt(90, 100);
        Nuzzle nuzzle = new Nuzzle(20, 100);
        Swagger swagger = new Swagger(20, 100);
        super.setMove(wildcharge, thunderbolt, nuzzle, swagger);


    }

}
