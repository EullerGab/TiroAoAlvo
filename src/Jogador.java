import java.util.Scanner;

public class Jogador {
    protected  String nome;
    protected int pontos;
    Scanner scan = new Scanner(System.in);

    //region Construtor
     Jogador() {
    }
    //endregion


    //region Getter e Setter
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
    //endregion

    public void registaJogador(){
        System.out.println("Insira o nome do jogador: ");
       this.nome = scan.nextLine();
        this.pontos = 0;
        System.out.println("Jogador: "+this.nome);
    }

    public void mostraPontos(){
        System.out.printf("Pontuação: %d",this.getPontos());
    }
}
