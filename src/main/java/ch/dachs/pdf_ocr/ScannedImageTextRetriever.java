package ch.dachs.pdf_ocr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import ch.dachs.pdf_ocr.stripper.ImageStripper;
import ch.dachs.pdf_ocr.stripper.TextAndTextPositionStripper;

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
	public List<String> retrieve(String path) throws IOException {
		// open document
		try (var doc = PDDocument.load(new File(path))) {
			int numberOfPages = doc.getNumberOfPages();
			List<List<String>> documentTextLines = new ArrayList<>();
			for (var currentPageNum = 1; currentPageNum < numberOfPages + 1; currentPageNum++) {	
				// retrieve images
				var pageImages = new ArrayList<PDImageXObject>();
				new ImageStripper(pageImages).processPage(doc.getPage(currentPageNum - 1));
				if (pageImages.isEmpty()) {
					continue;
				}
				// retrieve text lines only if there is an image on the page
				var pageTextLines = new ArrayList<String>();
				var stripper = new TextAndTextPositionStripper(pageTextLines);
				stripper.setStartPage(currentPageNum);
				stripper.setEndPage(currentPageNum);
				stripper.getText(doc);
				documentTextLines.add(pageTextLines);
			}
			return documentTextLines.stream().flatMap(List::stream).collect(Collectors.toList());
		}
	}
}
