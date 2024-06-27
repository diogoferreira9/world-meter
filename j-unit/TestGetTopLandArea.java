import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;


public class TestGetTopLandArea {

    @Test
    public void getTopLandArea_NoLimit() {

        Main.parseFiles(new File("test-files/TopPopIncrease"));
        Result result = Main.execute("GET_TOP_LAND_AREA -1 1");

        assertEquals("Albânia - 31467.69Km2\n" +
                "Andorra - 29877.13Km2\n" +
                "Portugal - 28780.72Km2\n" +
                "Afeganistão - 28079.98Km2\n" +
                "Alemanha - 27465.41Km2\n", result.result);

    }

    @Test
    public void getTopLandArea_WithLimitLandArea() {

        Main.parseFiles(new File("test-files/TopPopIncrease"));
        Result result = Main.execute("GET_TOP_LAND_AREA -1 28500");

        assertEquals("Albânia - 31467.69Km2\n" +
                "Andorra - 29877.13Km2\n" +
                "Portugal - 28780.72Km2\n", result.result);

    }
    @Test
    public void getTopLandArea_WithLimitedResults() {

        Main.parseFiles(new File("test-files/TopPopIncrease"));
        Result result = Main.execute("GET_TOP_LAND_AREA 2 1");

        assertEquals("Albânia - 31467.69Km2\n" +
                "Andorra - 29877.13Km2\n", result.result);

    }
    @Test
    public void getTopLandArea_BothLimited() {

        Main.parseFiles(new File("test-files/TopPopIncrease"));
        Result result = Main.execute("GET_TOP_LAND_AREA 1 29000");

        assertEquals("Albânia - 31467.69Km2\n", result.result);

    }

}
