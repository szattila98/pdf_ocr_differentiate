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

	private static final int MAX_WORD_OFFSET_Y = 5;
	private static final int MAX_WORD_OFFSET_X = 15;

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
		var trimmed = text.trim();
		if (!textLines.isEmpty()) {
			var lastLine = textLines.get(textLines.size() - 1);
			var newLine = new TextLine(trimmed, textPositions);
			// coupling text that should be one line but pdfbox broke it up
			if (Math.abs(newLine.getYPosition() - lastLine.getYPosition()) < MAX_WORD_OFFSET_Y && newLine.getFirstCharacterXPosition()
					- lastLine.getLastCharacterXPosition() < MAX_WORD_OFFSET_X) {
				lastLine.concatTextLine(newLine);
			} else {
				textLines.add(new TextLine(trimmed, textPositions));
			}
		} else {
			textLines.add(new TextLine(trimmed, textPositions));
		}
		super.writeString(trimmed, textPositions);
	}
}