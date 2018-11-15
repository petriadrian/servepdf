import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import static com.itextpdf.text.BaseColor.BLACK;
import static com.itextpdf.text.BaseColor.WHITE;
import static com.itextpdf.text.Chunk.NEWLINE;
import static com.itextpdf.text.Element.ALIGN_BOTTOM;
import static com.itextpdf.text.Element.ALIGN_CENTER;
import static com.itextpdf.text.Element.ALIGN_MIDDLE;
import static com.itextpdf.text.Font.BOLD;
import static com.itextpdf.text.Font.FontFamily.HELVETICA;
import static com.itextpdf.text.Font.NORMAL;
import static com.itextpdf.text.PageSize.A4;
import static com.itextpdf.text.Rectangle.BOX;
import static com.itextpdf.text.Rectangle.NO_BORDER;
import static com.itextpdf.text.Rectangle.TOP;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import static org.jfree.chart.ChartFactory.createPieChart;

public class PDfDocBuilder {

    private static final Font.FontFamily FONT_FAMILY = HELVETICA;
    private static final int FONT_SIZE = 12;
    private static final int A4_MARGIN = 20;
    private static final int PIE_CHART_HEIGHT = 200;
    private final PdfPTable pageLayout;
    private final Document document;
    private BaseColor headlineColor;

    public PDfDocBuilder(BaseColor headlineColor) throws FileNotFoundException, DocumentException {
        this.headlineColor = headlineColor;
        document = new Document(A4, A4_MARGIN, A4_MARGIN, A4_MARGIN, A4_MARGIN);
//        getInstance(document, new FileOutputStream("/home/ralucaf/projects/servepdf/piechart.pdf"));
        getInstance(document, new FileOutputStream("C:\\myprojects\\servepdf\\piechart.pdf"));
        document.open();
        pageLayout = new PdfPTable(1);
        pageLayout.setWidthPercentage(100);
        pageLayout.getDefaultCell().setBorder(0);
    }

    public PDfDocBuilder withParagraph(String title, String content) {
        PdfPCell cell = new PdfPCell();
        if (isNotBlank(title)) {
            cell.addElement(buildTextColored(title, headlineColor));
        }
        if (isNotBlank(content)) {
            cell.addElement(buildTextDefault(content));
        }
        cell.setBorder(NO_BORDER);
        pageLayout.addCell(cell);
        return this.withEmptyRow();
    }

    public PDfDocBuilder withEmptyRow() {
        PdfPCell cell = new PdfPCell();
        cell.addElement(NEWLINE);
        cell.setBorder(NO_BORDER);
        pageLayout.addCell(cell);
        return this;
    }

