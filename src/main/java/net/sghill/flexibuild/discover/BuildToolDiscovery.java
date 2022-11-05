package net.sghill.flexibuild.discover;

import org.apache.logging.log4j.message.ParameterizedMessage;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static net.sghill.flexibuild.Main.LOGGER;

public class BuildToolDiscovery {
    
    private final Path root;

    public BuildToolDiscovery(Path root) {
        this.root = root;
    }

    public BuildTool discover() {
        BuildToolVisitor visitor = new BuildToolVisitor(root);
        try {
            Files.walkFileTree(root, visitor);
            return visitor.tool;
        } catch (IOException e) {
            LOGGER.error(new ParameterizedMessage("Failed to walk {}", root), e);
            return new DiscoveryError();
        }
    }
    
    private static class BuildToolVisitor extends SimpleFileVisitor<Path> {
        private final Path root;
        private BuildTool tool = new NotFound();

        private BuildToolVisitor(Path root) {
            this.root = root;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            if (dir.equals(root)) {
                return FileVisitResult.CONTINUE;
            }
            return FileVisitResult.SKIP_SUBTREE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            String fileName = file.getFileName().toString();
            if ("build.gradle".equals(fileName) || "build.gradle.kts".equals(fileName)) {
                tool = new GradleWrapper();
                return FileVisitResult.TERMINATE;
            }
            if ("pom.xml".equals(fileName)) {
                tool = new Maven();
                return FileVisitResult.TERMINATE;
            }
            return FileVisitResult.CONTINUE;
        }
    }
}
