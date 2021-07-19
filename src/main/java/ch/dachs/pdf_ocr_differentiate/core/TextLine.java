package ch.dachs.pdf_ocr_differentiate.core;

import java.util.List;

import org.apache.pdfbox.text.TextPosition;

import lombok.Data;

/**
 * Represents a line of text stripped from the pdf.
 * 
 * @author Szőke Attila
 */
@Data
public class TextLine {
	private static final String SEPARATOR = " ";

	private String text;
	private List<TextPosition> characterPositions;
	private float firstCharacterXPosition;
	private float lastCharacterXPosition;
	private float yPosition;

	/**
	 * Basic constructor. Adds text, character text positions and sets position data
	 * with the help of character positions.
	 * 
	 * @param text               the text of the line
	 * @param characterPositions the character position information
	 */
	public TextLine(String text, List<TextPosition> characterPositions) {
		this.text = text;
		this.characterPositions = characterPositions;
		setPositionData();
	}

	/**
	 * Gets the first characters position data.
	 * 
	 * @return first character position
	 */
	public TextPosition firstCharacter() {
		return characterPositions.get(0);
	}

	/**
	 * Gets the last characters position data.
	 * 
	 * @return last character position
	 */
	public TextPosition lastCharacter() {
		return characterPositions.get(characterPositions.size() - 1);
	}

	/**
	 * Concats another line to this line.
	 * 
	 * @param textLine the line to concat
	 */
	public void concatTextLine(TextLine textLine) {
		this.text = this.text.concat(SEPARATOR + textLine.getText());
		this.characterPositions.addAll(textLine.getCharacterPositions());
		setPositionData();
	}

	/**
	 * Sets text position data for the text line based on character text positions.
	 */
	public void setPositionData() {
		this.firstCharacterXPosition = firstCharacter().getTextMatrix().getTranslateX();
		this.lastCharacterXPosition = lastCharacter().getTextMatrix().getTranslateX();
		this.yPosition = firstCharacter().getTextMatrix().getTranslateY();
	}

	@Override
	public String toString() {
		return text;
	}
}
