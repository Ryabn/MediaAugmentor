package tech.ryanqyang;

public class FrameData {

    private int frameIndex;

    public FrameData(int frameIndex){
        this.frameIndex = frameIndex;
    }

    /**
     * returns frame index in media that data refers to
     *
     * @return frameIndex
     */
    public int getFrameIndex() {
        return frameIndex;
    }


}
