package services;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import utils.HashingUtil;

public class CadastroService {

    private static final String ARQUIVO_CLIENTE = "usuarios/clientes.txt";
    private static final String ARQUIVO_FUNCIONARIOS = "usuarios/funcionarios.txt";

    public void registrarUsuario(String usuario, String senha, String tipoUsuario) throws IOException {
        String arquivo;

        if (tipoUsuario.equals("cliente")) {
            arquivo = ARQUIVO_CLIENTE;
        } else if (tipoUsuario.equals("funcionario")) {
            arquivo = ARQUIVO_FUNCIONARIOS;
        } else {
            throw new IllegalArgumentException("Tipo de usuário inválido");
        }

        if (usuarioJaRegistrado(usuario, arquivo)) {
            throw new IllegalArgumentException("Nome de usuário já está em uso");
        }

        String hashComSalt = HashingUtil.gerarHashComSalt(senha);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo, true))) {
            writer.write(usuario + ":" + hashComSalt);
            writer.newLine();
        }
    }

    private boolean usuarioJaRegistrado(String usuario, String arquivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(":");
                if (partes[0].equals(usuario)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        CadastroService cadastroService = new CadastroService();
        try {
            cadastroService.registrarUsuario("funcionario3", "123456", "funcionario");
        }
        catch (IOException e) {
            System.out.println("Erro ao registrar usuário: " + e.getMessage());
        }
    }
}
