package pt.ulusofona.aed.deisiworldmeter;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;


public class TestGetCitiesAtDistance {

    @Test
    public void getCitiesAtDistance_NoResults() {

        Main.parseFiles(new File("test-files/Queries"));
        Result result = Main.execute("GET_CITIES_AT_DISTANCE 100 Portugal");

        assertEquals("Sem resultados", result.result);

    }
    @Test
    public void getCitiesAtDistance_WithResults() {

        Main.parseFiles(new File("test-files/Queries"));
        Result result = Main.execute("GET_CITIES_AT_DISTANCE 10 Portugal");

        assertEquals("almada->seixal\n", result.result);

    }

}