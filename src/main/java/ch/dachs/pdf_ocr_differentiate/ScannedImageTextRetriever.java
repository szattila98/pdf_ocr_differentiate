package ch.dachs.pdf_ocr_differentiate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import ch.dachs.pdf_ocr_differentiate.core.ImageInfo;
import ch.dachs.pdf_ocr_differentiate.core.TextLine;
import ch.dachs.pdf_ocr_differentiate.stripper.ImageStripper;
import ch.dachs.pdf_ocr_differentiate.stripper.TextAndTextPositionStripper;

/**
 * Retrieves scanned image text from the document.
 * 
 * @author Szőke Attila
 */
public class ScannedImageTextRetriever {

	/**
	 * Retrieves scanned image text from the given document.
	 * 
	 * @param path the PDF doc path
	 * @return the list of text lines
	 * @throws IOException thrown when PDF cannot be processed
	 */
	public List<List<TextLine>> retrieve(String path) throws IOException {
		// open document
		try (var doc = PDDocument.load(new File(path))) {
			int numberOfPages = doc.getNumberOfPages();
			List<List<TextLine>> documentTextLinesPerImage = new ArrayList<>();
			for (var currentPageNum = 1; currentPageNum < numberOfPages + 1; currentPageNum++) {
				// retrieve images from the page
				var pageImages = new ArrayList<ImageInfo>();
				new ImageStripper(pageImages).processPage(doc.getPage(currentPageNum - 1));
				// retrieve text lines only if there is an image on the page
				if (pageImages.isEmpty()) {
					continue;
				}
				var pageTextLines = new ArrayList<TextLine>();
				var stripper = new TextAndTextPositionStripper(pageTextLines);
				stripper.setStartPage(currentPageNum);
				stripper.setEndPage(currentPageNum);
				stripper.getText(doc);
				// only retain text lines which are contained by the image
				var imageText = new ArrayList<TextLine>();
				for (var image : pageImages) {
					pageTextLines.stream().filter(textLine -> textIsInImage(image, textLine)).forEach(imageText::add);
				}
				documentTextLinesPerImage.add(imageText);
			}
			return documentTextLinesPerImage;
		}
	}

	private boolean textIsInImage(ImageInfo imageInfo, TextLine textLine) {
		return textLine.getFirstCharacterXPosition() > imageInfo.getBottomLeftX()
				&& textLine.getFirstCharacterXPosition() < imageInfo.getTopRightX()
				&& textLine.getLastCharacterXPosition() > imageInfo.getBottomLeftX()
				&& textLine.getLastCharacterXPosition() < imageInfo.getTopRightX()
				&& textLine.getYPosition() > imageInfo.getBottomLeftY()
				&& textLine.getYPosition() < imageInfo.getTopRightY();
	}
}
