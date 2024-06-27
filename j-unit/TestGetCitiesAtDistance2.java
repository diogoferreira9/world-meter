package pt.ulusofona.aed.deisiworldmeter;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;


public class TestGetCitiesAtDistance2 {

    @Test
    public void getCitiesAtDistance2_NoResults() {

        Main.parseFiles(new File("test-files/Queries"));
        Result result = Main.execute("GET_CITIES_AT_DISTANCE2 30 Portugal");

        assertEquals("Sem resultados", result.result);

    }
    @Test
    public void getCitiesAtDistance2_WithResults() {

        Main.parseFiles(new File("test-files/Queries"));
        Result result = Main.execute("GET_CITIES_AT_DISTANCE2 10 Portugal");

        assertEquals("coimbra->sant julia de loria\n" +
                "les escaldes->seixal\n" +
                "porto->sant julia de loria\n", result.result);

    }

}