package net.sghill.flexibuild.discover;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.io.IoBuilder;

import java.io.OutputStream;

import static net.sghill.flexibuild.Main.LOGGER;

public class NotFound implements BuildTool {
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
    public OutputStream stdout() {
        return IoBuilder.forLogger(LOGGER)
                .setLevel(Level.INFO)
                .buildOutputStream();
    }

    @Override
    public OutputStream stderr() {
        return IoBuilder.forLogger(LOGGER)
                .setLevel(Level.INFO)
                .buildOutputStream();
    }
}
