package api.PojoAndSpec;

public class SuccessReg {
    public int id;
    public String token;

    public SuccessReg(int id, String token) {
        this.id = id;
        this.token = token;
    }

    public SuccessReg() {
    }

    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
