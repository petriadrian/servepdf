import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.*;
import org.jfree.data.general.DefaultPieDataset;

import java.io.FileNotFoundException;

import static com.itextpdf.text.BaseColor.*;
import static com.itextpdf.text.Element.ALIGN_BOTTOM;
import static com.itextpdf.text.Element.ALIGN_CENTER;
import static com.itextpdf.text.Element.ALIGN_MIDDLE;
import static com.itextpdf.text.Font.BOLD;
import static com.itextpdf.text.Font.FontFamily.HELVETICA;
import static com.itextpdf.text.Font.NORMAL;
import static com.itextpdf.text.Rectangle.*;
import static com.itextpdf.text.pdf.PdfWriter.getInstance;
import static java.util.Arrays.asList;
import static org.jfree.chart.ChartFactory.createPieChart;

public class ServePdf {

    public static void main(String[] args) {
        try {

            new PDfDocBuilder(BLUE)
                    .withParagraph("CASH COMPENSATION AND BENEFITS SUMMARY", "text text text text text text text text text text text text text text text text text text text text text text text text text text text text text ")
                    .withCompensationTable(asList(
                            new CompensationRow()
                                    .withDirectCompensation("Salary")
                                    .withAmount("$55656"),
                            new CompensationRow()
                                    .withDirectCompensation("Test")
                                    .withAmount("$55656")),
                            "$454545")
                    .withBenefitsTable(asList(
                            new BenefitsRow()
                                    .withBenefit("text")
                                    .withPlan("plan")
                                    .withYourContribution("$5454")
                                    .withYourContribution("$5454"),
                            new BenefitsRow().withBenefit("ben2")
                                    .withYourContribution("$5454")
                                    .withYourContribution("$5454")),
                            "$4343", "$4343")
                    .withPieChart(asList(
                            new InfoAndSize()
                                    .withInfo("Total salary")
                                    .withSize(23.43f),
                            new InfoAndSize()
                                    .withInfo("Total alc")
                                    .withSize(34f)))
                    .withTotalEquation("$454", "$454", "$454", "$454")
                    .build();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

