package tech.ryanqyang;

import java.io.File;

public interface Splittable {
    public int getFrameCount();
    public int getDelay();
    public void splitIntoFrames(File outputLocation);
}
