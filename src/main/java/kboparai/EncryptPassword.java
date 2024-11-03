package kboparai;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptPassword {
    public static String encrypt (String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static void main (String [] args) {
        //System.out.println(encrypt("insertPassHere"));
    }
}
