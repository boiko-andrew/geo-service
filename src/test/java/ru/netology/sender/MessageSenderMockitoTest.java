package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MessageSenderMockitoTest {
    @ParameterizedTest
    @MethodSource("sendMethodSource")
    public void parametrizedMockitoTestSend(String ip, String expectedMessage) {
        //arrange
        final String IP_ADDRESS_HEADER = "x-real-ip";
        Map<String, String> headers = new HashMap<>();
        headers.put(IP_ADDRESS_HEADER, ip);

        String message;
        boolean result;

        GeoService geoService = Mockito.mock(GeoService.class);
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        switch (ip) {
            case ("172.0.32.11"):
                // Moscow ip test
                Mockito.when(geoService.byIp("172.0.32.11"))
                        .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));
                Mockito.when(localizationService.locale(Country.RUSSIA))
                        .thenReturn("Добро пожаловать");
                break;
            case ("96.44.183.149"):
                // New-York ip test
                Mockito.when(geoService.byIp("96.44.183.149"))
                        .thenReturn(new Location("New York", Country.USA, " 10th Avenue", 32));
                Mockito.when(localizationService.locale(Country.USA))
                        .thenReturn("Welcome");
                break;
            case ("85.214.26.37"):
                // Germany ip test
                Mockito.when(geoService.byIp("85.214.26.37"))
                        .thenReturn(new Location(null, Country.GERMANY, null, 0));
                Mockito.when(localizationService.locale(Country.GERMANY))
                        .thenReturn("Welcome");
            case ("45.70.92.0"):
                // Brazil ip test
                Mockito.when(geoService.byIp("45.70.92.0"))
                        .thenReturn(new Location(null, Country.BRAZIL, null, 0));
                Mockito.when(localizationService.locale(Country.BRAZIL))
                        .thenReturn("Welcome");
            default:
                // Unknown ip test
                Mockito.when(geoService.byIp("116.203.28.43"))
                        .thenReturn(new Location(null, Country.USA, null, 0));
                Mockito.when(localizationService.locale(Country.USA))
                        .thenReturn("Welcome");
                break;
        }
        MessageSenderImpl messageSenderImpl = new MessageSenderImpl(geoService, localizationService);

        //act
        message = messageSenderImpl.send(headers);
        System.out.println();
        result = message.equals(expectedMessage);

        //assert
        Assertions.assertTrue(result);
    }

    public static Stream<Arguments> sendMethodSource() {
        return Stream.of(
                Arguments.of("172.0.32.11", "Добро пожаловать"),
                Arguments.of("96.44.183.149", "Welcome"),
                Arguments.of("85.214.26.37", "Welcome"),
                Arguments.of("45.70.92.0", "Welcome"),
                Arguments.of("116.203.28.43", "Welcome")
        );
    }
}