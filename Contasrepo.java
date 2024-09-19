import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContasRepo {
  
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:contas.db");
    }

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
        return null;
    }

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

    public int apagar(int numero) throws SQLException {
        String sql = "DELETE FROM contas WHERE numero = ?";
        try (Connection conn = this.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, numero);
            return pstmt.executeUpdate();
        }
    }
}
