package net.sghill.flexibuild.discover;

import java.io.OutputStream;

public interface BuildTool {
    String smokeTestTool();
    String compile();
    String pkg();
    String test();
    
    OutputStream stdout();
    OutputStream stderr();
}
