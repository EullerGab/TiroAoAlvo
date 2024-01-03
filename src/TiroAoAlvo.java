import java.util.Random;
import java.util.Scanner;

public class TiroAoAlvo extends Jogador implements IJogo {

    protected char[][] mapaAlvo;
    private int alvo, rodadas = 5;
    private int alvoOuro, alvoPrata, alvoBronze;
    private int balaPrata = 0;
    Random r = new Random();
    Scanner scan = new Scanner(System.in);


    //region Construtor
    TiroAoAlvo() {

    }
    //endregion

    //region Getter e Setter
    public int getAlvo() {
        return alvo;
    }

    public void setAlvo(int alvo) {
        this.alvo = alvo;
    }

    //endregion

    NewScoreListener listener;

    public void setOnNewScoreListener(NewScoreListener lst) {
        listener = lst;
    }

    @Override //Método que executa o jogo
    public void jogar() {
        rodadas = 5;
        registaJogador();
        geraMapa();
        sorteiaAlvo();
        printMapa();
        atirar();

    }

    @Override
    public void premiar() {
        if (this.alvo == alvoOuro) {
            if (balaPrata == 1) {//Verifica se o jogador esta a usar a bala de prata, caso esteja dobra o valor do premio.
                this.pontos += PREMIOS_JOGO.OURO.get_valor() * 2;
                System.out.println("ALVO OURO ABATIDO!!");
                balaPrata = 2;
            } else {//Caso a bala de prata nao esteja a ser usada, usa o valor do premio normal.
                this.pontos += PREMIOS_JOGO.OURO.get_valor();
                System.out.println("ALVO OURO ABATIDO!!");
            }
        } else if (this.alvo == alvoPrata) {
            if (balaPrata == 1) {
                this.pontos += PREMIOS_JOGO.PRATA.get_valor() * 2;
                System.out.println("ALVO PRATA ABATIDO!!");
                balaPrata = 2;
            } else {
                this.pontos += PREMIOS_JOGO.PRATA.get_valor();
                System.out.println("ALVO PRATA ABATIDO!!");

            }
        } else if (this.alvo == alvoBronze) {
            if (balaPrata == 1) {
                this.pontos += PREMIOS_JOGO.BRONZE.get_valor() * 2;
                System.out.println("ALVO BRONZE ABATIDO!!");
                balaPrata = 2;
            } else {
                this.pontos += PREMIOS_JOGO.BRONZE.get_valor();
                System.out.println("ALVO BRONZE ABATIDO!!");
            }
        } else {
            System.out.println("Alvo vazio");
            balaPrata = 2;
        }

    }

    //region Métodos de jogo

    //Método para escolher os alvo e realizar os tiros.
    public void atirar() {
        do {
            System.out.println("\nBalas no Cartucho: " + rodadas);      //Mostra ao jogador o número de rodadas disponíveis.
            balaPrata();//Executa a mecânica da bala de prata.
            System.out.println("\nEm qual alvo deseja atirar? ");
            int alvo = scan.nextInt();
            if (alvo > 0 && alvo < 10) { //Verifica se o input do jogador é válido (1 a 9)
                this.setAlvo(alvo); //Recebe a posição do alvo que o jogador deseja abater e guarda na variável.
            } else {
                System.out.println("Alvo inválido! Tente outra vez");
                ; //Repõe a jogada perdida caso o jogador insira um alvo inválido
            }
            boolean resAlvo = removerAlvo(this.alvo);//Aplica o alvo desejado ao método remorverAlvo.
            if (resAlvo) {
                premiar();
                rodadas--; //Atualiza o numero de jogadas restantes.
            }
            this.printMapa();       //Printa o mapa atualizando a posiçãodo alvo abatido.
        } while (rodadas > 0);        //Executa o jogo levando em consideração o número de rodadas disponíveis
        balaPrata = 0;
        listener.onNewScore(this.nome, this.pontos);
    }

    //Método substitui o numero alvo do mapa por 'X' indicando que o alvo foi abatido.
    public boolean removerAlvo(int alvo) {
        boolean resultado = false;
        if (this.alvo > 0 && this.alvo < 10) {
            int linha = (alvo - 1) / mapaAlvo[0].length; //alvo-1 ajusta o valor para o index da array que começa em 0, o resultado é dividido pelo comprimento da array (3) e o quociente dessa divisão é o valor da linha.
            int coluna = (alvo - 1) % mapaAlvo[0].length; //alvo-1 ajusta o valor para o index da array que começa em 0, o resultado é dividido pelo comprimento da array (3) e o resto dessa divisão é o valor da coluna.
            resultado = verificaAlvo(linha, coluna);//True = alvo pode ser abatido | False = alvo já previamente abatido
            this.mapaAlvo[linha][coluna] = 'X'; // Caso true, modifica o valor do alvo para 'X', indicando que o alvo foi abatido
        }
        return resultado;
    }

    //Método verifica se o alvo escolhido já foi previamente abatido
    public boolean verificaAlvo(int linha, int coluna) {
        if (this.mapaAlvo[linha][coluna] == 'X') {    //Char 'X' sinaliza que o alvo ja foi abatido
            System.out.println("\n-------------------------------------");
            System.out.println(" Alvo já abatido! Escolha outro alvo");
            System.out.println("-------------------------------------");
            return false; //Retorna false se o alvo foi previamente abatido.
        } else {
            System.out.println("\n---------------");
            System.out.println("Alvo " + this.alvo + " abatido!");    //Informa ao jogador qual o alvo abatido com sucesso.
            System.out.println("---------------");
            return true;//Retorna true se for possível abater o alvo.
        }
    }

    public void balaPrata() {
        String resposta;
        if (balaPrata == 0 && rodadas != 1) {
            System.out.println("\nUsar Bala de Prata? [Sim][Nao]");
            resposta = scan.next();
            if (resposta.equals("Sim") || resposta.equals("sim") || resposta.equals("SIM")) {
                rodadas--;
                balaPrata = 1;
            }
        }
    }


    //endregion


    //region Mapa de Jogo
    public void geraMapa() {
        this.mapaAlvo = new char[][]{{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', '9'}};
    }

    //Printa o mapa de alvos na consola.
    public void printMapa() {
        System.out.println();
        mostraPontos();
        System.out.print("\n||ALVOS||");
        for (int linha = 0; linha < this.mapaAlvo.length; linha++) { //Percorre as linhas da matriz
            System.out.println();
            for (int coluna = 0; coluna < this.mapaAlvo.length; coluna++) {//Percorre as colunas da matriz de acordo com as linhas e printa o seu conteudo.
                System.out.print(" " + this.mapaAlvo[linha][coluna] + " ");
            }
        }

    }

    //Método gera os alvos premiados do mapa.
    public void sorteiaAlvo() {
        alvoOuro = r.nextInt(1, 10);//Alvo premiado é dado por um número aletório de 1 a 9.
        do {
            alvoPrata = r.nextInt(1, 10);
        } while (alvoOuro == alvoPrata);//Lógica garante que não haja alvos com múltiplas premiações.
        do {
            alvoBronze = r.nextInt(1, 10);
        } while (alvoBronze == alvoOuro || alvoBronze == alvoPrata);//Lógica garante que não haja alvos com múltiplas premiações.

    }
    //endregion

}
