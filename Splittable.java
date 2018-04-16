package tech.ryanqyang;

import java.io.File;

public interface Splittable {
    int getFrameCount();
    int getDelay();
    void splitIntoFrames(File outputLocation);
}
