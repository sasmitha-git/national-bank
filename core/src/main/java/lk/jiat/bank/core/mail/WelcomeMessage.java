package lk.jiat.bank.core.mail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;

public class WelcomeMessage extends Mailable {

    private final String to;
    private final String name;
    private final String password;

    public WelcomeMessage(String to, String name, String password) {
        this.to = to;
        this.name = name;
        this.password = password;
    }

    @Override
    public void build(Message message) throws MessagingException {
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Welcome to National Bank");

        String body = "<h3>Welcome, " + name + "!</h3>"
                + "<p>Your customer account has been created successfully.</p>"
                + "<ul>"
                + "<li><strong>Email:</strong> " + to + "</li>"
                + "<li><strong>Temporary Password:</strong> " + password + "</li>"
                + "</ul>"
                + "<p>Please <strong>change your password</strong> after your first login.</p>"
                + "<br><p>Best Regards,<br>National Bank Team</p>";

        message.setContent(body, "text/html; charset=utf-8");

    }
}
