import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;


public class TestGetDuplicateCitiesDifCountry {

    @Test
    public void getDuplicateCitiesDifCountry_NoLimit() {

        Main.parseFiles(new File("test-files/DuplicateCitiesDifCountries"));
        Result result = Main.execute("GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES 1");

        assertEquals("cidade1: Angola,Portugal\n" +
                "cidade2: Cabo Verde,Portugal\n" +
                "cidade3: Angola,Brasil,Cabo Verde,Portugal\n", result.result);

    }
    @Test
    public void getDuplicateCitiesDifCountry_WithLimit() {

        Main.parseFiles(new File("test-files/DuplicateCitiesDifCountries"));
        Result result = Main.execute("GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES 2000");

        assertEquals("cidade2: Cabo Verde,Portugal\n" +
                "cidade3: Angola,Brasil,Cabo Verde,Portugal\n", result.result);

    }
    @Test
    public void getDuplicateCitiesDifCountry_WithLimit2() {

        Main.parseFiles(new File("test-files/DuplicateCitiesDifCountries"));
        Result result = Main.execute("GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES 3000");

        assertEquals( "cidade2: Cabo Verde,Portugal\n" +
                "cidade3: Angola,Brasil,Cabo Verde,Portugal\n", result.result);

    }
    @Test
    public void getDuplicateCitiesDifCountry_Video() {

        Main.parseFiles(new File("test-files/DuplicateCitiesDifCountries"));
        Result result = Main.execute("GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES 1000");

        assertEquals( "cidade1: Angola,Portugal\n" +
                "cidade2: Cabo Verde,Portugal\n" +
                "cidade3: Angola,Brasil,Cabo Verde,Portugal\n", result.result);

    }
    @Test
    public void getDuplicateCitiesDifCountry_SuperHighLimit() {

        Main.parseFiles(new File("test-files/DuplicateCitiesDifCountries"));
        Result result = Main.execute("GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES 100000000");

        assertEquals("Sem resultados", result.result);

    }

}
