import java.util.HashMap;
/*HashMap com capacidade para guardar as pontuações mais altas de múltiplos jogadores usando o nome como Key.
* Funciona como uma tabela de HihgScore*/

public class TabelaPontuacao implements NewScoreListener {
    HashMap<String, Integer> tabelaPontuacao = new HashMap<String, Integer>();


    //Event Handler - Atualiza a tabela de pontuação do jogador caso um novo recorde seja atingido.
    @Override
    public void onNewScore(String nome, int pontos) {
        Integer pontos_atuais = tabelaPontuacao.get(nome);
        System.out.println("\nNovos pontos de " + nome);
        System.out.println("Pontuação atual: " + pontos);
        if (pontos_atuais !=null){
            if(pontos_atuais < pontos){
                tabelaPontuacao.put(nome,pontos);
                System.out.println("Novo recorde para: " + nome + "!");
            }
        }else {tabelaPontuacao.put(nome,pontos);
            System.out.println("Novo recorde para: " + nome + "!");

        }
    }
}
