// Classe que representa o resultado de uma operação ou comando
public class Result {
    boolean success;    // Indica se a operação foi bem-sucedida
    String error;       // Mensagem de erro, caso a operação falhe
    String result;      // Resultado da operação, caso tenha sido bem-sucedida

    // Construtor que inicializa os atributos com os valores fornecidos
    public Result(boolean success, String error, String result) {
        this.success = success;
        this.error = error;
        this.result = result;
    }

    // Construtor padrão sem parâmetros
    public Result() {
    }
}
