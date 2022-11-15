package net.sghill.flexibuild.discover;

import org.apache.commons.exec.PumpStreamHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.io.IoBuilder;

import java.io.OutputStream;
import java.nio.file.Path;

import static net.sghill.flexibuild.Main.LOGGER;

public class DiscoveryError implements BuildTool {
    private static final String ERROR = "echo '[ERROR] filesystem error during discovery' && exit 223";
    
    @Override
    public String smokeTestTool() {
        return ERROR;
    }

    @Override
    public String compile() {
        return ERROR;
    }

    @Override
    public String pkg() {
        return ERROR;
    }

    @Override
    public String test() {
        return ERROR;
    }

    @Override
    public PumpStreamHandler teeErrorsTo(Path path) {
        OutputStream outputStream = IoBuilder.forLogger(LOGGER)
                .setLevel(Level.INFO)
                .buildOutputStream();
        return new PumpStreamHandler(outputStream);
    }
}
