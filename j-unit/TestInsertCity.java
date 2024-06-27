package pt.ulusofona.aed.deisiworldmeter;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;


public class TestInsertCity {
    @Test
    public void insertCity() {

        Main.parseFiles(new File("test-files/InsertCity"));
        Result result1 = Main.execute("GET_TOP_CITIES_BY_COUNTRY -1 Portugal");
        assertEquals("almada:20K\n" +
                "seixal:20K\n" +
                "porto:11K\n"+
                "coimbra:10K\n", result1.result);

        Result result = Main.execute("INSERT_CITY pt lisboa 01 1000000");
        Result result2 = Main.execute("GET_TOP_CITIES_BY_COUNTRY -1 Portugal");
        assertEquals("lisboa:1000K\n" +
                "almada:20K\n" +
                "seixal:20K\n" +
                "porto:11K\n"+
                "coimbra:10K\n", result2.result);

        assertEquals("Inserido com sucesso", result.result);

    }
    @Test
    public void insertCity_DontExist() {

        Main.parseFiles(new File("test-files/InsertCity"));
        Result result1 = Main.execute("GET_TOP_CITIES_BY_COUNTRY -1 Portugal");
        assertEquals("almada:20K\n" +
                "seixal:20K\n" +
                "porto:11K\n"+
                "coimbra:10K\n", result1.result);

        Result result = Main.execute("INSERT_CITY jk lisboa 01 1000000");
        Result result2 = Main.execute("GET_TOP_CITIES_BY_COUNTRY -1 Portugal");
        assertEquals("almada:20K\n" +
                "seixal:20K\n" +
                "porto:11K\n"+
                "coimbra:10K\n", result2.result);

        assertEquals("Pais invalido", result.result);

    }
}