package net.sghill.flexibuild.discover;

import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.io.IoBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import static net.sghill.flexibuild.Main.LOGGER;

public class NotFound implements BuildTool {
    private final List<OutputStream> streams = new LinkedList<>();
    private static final String NOT_FOUND = "echo '[ERROR] no build tool found' && exit 222";
    
    @Override
    public String smokeTestTool() {
        return NOT_FOUND;
    }

    @Override
    public String compile() {
        return NOT_FOUND;
    }

    @Override
    public String pkg() {
        return NOT_FOUND;
    }

    @Override
    public String test() {
        return NOT_FOUND;
    }

    @Override
    public PumpStreamHandler teeErrorsTo(Path path) {
        OutputStream outputStream = IoBuilder.forLogger(LOGGER)
                .setLevel(Level.INFO)
                .buildOutputStream();
        streams.add(outputStream);
        return new PumpStreamHandler(outputStream);
    }

    @Override
    public void close() throws IOException {
        for (OutputStream stream : streams) {
            IOUtils.closeQuietly(stream);
        }
    }
}
