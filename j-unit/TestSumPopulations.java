import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;


public class TestSumPopulations {

    @Test
    public void sumPopulations_OnlyOnePopulationLine_OneCountry() {

        Main.parseFiles(new File("test-files/Queries"));
        Result result = Main.execute("SUM_POPULATIONS Albânia");

        assertEquals(2351536, Integer.parseInt(result.result));

    }
    @Test
    public void sumPopulations_TwoPopulationLines_FirstLine() {
        Main.parseFiles(new File("test-files/Queries"));
        Result result = Main.execute("SUM_POPULATIONS Portugal");

        assertEquals(2254113, Integer.parseInt(result.result));

    }
    @Test
    public void sumPopulations_TwoPopulationLines_LastLine() {
        Main.parseFiles(new File("test-files/Queries"));
        Result result = Main.execute("SUM_POPULATIONS Afeganistão");

        assertEquals(2495194, Integer.parseInt(result.result));

    }
    @Test
    public void sumPopulations_MultiplePopulations() {
        Main.parseFiles(new File("test-files/Queries"));
        Result result = Main.execute("SUM_POPULATIONS Portugal,Albânia,Alemanha");

        assertEquals(7004748, Integer.parseInt(result.result));

    }

}
