// Classe que representa os dados de população de um país para um determinado ano
public class Populacao {
    int id;             // Identificador do país
    int ano;            // Ano dos dados de população
    int populacaoM;     // População masculina
    int populacaoF;     // População feminina
    Double densidade;   // Densidade populacional

    // Construtor que inicializa os atributos da população com os valores fornecidos
    public Populacao(int id, int ano, int populacaoM, int populacaoF, Double densidade) {
        this.id = id;
        this.ano = ano;
        this.populacaoM = populacaoM;
        this.populacaoF = populacaoF;
        this.densidade = densidade;
    }
}
