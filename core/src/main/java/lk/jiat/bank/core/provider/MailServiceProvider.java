package lk.jiat.bank.core.provider;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import lk.jiat.bank.core.mail.Mailable;
import lk.jiat.bank.core.util.Env;

import java.util.Properties;
import java.util.concurrent.*;

public class MailServiceProvider {
    private final Properties properties = new Properties();
    private Authenticator authenticator;
    private static MailServiceProvider instance;
    private ThreadPoolExecutor executor;
    private final BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();

    private MailServiceProvider() {
        properties.setProperty("mail.smtp.host", Env.get("mailtrap.host"));
        properties.setProperty("mail.smtp.port", Env.get("mailtrap.port"));
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable","true");
        properties.setProperty("mail.smtp.ssl.trust", Env.get("mailtrap.host"));
    }

    public static MailServiceProvider getInstance() {
        if(instance == null) {
            instance = new MailServiceProvider();
        }
        return instance;
    }

    public void start(){
        authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Env.get("mailtrap.username"),Env.get("mailtrap.password"));
            }

        };
        executor = new ThreadPoolExecutor(5,10,5, TimeUnit.SECONDS,blockingQueue,
                new ThreadPoolExecutor.AbortPolicy());
        executor.prestartAllCoreThreads();


        System.out.println("Mail service provider Running...");
    }

    public void sendmail(Mailable mailable){
        blockingQueue.offer(mailable);
    }


    public Properties getProperties() {
        return properties;
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }


    public void shutdown(){
        if(executor != null){
            executor.shutdown();
        }
    }
}

