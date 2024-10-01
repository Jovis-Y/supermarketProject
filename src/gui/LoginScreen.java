package gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import services.AutenticaçãoService;
import java.io.IOException;

public class LoginScreen extends JFrame {

    
    private JTextField userField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;
    private AutenticaçãoService authService;
    private boolean loginSucess = false;
    private boolean isAdmin = false;
    private boolean isClient = false;

    public LoginScreen() {
        
        authService = new AutenticaçãoService();

        
        setTitle("Supermarket Login");
        setSize(400, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Margem interna
        panel.setBackground(new Color(245, 245, 245)); // Cor de fundo

        
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font buttonFont = new Font("Arial", Font.PLAIN, 14);
        Color primaryColor = new Color(60, 63, 65); // Cor primária para o texto
        Color secondaryColor = new Color(0, 120, 215); // Cor para botões e destaques

        
        JLabel userLabel = new JLabel("Usuário:");
        userLabel.setFont(labelFont);
        userLabel.setForeground(primaryColor);
        panel.add(userLabel);

        userField = new JTextField(15);
        userField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        userField.setFont(new Font("Arial", Font.PLAIN, 14));
        userField.setBorder(BorderFactory.createLineBorder(secondaryColor, 2));
        panel.add(userField);

        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Espaço entre os componentes

        
        JLabel passwordLabel = new JLabel("Senha:");
        passwordLabel.setFont(labelFont);
        passwordLabel.setForeground(primaryColor);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(15);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createLineBorder(secondaryColor, 2));
        panel.add(passwordField);

        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Espaço entre os componentes

        
        loginButton = new JButton("Login");
        loginButton.setFont(buttonFont);
        loginButton.setBackground(secondaryColor);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        panel.add(loginButton);

        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Espaço entre os componentes

        
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(messageLabel);

        
        loginButton.addActionListener(new LoginAction());

       
        add(panel);
    }


    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String user = userField.getText();
            String password = new String(passwordField.getPassword());

            try {
                
                if (authService.autenticarUsuario(user, password, "funcionario")) {
                    messageLabel.setText("Login como Administrador bem-sucedido!");
                    messageLabel.setForeground(Color.GREEN);
                    
                    loginSucess = true;
                    isAdmin = true;
                    dispose();
                }

                else if (authService.autenticarUsuario(user, password, "cliente")) {
                    messageLabel.setText("Login como Cliente bem-sucedido!");
                    messageLabel.setForeground(Color.BLUE);

                    loginSucess = true;
                    isClient = true;
                    dispose();
                }
                
                else {
                    messageLabel.setText("Usuário ou senha incorretos.");
                    messageLabel.setForeground(Color.RED);
                }
            } catch (IOException ex) {
                messageLabel.setText("Erro de autenticação: " + ex.getMessage());
                messageLabel.setForeground(Color.RED);
            }
        }
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isClient() {
        return isClient;
    }

    public boolean isLoginSucess() {
        return loginSucess;
    }
    
    public static void main(String[] args) {
        
        
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.setVisible(true);


        while (!loginScreen.loginSucess) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
