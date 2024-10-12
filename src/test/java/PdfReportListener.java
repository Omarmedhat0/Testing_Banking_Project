import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;
import org.testng.*;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class PdfReportListener implements ITestListener, ISuiteListener {
    private Document document;
    private static final String REPORTS_DIR = "test-reports";
    private int passedTests = 0;
    private int failedTests = 0;

    @Override
    public void onStart(ISuite suite) {
        try {
            // Create the reports directory if it doesn't exist
            Path reportsPath = Paths.get(REPORTS_DIR);
            if (!Files.exists(reportsPath)) {
                Files.createDirectories(reportsPath);
            }

            // Create the PDF document
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(REPORTS_DIR + "/test-report.pdf"));
            document.open();

            // Add title with custom font and color
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.BLUE);
            Paragraph title = new Paragraph("Test Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add date and time
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy HH:mm:ss");
            String formattedDate = now.format(formatter);
            Font dateFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.GRAY);
            Paragraph date = new Paragraph(formattedDate, dateFont);
            date.setAlignment(Element.ALIGN_CENTER);
            document.add(date);

            // Add a line separator
            document.add(new Paragraph("\n"));
            document.add(new LineSeparator());

        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ISuite suite) {
        try {
            // Add suite information
            Font suiteFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Color.BLACK);
            Paragraph suiteTitle = new Paragraph("Suite: " + suite.getName(), suiteFont);
            suiteTitle.setAlignment(Element.ALIGN_LEFT);
            document.add(suiteTitle);

            // Add table for suite details
            PdfPTable suiteTable = new PdfPTable(4);
            suiteTable.setWidthPercentage(100);
            suiteTable.setSpacingBefore(10f);
            suiteTable.setSpacingAfter(10f);

            // Add table headers
            addTableHeader(suiteTable, "Group");
            addTableHeader(suiteTable, "Class");
            addTableHeader(suiteTable, "Test");
            addTableHeader(suiteTable, "Result");

            // Add table rows for each test result
            Map<String, ISuiteResult> suiteResults = suite.getResults();
            for (ISuiteResult suiteResult : suiteResults.values()) {
                ITestContext testContext = suiteResult.getTestContext();
                addTestResultsToTable(suiteTable, testContext.getPassedTests(), "PASSED", Color.GREEN);
                addTestResultsToTable(suiteTable, testContext.getFailedTests(), "FAILED", Color.RED);
                addTestResultsToTable(suiteTable, testContext.getSkippedTests(), "SKIPPED", Color.ORANGE);
            }

            document.add(suiteTable);

            // Add summary
            Font summaryFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.BLACK);
            Paragraph summary = new Paragraph("Summary", summaryFont);
            summary.setAlignment(Element.ALIGN_LEFT);
            document.add(summary);

            Font resultFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK);
            document.add(new Paragraph("Total Passed: " + passedTests, resultFont));
            document.add(new Paragraph("Total Failed: " + failedTests, resultFont));

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }

    private void addTableHeader(PdfPTable table, String headerTitle) {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(Color.LIGHT_GRAY);
        header.setBorderWidth(2);
        header.setPhrase(new Phrase(headerTitle));
        table.addCell(header);
    }

    private void addTestResultsToTable(PdfPTable table, IResultMap resultMap, String result, Color color) {
        for (ITestResult testResult : resultMap.getAllResults()) {
            table.addCell(testResult.getTestContext().getName());
            table.addCell(testResult.getTestClass().getName());
            table.addCell(testResult.getName());
            PdfPCell resultCell = new PdfPCell(new Phrase(result));
            resultCell.setBackgroundColor(color);
            table.addCell(resultCell);

            if ("PASSED".equals(result)) {
                passedTests++;
            } else if ("FAILED".equals(result)) {
                failedTests++;
            }
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // No implementation needed for this example
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // No implementation needed for this example
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // No implementation needed for this example
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // No implementation needed for this example
    }

    @Override
    public void onStart(ITestContext context) {
        // No implementation needed for this example
    }

    @Override
    public void onFinish(ITestContext context) {
        // No implementation needed for this example
    }
}