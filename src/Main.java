import java.util.Scanner;

import controllers.ProdutoController;
import controllers.CaixaController;
import services.AutenticaçãoService;
import services.CadastroService;
import gui.LoginScreen;
import gui.DashboardScreen;

import models.Produto;

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
            // Cria e exibe a tela de produtos
            
        }

    }
}

