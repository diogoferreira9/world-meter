import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;


public class TestGetCountriesGenderGap {
    @Test
    public void getCountriesGenderGap_OneResult() {

        Main.parseFiles(new File("test-files/Queries"));
        Result result = Main.execute("GET_COUNTRIES_GENDER_GAP 4");

        assertEquals("Portugal:4.20\n", result.result);

    }
    @Test
    public void getCountriesGenderGap_AllResults() {

        Main.parseFiles(new File("test-files/Queries"));
        Result result = Main.execute("GET_COUNTRIES_GENDER_GAP 0");

        assertEquals("Portugal:4.20\n" +
                "Albânia:3.99\n" +
                "Alemanha:3.90\n" +
                "Afeganistão:3.73\n" +
                "Andorra:3.49\n", result.result);

    }

    @Test
    public void getCountriesGenderGap_NoResults() {

        Main.parseFiles(new File("test-files/Queries"));
        Result result = Main.execute("GET_COUNTRIES_GENDER_GAP 5");

        assertEquals("Sem resultados", result.result);

    }
}
