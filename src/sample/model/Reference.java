package sample.model;

public class Reference {
    private String referenceType;
    private String referenceValue;

    public Reference() {
    }

    public Reference(String referenceType, String referenceValue) {
        this.referenceType = referenceType;
        this.referenceValue = referenceValue;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public String getReferenceValue() {
        return referenceValue;
    }

    public void setReferenceValue(String referenceValue) {
        this.referenceValue = referenceValue;
    }

    @Override
    public String toString() {
        return referenceType +"~"+  referenceValue ;
    }
}
