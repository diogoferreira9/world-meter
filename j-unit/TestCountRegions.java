import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestCountRegions {
    @Test
    public void countRegions_NoResults() {

        Main.parseFiles(new File("test-files/Queries"));
        Result result = Main.execute("COUNT_REGIONS alala,balala,atata");

        assertEquals("-1",result.result);

    }
    @Test
    public void countRegions_OneCountry() {

        Main.parseFiles(new File("test-files/Queries"));
        Result result = Main.execute("COUNT_REGIONS Portugal");

        assertEquals("4",result.result);

    }
    @Test
    public void countRegions_AllCountries() {

        Main.parseFiles(new File("test-files/Queries"));
        Result result = Main.execute("COUNT_REGIONS Portugal,Albânia,Alemanha,Andorra,Afeganistão");

        assertEquals("12",result.result);

    }


}
