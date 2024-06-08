package servmanagers;

import models.Route;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import static java.lang.Math.max;


public class CollectionManager {
    private int currentId = 1;
    private final Map<Integer, Route> routes = new HashMap<>();
    private LinkedList<Route> collection = new LinkedList<Route>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;

    private final ParseManager parseManager;

    public CollectionManager(ParseManager parseManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.parseManager = parseManager;
    }

    /**
     * @return Последнее время инициализации.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Последнее время сохранения.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return коллекция.
     */
    public LinkedList<Route> getCollection() {
        return collection;
    }

    public void setCollection(LinkedList<Route> a) {
        this.collection = a;
    }

    /**
     * Получить Route по ID
     */
    public Route byId(int id) { return routes.get(id); }

    /**
     * Содержит ли колекции Route
     */
    public boolean isContain(Route e) { return e == null || byId((int) e.getId()) != null; }

    /**
     * Получить свободный ID
     */
    public long getFreeId() {
        while (byId(++currentId) != null);
        return currentId;
    }

    /**
     * Добавляет Route
     */
    public int add(Route a) {
        if (isContain(a)) return (int) a.getId();
        routes.put((int) a.getId(), a);
        collection.add(a);
        update();
        return (int) a.getId();
    }


    /**
     * Обновляет Route
     */
    public void updateById(Route a, int id) {
        if (!isContain(a)) return;
        remove(id);
        routes.put((Integer) id, a);
        collection.add(a);
        update();
    }

    /**
     * Удаляет Route по ID
     */
    public void remove(long id) {
        var a = byId((int) id);
        if (a == null) return;
        routes.remove(a.getId());
        collection.remove(a);
        update();
    }

    /**
     * Фиксирует изменения коллекции
     */
    public void update() {
        Collections.sort(collection);
    }

    public void init() {
        collection.clear();
        routes.clear();
        collection = parseManager.readCollection();
        lastInitTime = LocalDateTime.now();
        for (var e : collection)
            if (byId((int) e.getId()) != null) {
                collection.clear();
                routes.clear();
                return;
            } else {
                if (e.getId()>currentId) currentId = (int) e.getId();
                routes.put((int) e.getId(), e);
            }
        update();
    }

    /**
     * Сохраняет коллекцию в файл
     */
    public void save() throws IOException {
        parseManager.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     *
     * @return максимальную distance в коллекции
     */
    public int getMaxDistance(){
        int distance = 0;
        for(Route a : collection){
            distance = max(distance, a.getDistance());
        }
        return distance;
    }

    /**
     *
     * @return string
     */
    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (var Route : collection) {
            info.append(Route+"\n\n");
        }
        return info.toString().trim();
    }
} 