    public PDfDocBuilder withBenefitsTable(List<BenefitsRow> benefitsRows, String totalYourContribution, String totalCompanyContribution) {
        PdfPTable table = new PdfPTable(new float[]{2, 1, 1, 1, 1});
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100);
        table.addCell(buildHeaderCell("BENEFITS", headlineColor));
        table.addCell(buildHeaderCell("Plan", headlineColor));
        table.addCell(buildHeaderCell("Coverage", headlineColor));
        table.addCell(buildHeaderCell("Your contribution", headlineColor));
        table.addCell(buildHeaderCell("Company contribution", headlineColor));
        benefitsRows.forEach((item) -> {
            table.addCell(buildCell(item.getBenefit()));
            table.addCell(buildCell(item.getPlan()));
            table.addCell(buildCell(item.getCoverage()));
            table.addCell(buildCell(item.getYourContribution()));
            table.addCell(buildCell(item.getCompanyContribution()));
        });
        table.addCell(buildTotalHeaderCell());
        table.addCell(buildCell(""));
        table.addCell(buildCell(""));
        table.addCell(buildTotalCell(totalYourContribution));
        table.addCell(buildTotalCell(totalCompanyContribution));
        pageLayout.addCell(table);
        return this.withEmptyRow();
    }

    public PDfDocBuilder withCompensationTable(List<CompensationRow> benefitsTables, String totalAmount) {
        PdfPTable table = new PdfPTable(new float[]{3, 1});
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100);
        table.addCell(buildHeaderCell("Direct Compensation", headlineColor));
        table.addCell(buildHeaderCell("Amount", headlineColor));
        benefitsTables.forEach((item) -> {
            table.addCell(buildCell(item.getDirectCompensation()));
            table.addCell(buildCell(item.getAmount()));
        });
        table.addCell(buildTotalHeaderCell());
        table.addCell(buildTotalCell(totalAmount));
        pageLayout.addCell(table);
        return this.withEmptyRow();
    }

    public PDfDocBuilder withPieChart(List<InfoAndSize> infoAndSize) {
        DefaultPieDataset pie = new DefaultPieDataset();
        infoAndSize.forEach((item) -> pie.setValue(item.getInfo(), item.getSize()));
        PdfPCell cell = new PdfPCell();
        cell.setCellEvent(new JFreeChartEvent(createPieChart("", pie, true, true, false)));
        cell.setFixedHeight(PIE_CHART_HEIGHT);
        cell.setBorder(NO_BORDER);
        pageLayout.addCell(cell);
        return this.withEmptyRow();
    }

    public PDfDocBuilder withTotalEquation(String totalSalary, String flexBenefit, String cieContribution, String totalReward) {
        PdfPTable table = new PdfPTable(new float[]{3, 1, 3, 1, 3, 1, 3});
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100);
        table.addCell(buildEquationCell("Total Salary", totalSalary, headlineColor));
        table.addCell(buildAlignMiddleCell(buildTextDefault("+"), NO_BORDER));
        table.addCell(buildEquationCell("Flex Benefit", flexBenefit, headlineColor));
        table.addCell(buildAlignMiddleCell(buildTextDefault("+"), NO_BORDER));
        table.addCell(buildEquationCell("Cie Contribution", cieContribution, headlineColor));
        table.addCell(buildAlignMiddleCell(buildTextDefault("="), NO_BORDER));
        table.addCell(buildEquationCell("Total Reward", totalReward, headlineColor));
        pageLayout.addCell(table);
        return this.withEmptyRow();
    }

    public void build() throws DocumentException {
        document.add(pageLayout);
        document.close();
    }

    private PdfPCell buildTotalHeaderCell() {
        PdfPCell cell = new PdfPCell(buildText("Total:", BLACK, BOLD));
        cell.setBorder(NO_BORDER);
        return cell;
    }

    private PdfPCell buildTotalCell(String content) {
        PdfPCell cell = new PdfPCell(buildText(content, BLACK, BOLD));
        cell.setBorder(TOP);
        return cell;
    }

    private PdfPTable buildEquationCell(String aboveContent, String bellowContent, BaseColor headlineColor) {
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

    private PdfPCell buildHeaderCell(String text, BaseColor color) {
        PdfPCell cell = new PdfPCell(buildTextColored(text, color));
        cell.setBorder(NO_BORDER);
        cell.setVerticalAlignment(ALIGN_BOTTOM);
        return cell;
    }

    private PdfPCell buildCell(String content) {
        PdfPCell cell = new PdfPCell(buildTextDefault(content));
        cell.setBorder(NO_BORDER);
        return cell;
    }

    private Phrase buildTextColored(String content, BaseColor color) {
        return buildText(content, color, BOLD);
    }

    private Phrase buildTextDefault(String content) {
        return buildText(content, BLACK, NORMAL);
    }

    private Phrase buildText(String content, BaseColor color, int fontStyle) {
        return new Phrase(content, new Font(FONT_FAMILY, FONT_SIZE, fontStyle, color));
    }

    private boolean isBlank(String text) {
        return !isNotBlank(text);
    }

    private boolean isNotBlank(String text) {
        return text != null && !"".equals(text);
    }

}

class InfoAndSize {
    private String info = "";
    private float size = 0;

    public InfoAndSize withInfo(String info) {
        this.info = info;
        return this;
    }

    public InfoAndSize withSize(float size) {
        this.size = size;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public float getSize() {
        return size;
    }
}

class CompensationRow {
    private String directCompensation = "";
    private String amount = "";

    public CompensationRow withDirectCompensation(String directCompensation) {
        this.directCompensation = directCompensation;
        return this;
    }

    public CompensationRow withAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public String getDirectCompensation() {
        return directCompensation;
    }

    public String getAmount() {
        return amount;
    }
}

class BenefitsRow {
    private String benefit = "";
    private String plan = "";
    private String coverage = "";
    private String yourContribution = "";
    private String companyContribution = "";

    public BenefitsRow withBenefit(String benefit) {
        this.benefit = benefit;
        return this;
    }

    public BenefitsRow withPlan(String plan) {
        this.plan = plan;
        return this;
    }

    public BenefitsRow withCoverage(String coverage) {
        this.coverage = coverage;
        return this;
    }

    public BenefitsRow withYourContribution(String yourContribution) {
        this.yourContribution = yourContribution;
        return this;
    }

    public BenefitsRow withCompanyContribution(String companyContribution) {
        this.companyContribution = companyContribution;
        return this;
    }

    public String getBenefit() {
        return benefit;
    }

    public String getPlan() {
        return plan;
    }

    public String getCoverage() {
        return coverage;
    }

    public String getYourContribution() {
        return yourContribution;
    }

    public String getCompanyContribution() {
        return companyContribution;
    }
}

class JFreeChartEvent implements PdfPCellEvent {
    private JFreeChart chart;

    public JFreeChartEvent(final JFreeChart chart) {
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