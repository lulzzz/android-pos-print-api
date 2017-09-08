package com.aevi.print;

import android.os.Build;

import com.aevi.print.model.FontStyle;
import com.aevi.print.model.PaperKind;
import com.aevi.print.model.PrintPayload;
import com.aevi.print.model.PrinterFont;
import com.aevi.print.model.PrinterSettings;
import com.aevi.print.model.TestPrinterFontBuilder;
import com.aevi.print.model.TestPrinterSettingsBuilder;
import com.aevi.print.model.TextRow;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

@Config(sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class PrintPreviewTest {

    private static final PrinterFont FONT_A = new TestPrinterFontBuilder()
            .withId(1)
            .withName("Font A")
            .withSupportedFontStyles(FontStyle.values())
            .withHeight(24)
            .withWidth(12)
            .withLineHeight(32)
            .withIsDefault(true)
            .withNumColumns(48)
            .build();

    private static final PrinterFont FONT_B = new TestPrinterFontBuilder()
            .withId(2)
            .withName("Font B")
            .withSupportedFontStyles(FontStyle.values())
            .withHeight(17)
            .withWidth(9)
            .withLineHeight(25)
            .withIsDefault(false)
            .withNumColumns(64)
            .build();

    private static final PrinterFont[] DEFAULT_FONTS = new PrinterFont[]{
            FONT_A,
            FONT_B
    };

    public PrinterSettings getPrinterSettings() {
        return new TestPrinterSettingsBuilder()
                .withPrinterId("TestPrinterSettings")
                .withPaperWidth(576)
                .withPrinterFonts(DEFAULT_FONTS)
                .withPaperKind(PaperKind.THERMAL)
                .withDoesReportStatus(true)
                .withCanHandleCommands(true)
                .withDoesSupportCodepages(false)
                .build();
    }

    @Test
    public void willSplitLongLineCorrectly() {
        PrintPayload payload = new PrintPayload();
        TextRow row = payload.append("this is a very long line of text that should be split across multiple lines");

        PrintPreview printPreview = new PrintPreview(payload, getPrinterSettings());

        List<TextRow> rows = printPreview.splitLongTextRow(row);

        assertThat(rows).hasSize(2);
        assertThat(rows.get(0).getText()).isEqualTo("this is a very long line of text that should be ");
        assertThat(rows.get(1).getText()).isEqualTo("split across multiple lines");
        assertThat(rows.get(0).getText().length()).isLessThanOrEqualTo(FONT_A.getNumColumns());
        assertThat(rows.get(1).getText().length()).isLessThanOrEqualTo(FONT_A.getNumColumns());
    }
}
