package holiday.web.entities;

public class ResetPassword {

    private String message;

    private boolean success;

    private String requesteduuid;

    private String password;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRequesteduuid() {
        return requesteduuid;
    }

    public void setRequesteduuid(String requesteduuid) {
        this.requesteduuid = requesteduuid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
