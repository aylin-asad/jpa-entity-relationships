package az.iktex.organization.structure.testders.enums;

public enum Gender {
    MALE(1), FEMALE(2);

    private final int id;

    Gender(int id) { this.id = id; }
    public int getId() { return id; }

    public static Gender fromId(int id) {
        for (Gender gender : values()) {
            if (gender.id == id) return gender;
        }
        throw new IllegalArgumentException("Naməlum gender id: " + id);
    }
}
