
package model;

public class Message {
    private String sender;
    private String receiver;
    private String text;
    private String date;
    private String time;

    public Message(String sender, String receiver, String text, String date, String time) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.date = date;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "To: " + receiver + "\n"
            + date + " " + time + "\n"
            + text;
    }
}