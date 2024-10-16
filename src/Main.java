import gui.LoginScreen;
import gui.DashboardScreen;
import gui.DashboardCliente;

public class Main {
    public static void main(String[] args) {
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.setVisible(true);

        while (!loginScreen.isLoginSucess()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (loginScreen.isAdmin()) {
            DashboardScreen dashboardScreen = new DashboardScreen();
            dashboardScreen.setVisible(true);
        } else if (loginScreen.isClient()) {
            String usuario = loginScreen.getUsuario(); // Supondo que você tenha um método getUsuario() na LoginScreen
            DashboardCliente dashboardCliente = new DashboardCliente(usuario);
            dashboardCliente.setVisible(true);
        }
    }
}