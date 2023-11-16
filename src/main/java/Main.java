import mvc.controller.CurrentSession;
import mvc.view.MainMenuView;


public class Main {
    public static void main(String[] args) {
        CurrentSession.initDatabase();
        new MainMenuView().setVisible(true);
    }
}