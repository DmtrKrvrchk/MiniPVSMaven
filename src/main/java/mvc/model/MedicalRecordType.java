package mvc.model;

public enum MedicalRecordType {
    DIAGNOSE("Diagnose"),
    SCHEIN("Schein"),
    ANTRAG("Antrag"),
    RECHNUNG("Rechnung"),
    BRIEF("Brief");


    private final String displayName;

    MedicalRecordType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}