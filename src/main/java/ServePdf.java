import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import static com.itextpdf.text.BaseColor.*;
import static com.itextpdf.text.Chunk.NEWLINE;
import static com.itextpdf.text.Element.ALIGN_BOTTOM;
import static com.itextpdf.text.Element.ALIGN_CENTER;
import static com.itextpdf.text.Element.ALIGN_MIDDLE;
import static com.itextpdf.text.Font.BOLD;
import static com.itextpdf.text.Font.FontFamily.HELVETICA;
import static com.itextpdf.text.Font.NORMAL;
import static com.itextpdf.text.PageSize.A4;
import static com.itextpdf.text.Rectangle.*;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import static java.util.Arrays.asList;
import static org.jfree.chart.ChartFactory.createPieChart;

public class ServePdf {

    public static final FontFamily FONT_FAMILY = HELVETICA;
    public static final int FONT_SIZE = 12;
    public static final int A4_MARGIN = 20;
    public static final int PIE_CHART_HEIGHT = 200;

    public static void main(String[] args) {
        try {

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

            new PDfDocBuilder(BLUE)
                    .withParagraph("CASH COMPENSATION AND BENEFITS SUMMARY", "text text text text text text text text text text text text text text text text text text text text text text text text text text text text text ")
                    .withParagraph("", "")
                    .withBenefitsTable(asList(new BenefitsTable().withBenefit("Medical Ins")))
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void buildBenefitsPdf(String fileName, BaseColor headlineColor) throws DocumentException, FileNotFoundException {
        pageLayout.addCell(buildCompensationTable(headlineColor));
        pageLayout.addCell(buildEmptyRow());
        pageLayout.addCell(buildBenefitsTable(headlineColor));
        pageLayout.addCell(buildEmptyRow());
        pageLayout.addCell(buildPieChartCell());
        pageLayout.addCell(buildEmptyRow());
        pageLayout.addCell(buildTotalEquation(headlineColor));
        document.add(pageLayout);
        document.close();
    }

    private static PdfPTable buildTotalEquation(BaseColor headlineColor) {
        PdfPTable table = new PdfPTable(new float[]{3, 1, 3, 1, 3, 1, 3});
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100);
        table.addCell(buildEquationCell("Total Salary", "$434343", headlineColor));
        table.addCell(buildAlignMiddleCell(buildTextDefault("+"), NO_BORDER));
        table.addCell(buildEquationCell("Flex Benefit", "$5434343", headlineColor));
        table.addCell(buildAlignMiddleCell(buildTextDefault("+"), NO_BORDER));
        table.addCell(buildEquationCell("Cie Contribution", "$434343", headlineColor));
        table.addCell(buildAlignMiddleCell(buildTextDefault("="), NO_BORDER));
        table.addCell(buildEquationCell("Total Reward", "$11434343", headlineColor));
        return table;
    }

    private static PdfPTable buildEquationCell(String aboveContent, String bellowContent, BaseColor headlineColor) {
        PdfPTable table = new PdfPTable(1);
        PdfPCell aboveCell = buildAlignMiddleCell(buildTextColored(aboveContent, WHITE), BOX);
        aboveCell.setBorderColor(headlineColor);
        aboveCell.setBackgroundColor(headlineColor);
        table.addCell(aboveCell);
        PdfPCell bellowCell = buildAlignMiddleCell(buildTextDefault(bellowContent), BOX);
        bellowCell.setBorderColor(headlineColor);
        table.addCell(bellowCell);
        return table;
    }

    private static PdfPCell buildAlignMiddleCell(Phrase phrase, int border) {
        PdfPCell cell = new PdfPCell(phrase);
        cell.setVerticalAlignment(ALIGN_MIDDLE);
        cell.setHorizontalAlignment(ALIGN_CENTER);
        cell.setPadding(5);
        cell.setBorder(border);
        return cell;
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
        table.addCell(buildCell("rand1 text"));
        table.addCell(buildCell("21"));
        table.addCell(buildCell("213"));
        table.addCell(buildCell("213"));
        table.addCell(buildCell("21"));
        table.addCell(buildCell("rand2 text"));
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
        PdfPCell cell = new PdfPCell(buildTextColored(text, color));
        cell.setBorder(NO_BORDER);
        cell.setVerticalAlignment(ALIGN_BOTTOM);
        return cell;
    }

    public static PdfPCell buildCell(String content) {
        PdfPCell cell = new PdfPCell(buildTextDefault(content));
        cell.setBorder(NO_BORDER);
        return cell;
    }

    public static PdfPCell buildTotalHeaderCell() {
        PdfPCell cell = new PdfPCell(buildText("Total:", BLACK, BOLD));
        cell.setBorder(NO_BORDER);
        return cell;
    }

    public static PdfPCell buildTotalCell(String content) {
        PdfPCell cell = new PdfPCell(buildText(content, BLACK, BOLD));
        cell.setBorder(TOP);
        return cell;
    }

    private static Phrase buildTextColored(String content, BaseColor color) {
        return buildText(content, color, BOLD);
    }

    private static Phrase buildTextDefault(String content) {
        return buildText(content, BLACK, NORMAL);
    }

    private static Phrase buildText(String content, BaseColor color, int fontStyle) {
        return new Phrase(content, new Font(FONT_FAMILY, FONT_SIZE, fontStyle, color));
    }

}

