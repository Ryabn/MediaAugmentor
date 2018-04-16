# MediaAugmentor

Java program that takes a media input (currently only GIF's supported) and doubles
 the frame rate of the media by generating frames in between frames.
<br>

The basic algorithm generates a frame by averaging pixel values between frames. The frame
generated looks like setting the transparency of both frames to 50% and overlaying them on
top of each other.
<br>

The basic algorithm can be called this way (in the main method):

***

First create a File object with a path to the media file you want to enhance
>File testFile = new File("/file/to/image.gif");

Then, create a BasicEnhanceAlgorithm object with the file

> BasicEnhanceAlgorithm test = new BasicEnhanceAlgorithm( testFile );

Finally, call the enhance method
> test.enhance();

***
Example of basic algorithm (image taken from imgur i forgot the source please don't sue):

Before / After
<br>

<img style="width:48%; float:left;" src="assets/basicGifTest.gif"/>
<img style="width:48%; float:left;" src="assets/output/Mon Apr 16 15:06:08 PDT 2018.gif" />

