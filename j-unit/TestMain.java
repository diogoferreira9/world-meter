package pt.ulusofona.aed.deisiworldmeter;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class TestMain {
    @Test
    public void getObjects() {

        Main.parseFiles(new File("test-files/Queries"));
        ArrayList<Object> resultPais = Main.getObjects(TipoEntidade.PAIS);
        ArrayList<Object> resultCidade = Main.getObjects(TipoEntidade.CIDADE);

        assertEquals("Portugal", Main.paisesT.get(0).nome);
        assertEquals("Alemanha", Main.paisesT.get(1).nome);

    }
    @Test
    public void parseFiles_() {

        Main.parseFiles(new File("test-files/MainTest"));

        assertEquals("Portugal", Main.paisesT.get(0).nome);
        assertEquals("Albânia", Main.paisesT.get(1).nome);

    }
    @Test
    public void parseFiles() {

        Main.parseFiles(new File("test-files/MainTest"));

        assertEquals("Portugal", Main.paisesT.get(0).nome);
        assertEquals("Albânia", Main.paisesT.get(1).nome);

    }
}