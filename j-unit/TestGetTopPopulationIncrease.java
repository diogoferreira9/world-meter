package pt.ulusofona.aed.deisiworldmeter;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;


public class TestGetTopPopulationIncrease {
    @Test
    public void normaliza2CasasDecimais() {
        double resultado1 = Queries.normaliza2CasasDecimais(10.22000);
        assertEquals(10.22, resultado1);
        double resultado2 = Queries.normaliza2CasasDecimais(10.22999);
        assertEquals(10.23, resultado2);

    }
    @Test
    public void getTopPopulationIncrease_LessThanFive() {

        Main.parseFiles(new File("test-files/TopPopIncrease"));
        Result result = Main.execute("GET_TOP_POPULATION_INCREASE 2022 2024");

        assertEquals("Albânia:2023-2024:17.53%\n" +
                "Andorra:2022-2024:16.39%\n" +
                "Andorra:2023-2024:11.48%\n" +
                "Andorra:2022-2023:5.55%\n", result.result);

    }
    @Test
    public void getTopPopulationIncrease_MoreThanFive() {

        Main.parseFiles(new File("test-files/TopPopIncrease"));
        Result result = Main.execute("GET_TOP_POPULATION_INCREASE 1952 2024");

        assertEquals("Afeganistão:1952-2024:25.93%\n" +
                "Portugal:2020-2024:20.00%\n" +
                "Albânia:2023-2024:17.53%\n" +
                "Andorra:2022-2024:16.39%\n" +
                "Afeganistão:1954-2024:14.81%\n", result.result);

    }
    @Test
    public void getTopPopulationIncrease_NoResults() {

        Main.parseFiles(new File("test-files/TopPopIncrease"));
        Result result = Main.execute("GET_TOP_POPULATION_INCREASE 1900 1901");

        assertEquals("Sem resultados", result.result);

    }
}