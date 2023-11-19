import ru.ifmo.se.pokemon.*;
import moves.*
public class Pichu extends Pokemon {
    public Pichu(String name, int lvl) {
        //pokemon_info
        super(name, lvl);
        super.setType(Type.ELECTRIC);
        super.setStats(20, 40, 15, 35, 35, 60);

        //pokemon_moves

        Wild_Charge wild_charge = new Wild_Charge(90, 100);
        Thunderbolt thunderbolt = new Thunderbolt(90, 100);
        super.setMove(wild_charge, thunderbolt);
    }
}
