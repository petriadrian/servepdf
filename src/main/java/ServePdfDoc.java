import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
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
import static org.jfree.chart.ChartFactory.createPieChart;

public class ServePdfDoc {

    public static final FontFamily FONT_FAMILY = HELVETICA;
    public static final int FONT_SIZE = 12;
    public static final int A4_MARGIN = 20;
    public static final int PIE_CHART_HEIGHT = 200;

    public static void main(String[] args) {
        buildBenefitsPdf("/home/ralucaf/projects/servepdf/piechart.pdf", BLUE);
    }

    public static void buildBenefitsPdf(String fileName, BaseColor headlineColor) {

        Document document = new Document(A4, A4_MARGIN, A4_MARGIN, A4_MARGIN, A4_MARGIN);
        document.open();
        PdfPTable pageLayout = new PdfPTable(1);
        pageLayout.setWidthPercentage(100);
        pageLayout.getDefaultCell().setBorder(0);
        pageLayout.addCell(buildIntroText());
        pageLayout.addCell(buildPieChartCell());
        document.close();


        document.add();
            document.add(NEWLINE);
            document.add(buildText("text text text text text text text text text text text text text text text text text text text text text text text text text text text text text "));
            document.add(NEWLINE);
            document.add(buildCompensationTable(headlineColor));
            document.add(NEWLINE);
            document.add(buildBenefitsTable(headlineColor));
            document.add(NEWLINE);
            document.add(pageLayout);
//            buildPieChart(pdfWriter.getDirectContent());
        }

    private static String buildIntroText(BaseColor headlineColor) {
        PdfPCell cell = new PdfPCell();
        cell.addElement(buildHeaderText("CASH COMPENSATION AND BENEFITS SUMMARY", headlineColor));
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

    public static PdfPCell buildPieChartCell() {
        DefaultPieDataset dataSet = new DefaultPieDataset();
        dataSet.setValue("Total salary", 19.64);
        dataSet.setValue("Flex Allowance", 17.3);
        dataSet.setValue("Company contribution", 4.54);
        PdfPCell cell = new PdfPCell();
        cell.setCellEvent(new JFreeChartEvent(createPieChart("", dataSet, true, true, false)));
        cell.setFixedHeight(PIE_CHART_HEIGHT);
        cell.setBorder(NO_BORDER);
        return cell;
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

class JFreeChartEvent implements PdfPCellEvent {
    private JFreeChart chart;

    public JFreeChartEvent(final JFreeChart chart){
        this.chart = chart;
    }

    public void cellLayout(PdfPCell pdfPCell, Rectangle rectangle, PdfContentByte[] pdfContentBytes) {
        PdfContentByte cb = pdfContentBytes[PdfPTable.TEXTCANVAS]; //optional, can be other canvas
        PdfTemplate pie = cb.createTemplate(rectangle.getWidth(), rectangle.getHeight());
        Graphics2D g2d1 = new PdfGraphics2D(pie, rectangle.getWidth(), rectangle.getHeight());
        Rectangle2D r2d1 = new Rectangle2D.Double(0, 0, rectangle.getWidth(), rectangle.getHeight());
        chart.draw(g2d1, r2d1);
        g2d1.dispose();
        cb.addTemplate(pie, rectangle.getLeft(), rectangle.getBottom());
    }

}

