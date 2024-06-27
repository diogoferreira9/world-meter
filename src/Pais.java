// Classe que representa um país
class Pais {
    int id;         // Identificador único do país
    String nome;    // Nome do país
    String alfa2;   // Código Alfa-2 do país
    String alfa3;   // Código Alfa-3 do país

    // Construtor que inicializa os atributos do país com os valores fornecidos
    public Pais(int id, String alfa2, String alfa3, String nome) {
        this.id = id;
        this.alfa2 = alfa2;
        this.alfa3 = alfa3;
        this.nome = nome;
    }

    // Construtor padrão sem parâmetros
    public Pais() {
    }

    // Método toString para retornar a representação em string do objeto Pais
    @Override
    public String toString() {
        if (id <= 700) {
            // Retorna a representação padrão se o id for menor ou igual a 700
            return nome + " | " + id + " | " + alfa2.toUpperCase() + " | " + alfa3.toUpperCase();
        } else {
            // Conta o número de ocorrências deste país na lista de populações
            int contagemOcorrencias = 0;
            for (Populacao populacao : Main.populacaoT) {
                if (populacao.id == id) {
                    contagemOcorrencias++;
                }
            }
            // Retorna a representação com o número de ocorrências se o id for maior que 700
            return nome + " | " + id + " | " + alfa2.toUpperCase() + " | " + alfa3.toUpperCase() + " | "
                    + contagemOcorrencias;
        }
    }
}
