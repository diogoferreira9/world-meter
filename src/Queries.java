import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class Queries {

    //FUNÇÕES AUXILIARES

    // Função auxiliar para normalizar um número para 2 casas decimais
    public static double normaliza2CasasDecimais(double numero) {
        // Multiplica o número por 100, arredonda para o inteiro mais próximo,
        // e depois divide por 100.0 para retornar com duas casas decimais
        return Math.round(numero * 100) / 100.0;
    }

    // Função auxiliar para obter um país pelo código Alfa-2
    private static Pais getCountryByAlfa2(String alfa2) {
        // Itera sobre a lista de países
        for (Pais pais : Main.paisesT) {
            // Compara o código Alfa-2 (ignorando maiúsculas/minúsculas)
            if (pais.alfa2.equalsIgnoreCase(alfa2)) {
                // Retorna o país se encontrar uma correspondência
                return pais;
            }
        }
        // Retorna null se não encontrar nenhum país com o código Alfa-2 fornecido
        return null;
    }

    // Função auxiliar para analisar coordenadas no formato (latitude, longitude)
    private static double[] parseCoordinates(String coordinates) {
        // Remove os parênteses e divide a string pelo caractere de vírgula
        String[] parts = coordinates.replace("(", "").replace(")", "").split(",");
        // Converte as partes da string em números double e retorna um array com latitude e longitude
        return new double[] { Double.parseDouble(parts[0]), Double.parseDouble(parts[1]) };
    }

    // Função auxiliar para converter graus em radianos
    private static double toRadians(double degrees) {
        // Converte graus em radianos usando a fórmula: graus * PI / 180
        return degrees * Math.PI / 180;
    }

    // Função auxiliar para calcular a distância haversine entre duas coordenadas
    private static double haversineDistance(double lat1, double lon1, double lat2, double lon2, double earthRadius) {
        // Calcula a diferença de latitude e longitude em radianos
        double dLat = toRadians(lat2 - lat1);
        double dLon = toRadians(lon2 - lon1);
        lat1 = toRadians(lat1);
        lat2 = toRadians(lat2);

        // Aplica a fórmula haversine
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Retorna a distância calculada usando o raio da Terra fornecido
        return earthRadius * c;
    }

    // Função auxiliar para ordenar uma linha de nomes em ordem alfabética
    public static String ordenarLinha(String linha) {
        // Divide a linha em prefixo e nomes
        String[] partes = linha.split(": ");
        String prefixo = partes[0];
        String[] nomes = partes[1].split(",");

        // Ordena os nomes em ordem alfabética
        Arrays.sort(nomes);

        // Constrói a string resultado com os nomes ordenados
        StringBuilder resultado = new StringBuilder();
        resultado.append(prefixo).append(": ");
        for (int i = 0; i < nomes.length; i++) {
            resultado.append(nomes[i]);
            if (i < nomes.length - 1) {
                resultado.append(",");  // Adiciona uma vírgula entre os nomes, excepto após o último nome
            }
        }

        // Retorna a linha ordenada
        return resultado.toString();
    }

    // Função para contar o número de regiões de uma lista de países fornecida
    public static Result countRegions(String countriesList) {
        // Divide a lista de países recebida numa array de nomes de países
        String[] countryNames = countriesList.split(",");
        // Cria uma lista para armazenar as regiões
        ArrayList<String> regions = new ArrayList<>();
        // Inicializa o contador de regiões
        int countRegions = 0;
        Pais country;

        // Itera sobre cada nome de país na lista
        for (String countryName : countryNames) {
            // Verifica se o nome do país existe no mapa nomePorPais
            if (!Main.nomePorPais.containsKey(countryName)) {
                // Se for o último país na lista e não encontrou nenhuma região
                if (countryNames[countryNames.length - 1].equals(countryName) && regions.isEmpty()) {
                    // Retorna um resultado com valor -1 indicando que não encontrou o país
                    return new Result(true, null, String.valueOf(-1));
                }
                // Continua para o próximo país se o país atual não estiver no mapa
                continue;
            }

            // Limpa a lista de regiões para cada país
            regions.clear();
            // Obtém o objeto Pais correspondente ao nome do país
            country = Main.nomePorPais.get(countryName);

            // Itera sobre a lista de cidades
            for (Cidade cidade : Main.cidadesT) {
                // Verifica se a cidade pertence ao país atual (comparando alfa2)
                if (country.alfa2.equals(cidade.alfa2)) {
                    // Se a região da cidade não estiver na lista de regiões
                    if (!regions.contains(cidade.regiao)) {
                        // Adiciona a região à lista e incrementa o contador de regiões
                        regions.add(cidade.regiao);
                        countRegions++;
                    }
                }
            }
        }
        // Retorna o número total de regiões encontradas
        return new Result(true, null, String.valueOf(countRegions));
    }

    // Função para obter os anos em que a densidade populacional de um país é menor que um valor máximo
    public static Result getDensityBelow(String maxDensity, String countryName) {
        // Obtém o objeto Pais correspondente ao nome do país
        Pais country = Main.nomePorPais.get(countryName);
        // Cria uma lista para armazenar os resultados
        ArrayList<String> resultado = new ArrayList<>();
        // Verifica se o país não foi encontrado
        if (country == null) {
            // Retorna um resultado indicando "Sem resultados"
            return new Result(true, null, "Sem resultados");
        }

        // Itera sobre a lista de populações
        for (Populacao populacao : Main.populacaoT) {
            // Verifica se a população pertence ao país atual (comparando id)
            if (country.id == populacao.id) {
                // Verifica se a densidade da população é menor que a densidade máxima permitida
                if (populacao.densidade < Double.parseDouble(maxDensity)) {
                    // Adiciona o ano e a densidade da população à lista de resultados
                    resultado.add(populacao.ano + " - " + populacao.densidade);
                }
            }
        }
        // Verifica se não há resultados
        if (resultado.isEmpty()) {
            // Retorna um resultado indicando "Sem resultados"
            return new Result(true, null, "Sem resultados");
        }

        // Ordena os resultados em ordem decrescente
        resultado.sort(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s2.compareTo(s1);
            }
        });

        // Constrói a string de resultados
        StringBuilder resultadoString = new StringBuilder();
        for (String linha : resultado) {
            resultadoString.append(linha).append("\n");
        }
        // Retorna os resultados
        return new Result(true, null, resultadoString.toString());
    }

    //FUNÇÕES PRINCIPAIS

    // Função para contar o número de cidades com uma população mínima especificada
    public static Result countCities(String minPopulationString) {
        // Inicializa o contador de cidades
        int count = 0;
        // Converte a string da população mínima para um valor inteiro
        int minPopulation = Integer.parseInt(minPopulationString);

        // Itera sobre a lista de cidades
        for (Cidade cidade : Main.cidadesT) {
            // Verifica se a população da cidade é maior ou igual à população mínima
            if (cidade.populacao >= minPopulation) {
                // Incrementa o contador de cidades
                count++;
            }
        }
        // Retorna o resultado com o número de cidades encontradas
        return new Result(true, null, String.valueOf(count));
    }

    // Função para obter um número especificado de cidades de um país
    public static Result getCitiesByCountry(String numResults, String countryName) {
        // Converte a string do número de resultados para um valor inteiro
        int limite = Integer.parseInt(numResults);
        // Cria uma lista para armazenar as cidades filtradas
        List<Cidade> filteredCities = new ArrayList<>();
        // Inicializa uma string para armazenar o código Alfa-2 do país
        String alfa2Pais = "";

        // Itera sobre a lista de países para encontrar o país pelo nome
        for (Pais pais : Main.paisesT) {
            if (pais.nome.equalsIgnoreCase(countryName)) {
                alfa2Pais = pais.alfa2;
                break;
            }
        }

        // Verifica se o país foi encontrado
        if (alfa2Pais.isEmpty()) {
            // Retorna um resultado indicando "País inválido" se o país não foi encontrado
            return new Result(true, null, "Pais invalido: " + countryName);
        }

        // Itera sobre a lista de cidades
        for (Cidade cidade : Main.cidadesT) {
            // Verifica se o limite de cidades já foi atingido
            if (filteredCities.size() == limite) {
                break;
            }
            // Verifica se a cidade pertence ao país pelo código Alfa-2
            if (alfa2Pais.equals(cidade.alfa2)) {
                // Adiciona a cidade à lista de cidades filtradas
                filteredCities.add(cidade);
            }
        }

        // Constrói a string de resultados com os nomes das cidades
        StringBuilder resultString = new StringBuilder();
        for (Cidade cidade : filteredCities) {
            resultString.append(cidade.nome).append("\n");
        }

        // Retorna os resultados
        return new Result(true, null, resultString.toString());
    }

    // Função para somar a população de uma lista de países para o ano de 2024
    public static Result sumPopulations(String countriesList) {
        // Divide a lista de países recebida numa array de nomes de países
        String[] countryNames = countriesList.split(",");
        // Cria uma lista para armazenar os IDs dos países encontrados
        List<Integer> countryIds = new ArrayList<>();
        // Cria uma lista para armazenar os nomes de países inválidos (não encontrados)
        List<String> invalidCountries = new ArrayList<>();

        // Itera sobre cada nome de país na lista
        for (String countryName : countryNames) {
            countryName = countryName.trim();  // Remove espaços em branco
            boolean found = false;  // Flag para verificar se o país foi encontrado

            // Itera sobre a lista de países para encontrar o país pelo nome
            for (Pais pais : Main.paisesT) {
                if (pais.nome.equalsIgnoreCase(countryName)) {
                    countryIds.add(pais.id);  // Adiciona o ID do país à lista
                    found = true;  // Marca que o país foi encontrado
                    break;
                }
            }
            // Se o país não foi encontrado, adiciona o nome do país à lista de países inválidos
            if (!found) {
                invalidCountries.add(countryName);
            }
        }

        // Verifica se há países inválidos
        if (!invalidCountries.isEmpty()) {
            // Retorna um resultado indicando "País inválido" para cada país não encontrado
            return new Result(true, null, "Pais invalido: " + String.join(", ", invalidCountries));
        }

        // Inicializa as variáveis para armazenar a população masculina e feminina total
        int totalPopulationM = 0;
        int totalPopulationF = 0;

        // Itera sobre a lista de populações
        for (Populacao populacao : Main.populacaoT) {
            // Verifica se a população pertence a um dos países encontrados e se o ano é 2024
            if (countryIds.contains(populacao.id) && populacao.ano == 2024) {
                totalPopulationM += populacao.populacaoM;  // Soma a população masculina
                totalPopulationF += populacao.populacaoF;  // Soma a população feminina
            }
        }

        // Calcula a população total (masculina + feminina)
        int totalPopulation = totalPopulationM + totalPopulationF;

        // Retorna o resultado com a população total
        return new Result(true, null, "" + totalPopulation);
    }

    // Função para obter a história populacional de um país entre dois anos especificados
    public static Result getHistory(String yearStart, String yearEnd, String countryName) {
        // Inicializa a variável para armazenar o ID do país
        int countryId = -1;

        // Itera sobre a lista de países para encontrar o país pelo nome
        for (Pais pais : Main.paisesT) {
            if (pais.nome.equalsIgnoreCase(countryName)) {
                countryId = pais.id;  // Armazena o ID do país
                break;
            }
        }

        // Verifica se o país foi encontrado
        if (countryId == -1) {
            // Retorna um resultado indicando "País inválido" se o país não foi encontrado
            return new Result(true, null, "Pais invalido: " + countryName);
        }

        // Cria um StringBuilder para armazenar os resultados
        StringBuilder result = new StringBuilder();
        boolean foundAnyData = false;  // Flag para verificar se foram encontrados dados

        // Itera sobre os anos especificados
        for (int year = Integer.parseInt(yearStart); year <= Integer.parseInt(yearEnd); year++) {
            // Itera sobre a lista de populações
            for (Populacao populacao : Main.populacaoT) {
                // Verifica se a população pertence ao país e ao ano atual
                if (populacao.id == countryId && populacao.ano == year) {
                    // Se já foram encontrados dados anteriormente, adiciona uma nova linha
                    if (foundAnyData) {
                        result.append("\n");
                    }
                    // Adiciona os dados da população para o ano atual
                    result.append(year).append(":")
                            .append(populacao.populacaoM / 1000).append("k:")
                            .append(populacao.populacaoF / 1000).append("k");
                    foundAnyData = true;  // Marca que foram encontrados dados
                    break;
                }
            }
        }

        // Retorna o resultado com a história populacional
        return new Result(true, null, result.toString() + "\n");
    }

    // Função para encontrar os países que têm anos faltando dados populacionais entre dois anos especificados
    public static Result getMissingHistory(String yearStart, String yearEnd) {
        // Converte os anos de início e fim para inteiros
        int startYear = Integer.parseInt(yearStart);
        int endYear = Integer.parseInt(yearEnd);

        // Mapa para armazenar os anos disponíveis para cada país (identificado pelo seu ID)
        HashMap<Integer, HashSet<Integer>> countryYearDataMap = new HashMap<>();
        for (Populacao populacao : Main.populacaoT) {
            countryYearDataMap
                    .computeIfAbsent(populacao.id, k -> new HashSet<>())
                    .add(populacao.ano);  // Adiciona o ano disponível para o país
        }

        // Conjunto para armazenar os países que têm anos faltando
        HashSet<String> missingCountries = new HashSet<>();
        StringBuilder result = new StringBuilder();

        // Itera sobre a lista de países
        for (Pais pais : Main.paisesT) {
            HashSet<Integer> availableYears = countryYearDataMap.getOrDefault(pais.id, new HashSet<>());
            boolean hasMissingYears = false;

            // Verifica se há anos faltando entre o intervalo especificado
            for (int year = startYear; year <= endYear; year++) {
                if (!availableYears.contains(year)) {
                    hasMissingYears = true;
                    break;
                }
            }

            // Se houver anos faltando, adiciona o país ao conjunto de países com anos faltando
            if (hasMissingYears) {
                missingCountries.add(pais.alfa2 + ":" + pais.nome);
            }
        }

        // Verifica se há países com anos faltando
        if (missingCountries.isEmpty()) {
            // Retorna um resultado indicando "Sem resultados" se não houver anos faltando
            return new Result(true, null, "Sem resultados");
        }

        // Constrói a string de resultados com os países que têm anos faltando
        result.append(String.join("\n", missingCountries));
        // Retorna os resultados
        return new Result(true, null, result.toString().trim() + "\n");
    }

    // Função para encontrar cidades duplicadas em diferentes países com uma população mínima especificada
    public static Result getDuplicateCitiesDiferentCountries(String minPopulation) {
        // Mapa para armazenar os países onde cada cidade aparece
        HashMap<String, HashSet<String>> cidadePaisMap = new HashMap<>();
        // Lista para armazenar as cidades duplicadas
        ArrayList<String> cidadesDuplicadas = new ArrayList<>();
        // Lista final para armazenar as cidades duplicadas ordenadas
        ArrayList<String> listaFinal = new ArrayList<>();
        StringBuilder listaDuplicados = new StringBuilder();

        // Itera sobre a lista de cidades
        for (Cidade cidade : Main.cidadesT) {
            if (Integer.parseInt(minPopulation) <= cidade.populacao) {
                cidadePaisMap.putIfAbsent(cidade.nome, new HashSet<>());
                cidadePaisMap.get(cidade.nome).add(Main.alfa2Pais.get(cidade.alfa2).nome);  // Adiciona o país à cidade
            }
        }

        // Verifica as cidades que aparecem em mais de um país
        for (Cidade cidade : Main.cidadesT) {
            if (Integer.parseInt(minPopulation) <= cidade.populacao) {
                HashSet<String> paises = cidadePaisMap.get(cidade.nome);
                if (paises.size() > 1) {
                    cidadesDuplicadas.add(cidade.nome + ": " + String.join(",", paises));
                }
            }
        }

        // Remove duplicatas e ordena as linhas
        for (String cidadeInfo : cidadesDuplicadas) {
            if (!listaFinal.contains(ordenarLinha(cidadeInfo))) {
                listaFinal.add(ordenarLinha(cidadeInfo));
            }
        }
        for (String linha : listaFinal) {
            listaDuplicados.append(linha);
            listaDuplicados.append("\n");
        }

        // Verifica se há cidades duplicadas
        if (cidadesDuplicadas.isEmpty()) {
            // Retorna um resultado indicando "Sem resultados" se não houver duplicatas
            return new Result(true, null, "Sem resultados");
        }

        // Retorna os resultados
        return new Result(true, null, listaDuplicados.toString());
    }

    // Função para obter as principais cidades de um país, ordenadas por população
    public static Result getTopCitiesByCountry(String numResults, String countryName) {
        // Converte a string do número de resultados para um valor inteiro
        int nrResults = Integer.parseInt(numResults);
        // Cria uma lista para armazenar as cidades filtradas
        ArrayList<Cidade> cidades = new ArrayList<>();
        // Inicializa uma string para armazenar o código Alfa-2 do país
        String alfa2Pais = "";

        // Itera sobre a lista de países para encontrar o país pelo nome
        for (Pais pais : Main.paisesT) {
            if (pais.nome.equals(countryName)) {
                alfa2Pais = pais.alfa2;
                break;
            }
        }

        // Itera sobre a lista de cidades para filtrar as cidades do país com população >= 10000
        for (Cidade cidade : Main.cidadesT) {
            if (cidade.alfa2.equals(alfa2Pais)) {
                if (cidade.populacao >= 10000) {
                    cidades.add(cidade);
                }
            }
        }

        // Ordena as cidades pela população em ordem decrescente; em caso de empate, ordena por nome
        cidades.sort(new Comparator<Cidade>() {
            @Override
            public int compare(Cidade c1, Cidade c2) {
                int milhares1 = (int) Math.round(c1.populacao / 1000.0);
                int milhares2 = (int) Math.round(c2.populacao / 1000.0);
                int compararPopulacao = Integer.compare(milhares2, milhares1);
                if (compararPopulacao == 0) {
                    return c1.nome.compareTo(c2.nome);
                }
                return compararPopulacao;
            }
        });

        // Cria uma lista para armazenar as cidades ordenadas
        ArrayList<Cidade> sortedCities = new ArrayList<>();

        // Se o número de resultados for -1, adiciona todas as cidades; caso contrário, adiciona até o limite
        if (nrResults == -1) {
            sortedCities.addAll(cidades);
        } else {
            for (int i = 0; i < nrResults && i < cidades.size(); i++) {
                sortedCities.add(cidades.get(i));
            }
        }

        // Constrói a string de resultados com os nomes e populações das cidades
        StringBuilder result = new StringBuilder();
        for (Cidade cidade : sortedCities) {
            result.append(cidade.nome).append(":").append((int) cidade.populacao / 1000).append('K').append("\n");
        }

        // Retorna os resultados
        return new Result(true, null, result.toString().trim() + "\n");
    }

    // Função para remover um país e todas as suas cidades
    public static Result removeCountry(String countryName) {
        // Inicializa a variável para armazenar o país a ser removido
        Pais countryToRemove = null;

        // Itera sobre a lista de países usando um iterator
        for (Iterator<Pais> iterator = Main.paisesT.iterator(); iterator.hasNext();) {
            Pais country = iterator.next();
            if (country.nome.equalsIgnoreCase(countryName)) {
                iterator.remove();  // Remove o país da lista
                countryToRemove = country;  // Armazena o país removido
                break;
            }
        }

        // Verifica se o país foi encontrado e removido
        if (countryToRemove == null) {
            // Retorna um resultado indicando "País inválido" se o país não foi encontrado
            return new Result(true, null, "Pais invalido");
        }

        // Itera sobre a lista de cidades usando um iterator
        for (Iterator<Cidade> iterator = Main.cidadesT.iterator(); iterator.hasNext();) {
            Cidade cidade = iterator.next();
            if (cidade.alfa2.equalsIgnoreCase(countryToRemove.alfa2)) {
                iterator.remove();  // Remove a cidade da lista
            }
        }

        // Retorna um resultado indicando que o país e suas cidades foram removidos com sucesso
        return new Result(true, null, "Removido com sucesso");
    }

    // Função para encontrar cidades duplicadas com uma população mínima especificada
    public static Result getDuplicateCities(String minPopulation) {
        // Conjunto para armazenar os nomes das cidades únicas
        HashSet<String> set = new HashSet<>();
        // Conjunto para armazenar as cidades duplicadas
        HashSet<Cidade> duplicates = new HashSet<>();

        // Itera sobre a lista de cidades
        for (Cidade cidade : Main.cidadesT) {
            // Verifica se a população da cidade é maior ou igual à população mínima
            if (cidade.populacao >= Integer.parseInt(minPopulation)) {
                // Tenta adicionar o nome da cidade ao conjunto; se já existir, adiciona a cidade ao conjunto de duplicados
                if (!set.add(cidade.nome)) {
                    duplicates.add(cidade);
                }
            }
        }

        // StringBuilder para construir a lista de cidades duplicadas
        StringBuilder listaDuplicados = new StringBuilder();

        // Verifica se há cidades duplicadas
        if (duplicates.isEmpty()) {
            // Retorna um resultado indicando "Sem resultados" se não houver duplicatas
            return new Result(true, null, "Sem resultados");
        }

        // Constrói a lista de cidades duplicadas com o nome, país e região
        for (Cidade cidade : duplicates) {
            listaDuplicados.append(cidade.nome).append(" (").append(Main.alfa2Pais.get(cidade.alfa2).nome)
                    .append(",").append(cidade.regiao).append(")\n");
        }

        // Retorna os resultados
        return new Result(true, null, listaDuplicados.toString());
    }

    // Função para inserir uma nova cidade
    public static Result insertCity(String alfa2, String cityName, String region, String population) {
        // Converte a string de população para um valor double
        double pop = Double.parseDouble(population);

        // Obtém o objeto Pais correspondente ao código Alfa-2
        Pais country = getCountryByAlfa2(alfa2);
        // Verifica se o país foi encontrado
        if (country == null) {
            // Retorna um resultado indicando "País inválido" se o país não foi encontrado
            return new Result(true, null, "Pais invalido");
        }

        // Adiciona a nova cidade à lista de cidades
        Main.cidadesT.add(new Cidade(alfa2, cityName, region, pop, 0.0, 0.0));
        // Retorna um resultado indicando que a cidade foi inserida com sucesso
        return new Result(true, null, "Inserido com sucesso");
    }

    // Função para encontrar pares de cidades dentro de um país que estão a uma distância específica uma da outra
    public static Result getCitiesAtDistance(String distance, String countryName) {
        // Converte a string de distância para um valor double
        double targetDistance = Double.parseDouble(distance);
        // Inicializa uma string para armazenar o código Alfa-2 do país
        String alfa2Pais = "";

        // Itera sobre a lista de países para encontrar o país pelo nome
        for (Pais pais : Main.paisesT) {
            if (pais.nome.equalsIgnoreCase(countryName)) {
                alfa2Pais = pais.alfa2;
                break;
            }
        }

        // Verifica se o país foi encontrado
        if (alfa2Pais.isEmpty()) {
            // Retorna um resultado indicando "País inválido" se o país não foi encontrado
            return new Result(true, null, "Pais Inválido: " + countryName);
        }

        // Cria uma lista para armazenar as cidades do país
        List<Cidade> citiesInCountry = new ArrayList<>();
        for (Cidade cidade : Main.cidadesT) {
            if (cidade.alfa2.equals(alfa2Pais)) {
                citiesInCountry.add(cidade);
            }
        }

        // Lista para armazenar os pares de cidades que estão à distância especificada
        List<String> cityPairs = new ArrayList<>();
        double earthRadius = 6371.0; // raio da terra em kms

        // Itera sobre a lista de cidades para encontrar pares que estão à distância especificada
        for (int i = 0; i < citiesInCountry.size(); i++) {
            for (int j = i + 1; j < citiesInCountry.size(); j++) {
                Cidade city1 = citiesInCountry.get(i);
                Cidade city2 = citiesInCountry.get(j);
                double[] coords1 = parseCoordinates(city1.coordenadas);
                double[] coords2 = parseCoordinates(city2.coordenadas);
                double distanceBetweenCities = haversineDistance(coords1[0], coords1[1], coords2[0], coords2[1], earthRadius);
                if (Math.abs(distanceBetweenCities - targetDistance) <= 1) {
                    String pair = city1.nome.compareTo(city2.nome) < 0
                            ? city1.nome + "->" + city2.nome
                            : city2.nome + "->" + city1.nome;
                    if (!cityPairs.contains(pair)) {
                        cityPairs.add(pair);
                    }
                }
            }
        }

        // Verifica se há pares de cidades encontrados
        if (cityPairs.isEmpty()) {
            // Retorna um resultado indicando "Sem resultados" se não houver pares encontrados
            return new Result(true, null, "Sem resultados");
        }

        // Constrói a string de resultados com os pares de cidades
        StringBuilder result = new StringBuilder();
        for (String pair : cityPairs) {
            result.append(pair).append("\n");
        }

        // Retorna os resultados
        return new Result(true, null, result.toString().trim() + "\n");
    }

    // Função para encontrar países com uma diferença de género mínima especificada na população de 2024
    public static Result getCountriesGenderGap(String minGenderGap) {
        double increaseValue;
        StringBuilder countryIncreaseValues = new StringBuilder();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat gapValue2 = new DecimalFormat("0.00", symbols);
        double increaseValueNormalizado;

        // Itera sobre a lista de populações
        for (Populacao populacao : Main.populacaoT) {
            if (populacao.ano == 2024) {
                // Calcula a diferença de género como uma percentagem
                increaseValue = ((Math.abs((double) populacao.populacaoM - (double) populacao.populacaoF))
                        / ((double) populacao.populacaoM + (double) populacao.populacaoF)) * 100;

                // Normaliza a diferença de género para duas casas decimais
                increaseValueNormalizado = normaliza2CasasDecimais(increaseValue);

                // Verifica se a diferença de género é maior que o mínimo especificado
                if (increaseValueNormalizado > Integer.parseInt(minGenderGap)) {
                    // Adiciona o país e a diferença de género à lista de resultados
                    countryIncreaseValues.append(Main.idPorNomePais.get(populacao.id)).append(":")
                            .append(gapValue2.format(increaseValueNormalizado)).append("\n");
                }
            }
        }

        // Verifica se há países encontrados
        if (countryIncreaseValues.isEmpty()) {
            // Retorna um resultado indicando "Sem resultados" se não houver países encontrados
            return new Result(true, null, "Sem resultados");
        }

        // Retorna os resultados
        return new Result(true, null, countryIncreaseValues.toString());
    }

    // Função para obter as cidades mais populosas entre todos os países
    public static Result getMostPopulous(String numResults) {
        // Converte a string do número de resultados para um valor inteiro
        int limit = Integer.parseInt(numResults);

        // Mapa para armazenar a cidade mais populosa de cada país (identificado pelo código Alfa-2)
        Map<String, Cidade> mostPopulousCities = new HashMap<>();

        // Itera sobre a lista de cidades
        for (Cidade cidade : Main.cidadesT) {
            // Obtém o país correspondente ao código Alfa-2 da cidade
            Pais country = Main.alfa2Pais.get(cidade.alfa2);
            if (country != null) {
                // Verifica se a cidade atual é mais populosa que a cidade armazenada no mapa para o mesmo país
                if (!mostPopulousCities.containsKey(country.alfa2) ||
                        cidade.populacao > mostPopulousCities.get(country.alfa2).populacao) {
                    mostPopulousCities.put(country.alfa2, cidade);
                }
            }
        }

        // Cria uma lista a partir dos valores do mapa (cidades mais populosas)
        List<Cidade> cityList = new ArrayList<>(mostPopulousCities.values());

        // Ordena a lista de cidades pela população em ordem decrescente
        cityList.sort((c1, c2) -> Double.compare(c2.populacao, c1.populacao));

        // Cria uma lista para armazenar as cidades ordenadas até o limite especificado
        List<Cidade> sortedCities = new ArrayList<>();
        for (int i = 0; i < limit && i < cityList.size(); i++) {
            sortedCities.add(cityList.get(i));
        }

        // Constrói a string de resultados com os nomes dos países, nomes das cidades e populações
        StringBuilder result = new StringBuilder();
        for (Cidade cidade : sortedCities) {
            Pais country = getCountryByAlfa2(cidade.alfa2);
            if (country != null) {
                result.append(country.nome).append(":")
                        .append(cidade.nome).append(":")
                        .append((int)cidade.populacao).append("\n");
            }
        }

        // Retorna os resultados
        return new Result(true, null, result.toString().trim() + "\n");
    }

    // Função para obter os países com maior aumento populacional entre dois anos especificados
    public static Result getTopPopulationIncrease(String yearStart, String yearEnd) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DecimalFormat gapValue2 = new DecimalFormat("0.00", symbols);
        List<Populacao> populacaoList = Main.populacaoT;
        StringBuilder finalResult = new StringBuilder();

        // Converte os anos de início e fim para inteiros
        int yearStartInt = Integer.parseInt(yearStart);
        int yearEndInt = Integer.parseInt(yearEnd);

        // Mapa para agrupar as populações pelo ID do país
        Map<Integer, List<Populacao>> groupedPopulacao = new HashMap<>();
        for (Populacao p : populacaoList) {
            if (p.ano >= yearStartInt && p.ano <= yearEndInt) {
                groupedPopulacao.computeIfAbsent(p.id, k -> new ArrayList<>()).add(p);
            }
        }

        // Fila de prioridade para armazenar os maiores aumentos populacionais
        PriorityQueue<String> higherIncreaseValues = new PriorityQueue<>(
                Comparator.comparingDouble((String s) ->
                                Double.parseDouble(s.split(":")[2].replaceAll("[^\\d.]", "")))
                        .reversed()
        );

        // Itera sobre as entradas do mapa
        for (Map.Entry<Integer, List<Populacao>> entry : groupedPopulacao.entrySet()) {
            List<Populacao> pops = entry.getValue();
            pops.sort(Comparator.comparingInt(p -> p.ano));

            // Mapa para armazenar a população total por ano
            Map<Integer, Integer> yearToPopMap = new HashMap<>();
            for (Populacao p : pops) {
                yearToPopMap.put(p.ano, p.populacaoF + p.populacaoM);
            }

            List<Integer> years = new ArrayList<>(yearToPopMap.keySet());
            Collections.sort(years);

            // Calcula os aumentos populacionais entre os anos
            for (int i = 0; i < years.size(); i++) {
                int startYear = years.get(i);
                double popStart = yearToPopMap.get(startYear);

                for (int j = i + 1; j < years.size(); j++) {
                    int endYear = years.get(j);
                    double popEnd = yearToPopMap.get(endYear);

                    if (popEnd <= popStart) {
                        continue;
                    }

                    double increaseValue = ((popEnd - popStart) / popEnd) * 100;
                    double normalizedIncrease = normaliza2CasasDecimais(increaseValue);

                    higherIncreaseValues.add(Main.idPorNomePais.get(entry.getKey()) +
                            ":" + startYear + "-" + endYear + ":" +
                            gapValue2.format(normalizedIncrease) + "%");
                }
            }
        }

        // Verifica se há aumentos populacionais encontrados
        if (higherIncreaseValues.isEmpty()) {
            // Retorna um resultado indicando "Sem resultados" se não houver aumentos encontrados
            return new Result(true, null, "Sem resultados");
        }

        // Constrói a string de resultados com os maiores aumentos populacionais
        int count = 0;
        while (!higherIncreaseValues.isEmpty() && count < 5) {
            finalResult.append(higherIncreaseValues.poll()).append("\n");
            count++;
        }

        // Retorna os resultados
        return new Result(true, null, finalResult.toString());
    }

    // Função para encontrar pares de cidades onde uma cidade está dentro de um país e a outra está fora, a uma distância específica
    public static Result getCitiesAtDistance2(String distance, String countryName) {
        // Converte a string de distância para um valor double
        double targetDistance = Double.parseDouble(distance);
        // Inicializa uma string para armazenar o código Alfa-2 do país
        String alfa2Pais = "";

        // Itera sobre a lista de países para encontrar o país pelo nome
        for (Pais pais : Main.paisesT) {
            if (pais.nome.equalsIgnoreCase(countryName)) {
                alfa2Pais = pais.alfa2;
                break;
            }
        }

        // Verifica se o país foi encontrado
        if (alfa2Pais.isEmpty()) {
            // Retorna um resultado indicando "País inválido" se o país não foi encontrado
            return new Result(true, null, "Pais Inválido: " + countryName);
        }

        // Cria listas para armazenar as cidades dentro e fora do país
        List<Cidade> citiesInCountry = new ArrayList<>();
        List<Cidade> citiesOutsideCountry = new ArrayList<>();
        for (Cidade cidade : Main.cidadesT) {
            if (cidade.alfa2.equals(alfa2Pais)) {
                citiesInCountry.add(cidade);
            } else {
                citiesOutsideCountry.add(cidade);
            }
        }

        // Lista para armazenar os pares de cidades que estão à distância especificada
        List<String> cityPairs = new ArrayList<>();
        double earthRadius = 6371.0; // raio da terra em kms

        // Itera sobre as listas de cidades para encontrar pares que estão à distância especificada
        for (Cidade city1 : citiesInCountry) {
            for (Cidade city2 : citiesOutsideCountry) {
                double[] coords1 = parseCoordinates(city1.coordenadas);
                double[] coords2 = parseCoordinates(city2.coordenadas);
                double distanceBetweenCities = haversineDistance(coords1[0], coords1[1], coords2[0], coords2[1], earthRadius);
                if (Math.abs(distanceBetweenCities - targetDistance) <= 1) {
                    String pair = city1.nome.compareTo(city2.nome) < 0
                            ? city1.nome + "->" + city2.nome
                            : city2.nome + "->" + city1.nome;
                    if (!cityPairs.contains(pair)) {
                        cityPairs.add(pair);
                    }
                }
            }
        }

        // Verifica se há pares de cidades encontrados
        if (cityPairs.isEmpty()) {
            // Retorna um resultado indicando "Sem resultados" se não houver pares encontrados
            return new Result(true, null, "Sem resultados");
        }

        // Ordena a lista de pares de cidades
        Collections.sort(cityPairs);

        // Constrói a string de resultados com os pares de cidades
        StringBuilder result = new StringBuilder();
        for (String pair : cityPairs) {
            result.append(pair).append("\n");
        }

        // Retorna os resultados
        return new Result(true, null, result.toString().trim() + "\n");
    }

    // Função para obter os países com a maior área de terra que têm uma área mínima especificada
    public static Result getTopLandArea(String numResults, String minLandArea) {
        ArrayList<String> idByLandArea = new ArrayList<>();
        double totalPopulation;
        double landArea;
        double landAreaNormalizado;
        StringBuilder finalResult = new StringBuilder();

        // Itera sobre a lista de populações
        for (Populacao populacao : Main.populacaoT) {
            if (populacao.ano == 2024) {
                // Verifica se o país é a Rússia para usar uma área de terra fixa
                if (Main.idPorNomePais.get(populacao.id).equals("Rússia")) {
                    BigDecimal landAreaRussia = new BigDecimal(1.637569282E7);
                    idByLandArea.add(Main.idPorNomePais.get(populacao.id) + " - " +
                            landAreaRussia.setScale(2, RoundingMode.HALF_UP));

                } else {
                    // Calcula a área de terra para outros países
                    totalPopulation = (double) populacao.populacaoF + (double) populacao.populacaoM;
                    landArea = totalPopulation / populacao.densidade;
                    landAreaNormalizado = normaliza2CasasDecimais(landArea);
                    if (Integer.parseInt(minLandArea) < landAreaNormalizado) {
                        idByLandArea.add(Main.idPorNomePais.get(populacao.id) + " - " + landAreaNormalizado);
                    }
                }
            }
        }

        // Ordena os países pela área de terra em ordem decrescente
        idByLandArea.sort(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                String[] partes1 = s1.split(" - ");
                String[] partes2 = s2.split(" - ");

                double valor1 = Double.parseDouble(partes1[partes1.length - 1].replaceAll("[^\\d.]", ""));
                double valor2 = Double.parseDouble(partes2[partes2.length - 1].replaceAll("[^\\d.]", ""));

                return Double.compare(valor2, valor1);
            }
        });

        // Constrói a lista de resultados com os países e suas áreas de terra
        for (int i = 0; i < idByLandArea.size(); i++) {
            if (i >= Integer.parseInt(numResults) && Integer.parseInt(numResults) != -1) {
                break;
            } else {
                finalResult.append(idByLandArea.get(i)).append("Km2").append("\n");
            }
        }

        // Retorna os resultados
        return new Result(true, null, finalResult.toString());
    }
    public static Result help(){
        String help = """
                -------------------------
                Commands available:
                COUNT_CITIES <min-population>
                GET_CITIES_BY_COUNTRY <num-results> <country-name>
                SUM_POPULATIONS <countries-list>
                GET_HISTORY <year-start> <year-end> <country-name>
                GET_MISSING_HISTORY <year-start> <year-end>
                GET_MOST_POPULOUS <num-results>
                GET_TOP_CITIES_BY_COUNTRY <num-results> <country-name>
                GET_DUPLICATE_CITIES <min-population>
                GET_COUNTRIES_GENDER_GAP <min-gender-gap>
                GET_TOP_POPULATION_INCREASE <year-start> <year-end>
                GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES <min-population>
                GET_CITIES_AT_DISTANCE <distance> <country-name>
                GET_CITIES_AT_DISTANCE2 <distance> <country-name>
                GET_TOP_LAND_AREA <num_results> <minLandArea>
                COUNT_REGIONS <countries-list>
                GET_DENSITY_BELOW <max-density> <country_name>
                INSERT_CITY <alfa2> <city-name> <region> <population>
                REMOVE_COUNTRY <country_name>
                HELP
                quit
                -------------------------
                """;

        return new Result(true, null, help);
    }
    public static Result quit(){
        return null;
    }




}
