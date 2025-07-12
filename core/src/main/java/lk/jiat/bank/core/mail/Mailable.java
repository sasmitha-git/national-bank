package lk.jiat.bank.core.mail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lk.jiat.bank.core.provider.MailServiceProvider;
import lk.jiat.bank.core.util.Env;

public abstract class Mailable implements Runnable {

    private final MailServiceProvider mailServiceProvider;

    public Mailable() {
        mailServiceProvider = MailServiceProvider.getInstance();
    }

    @Override
    public void run() {

        try {

            Session session = Session.getInstance(mailServiceProvider.getProperties(),
                    mailServiceProvider.getAuthenticator());

            Message message  = new MimeMessage(session);
            message.setFrom(new InternetAddress(Env.get("app.email")));
            build(message);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void build(Message message) throws MessagingException;
}
