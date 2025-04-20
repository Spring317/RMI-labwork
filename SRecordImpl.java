
public class SRecordImpl implements SRecord {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String email;

    public SRecordImpl(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }
}