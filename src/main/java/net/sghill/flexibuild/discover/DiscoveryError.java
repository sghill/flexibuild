package net.sghill.flexibuild.discover;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.io.IoBuilder;

import java.io.OutputStream;

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
    public OutputStream stdout() {
        return IoBuilder.forLogger("discovery-out")
                .setLevel(Level.INFO)
                .buildOutputStream();
    }

    @Override
    public OutputStream stderr() {
        return IoBuilder.forLogger("discovery-err")
                .setLevel(Level.INFO)
                .buildOutputStream();
    }
}
