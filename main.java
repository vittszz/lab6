import java.sql.*;
import java.util.List;
import static java.lang.System.out;
import java.util.Scanner;

public class Main {
  static Scanner leitor = new Scanner(System.in);

  public static void main(String[] args) throws SQLException {
    ContasRepo repo = new ContasRepo(); 
    int opcao = -1;

    while (opcao != 0) {
      out.println("\nMenu:");
      out.println("1 - Criar nova Conta");
      out.println("2 - Ler todas as Contas");
      out.println("3 - Ler dados de uma Conta");
      out.println("4 - Alterar os dados de uma Conta");
      out.println("5 - Apagar uma Conta");
      out.println("0 - Sair");
      out.println("Escolha uma opção: ");
      opcao = Integer.parseInt(leitor.nextLine());

      switch (opcao) {
        case 1:
          out.println("Número da conta: ");
          int numero = Integer.parseInt(leitor.nextLine());
          
          out.println("Titular: ");
          String titular = leitor.nextLine();
          
          out.println("Saldo: ");
          double saldo = Double.parseDouble(leitor.nextLine());

          Conta conta = new Conta(numero, titular, saldo);
          int n = repo.criar(conta);
          out.println("Registros inseridos: " + n);
          break;

        case 2:
          List<Conta> contas = repo.lerTodas();
          for (Conta c : contas) {
            out.println("# A conta " + c.getNumero() + " pertence a " + c.getTitular() + " e tem R$ " + c.getSaldo());
          }
          break;

        case 3:
          out.println("Número da conta para busca: ");
          int numBusca = Integer.parseInt(leitor.nextLine());
          Conta contaBusca = repo.lerConta(numBusca);
          if (contaBusca != null) {
            out.println("# A conta " + contaBusca.getNumero() + " pertence a " + contaBusca.getTitular() + " e tem R$ " + contaBusca.getSaldo());
          } else {
            out.println("Conta não encontrada.");
          }
          break;

        case 4:
          out.println("Número da conta para alterar: ");
          int numAlterar = Integer.parseInt(leitor.nextLine());
          Conta contaExistente = repo.lerConta(numAlterar);
          if (contaExistente != null) {
            out.println("Novo titular: ");
            String novoTitular = leitor.nextLine();
            out.println("Novo saldo: ");
            double novoSaldo = Double.parseDouble(leitor.nextLine());
            Conta contaAlterada = new Conta(numAlterar, novoTitular, novoSaldo);
            int registrosAlterados = repo.atualizar(contaAlterada);
            out.println("Registros alterados: " + registrosAlterados);
          } else {
            out.println("Conta não encontrada.");
          }
          break;

        case 5:
          out.println("Número da conta para apagar: ");
          int numApagar = Integer.parseInt(leitor.nextLine());
          int registrosApagados = repo.apagar(numApagar);
          out.println("Registros apagados: " + registrosApagados);
          break;

        case 0:
          out.println("Saindo...");
          break;

        default:
          out.println("Opção inválida. Tente novamente.");
      }
    }
  }
}
