package itmo.common.description;

import java.io.Serializable;

public enum MpaaRating implements Serializable {
    G(6,"G"),
    PG_13(12,"PG_13"),
    NC_17(18,"NC_17");
    private final int value;
    private final String name;

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name;
    }

    MpaaRating(int value, String name) {
        this.value = value;
        this.name = name;
    }




    public static MpaaRating fromValue(String value) {
        for (MpaaRating rating : MpaaRating.values()) {
            if (rating.name.equalsIgnoreCase(value) || String.valueOf(rating.value).equalsIgnoreCase(value)) {
                return rating;
            }
        }
        throw new IllegalArgumentException("Invalid value for MpaaRating: " + value);
    }

}
