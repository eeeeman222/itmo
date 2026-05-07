package edu.itmo.functions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Ожидаемые значения — литералы из справочников / CAS (не {@link java.lang.Math#asin(double)}).
 * <p>
 * JSON-таблица (два способа чтения одного файла):
 * (1) {@code URL} + {@link java.nio.file.Files#readString(java.nio.file.Path, java.nio.charset.Charset) Files.readString(Paths.get(url.toURI()))};
 * (2) {@link Class#getResourceAsStream(String) getResourceAsStream} + {@link java.io.InputStream#readAllBytes() readAllBytes} и {@link String#String(byte[], java.nio.charset.Charset)}.
 * XML-таблица: встроенная строка (text block) и
 * {@link org.junit.jupiter.params.provider.MethodSource}.
 */
@DisplayName("Модульное тестирование arcsin(x) через степенной ряд")
class ArcsinSeriesTest {

    private static final double TOL = 1e-9;

    @ParameterizedTest(name = "arcsin({0}) ≈ {1}")
    @CsvSource({
            // π/6, π/4, π/3 — табличные константы
            "0.0, 0.0",
            "0.5, 0.52359877559829887307710723054658318",
            "-0.5, -0.52359877559829887307710723054658318",
            // arcsin(0.25) ≈ 0.25268… (Wolfram Alpha)
            "0.25, 0.25268025514207863312790749583503405",
            "0.1, 0.10016742116155979634552317945269331",
    })
    @DisplayName("Параметризованное сравнение с табличными значениями")
    void parameterizedAgainstReferenceTable(double x, double expected) {
        assertEquals(expected, ArcsinSeries.arcsin(x), TOL);
    }

    @ParameterizedTest(name = "json (URL+Files): arcsin({0}) ≈ {1}")
    @ArgumentsSource(JsonCasesFile.class)
    @DisplayName("JSON: URL → Files.readString (ArgumentsSource)")
    void referenceTableFromJsonReadViaUrlAndFiles(double x, double expected) {
        assertEquals(expected, ArcsinSeries.arcsin(x), TOL);
    }

    @ParameterizedTest(name = "json (InputStream): arcsin({0}) ≈ {1}")
    @MethodSource("jsonArgumentsFromInputStream")
    @DisplayName("JSON: getResourceAsStream + readAllBytes (MethodSource)")
    void referenceTableFromJsonReadViaInputStream(double x, double expected) {
        assertEquals(expected, ArcsinSeries.arcsin(x), TOL);
    }

    @ParameterizedTest(name = "xml: arcsin({0}) ≈ {1}")
    @MethodSource("referencePairsFromXmlString")
    @DisplayName("Таблица: XML как строка в классе (MethodSource)")
    void referenceTableFromXmlString(double x, double expected) {
        assertEquals(expected, ArcsinSeries.arcsin(x), TOL);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.0000001, -1.0000001, 2.0, -2.0})
    @DisplayName("Вне [-1, 1] результат — NaN")
    void outOfDomainReturnsNaN(double x) {
        assertTrue(Double.isNaN(ArcsinSeries.arcsin(x)));
    }

    @Test
    @DisplayName("Границы x = ±1 без Math.asin")
    void boundariesMatchHalfPi() {
        double halfPi = 1.57079632679489661923132169163975144;
        assertEquals(halfPi, ArcsinSeries.arcsin(1.0), TOL);
        assertEquals(-halfPi, ArcsinSeries.arcsin(-1.0), TOL);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.NaN})
    @DisplayName("NaN на входе")
    void nanInputReturnsNaN(double x) {
        assertTrue(Double.isNaN(ArcsinSeries.arcsin(x)));
    }

    @Test
    @DisplayName("Ряд: выход из цикла без break (условие n < maxTerms становится ложным)")
    void positiveSeriesStopsWhenMaxTermsExhausted() {
        double value = ArcsinSeries.arcsinPositiveSeries(0.2, 0.0, 12);
        assertTrue(Double.isFinite(value));
    }

    @Test
    @DisplayName("Ряд: ноль итераций цикла при maxTerms == 1")
    void positiveSeriesSkipsLoopWhenMaxTermsIsOne() {
        assertEquals(0.3, ArcsinSeries.arcsinPositiveSeries(0.3, 1e-15, 1), 0.0);
    }

    private static final String XML_REFERENCE_CASES = """
            <cases>
            <c x="0.0" e="0.0"/>
            <c x="0.5" e="0.52359877559829887307710723054658318"/>
            <c x="-0.5" e="-0.52359877559829887307710723054658318"/>
            <c x="0.25" e="0.25268025514207863312790749583503405"/>
            <c x="0.1" e="0.10016742116155979634552317945269331"/>
            </cases>
            """;

    static Stream<Arguments> referencePairsFromXmlString() {
        Pattern p = Pattern.compile("x=\"([^\"]+)\"\\s+e=\"([^\"]+)\"");
        Matcher m = p.matcher(XML_REFERENCE_CASES);
        List<Arguments> list = new ArrayList<>();
        while (m.find()) {
            list.add(Arguments.of(
                    Double.parseDouble(m.group(1)),
                    Double.parseDouble(m.group(2))));
        }
        return list.stream();
    }

    private static List<Arguments> jsonPairsFromText(String jsonText) {
        String s = jsonText.replaceAll("\\s", "");
        s = s.substring(1, s.length() - 1);
        List<Arguments> list = new ArrayList<>();
        for (String chunk : s.split("\\],\\[")) {
            String t = chunk.replace("[", "").replace("]", "");
            int comma = t.indexOf(',');
            list.add(Arguments.of(
                    Double.parseDouble(t.substring(0, comma)),
                    Double.parseDouble(t.substring(comma + 1))));
        }
        return list;
    }

    static Stream<Arguments> jsonArgumentsFromInputStream() {
        try (InputStream in = ArcsinSeriesTest.class.getResourceAsStream("arcsin-cases.json")) {
            if (in == null) {
                throw new IllegalStateException("classpath: edu/itmo/functions/arcsin-cases.json");
            }
            String text = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            return jsonPairsFromText(text).stream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static final class JsonCasesFile implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            try {
                URL url = ArcsinSeriesTest.class.getResource("arcsin-cases.json");
                if (url == null) {
                    throw new IllegalStateException("classpath: edu/itmo/functions/arcsin-cases.json");
                }
                String text = Files.readString(Paths.get(url.toURI()));
                return jsonPairsFromText(text).stream();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
