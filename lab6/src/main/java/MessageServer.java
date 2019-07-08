public class MessageServer {
    private String message="";
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void addMessage(String message){
        this.message += message;
    }

    public void addMessageWithN(String message){
        this.message += message + "\n";
    }


}
