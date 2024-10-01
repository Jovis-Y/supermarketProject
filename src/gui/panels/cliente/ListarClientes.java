package gui.panels.cliente;

import javax.swing.*;

import gui.panels.PainelExibicao;

import java.awt.*;

public class ListarClientes implements PainelExibicao {

    @Override
    public JPanel mostrar() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Listando Clientes...", JLabel.CENTER);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
}
