package net.sghill.flexibuild.discover;

import net.sghill.flexibuild.Main;
import net.sghill.flexibuild.error.MavenStreamFilter;
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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Maven implements BuildTool {
    private final List<OutputStream> streams = new LinkedList<>();
    private final OutputStream stdout = IoBuilder.forLogger("mvn/out")
            .setLevel(Level.INFO)
            .buildOutputStream();
    private final OutputStream errors = new MavenStreamFilter(stdout);

    @Override
    public String smokeTestTool() {
        return mvn("help:effective-pom");
    }

    @Override
    public String compile() {
        return mvn("compile");
    }

    @Override
    public String pkg() {
        return mvn("-DskipTests=true", "package", "hpi:hpi");
    }

    @Override
    public String test() {
        return mvn("verify");
    }

    @Override
    public PumpStreamHandler teeErrorsTo(Path path) {
        OutputStream stdout = IoBuilder.forLogger("mvn/out")
                .setLevel(Level.INFO)
                .buildOutputStream();
        streams.add(stdout);
        try {
            OutputStream errorFile = Files.newOutputStream(path);
            streams.add(errorFile);
            TeeOutputStream teeError = new TeeOutputStream(stdout, new MavenStreamFilter(errorFile));
            streams.add(teeError);
            return new PumpStreamHandler(teeError);
        } catch (IOException e) {
            Main.LOGGER.error(new ParameterizedMessage("Failed to create {}", path), e);
            return new PumpStreamHandler(stdout);
        }
    }

    private static String mvn(String arg, String... rest) {
        List<String> args = new LinkedList<>();
        args.add("mvn");
        args.add("--batch-mode");
        args.add("--no-transfer-progress");
        args.add(arg);
        args.addAll(Arrays.asList(rest));
        return String.join(" ", args);
    }

    @Override
    public void close() throws IOException {
        for (OutputStream stream : streams) {
            IOUtils.closeQuietly(stream);
        }
    }
}
