package net.sghill.flexibuild;

import java.nio.file.Path;
import java.nio.file.Paths;

public class DefaultConfig implements Config {
    @Override
    public Path outputDir() {
        return Paths.get(".");
    }
}
