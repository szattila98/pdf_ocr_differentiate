package ch.dachs.pdf_ocr_differentiate.core;

import java.util.List;

import org.apache.pdfbox.text.TextPosition;

import lombok.Data;

@Data
public class TextLine {
	private static final String SEPARATOR = " ";
	
	private String text;
	private List<TextPosition> characterPositions;
	private float firstCharacterXPosition;
	private float lastCharacterXPosition;
	private float yPosition;
	
	public TextLine(String text, List<TextPosition> characterPositions) {
		this.text = text;
		this.characterPositions = characterPositions;
		this.firstCharacterXPosition = characterPositions.get(0).getTextMatrix().getTranslateX();
		this.lastCharacterXPosition = characterPositions.get(characterPositions.size() - 1).getTextMatrix().getTranslateX();
		this.yPosition = characterPositions.get(0).getTextMatrix().getTranslateY();
	}
	
	public void concatTextLine(TextLine textLine) {
		this.text = this.text.concat(SEPARATOR + textLine.getText());
		this.characterPositions.addAll(textLine.getCharacterPositions());
		this.firstCharacterXPosition = characterPositions.get(0).getTextMatrix().getTranslateX();
		this.lastCharacterXPosition = characterPositions.get(characterPositions.size() - 1).getTextMatrix().getTranslateX();
		this.yPosition = characterPositions.get(0).getTextMatrix().getTranslateY();
	}
	
	@Override
	public String toString() {
		return text;
	}
}
