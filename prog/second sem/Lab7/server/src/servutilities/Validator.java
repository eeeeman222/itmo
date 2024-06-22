package servutilities;

import models.Route;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Validator {
    /**
     * Список проверяемых маршрутов
     */
    static List<Route> routes;

    /**
     * Конструктор валидатора.
     */
    public Validator(List<Route> routes) {
        this.routes = routes;
    }
    public static List<Route> validate(){
        HashSet<Long> idSet = new HashSet<>();
        for(Iterator<Route> iterator = routes.iterator(); iterator.hasNext(); ){
            Route r = iterator.next();
            if(r.getName() == null || r.getName().equals("")) {
                iterator.remove();}
            if(r.getDistance() < 2){
                iterator.remove();
            }
            if(r.getCoordinates() == null) {
                iterator.remove();}
            else {
                if(r.getCoordinates().getX() == null){
                    iterator.remove();
                }
                if((r.getCoordinates().getY() == null)||(r.getCoordinates().getY() > 56)){
                    iterator.remove();
                }
            }
            if(r.getFrom() == null) {
                iterator.remove();}
            else if(!r.getFrom().validate()){
                iterator.remove();
            }
            if(r.getTo() != null) {
                if (!r.getTo().validate()) {
                    iterator.remove();
                }
            }
        }
        return routes;
    }
}
