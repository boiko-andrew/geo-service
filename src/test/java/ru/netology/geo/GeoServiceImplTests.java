package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

public class GeoServiceImplTests {
    GeoServiceImpl geoServiceImpl;

    @BeforeEach
    public void beforeEach() {
        geoServiceImpl = new GeoServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("byIpMethodSource")
    public void parametrizedTestByIp(String ip, Location expectedLocation) {
        //arrange
        Location actualLocation = geoServiceImpl.byIp(ip);
        boolean cityResult, countryResult, streetResult, buildingResult;
        boolean result;

        //act
        if (actualLocation != null) {
            // City equality test
            if (actualLocation.getCity() == null && expectedLocation.getCity() == null) {
                cityResult = true;
            } else if (actualLocation.getCity() != null && expectedLocation.getCity() == null ||
                    actualLocation.getCity() == null && expectedLocation.getCity() != null) {
                cityResult = false;
            } else cityResult = actualLocation.getCity() != null &&
                    expectedLocation.getCity() != null &&
                    actualLocation.getCity().equals(expectedLocation.getCity());


            // Country equality test
            if (actualLocation.getCountry() == null && expectedLocation.getCountry() == null) {
                countryResult = true;
            } else if (actualLocation.getCountry() != null && expectedLocation.getCountry() == null ||
                    actualLocation.getCountry() == null && expectedLocation.getCountry() != null) {
                countryResult = false;
            } else countryResult = actualLocation.getCountry() != null &&
                    expectedLocation.getCountry() != null &&
                    actualLocation.getCountry().equals(expectedLocation.getCountry());

            // Street equality test
            if (actualLocation.getStreet() == null && expectedLocation.getStreet() == null) {
                streetResult = true;
            } else if (actualLocation.getStreet() != null && expectedLocation.getStreet() == null ||
                    actualLocation.getStreet() == null && expectedLocation.getStreet() != null) {
                streetResult = false;
            } else streetResult = actualLocation.getStreet() != null &&
                    expectedLocation.getStreet() != null &&
                    actualLocation.getStreet().equals(expectedLocation.getStreet());

            // Building equality test
            buildingResult = actualLocation.getBuiling() == expectedLocation.getBuiling();

            // Overall equality test
            result = cityResult && countryResult && streetResult && buildingResult;
        } else {
            result = expectedLocation == null;
        }

        //assert
        Assertions.assertTrue(result);
    }

    public static Stream<Arguments> byIpMethodSource() {
        return Stream.of(
                Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.0.32.0", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.44.183.0", new Location("New York", Country.USA, null, 0)),
                Arguments.of("195.19.45.81", null)
        );
    }

    @Test
    public void testByCoordinates() {
        //arrange
        double latitude = 100.0;
        double longitude = 200.0;
        Class<RuntimeException> expectedType = RuntimeException.class;

        //act
        org.junit.jupiter.api.function.Executable executable =
                () -> geoServiceImpl.byCoordinates(latitude, longitude);

        //assert
        Assertions.assertThrows(expectedType, executable);
    }
}