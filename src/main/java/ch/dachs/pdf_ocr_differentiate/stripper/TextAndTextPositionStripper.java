package ch.dachs.pdf_ocr_differentiate.stripper;

import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import ch.dachs.pdf_ocr_differentiate.core.TextLine;

/**
 * Extension of PDFTextStripper. Strips text and the word character positions.
 * 
 * @author Szőke Attila
 */
public class TextAndTextPositionStripper extends PDFTextStripper {
	
	private final List<TextLine> textLines;

	/**
	 * Basic constructor. Sets the result list.
	 * 
	 * @param documentImageCaptions result list
	 * @throws IOException thrown when the pdf cannot be processed
	 */
	public TextAndTextPositionStripper(List<TextLine> textLines) throws IOException {
		this.textLines = textLines;
	}

	/**
	 * Called when, getText is called. It is overridden so it extracts the text.
	 */
	@Override
	public void writeString(String text, List<TextPosition> textPositions) throws IOException {
		textLines.add(new TextLine(text, textPositions));
		super.writeString(text, textPositions);
	}
}