package ch.dachs.pdf_ocr_differentiate.core;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Contains image metadata, which is needed to couple images to captions.
 * 
 * @author Szőke Attila
 */
@Data
@AllArgsConstructor
public class ImageInfo {
	private float bottomLeftX;
	private float bottomLeftY; 
	private int topRightX;
	private int topRightY;
}
