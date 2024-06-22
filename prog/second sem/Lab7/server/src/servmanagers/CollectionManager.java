package servmanagers;

import models.Route;
import models.User;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Math.max;
import static net.UDPDatagramServer.logger;

public class CollectionManager {
    private int currentId = 1;

    public static boolean authoriz;
    public static int user_id;
    public static boolean pass;

    public List<User> users = Collections.synchronizedList(new LinkedList<>());
    private final Map<Integer, Route> routes = new HashMap<>();
    public List<Route> collection = Collections.synchronizedList(new LinkedList<>());
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;

    private JDBCManager JDBCManager = new JDBCManager();
    long idMax;

    public CollectionManager() {
        this.lastInitTime = null;
        this.lastSaveTime = null;
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
    public List<Route> getCollection() {
        return collection;
    }

    public List<User> getUsers() {
        return users;
    }

    public Map<Integer, Route> getRoutes() {return routes;}

    public void setCollection(LinkedList<Route> a) {
        this.collection = Collections.synchronizedList(a);
    }

    /**
     * Получить Route по ID
     */
    public Route byId(int id) { return routes.get(id); }

    /**
     * Содержит ли коллекции Route
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
        synchronized (collection) {
            if (isContain(a)) return (int) a.getId();
            routes.put((int) a.getId(), a);
            collection.add(a);
            update();
            return (int) a.getId();
        }
    }

    public void insert(Route a) {
        synchronized (collection) {
            if (!isContain(a)) {
                routes.put((int) a.getId(), a);
                collection.add(a);
                update();
            }
        }
    }


    public int regInManager(String user, String password) {
        synchronized (users) {
            for (User user1: users) {
                idMax = max(idMax, user1.getId());
                if ((user1.getName()).equals(user)) {
                    authoriz = false;
                    user_id = 1;
                    return -1;
                }
            }
            String salt = generateSalt();
            authoriz = true;
            user_id = 1;
            idMax += 1;
            JDBCManager.addUser((int) idMax, user, password, salt);
            users.add(new User((int) idMax, user, password, salt));
            return (int) idMax;
        }
    }

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Обновляет Route
     */
    public void updateById(Route a, int id) {
        synchronized (collection) {
            if (!isContain(a)) return;
            remove(id);
            routes.put(id, a);
            collection.add(a);
            update();
        }
    }

    /**
     * Удаляет Route по ID
     */
    public void remove(long id) {
        synchronized (collection) {
            var a = byId((int) id);
            if (a == null) return;
            routes.remove(a.getId());
            collection.remove(a);
            update();
        }
    }

    /**
     * Фиксирует изменения коллекции
     */
    public void update() {
        synchronized (collection) {
            Collections.sort(collection);
        }
    }

    /**
     * @return максимальную distance в коллекции
     */
    public int getMaxDistance() {
        synchronized (collection) {
            int distance = 0;
            for(Route a : collection) {
                distance = max(distance, a.getDistance());
            }
            return distance;
        }
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        synchronized (collection) {
            if (collection.isEmpty()) return "Коллекция пуста!";

            StringBuilder info = new StringBuilder();
            for (Object Route : collection) {
                info.append(Route).append("\n\n");
            }
            return info.toString().trim();
        }
    }
}
