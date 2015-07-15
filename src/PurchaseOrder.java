import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/** Class used to create the purchase orders **/

public class PurchaseOrder {
	private static DatabaseConnection db;
	private static ArrayList<Integer> productID;
    private static ArrayList<String> productName;
    private static ArrayList<Integer> productQuantity;
    private static ArrayList<Integer> productThreshold;
    private static ArrayList<Integer> productPrice;
    private static int totalPrice;
	static DateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");    
	static Date date = new Date();
	static String fileDate = dateFormat.format(date);
  static String FILE = fileDate + "PurchaseOrder.pdf";
  private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
      Font.BOLD);
  private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
      Font.BOLD);
  private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
      Font.BOLD);


  
  public PurchaseOrder(){
		db = new DatabaseConnection();
		db.accessDB();
		db.readDB();
					
		productID = db.getProductID();
		productName = db.getProductName();
		productQuantity = db.getProductQuantity();
		productThreshold = db.getProductThreshold();
		productPrice = db.getProductPrice();
	  
	  try {
	      Document document = new Document();
	      PdfWriter.getInstance(document, new FileOutputStream(FILE));
	      document.open();
	      addMetaData(document);
	      addTitlePage(document);
	      addContent(document);
	      document.close();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}
  

  private static void addMetaData(Document document) {
    document.addTitle("Stock Report");
    document.addSubject("Using iText");
    document.addKeywords("Java, PDF, iText");
    document.addAuthor("Jordan Mander");
  }

  private static void addTitlePage(Document document)
      throws DocumentException {
    Paragraph preface = new Paragraph();
    // We add one empty line
    addEmptyLine(preface, 1);
    // Lets write a big header
    preface.add(new Paragraph("NB Gardens Purchase Order", catFont));

    addEmptyLine(preface, 1);
    // Will create: Report generated by: _name, _date
    preface.add(new Paragraph("Report generated : " + new Date(),
        smallBold));
    addEmptyLine(preface, 3);
    preface.add(new Paragraph("A purchase order for products in low stock generated by the Inventory Management System",
        smallBold));

    document.add(preface);
    // Start a new page
    document.newPage();
  }

  private static void addContent(Document document) throws DocumentException {
    Anchor anchor = new Anchor("NB Gardens", catFont);
    anchor.setName("NB Gardens");

    // Second parameter is the number of the chapter
    Chapter catPart = new Chapter(new Paragraph(anchor), 1);

    Paragraph subPara = new Paragraph("Receipt", subFont);
    Section subCatPart = catPart.addSection(subPara);


    Paragraph paragraph = new Paragraph();
    addEmptyLine(paragraph, 5);
    subCatPart.add(paragraph);

    // add a table
    createTable(subCatPart);

    // now add all this to the document
    document.add(catPart);

  }

  private static void createTable(Section subCatPart)
      throws BadElementException {	  
    PdfPTable table = new PdfPTable(5);
    Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
    
    PdfPCell c1 = new PdfPCell(new Phrase("ID", font));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase("Product Name", font));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase("Re-order Quantity", font));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(c1);
    table.setHeaderRows(1);
    
    c1 = new PdfPCell(new Phrase("Price per Item", font));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(c1);

    c1 = new PdfPCell(new Phrase("Total Price", font));
    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(c1);
    table.setHeaderRows(1);
    
    totalPrice =0;
    
	for(int i=0; i<productID.size(); i++){
		if(productQuantity.get(i)<productThreshold.get(i)){
			table.addCell(Integer.toString(productID.get(i)));
	    	table.addCell(productName.get(i));
	    	table.addCell(Integer.toString (AppLoader.maxStock - (productQuantity.get(i))));
	    	table.addCell("�" + Integer.toString(productPrice.get(i)) + ".00");
	    	table.addCell("�" + Integer.toString(productPrice.get(i) * ((AppLoader.maxStock - (productQuantity.get(i))))) + ".00");
	    	totalPrice+=(productPrice.get(i) * ((AppLoader.maxStock - (productQuantity.get(i)))));
		}
	}
	
	table.addCell("");
	table.addCell("");
	table.addCell("");
	table.addCell("");
	table.addCell("�" + Integer.toString(totalPrice) + ".00");
	
    subCatPart.add(table);

  }


  private static void addEmptyLine(Paragraph paragraph, int number) {
    for (int i = 0; i < number; i++) {
      paragraph.add(new Paragraph(" "));
    }
  }
} 