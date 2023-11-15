package mvc.controller;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import mvc.view.MainMenuView;


public class Main {
    public static EntityManagerFactory session;

    public static void main(String[] args) {
        session = Persistence.createEntityManagerFactory("EntityManager");
        new MainMenuView().setVisible(true);
    }
}