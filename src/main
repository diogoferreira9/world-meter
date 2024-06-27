import java.io.*;
import java.util.*;

public class Main {
    // Lista para armazenar todos os países
    static ArrayList<Pais> paisesT = new ArrayList<>();

    // Lista para armazenar todas as cidades
    static ArrayList<Cidade> cidadesT = new ArrayList<>();

    // Lista para armazenar entradas inválidas
    static ArrayList<InputInvalido> inputsInvalidos = new ArrayList<>();

    // Lista para armazenar dados de população
    static ArrayList<Populacao> populacaoT = new ArrayList<>();

    // Lista para armazenar códigos Alfa-2 dos países
    static ArrayList<String> codigosAlfa2Paises = new ArrayList<>();

    // Mapa para armazenar a relação entre o ID do país e seu código Alfa-2
    static HashMap<Integer, String> paisesHm = new HashMap<>();

    // Mapa para armazenar a relação entre o código Alfa-2 do país e o objeto Pais
    static HashMap<String, Pais> alfa2Pais = new HashMap<>();

    // Mapa para armazenar a relação entre o nome do país e o objeto Pais
    static HashMap<String, Pais> nomePorPais = new HashMap<>();

    // Mapa para armazenar a relação entre o ID do país e seu nome
    static HashMap<Integer, String> idPorNomePais = new HashMap<>();

    // Conjunto para armazenar códigos Alfa-2 das cidades
    static Set<String> codigosAlfa2Cidades = new HashSet<>();

    // Função para validar o formato dos dados em uma linha
    public static boolean validaTipos(String[] linha) {
        if (linha.length == 4) {
            // Valida se a linha tem 4 elementos, onde o primeiro é um número, o segundo tem 2 caracteres e o terceiro tem 3 caracteres
            return !linha[0].matches("\\d+") || linha[1].length() != 2 || linha[2].length() != 3;
        }
        if (linha.length == 5) {
            // Valida se a linha tem 5 elementos, onde os quatro primeiros são números e o último é um número ou decimal (positivo ou negativo)
            return !linha[0].matches("\\d+") || !linha[1].matches("\\d+") || !linha[2].matches("\\d+")
                    || !linha[3].matches("\\d+") || !linha[4].matches("-?\\d+(\\.\\d+)?");
        }
        if (linha.length == 6) {
            // Valida se o quarto elemento de uma linha com 6 elementos é um número ou decimal (positivo ou negativo)
            return !linha[3].matches("-?\\d+(\\.\\d+)?");
        }
        return true;
    }

    // Função para verificar se um país já existe na lista de países
    public static boolean validaExistePais(String[] paises) {
        for (Pais pais : paisesT) {
            // Compara o ID do país da linha com os IDs dos países na lista
            if (pais.id == Integer.parseInt(paises[0])) {
                return true;
            }
        }
        return false;
    }

    // Função para verificar se uma cidade tem um país correspondente na lista de países
    public static boolean validaCidadeTemPais(String[] cidades) {
        for (int i = 0; i < paisesHm.size(); i++) {
            // Verifica se o código Alfa-2 da cidade está presente no mapa de países
            if (paisesHm.containsValue(cidades[0])) {
                return true;
            }
        }
        return false;
    }

    // Função para verificar se uma entrada de população tem um país correspondente na lista de países
    public static boolean validaPopulacaoTemPais(String[] populacao) {
        for (Pais pais : paisesT) {
            // Compara o ID do país da linha com os IDs dos países na lista
            if (pais.id == Integer.parseInt(populacao[0])) {
                return true;
            }
        }
        return false;
    }

