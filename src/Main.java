import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

    boolean novojogo = true;
    String resposta;

    TiroAoAlvo jogo = new TiroAoAlvo();
    jogo.setOnNewScoreListener(new TabelaPontuacao());
    while (novojogo){
        jogo.jogar();
        System.out.println("Deseja jogar outra vez? [Sim][Nao]");
        Scanner scan = new Scanner(System.in);
        resposta = scan.nextLine();
        novojogo =  resposta .equals("Sim") || resposta.equals("sim") || resposta.equals("SIM"); // .equals retorna true/false, se o input for "sim", variavel novo jogo recebe true e cria novo jogo.

    }






    }
}