package ir.softernet.lib.quemars;

/**
 * Created by saman on 4/19/16.
 */
public enum Priority {

    LOW("low"),
    MEDIUM("medium"),
    HIGH("high");


    private final String identifier;

    Priority(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

}
