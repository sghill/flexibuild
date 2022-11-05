package net.sghill.flexibuild.discover;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class BuildToolDiscoveryTest {
    private final FileSystem fs = Jimfs.newFileSystem(Configuration.unix());
    private Path cwd;
    private BuildToolDiscovery discovery;

    @BeforeEach
    void setup() throws IOException {
        cwd = fs.getPath("/project");
        Files.createDirectories(cwd);
        discovery = new BuildToolDiscovery(cwd);
    }

    @AfterEach
    void teardown() throws IOException {
        fs.close();
    }

    @Test
    void shouldDiscoverMavenPom() throws IOException {
        Files.createFile(cwd.resolve("pom.xml"));
        BuildTool tool = discovery.discover();
        assertThat(tool).isInstanceOf(Maven.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"build.gradle", "build.gradle.kts"})
    void shouldDiscoverGradle(String input) throws IOException {
        Files.createFile(cwd.resolve(input));
        BuildTool tool = discovery.discover();
        assertThat(tool).isInstanceOf(GradleWrapper.class);
    }

    @Test
    void shouldReportNothingFound() {
        BuildTool tool = discovery.discover();
        assertThat(tool).isInstanceOf(NotFound.class);
    }
}
