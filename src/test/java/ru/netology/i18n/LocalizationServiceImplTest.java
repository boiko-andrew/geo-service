package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

public class LocalizationServiceImplTest {
    LocalizationServiceImpl localizationServiceImpl;

    @BeforeEach
    public void beforeEach() {
        localizationServiceImpl = new LocalizationServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("localeMethodSource")
    public void parametrizedTestLocale(Country country, String expectedMessage) {
        //arrange
        String message = localizationServiceImpl.locale(country);
        boolean result;

        //act
        result = message.equals(expectedMessage);

        //assert
        Assertions.assertTrue(result);
    }

    public static Stream<Arguments> localeMethodSource() {
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome")
        );
    }
}