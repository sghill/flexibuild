package net.sghill.flexibuild.discover;

import net.sghill.flexibuild.Main;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.TeeOutputStream;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.io.IoBuilder;
import org.apache.logging.log4j.message.ParameterizedMessage;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class GradleWrapper implements BuildTool {
    private final List<OutputStream> streams = new LinkedList<>();

    @Override
    public String smokeTestTool() {
        return gradlew("help");
    }

    @Override
    public String compile() {
        return gradlew("classes");
    }

    @Override
    public String pkg() {
        return gradlew("jpi");
    }

    @Override
    public String test() {
        return gradlew("check");
    }

    @Override
    public PumpStreamHandler teeErrorsTo(Path path) {
        OutputStream stdout = IoBuilder.forLogger("gradle/out")
                .setLevel(Level.INFO)
                .buildOutputStream();
        streams.add(stdout);
        OutputStream stderr = IoBuilder.forLogger("gradle/err")
                .setLevel(Level.INFO)
                .buildOutputStream();
        streams.add(stderr);
        try {
            OutputStream errorFile = Files.newOutputStream(path);
            streams.add(errorFile);
            TeeOutputStream teeError = new TeeOutputStream(stderr, errorFile);
            streams.add(teeError);
            return new PumpStreamHandler(stdout, teeError);
        } catch (IOException e) {
            Main.LOGGER.error(new ParameterizedMessage("Failed to create {}", path), e);
            return new PumpStreamHandler(stdout, stderr);
        }
    }

    private static String gradlew(String arg) {
        return String.join(" ", "./gradlew", "--console=plain", arg);
    }

    @Override
    public void close() throws IOException {
        for (OutputStream stream : streams) {
            IOUtils.closeQuietly(stream);
        }
    }
}
