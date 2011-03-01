package models.general;

/**
 * @author Manuel Bernhardt <bernhardt.manuel@gmail.com>
 */
public class UnitRole implements models.deadbolt.Role {

    public static final String USER = "user";
    public static final String ADMIN = "admin";

    private String name;

    public String getRoleName() {
        return name;
    }

    public UnitRole(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof UnitRole && ((UnitRole)o).getRoleName().equals(this.getRoleName());
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public static UnitRole user() {
        return new UnitRole(USER);
    }

    public static UnitRole admin() {
        return new UnitRole(ADMIN);
    }
}


