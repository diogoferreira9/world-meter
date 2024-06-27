package pt.ulusofona.aed.deisiworldmeter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


public class TestGetDuplicateCities {

    @Test
    public void getDuplicateCities_NoLimit() {

        Main.parseFiles(new File("test-files/DuplicateCities"));
        Result result = Main.execute("GET_DUPLICATE_CITIES 1");
        ArrayList<String> resultado = new ArrayList<>();
        String [] spliter = result.result.split("\n");

        for(int i = 0; i < spliter.length; i++){
            resultado.add(spliter[i]);
        }
        Collections.sort(resultado);
        StringBuilder resultFin = new StringBuilder();

        for(String linha : resultado){
            resultFin.append(linha).append("\n");
        }

        assertEquals("cidade1 (Portugal,01)\n" +
                "cidade2 (Albânia,01)\n" +
                "cidade2 (Portugal,02)\n", resultFin.toString());

    }
    @Test
    public void getDuplicateCities_WithLimit() {

        Main.parseFiles(new File("test-files/DuplicateCities"));
        Result result = Main.execute("GET_DUPLICATE_CITIES 2000");
        ArrayList<String> resultado = new ArrayList<>();
        String [] spliter = result.result.split("\n");

        for(int i = 0; i < spliter.length; i++){
            resultado.add(spliter[i]);
        }
        Collections.sort(resultado);
        StringBuilder resultFin = new StringBuilder();

        for(String linha : resultado){
            resultFin.append(linha).append("\n");
        }

        assertEquals("cidade2 (Albânia,01)\n" +
                "cidade2 (Portugal,02)\n", resultFin.toString());

    }
    @Test
    public void getDuplicateCities_WithLimit2() {

        Main.parseFiles(new File("test-files/DuplicateCities"));
        Result result = Main.execute("GET_DUPLICATE_CITIES 3000");

        assertEquals( "cidade2 (Albânia,01)\n", result.result);

    }
    @Test
    public void getDuplicateCities_SuperHighLimit() {

        Main.parseFiles(new File("test-files/DuplicateCities"));
        Result result = Main.execute("GET_DUPLICATE_CITIES 1000000000");

        assertEquals("Sem resultados", result.result);

    }

}