//Enum que guarda e atribui o valor dos alvos premiados

public enum PREMIOS_JOGO {
    OURO(2000),
    PRATA(1500),
    BRONZE(1000);

    private final int _valor;

    PREMIOS_JOGO(int valor){
        this._valor = valor;
    }
    public int get_valor(){
        return _valor;
    }

}
