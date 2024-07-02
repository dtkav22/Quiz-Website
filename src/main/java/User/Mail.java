package User;

import java.sql.Date;

public class Mail {
    private String mail_text;
    private Date send_date;
    private String sender_id;
    private String receiver_id;
    public Mail(String mail_text, Date send_date, String sender_id, String receiver_id){
        this.mail_text = mail_text;
        this.send_date = send_date;
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
    }

    public String getMail_text(){
        return mail_text;
    }

    public Date getSend_date(){
        return send_date;
    }

    public String getSender_id(){
        return sender_id;
    }

    public String getReceiver_id(){
        return receiver_id;
    }
}
