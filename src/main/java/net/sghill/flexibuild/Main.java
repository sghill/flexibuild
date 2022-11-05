package net.sghill.flexibuild;

import net.sghill.flexibuild.discover.BuildTool;
import net.sghill.flexibuild.discover.BuildToolDiscovery;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class Main {
    public static final Logger LOGGER = LogManager.getLogger("flexibuild");
    private final BuildToolDiscovery discovery;

    public Main(BuildToolDiscovery discovery) {
        this.discovery = discovery;
    }

    public static void main(String[] args) {
        Path cwd = Paths.get("");
        BuildToolDiscovery discovery = new BuildToolDiscovery(cwd);
        new Main(discovery).run(args);
    }
    
    public void run(String[] args) {
        BuildTool tool = discovery.discover();
        String commandLine = "echo '[ERROR] command not found' && exit 224";
        switch (args[0]) {
            case "smoke-test":
                commandLine = tool.smokeTestTool();
                break;
            case "compile":
                commandLine = tool.compile();
                break;
            case "package":
                commandLine = tool.pkg();
                break;
            case "test":
                commandLine = tool.test();
                break;
        }
        CommandLine parsed = CommandLine.parse(commandLine);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(new PumpStreamHandler(tool.stdout(), tool.stderr()));
        ExecuteWatchdog watchdog = new ExecuteWatchdog(TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS));
        executor.setWatchdog(watchdog);
        try {
            LOGGER.info(commandLine);
            int exit = executor.execute(parsed);
            System.exit(exit);
        } catch (IOException e) {
            LOGGER.error("Failed to run", e);
            System.exit(225);
        }
    }
}
