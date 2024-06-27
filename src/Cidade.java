// Classe que representa uma cidade
public class Cidade {
    String alfa2;         // Código Alfa-2 do país ao qual a cidade pertence
    String nome;          // Nome da cidade
    String regiao;        // Região em que a cidade está localizada
    double populacao;     // População da cidade
    String coordenadas;   // Coordenadas geográficas da cidade (latitude, longitude)

    // Construtor que inicializa os atributos da cidade com os valores fornecidos
    public Cidade(String alfa2, String nome, String regiao, double populacao, double latitude, double longitude) {
        this.alfa2 = alfa2;
        this.nome = nome;
        this.regiao = regiao;
        this.populacao = populacao;
        // Formata as coordenadas como uma string no formato "(latitude,longitude)"
        this.coordenadas = "(" + latitude + "," + longitude + ")";
    }

    // Método toString para retornar a representação em string do objeto Cidade
    @Override
    public String toString() {
        int populacaoInt = (int) populacao;  // Converte a população para um valor inteiro
        // Retorna uma string com o formato: nome | alfa2 | regiao | populacao | coordenadas
        return nome + " | " + alfa2.toUpperCase() + " | " + regiao + " | " + populacaoInt + " | " + coordenadas;
    }
}
