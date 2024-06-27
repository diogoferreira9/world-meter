package pt.ulusofona.aed.deisiworldmeter;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHelp {
    @Test
    public void testHelp() {

        Main.parseFiles(new File("test-files/Queries"));
        Result result = Main.execute("HELP");

        assertEquals("-------------------------\n" +
               "Commands available:\n" +
               "COUNT_CITIES <min-population>\n" +
               "GET_CITIES_BY_COUNTRY <num-results> <country-name>\n" +
               "SUM_POPULATIONS <countries-list>\n" +
               "GET_HISTORY <year-start> <year-end> <country-name>\n" +
               "GET_MISSING_HISTORY <year-start> <year-end>\n" +
               "GET_MOST_POPULOUS <num-results>\n"+
               "GET_TOP_CITIES_BY_COUNTRY <num-results> <country-name>\n" +
               "GET_DUPLICATE_CITIES <min-population>\n" +
               "GET_COUNTRIES_GENDER_GAP <min-gender-gap>\n" +
               "GET_TOP_POPULATION_INCREASE <year-start> <year-end>\n" +
               "GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES <min-population>\n" +
               "GET_CITIES_AT_DISTANCE <distance> <country-name>\n" +
               "GET_CITIES_AT_DISTANCE2 <distance> <country-name>\n" +
               "GET_TOP_LAND_AREA <num_results> <minLandArea>\n" +
               "COUNT_REGIONS <countries-list>\n" +
               "GET_DENSITY_BELOW <max-density> <country_name>\n" +
               "INSERT_CITY <alfa2> <city-name> <region> <population>\n" +
               "REMOVE_COUNTRY <country_name>\n" +
               "HELP\n" +
               "quit\n" +
               "-------------------------\n", result.result);

    }
}
