package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestQuit {
    @Test
    public void testHelp() {

        Main.parseFiles(new File("test-files/Queries"));
        Result result = Main.execute("QUIT");

        assertEquals(null, result.result);

    }
}
