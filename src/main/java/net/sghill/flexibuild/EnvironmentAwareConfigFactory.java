package net.sghill.flexibuild;

public class EnvironmentAwareConfigFactory {
    public static Config create() {
        boolean isCircle = "true".equalsIgnoreCase(System.getenv("CIRCLECI"));
        return isCircle ? new CircleCiConfig() : new DefaultConfig();
    }
}
