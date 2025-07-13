package lk.jiat.bank.core.mail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;

public class VerificationCode extends Mailable{

    private final String toEmail;
    private final String verificationCode;

    public VerificationCode(String toEmail, String verificationCode) {
        this.toEmail = toEmail;
        this.verificationCode = verificationCode;
    }

    @Override
    public void build(Message message) throws MessagingException {

        message.setRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));
        message.setSubject("National-Bank - Password Verification Code");
        message.setText("Hello,\n\nYour verification code is: " + verificationCode);
    }
}
