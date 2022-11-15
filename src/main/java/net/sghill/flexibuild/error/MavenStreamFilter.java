package net.sghill.flexibuild.error;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class MavenStreamFilter extends FilterOutputStream {
    private static final Set<String> PREFIXES = Stream.of(
            "[ERROR]",
            "[FATAL]",
            "[WARNING]"
    ).collect(Collectors.toSet());
    private boolean lineUnfinished = false;
    public MavenStreamFilter(OutputStream out) {
        super(out);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        String input = new String(b, off, len, UTF_8);
        String[] lines = input.split("\n");
        for (String line : lines) {
            if (lineUnfinished || PREFIXES.stream().anyMatch(line::startsWith)) {
                byte[] bytes = (line + "\n").getBytes(UTF_8);
                super.write(bytes, 0, bytes.length);
            }
        }
        String last = lines[lines.length - 1];
        lineUnfinished = !last.endsWith("\n");
    }

}
