package ivan;

import java.util.ArrayList;

public class Section {
    public static class Field {
        public String name;
        public String value;

        public Field(String name, String value) {
            this.name = name;
            this.value = value;
        }


        @Override
        public String toString() {
            return name + " = " + value + "\n";
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Field other = (Field) obj;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            if (value == null) {
                if (other.value != null)
                    return false;
            } else if (!value.equals(other.value))
                return false;
            return true;
        }
    }
    
    private ArrayList<Field> fields;

    final private String name;

    Section(String name) {
        this(name, new ArrayList<>());
    }

    Section(String name, ArrayList<Field> fields) {
        this.name = name;
        this.fields = fields;
    }

    public String getName(){
        return name;
    }

    public ArrayList<String> getFieldNames() {
        ArrayList<String> fieldNames = new ArrayList<>();

        for (Field x : fields){
            fieldNames.add(x.name);
        }
        return fieldNames;
    }

    public String getValue(String fieldName) {
        for (Field x : fields) {
            if (x.name.equals(fieldName)) {
                return x.value;
            }
        }
        throw new ContainException("\"" + name + "\" section doesn't contain \"" + fieldName + "\" field \n");
    }

    public void add(String name, String value){
        fields.add(new Field(name, value));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[" + name + "]\n");

        for (Field x : fields) {
            stringBuilder.append(x.toString());
        }
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fields == null) ? 0 : fields.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Section other = (Section) obj;
        if (fields == null) {
            if (other.fields != null)
                return false;
        } else if (!fields.equals(other.fields))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
