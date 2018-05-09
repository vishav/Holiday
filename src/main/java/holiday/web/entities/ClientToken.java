package holiday.web.entities;

public class ClientToken
{
    private String token;

    public ClientToken(String token)
    {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
