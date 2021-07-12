package ch.dachs.pdf_ocr;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Main class of the application.
 * 
 * @author SzokeAttila
 */
public class App {

	private static final String PDF_PATH_ERR_MSG = "Please specify a pdf path to continue with the extraction!";
	private static final String FILE_OPERATION_ERR_MSG = "File cound not be opened/parsed/written!";
	private static final String SUCCESS_MSG = "OCR image differentiated from real text and extracted to new PDF document!";

	private static final Log logger = LogFactory.getLog(App.class);

	public static void main(String[] args) {
		if (args.length == 0) {
			logger.error(PDF_PATH_ERR_MSG);
			return;
		}
		
	}
}
