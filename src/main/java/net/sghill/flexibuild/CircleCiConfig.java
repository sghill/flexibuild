package net.sghill.flexibuild;

import org.apache.logging.log4j.message.ParameterizedMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CircleCiConfig implements Config {
    @Override
    public Path outputDir() {
        Path path = Paths.get("/home/circleci/project/buildlab");
        try {
            Files.createDirectories(path);
            return path;
        } catch (IOException e) {
            Main.LOGGER.error(new ParameterizedMessage("Failed to create {}", path), e);
            throw new RuntimeException(e);
        }
    }
}
