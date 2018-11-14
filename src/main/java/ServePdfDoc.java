import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.*;
import org.jfree.chart.ChartFactory;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;

import static com.itextpdf.text.BaseColor.BLACK;
import static com.itextpdf.text.BaseColor.BLUE;
import static com.itextpdf.text.Chunk.*;
import static com.itextpdf.text.Element.ALIGN_BOTTOM;
import static com.itextpdf.text.Font.BOLD;
import static com.itextpdf.text.Font.FontFamily.HELVETICA;
import static com.itextpdf.text.Font.NORMAL;
import static com.itextpdf.text.PageSize.A4;
import static com.itextpdf.text.Rectangle.NO_BORDER;
import static com.itextpdf.text.Rectangle.TOP;

public class ServePdfDoc {

    public static final FontFamily FONT_FAMILY = HELVETICA;
    public static final int FONT_SIZE = 12;
    public static final int A4_MARGIN = 20;
    public static final int PIE_CHART_HEIGHT = 200;
    public static final float PIE_CHART_WIDTH = A4.getWidth() - (A4_MARGIN * 2);

    public static void main(String[] args) {
        buildBenefitsPdf("/home/ralucaf/projects/servepdf/piechart.pdf", BLUE);
    }

    public static void buildBenefitsPdf(String fileName, BaseColor headlineColor) {

        Document document = new Document(A4, A4_MARGIN, A4_MARGIN, A4_MARGIN, A4_MARGIN);

        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            document.add(buildHeaderText("CASH COMPENSATION AND BENEFITS SUMMARY", headlineColor));
            document.add(NEWLINE);
            document.add(buildText("text text text text text text text text text text text text text text text text text text text text text text text text text text text text text "));
            document.add(NEWLINE);
            document.add(buildCompensationTable(headlineColor));
            document.add(NEWLINE);
            document.add(buildBenefitsTable(headlineColor));
            generatePieChart(pdfWriter.getDirectContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        document.close();
    }

    private static PdfPTable buildCompensationTable(BaseColor headlineColor) {
        PdfPTable table = new PdfPTable(new float[]{3, 1});
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100);
        table.addCell(buildHeaderCell("DIRECT COMPENSATION", headlineColor));
        table.addCell(buildHeaderCell("Amount", headlineColor));
        table.addCell(buildCell("Salary"));
        table.addCell(buildCell("$55656"));
        table.addCell(buildCell("Altcevry"));
        table.addCell(buildCell("$564556"));
        table.addCell(buildCell("Text"));
        table.addCell(buildCell("$15656"));
        table.addCell(buildTotalHeaderCell());
        table.addCell(buildTotalCell("$50"));
        return table;
    }

    private static PdfPTable buildBenefitsTable(BaseColor headlineColor) {
        PdfPTable table = new PdfPTable(new float[]{2, 1, 1, 1, 1});
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100);
        table.addCell(buildHeaderCell("BENEFITS", headlineColor));
        table.addCell(buildHeaderCell("Provider", headlineColor));
        table.addCell(buildHeaderCell("Level", headlineColor));
        table.addCell(buildHeaderCell("Your contribution", headlineColor));
        table.addCell(buildHeaderCell("Company contribution", headlineColor));
        table.addCell(buildCell("21 contribution contribution contribution contribution contribution contribution "));
        table.addCell(buildCell("21"));
        table.addCell(buildCell("213"));
        table.addCell(buildCell("213"));
        table.addCell(buildCell("21"));
        table.addCell(buildCell("Atceva contribution contribution contribution contribution contribution contribution "));
        table.addCell(buildCell("121"));
        table.addCell(buildCell("1213"));
        table.addCell(buildCell("213"));
        table.addCell(buildCell("21"));
        table.addCell(buildTotalHeaderCell());
        table.addCell(buildCell(""));
        table.addCell(buildCell(""));
        table.addCell(buildTotalCell("$50"));
        table.addCell(buildTotalCell("$43"));
        return table;
    }

    public static void generatePieChart(PdfContentByte contentByte) {
        DefaultPieDataset dataSet = new DefaultPieDataset();
        dataSet.setValue("Total salary", 19.64);
        dataSet.setValue("Flex Allowance", 17.3);
        dataSet.setValue("Company contribution", 4.54);
        dataSet.setValue("test", 3.4);
        dataSet.setValue("test2", 2.83);
        dataSet.setValue("test3", 2.48);
        dataSet.setValue("test4", 2.38);
        PdfTemplate pie = contentByte.createTemplate(PIE_CHART_WIDTH, PIE_CHART_HEIGHT);
        Graphics2D graphics2d = new PdfGraphics2D(pie, PIE_CHART_WIDTH, PIE_CHART_HEIGHT);
        Rectangle2D rectangle2d = new Rectangle2D.Float(0, 0, PIE_CHART_WIDTH, PIE_CHART_HEIGHT);
        ChartFactory.createPieChart("", dataSet, true, true, false).draw(graphics2d, rectangle2d);
        graphics2d.dispose();
        contentByte.addTemplate(pie, A4_MARGIN, PIE_CHART_HEIGHT);
    }

    public static PdfPCell buildHeaderCell(String text, BaseColor color) {
        PdfPCell cell = new PdfPCell(buildHeaderText(text, color));
        cell.setBorder(NO_BORDER);
        cell.setVerticalAlignment(ALIGN_BOTTOM);
        return cell;
    }

    public static PdfPCell buildCell(String content) {
        PdfPCell cell = new PdfPCell(buildText(content));
        cell.setBorder(NO_BORDER);
        return cell;
    }

    public static PdfPCell buildTotalHeaderCell() {
        PdfPCell cell = new PdfPCell(buildTextRoot("Total:", BLACK, BOLD));
        cell.setBorder(NO_BORDER);
        return cell;
    }

    public static PdfPCell buildTotalCell(String content) {
        PdfPCell cell = new PdfPCell(buildTextRoot(content, BLACK, BOLD));
        cell.setBorder(TOP);
        return cell;
    }

    private static Phrase buildHeaderText(String content, BaseColor color) {
        return buildTextRoot(content, color, BOLD);
    }

    private static Phrase buildText(String content) {
        return buildTextRoot(content, BLACK, NORMAL);
    }

    private static Phrase buildTextRoot(String content, BaseColor color, int fontStyle) {
        return new Phrase(content, new Font(FONT_FAMILY, FONT_SIZE, fontStyle, color));
    }

}

