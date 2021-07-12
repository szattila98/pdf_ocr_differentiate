package ch.dachs.pdf_ocr.stripper;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

/**
 * Extension of PDFTextStripper. Strips text and the word character positions.
 * 
 * @author Szőke Attila
 */
public class TextAndTextPositionStripper extends PDFTextStripper {
	
	private final List<String> textLines;

	/**
	 * Basic constructor. Sets the result list.
	 * 
	 * @param documentImageCaptions result list
	 * @throws IOException thrown when the pdf cannot be processed
	 */
	public TextAndTextPositionStripper(List<String> textLines) throws IOException {
		this.textLines = textLines;
	}

	/**
	 * Called when, getText is called. It is overridden so it extracts the text.
	 */
	@Override
	public void writeString(String text, List<TextPosition> textPositions) throws IOException {
		textLines.add(text.trim());
		super.writeString(text, textPositions);
	}
}