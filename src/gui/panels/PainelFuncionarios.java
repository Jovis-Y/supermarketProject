package gui.panels;

import javax.swing.*;
import java.awt.*;

public class PainelFuncionarios implements PainelExibicao {

    @Override
    public JPanel mostrar() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Listando Funcionários...", JLabel.CENTER);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
}
