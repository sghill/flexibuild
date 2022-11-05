package net.sghill.flexibuild.discover;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.io.IoBuilder;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
        return mvn("package", "hpi:hpi");
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

    private static String mvn(String arg, String... rest) {
        List<String> args = new LinkedList<>();
        args.add("mvn");
        args.add("--batch-mode");
        args.add("--no-transfer-progress");
        args.add(arg);
        args.addAll(Arrays.asList(rest));
        return String.join(" ", args);
    }
}
