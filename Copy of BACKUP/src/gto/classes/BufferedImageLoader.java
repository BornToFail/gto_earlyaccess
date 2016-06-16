package gto.classes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	private BufferedImage image;

	//loads in buffered images
	public BufferedImage loadImage(String path) throws IOException{

		image = ImageIO.read(getClass().getResource(path));
//		image = ImageIO.read(new File("src/gto/res" + path));

		return image;
	}


}
