package pokemons;
import moves.Thunderbolt;
import ru.ifmo.se.pokemon.*;
import moves.Confide;
import moves.Sweet_Scent;
import moves.Double_team;

public class Surskit extends Pokemon {
    public Surskit(String name, int lvl) {
        //pokemon_info
        super(name, lvl);
        super.setType(Type.BUG, Type.WATER);
        super.setStats(40, 30, 32, 50, 52, 65);

        //pokemon_moves

        Confide confide = new Confide(0,100);
        Sweet_Scent sweet_scent = new Sweet_Scent(0, 100);
        Double_team double_team = new Double_team(0, 100);
        super.setMove(sweet_scent, confide, double_team);
    }

}
