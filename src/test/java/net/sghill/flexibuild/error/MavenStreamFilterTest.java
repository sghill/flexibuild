package net.sghill.flexibuild.error;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

class MavenStreamFilterTest {
    @ParameterizedTest
    @ValueSource(strings = {"error-and-fatal", "warnings"})
    void shouldLeaveErrorAndFatal(String filename) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        String input = "/maven/" + filename + ".txt";
        String output = "/maven/" + filename + "-expected.txt";
        pipe(input, out);
        List<String> actual = Arrays.asList(out.toString(UTF_8.displayName()).split("\n"));
        try (InputStream is = MavenStreamFilterTest.class.getResourceAsStream(output)) {
            assertThat(is).isNotNull();
            List<String> expected = IOUtils.readLines(is, UTF_8);
            assertThat(actual).isEqualTo(expected);
        }
    }

    private static void pipe(String file, ByteArrayOutputStream out) {
        MavenStreamFilter filtered = new MavenStreamFilter(out);
        try (InputStream is = MavenStreamFilterTest.class.getResourceAsStream(file)) {
            assertThat(is).isNotNull();
            IOUtils.copy(is, filtered);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