    // Função para normalizar comandos, juntando partes de comandos que devem ser consideradas como um único argumento
    public static String[] normalizaComando(String command) {
        String[] parts = command.split(" ");

        if (parts.length == 1) {
            return parts;
        }
        else if (parts[0].equals("COUNT_CITIES") || parts[0].equals("SUM_POPULATIONS") ||
                parts[0].equals("GET_MOST_POPULOUS") || parts[0].equals("GET_DUPLICATE_CITIES") ||
                parts[0].equals("GET_COUNTRIES_GENDER_GAP") || parts[0].equals("REMOVE_COUNTRY") ||
                parts[0].equals("GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES") || parts[0].equals("COUNT_REGIONS")) {
            for (int i = 0; i < parts.length; i++) {
                if (i > 1) {
                    parts[1] += " " + parts[i];
                    parts[i] = null;
                }
            }
            return parts;

        } else if (parts[0].equals("GET_CITIES_BY_COUNTRY") || parts[0].equals("GET_MISSING_HISTORY") ||
                parts[0].equals("GET_TOP_CITIES_BY_COUNTRY") || parts[0].equals("GET_TOP_POPULATION_INCREASE") ||
                parts[0].equals("GET_CITIES_AT_DISTANCE") || parts[0].equals("GET_CITIES_AT_DISTANCE2") ||
                parts[0].equals("GET_TOP_LAND_AREA") || parts[0].equals("GET_DENSITY_BELOW")) {
            for (int i = 0; i < parts.length; i++) {
                if (i > 2) {
                    parts[2] += " " + parts[i];
                    parts[i] = null;
                }
            }
            return parts;
        } else if (parts[0].equals("GET_HISTORY")) {
            for (int i = 0; i < parts.length; i++) {
                if (i > 3) {
                    parts[3] += " " + parts[i];
                    parts[i] = null;
                }
            }
            return parts;

        } else if (parts[0].equals("INSERT_CITY")) {
            for (int i = 0; i < parts.length; i++) {
                if (i > 2 && i < parts.length - 1 && i < parts.length - 2) {
                    parts[2] += " " + parts[i];
                    parts[i] = null;
                }
            }
            parts[3] = parts[parts.length - 2];
            parts[4] = parts[parts.length - 1];
            return parts;
        }
        return parts;
    }

    // Função para executar comandos recebidos e retornar os resultados apropriados
    public static Result execute(String command) {
        // Normaliza o comando, dividindo-o em partes e juntando partes que devem ser consideradas um único argumento
        String[] parts = normalizaComando(command);

        // Cria um resultado padrão para comandos inválidos
        Result noCommandMatch = new Result(false, "Comando invalido", null);

        // Usa uma expressão switch para determinar qual função executar com base no comando
        return switch (parts[0]) {
            case "COUNT_CITIES" -> Queries.countCities(parts[1]);  // Conta cidades com base no critério fornecido
            case "COUNT_REGIONS" -> Queries.countRegions(parts[1]);  // Conta regiões com base na lista de países fornecida
            case "GET_DENSITY_BELOW" -> Queries.getDensityBelow(parts[1], parts[2]);  // Obtém densidade abaixo de um valor específico para um país
            case "GET_CITIES_BY_COUNTRY" -> Queries.getCitiesByCountry(parts[1], parts[2]);  // Obtém cidades de um país específico
            case "SUM_POPULATIONS" -> Queries.sumPopulations(parts[1]);  // Soma as populações dos países fornecidos
            case "GET_HISTORY" -> Queries.getHistory(parts[1], parts[2], parts[3]);  // Obtém histórico de população de um país
            case "GET_MISSING_HISTORY" -> Queries.getMissingHistory(parts[1], parts[2]);  // Obtém anos de histórico de população ausentes
            case "GET_MOST_POPULOUS" -> Queries.getMostPopulous(parts[1]);  // Obtém as cidades mais populosas
            case "GET_TOP_CITIES_BY_COUNTRY" -> Queries.getTopCitiesByCountry(parts[1], parts[2]);  // Obtém as principais cidades por país
            case "GET_DUPLICATE_CITIES" -> Queries.getDuplicateCities(parts[1]);  // Obtém cidades duplicadas com base na população mínima
            case "GET_COUNTRIES_GENDER_GAP" -> Queries.getCountriesGenderGap(parts[1]);  // Obtém a diferença de género em países com base no critério fornecido
            case "GET_TOP_POPULATION_INCREASE" -> Queries.getTopPopulationIncrease(parts[1], parts[2]);  // Obtém os países com maior aumento populacional
            case "GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES" -> Queries.getDuplicateCitiesDiferentCountries(parts[1]);  // Obtém cidades duplicadas em diferentes países
            case "GET_CITIES_AT_DISTANCE" -> Queries.getCitiesAtDistance(parts[1], parts[2]);  // Obtém pares de cidades a uma distância específica dentro de um país
            case "GET_CITIES_AT_DISTANCE2" -> Queries.getCitiesAtDistance2(parts[1], parts[2]);  // Obtém pares de cidades a uma distância específica entre países
            case "GET_TOP_LAND_AREA" -> Queries.getTopLandArea(parts[1], parts[2]);  // Obtém países com maior área de terra com base no critério fornecido
            case "INSERT_CITY" -> Queries.insertCity(parts[1], parts[2], parts[3], parts[4]);  // Insere uma nova cidade
            case "REMOVE_COUNTRY" -> Queries.removeCountry(parts[1]);  // Remove um país
            case "HELP" -> Queries.help();  // Exibe ajuda sobre os comandos disponíveis
            case "quit" -> Queries.quit();  // Sai do programa
            default -> noCommandMatch;  // Retorna resultado de comando inválido se o comando não corresponder a nenhum caso
        };
    }

