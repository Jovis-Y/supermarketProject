package services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import utils.HashingUtil;

public class AutenticaçãoService {

    private static final String ARQUIVO_CLIENTES = "usuarios/clientes.txt";
    private static final String ARQUIVO_FUNCIONARIOS = "usuarios/funcionarios.txt";

    public boolean autenticarUsuario(String username, String senha, String tipoUsuario) throws IOException {
        String arquivo;

        if (tipoUsuario.equals("cliente")) {
            arquivo = ARQUIVO_CLIENTES;
        } else if (tipoUsuario.equals("funcionario")) {
            arquivo = ARQUIVO_FUNCIONARIOS;
        } else {
            return false;
        }

        return verificarCredenciais(username, senha, arquivo);
    }

    private boolean verificarCredenciais(String username, String senha, String arquivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(":");

                if (partes.length == 3 && partes[0].equals(username)) {
                    String hashComSaltArmazenado = partes[1] + ":" + partes[2];
    
                    System.out.println(senha);
                    System.out.println(hashComSaltArmazenado);
                    return HashingUtil.validarSenha(senha, hashComSaltArmazenado);
                }
            }
        }
        return false; 
    }
}