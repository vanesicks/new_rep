package ivan;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class FileIniTest {

    private File getResource(final String path) {
        final ClassLoader classLoader = new FileIniTest().getClass().getClassLoader();
        final File file = new File(classLoader.getResource(path).getFile());
        return file;
    }

    @Test
    public void canReadValidFile() throws IOException {
        // given
        final FileIni fileINI = new FileIni();
        final File file = getResource("input.ini");
        final ArrayList<Section> expectedSections = new ArrayList<>(Arrays.asList(
            new Section("COMMON", new ArrayList<>(Arrays.asList(
                new Section.Field("StatisterTimeMs", "5000"),
                new Section.Field("LogNCMD", "1")
            ))),
            new Section("ADC_DEV", new ArrayList<>(Arrays.asList(
                new Section.Field("BufferLenSeconds", "0.65"),
                new Section.Field("SampleRate", "120000000.0"),
                new Section.Field("Driver", "libusb")
            )))
        ));
        // when
        fileINI.setInformation(file);
        // then
        final ArrayList<Section> actualSections = fileINI.getSections();
        assertEquals(expectedSections, actualSections);
    }

    @Test
    public void canGetValidValue() throws IOException {
        // given
        final FileIni fileINI = new FileIni();
        final File file = getResource("input.ini");
        final int expectedIntValue = 5000;
        final double expectedDoubleValue = 0.65;
        final String expectedStringValue = "libusb";

        // when
        fileINI.setInformation(file);
        // then
        final int actualIntValue = fileINI.getIntValue("COMMON","StatisterTimeMs");
        final double actualDoubleValue = fileINI.getDoubleValue("ADC_DEV","BufferLenSeconds");
        final String actualStringValue = fileINI.getStringValue("ADC_DEV","Driver");


        assertEquals(expectedIntValue, actualIntValue);
        assertEquals(new Double(expectedDoubleValue), new Double(actualDoubleValue));
        assertEquals(expectedStringValue, actualStringValue);
    }
}