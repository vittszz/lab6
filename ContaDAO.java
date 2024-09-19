import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContaDAO {

    // Método para conectar ao banco de dados SQLite
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:contas.db");
    }

    // Método para criar uma nova conta no banco de dados
    public int criar(Conta conta) throws SQLException {
        String sql = "INSERT INTO contas(numero, titular, saldo) VALUES(?, ?, ?)";
        try (Connection conn = this.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, conta.getNumero());
            pstmt.setString(2, conta.getTitular());
            pstmt.setDouble(3, conta.getSaldo());
            return pstmt.executeUpdate();
        }
    }

    // Método para ler todas as contas do banco de dados
    public List<Conta> lerTodas() throws SQLException {
        List<Conta> contas = new ArrayList<>();
        String sql = "SELECT numero, titular, saldo FROM contas";
        try (Connection conn = this.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Conta conta = new Conta(rs.getInt("numero"), rs.getString("titular"), rs.getDouble("saldo"));
                contas.add(conta);
            }
        }
        return contas;
    }

    // Método para ler uma conta específica pelo número
    public Conta lerConta(int numero) throws SQLException {
        String sql = "SELECT numero, titular, saldo FROM contas WHERE numero = ?";
        try (Connection conn = this.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, numero);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Conta(rs.getInt("numero"), rs.getString("titular"), rs.getDouble("saldo"));
            }
        }
        return null;  // Caso a conta não seja encontrada
    }

    // Método para atualizar os dados de uma conta no banco de dados
    public int atualizar(Conta conta) throws SQLException {
        String sql = "UPDATE contas SET titular = ?, saldo = ? WHERE numero = ?";
        try (Connection conn = this.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, conta.getTitular());
            pstmt.setDouble(2, conta.getSaldo());
            pstmt.setInt(3, conta.getNumero());
            return pstmt.executeUpdate();
        }
    }

    // Método para apagar uma conta pelo número
    public int apagar(int numero) throws SQLException {
        String sql = "DELETE FROM contas WHERE numero = ?";
        try (Connection conn = this.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, numero);
            return pstmt.executeUpdate();
        }
    }
}
