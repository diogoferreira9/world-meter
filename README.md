# World Meter

Welcome to the World Meter project! This project is a data analysis tool designed to process and analyze geographical and population data of various countries and cities. The tool provides a variety of commands to query and manipulate the data, making it a powerful resource for demographic and geographical analysis.

## Features

- **Flexible Data Loading:** Load country, city, and population data from CSV files.
- **Interactive Command Execution:** Execute a wide range of commands to query data.
- **Robust Data Validation:** Ensures data integrity through rigorous validation checks.
- **Efficient Data Processing:** Utilizes various data structures for optimized data retrieval and manipulation.

## Commands

Once the application is running, you can execute various commands. Here is a list of available commands:

- `COUNT_CITIES <min-population>`: Count cities with a minimum specified population.
- `GET_CITIES_BY_COUNTRY <num-results> <country-name>`: Get a specified number of cities for a given country.
- `SUM_POPULATIONS <countries-list>`: Sum populations of a list of countries for the year 2024.
- `GET_HISTORY <year-start> <year-end> <country-name>`: Get population history of a country between specified years.
- `GET_MISSING_HISTORY <year-start> <year-end>`: Find countries with missing population data between specified years.
- `GET_MOST_POPULOUS <num-results>`: Get the most populous cities globally.
- `GET_TOP_CITIES_BY_COUNTRY <num-results> <country-name>`: Get the top cities by population for a specified country.
- `GET_DUPLICATE_CITIES <min-population>`: Find duplicate city names with a minimum population.
- `GET_COUNTRIES_GENDER_GAP <min-gender-gap>`: Find countries with a minimum gender gap in population for 2024.
- `GET_TOP_POPULATION_INCREASE <year-start> <year-end>`: Get countries with the highest population increase between specified years.
- `GET_DUPLICATE_CITIES_DIFFERENT_COUNTRIES <min-population>`: Find duplicate city names in different countries with a minimum population.
- `GET_CITIES_AT_DISTANCE <distance> <country-name>`: Get pairs of cities within a specified distance in a country.
- `GET_CITIES_AT_DISTANCE2 <distance> <country-name>`: Get pairs of cities within a specified distance between countries.
- `GET_TOP_LAND_AREA <num_results> <minLandArea>`: Get countries with the largest land area above a minimum specified area.
- `COUNT_REGIONS <countries-list>`: Count regions in a list of countries.
- `GET_DENSITY_BELOW <max-density> <country_name>`: Get years where the population density of a country is below a specified value.
- `INSERT_CITY <alfa2> <city-name> <region> <population>`: Insert a new city.
- `REMOVE_COUNTRY <country_name>`: Remove a country and its cities.
- `HELP`: Display the list of available commands.
- `quit`: Exit the application.

## Technical Details

- Programming Language: Java
- Environment: Console-based application
- Data Structures: Utilizes lists, sets, and maps for efficient data management

## Conclusion

This World Meter project showcases the capability to process and analyze complex geographical and demographic data. It serves as a valuable tool for anyone interested in data analysis, providing a robust foundation for further development and enhancement.


Enjoy exploring the world of data with World Meter!
