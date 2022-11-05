package net.sghill.flexibuild.discover;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.io.IoBuilder;

import java.io.OutputStream;

public class Maven implements BuildTool {
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
        return mvn("hpi:hpi");
    }

    @Override
    public String test() {
        return mvn("verify");
    }

    @Override
    public OutputStream stdout() {
        return IoBuilder.forLogger("mvn/out")
                .setLevel(Level.INFO)
                .buildOutputStream();
    }

    @Override
    public OutputStream stderr() {
        return IoBuilder.forLogger("mvn/err")
                .setLevel(Level.INFO)
                .buildOutputStream();
    }

    private static String mvn(String arg) {
        return String.join(" ", "mvn", "--batch-mode", "--no-transfer-progress", arg);
    }
}