    // Função para obter objetos com base no tipo de entidade fornecido
    public static ArrayList getObjects(TipoEntidade tipo) {
        switch (tipo) {
            case PAIS:
                // Retorna uma nova lista contendo todos os países
                return new ArrayList<>(paisesT);

            case CIDADE:
                // Retorna uma nova lista contendo todas as cidades
                return new ArrayList<>(cidadesT);

            case INPUT_INVALIDO:
                // Cria uma lista de strings contendo os primeiros três inputs inválidos
                ArrayList<String> inputsString = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    inputsString.add(inputsInvalidos.get(i).toString());
                }
                return inputsString;

            default:
                // Retorna null se o tipo de entidade não corresponder a nenhum caso
                return null;
        }
    }

    // Função para analisar ficheiros e carregar dados
    public static boolean parseFiles(File folder) {
        // Limpa todas as listas e mapas antes de carregar novos dados
        paisesT.clear();
        cidadesT.clear();
        inputsInvalidos.clear();
        populacaoT.clear();
        codigosAlfa2Paises.clear();
        codigosAlfa2Cidades.clear();
        paisesHm.clear();
        alfa2Pais.clear();
        idPorNomePais.clear();
        nomePorPais.clear();

        // Define os ficheiros de países, cidades e população
        File ficheiroPaises = new File(folder, "paises.csv");
        File ficheiroCidades = new File(folder, "cidades.csv");
        File ficheiroPopulacao = new File(folder, "populacao.csv");

        try {
            // Variáveis para controlar a validação e contagem de linhas de países
            int linhaErradaPaises = 0;
            int nrLinhaPaises = 2;
            int countValidasPaises = 0;
            int countInvalidasPaises = 0;
            Scanner leitorPaises = new Scanner(ficheiroPaises);
            leitorPaises.nextLine();  // Ignora a primeira linha (cabeçalho)

            // Lê e valida linhas do ficheiro de países
            while (leitorPaises.hasNextLine()) {
                String linha = leitorPaises.nextLine();
                String[] paises = linha.split(",");
                // Verifica se a linha é inválida
                if (paises.length != 4 || validaTipos(paises) || validaExistePais(paises)) {
                    if (countInvalidasPaises < 1) {
                        linhaErradaPaises = nrLinhaPaises;
                    }
                    nrLinhaPaises++;
                    countInvalidasPaises++;
                    if (leitorPaises.hasNextLine()) {
                        continue;
                    } else {
                        break;
                    }
                }
                // Extrai e armazena os dados válidos
                int id = Integer.parseInt(paises[0].trim());
                String alfa2 = paises[1].trim();
                String alfa3 = paises[2].trim();
                String nome = paises[3];

                Pais country = new Pais(id, alfa2, alfa3, nome);
                paisesT.add(country);
                codigosAlfa2Paises.add(alfa2);
                alfa2Pais.put(alfa2, country);
                nomePorPais.put(nome, country);
                paisesHm.put(id, alfa2);
                idPorNomePais.put(id, nome);
                nrLinhaPaises++;
                countValidasPaises++;
            }

            // Variáveis para controlar a validação e contagem de linhas de cidades
            int linhaErradaCidades = 0;
            int nrLinhaCidades = 2;
            int countValidasCidades = 0;
            int countInvalidasCidades = 0;
            Scanner leitorCidades = new Scanner(ficheiroCidades);
            leitorCidades.nextLine();  // Ignora a primeira linha (cabeçalho)

            // Lê e valida linhas do ficheiro de cidades
            while (leitorCidades.hasNextLine()) {
                String linha = leitorCidades.nextLine();
                String[] cidades = linha.split(",");
                // Verifica se a linha é inválida
                if (cidades.length != 6 || validaTipos(cidades) || !validaCidadeTemPais(cidades)) {
                    if (countInvalidasCidades < 1) {
                        linhaErradaCidades = nrLinhaCidades;
                    }
                    nrLinhaCidades++;
                    countInvalidasCidades++;
                    if (leitorCidades.hasNextLine()) {
                        continue;
                    } else {
                        break;
                    }
                }
                // Extrai e armazena os dados válidos
                String alfa2 = cidades[0].trim();
                String cidade = cidades[1];
                String idRegiao = cidades[2].trim();
                double populacao = Double.parseDouble(cidades[3].trim());
                double latitude = Double.parseDouble(cidades[4].trim());
                double longitude = Double.parseDouble(cidades[5].trim());

                Cidade city = new Cidade(alfa2, cidade, idRegiao, populacao, latitude, longitude);
                cidadesT.add(city);
                codigosAlfa2Cidades.add(alfa2);
                nrLinhaCidades++;
                countValidasCidades++;
            }

            // Remove países que não têm cidades associadas
            Iterator<Pais> iteradorPais = paisesT.iterator();
            int nrLinhaPaises2 = 1;
            int countInvalidasPaises2 = 0;
            while (iteradorPais.hasNext()) {
                Pais pais = iteradorPais.next();
                nrLinhaPaises2++;
                if (!codigosAlfa2Cidades.contains(pais.alfa2)) {
                    if (countInvalidasPaises2 < 1) {
                        linhaErradaPaises = nrLinhaPaises2;
                    }
                    countValidasPaises--;
                    countInvalidasPaises++;
                    countInvalidasPaises2++;
                    iteradorPais.remove();
                    paisesHm.remove(pais.id);
                    alfa2Pais.remove(pais.alfa2);
                    idPorNomePais.remove(pais.id);
                }
            }

            // Exibe informações sobre linhas válidas e inválidas para países e cidades
            infoLinhas("paises.csv", countValidasPaises, countInvalidasPaises, linhaErradaPaises);
            infoLinhas("cidades.csv", countValidasCidades, countInvalidasCidades, linhaErradaCidades);

            // Variáveis para controlar a validação e contagem de linhas de população
            int linhaErradaPopulacao = 0;
            int nrLinhaPopulacao = 2;
            int countValidasPopulacao = 0;
            int countInvalidasPopulacao = 0;
            Scanner leitorPopulacao = new Scanner(ficheiroPopulacao);
            leitorPopulacao.nextLine();  // Ignora a primeira linha (cabeçalho)

            // Lê e valida linhas do ficheiro de população
            while (leitorPopulacao.hasNextLine()) {
                String linha = leitorPopulacao.nextLine();
                String[] populacao = linha.split(",");
                // Verifica se a linha é inválida
                if (populacao.length != 5 || validaTipos(populacao) || !validaPopulacaoTemPais(populacao)) {
                    if (countInvalidasPopulacao < 1) {
                        linhaErradaPopulacao = nrLinhaPopulacao;
                    }
                    nrLinhaPopulacao++;
                    countInvalidasPopulacao++;
                    if (leitorPopulacao.hasNextLine()) {
                        continue;
                    } else {
                        break;
                    }
                }
                // Extrai e armazena os dados válidos
                int id = Integer.parseInt(populacao[0].trim());
                int ano = Integer.parseInt(populacao[1].trim());
                int populacaoM = Integer.parseInt(populacao[2].trim());
                int populacaoF = Integer.parseInt(populacao[3].trim());
                Double densidade = Double.parseDouble(populacao[4].trim());

                Populacao population = new Populacao(id, ano, populacaoM, populacaoF, densidade);
                populacaoT.add(population);
                nrLinhaPopulacao++;
                countValidasPopulacao++;
            }

            // Exibe informações sobre linhas válidas e inválidas para população
            infoLinhas("populacao.csv", countValidasPopulacao, countInvalidasPopulacao, linhaErradaPopulacao);

        } catch (FileNotFoundException e) {
            return false;  // Retorna false se um dos ficheiros não for encontrado
        }
        return true;  // Retorna true se todos os ficheiros forem lidos com sucesso
    }

    // Função para exibir informações sobre linhas válidas e inválidas de um ficheiro
    public static void infoLinhas(String nameFile, int linhasValidas, int linhasInvalidas, int primeiraErrada) {
        // Cria um objeto InputInvalido para armazenar informações sobre as linhas do ficheiro
        InputInvalido inputInvalido;
        if (linhasInvalidas == 0) {
            // Se não houver linhas inválidas, define a primeira linha inválida como -1
            inputInvalido = new InputInvalido(nameFile, linhasValidas, linhasInvalidas, -1);
        } else {
            // Caso contrário, usa o valor fornecido para a primeira linha inválida
            inputInvalido = new InputInvalido(nameFile, linhasValidas, linhasInvalidas, primeiraErrada);
        }
        // Adiciona o objeto InputInvalido à lista de inputs inválidos
        inputsInvalidos.add(inputInvalido);
    }

    // Função principal (main) que inicia a aplicação
    public static void main(String[] args) {
        // Exibe uma mensagem de boas-vindas
        System.out.println("Welcome to DEISI World Meter");

        // Registra o tempo inicial de carregamento dos ficheiros
        long start = System.currentTimeMillis();
        // Tenta carregar os ficheiros de dados da pasta "BigFiles"
        boolean parseOk = parseFiles(new File("BigFiles"));
        if (!parseOk) {
            // Exibe uma mensagem de erro se o carregamento dos ficheiros falhar
            System.out.println("Error Loading files");
            return;
        }
        // Registra o tempo final de carregamento dos ficheiros e calcula o tempo decorrido
        long end = System.currentTimeMillis();
        System.out.println("Loaded files in " + (end - start) + "ms");

        // Exibe a ajuda de comandos disponíveis
        Result result = execute("HELP");
        System.out.println(result.result);

        // Cria um Scanner para ler a entrada do utilizador
        Scanner in = new Scanner(System.in);
        String line;
        do {
            // Exibe um prompt para o utilizador inserir um comando
            System.out.print("> ");
            line = in.nextLine();
            if (line != null && !line.equals("quit")) {
                // Registra o tempo inicial de execução do comando
                start = System.currentTimeMillis();
                // Executa o comando inserido pelo utilizador
                result = execute(line);
                // Registra o tempo final de execução do comando e calcula o tempo decorrido
                end = System.currentTimeMillis();
                if (!result.success) {
                    // Exibe uma mensagem de erro se a execução do comando falhar
                    System.out.println("Error: " + result.error);
                } else {
                    // Exibe o resultado do comando e o tempo decorrido para sua execução
                    System.out.println(result.result);
                    System.out.println("(took " + (end - start) + " ms)");
                }
            }
        } while (line != null && !line.equals("quit")); // Continua a ler comandos até que o utilizador insira "quit"
    }
}
