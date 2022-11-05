package net.sghill.flexibuild.discover;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.io.IoBuilder;

import java.io.OutputStream;

public class GradleWrapper implements BuildTool {
    
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
    public OutputStream stdout() {
        return IoBuilder.forLogger("gradle-out")
                .setLevel(Level.INFO)
                .buildOutputStream();
    }

    @Override
    public OutputStream stderr() {
        return IoBuilder.forLogger("gradle-err")
                .setLevel(Level.INFO)
                .buildOutputStream();
    }

    private static String gradlew(String arg) {
        return String.join(" ", "./gradlew", "--console=plain", arg);
    }
}
