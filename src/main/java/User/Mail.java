package User;

public class Mail {
    public String mail_text;
    public String send_date;
    public String sender_id;
    public String receiver_id;
    public Mail(String mail_text, String send_date, String sender_id, String receiver_id){
        this.mail_text = mail_text;
        this.send_date = send_date;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
    }

    public String getMail_text(){
        return mail_text;
    }

    public String getSend_date(){
        return send_date;
    }

    public String getSender_id(){
        return sender_id;
    }

    public String getReceiver_id(){
        return receiver_id;
    }
}
