package mvc.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class CurrentSession {
    private static EntityManagerFactory entityManagerFactory;
    private static CurrentSession instance;

    public CurrentSession(){}

    public static CurrentSession getInstance() {
        if (instance == null) {
            instance = new CurrentSession();
        }
        return instance;
    }

    public static void initDatabase() {
        entityManagerFactory = Persistence.createEntityManagerFactory("EntityManager");
    }

    public EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
}
