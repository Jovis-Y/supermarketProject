package gui.panels.funcionario;

import javax.swing.*;

import gui.panels.PainelExibicao;

import java.awt.*;

public class ListarFuncionarios implements PainelExibicao {

    @Override
    public JPanel mostrar() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Listando Funcionários...", JLabel.CENTER);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
}
