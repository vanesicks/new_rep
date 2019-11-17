package ivan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileIni {
    private ArrayList<Section> sections;

    public FileIni(){
        sections = new ArrayList<Section>();
    }

    public ArrayList<Section> getSections() {
        return sections;
    }

    /**
     * setInformation function
     * @param fileName
     * @throws IOException
     */
    public void setInformation(String fileName) throws IOException {
        final File file = new File(fileName);
        setInformation(file);
    }

    /**
     * 
     * @param file
     * @throws IOException
     */
    public void setInformation(File file) throws IOException  {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Pattern sectionPattern = Pattern.compile("^\\[([A-Z0-9_]+)\\] ?;?.*");
        Pattern fieldPattern = Pattern.compile("^([a-zA-Z0-9_]+) = ([^ ;\t]+) ?;?.*");
        Pattern neutralStringPattern = Pattern.compile("^(;.*)|(\t*)");
        String string;
        Section section = null;

        while ((string = reader.readLine()) != null) {
            Matcher sectionMatcher = sectionPattern.matcher(string);
            Matcher fieldMatcher = fieldPattern.matcher(string);
            Matcher neutralStringMatcher = neutralStringPattern.matcher(string);

            if(!neutralStringMatcher.matches()) {
                if (sectionMatcher.matches()) {
                    section = new Section(sectionMatcher.group(1));
                    sections.add(section);
                } else if (fieldMatcher.matches() && section != null) {
                    section.add(fieldMatcher.group(1), fieldMatcher.group(2));
                } else {
                    throw new IniFormatException("Wrong IniFile format at input string: \"" + string + "\"");
                }
            }
        }
        reader.close();
    }

    private  <T> T getValue(String sectionName, String fieldName, FromStringFunction<T> parser) {
        for (Section x : sections) {
            if (x.getName().equals(sectionName)) {
                return parser.parse(x.getValue(fieldName));
            }
        }
        throw new ContainException("The file doesn't contain \"" + sectionName + "\" section");
    }

    /**
     * 
     * @param sectionName
     * @param fieldName
     * @return
     */
    public int getIntValue(String sectionName, String fieldName) {
        return getValue(sectionName, fieldName, (String string) -> {
            try {
                return Integer.parseInt(string);
            } catch (NumberFormatException e){
                throw new IniFormatException("Incorrect value type " + e.getMessage().toLowerCase());
            }
        });
    }


    /**
     * 
     * @param sectionName
     * @param fieldName
     * @return
     */
    public boolean getBoolValue(String sectionName, String fieldName) {
        return getValue(sectionName, fieldName, (String string) -> {
            try {
                return Boolean.parseBoolean(string);
            } catch (NumberFormatException e){
                throw new IniFormatException("Incorrect value type " + e.getMessage().toLowerCase());
            }
        });
    }


    /**
     * 
     * @param sectionName
     * @param fieldName
     * @return
     */
    public double getDoubleValue(String sectionName, String fieldName) {
        return getValue(sectionName, fieldName, (String string) -> {
            try {
                return Double.parseDouble(string);
            } catch (NumberFormatException e) {
                throw new IniFormatException("Incorrect value type " + e.getMessage().toLowerCase());
            }
        });
    }

    /**
     * 
     * @param sectionName
     * @param fieldName
     * @return
     */
    public String getStringValue(String sectionName, String fieldName) {
        return getValue(sectionName, fieldName, (String string) -> string);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Section x : sections) {
            stringBuilder.append(x.toString());
        }
        return stringBuilder.toString();
    }
}