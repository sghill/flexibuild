package net.sghill.flexibuild.discover;

import org.apache.commons.exec.PumpStreamHandler;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;

public interface BuildTool extends Closeable {
    String smokeTestTool();
    String compile();
    String pkg();
    String test();

    PumpStreamHandler teeErrorsTo(Path path);

    @Override
    default void close() throws IOException {
    }
}
