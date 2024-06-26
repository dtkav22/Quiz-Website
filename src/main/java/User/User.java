package User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private final String userName;
    private final String password;
    private final String email;
    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = hashPassword(password);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    private String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i=0; i<bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;  // remove higher bits, sign
            if (val<16) buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }

    private String hashPassword(String password) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(password.getBytes());
            return hexToString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isPasswordCorrect(String possiblePassword) {
        return password.equals(hashPassword(possiblePassword));
    }
}
