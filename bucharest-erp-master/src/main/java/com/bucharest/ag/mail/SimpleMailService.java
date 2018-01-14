package com.bucharest.ag.mail;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * Helper class for creating and sending emails.
 * Contains only basic features. For adding more features extend this class
 *
 * @author Sabin Antohe
 */
public class SimpleMailService {

    /* Arrays of user to send email to*/
    private Set<String> recipent = null;

    /* Sender of email message */
    private String from = null;

    /* Name of the SMTP host */
    private String host = null;

    /* Subject of the email*/
    private String subject = null;

	/* Text for the body of the email */
    private String bodyText = null;

    public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}
    /* The default properties file of the application*/
    private final String defaultFilename = "/application.properties";

    /**
     * This constructor gets the configuration for the e-mail server and e-mail content from the
     * application.properties file.
     */
    public SimpleMailService() throws IOException {
        InputStream input = this.getClass().getClassLoader().getResourceAsStream(defaultFilename);

        Properties props = new Properties();
        props.load(input);
        
        this.from = props.getProperty("mail.from.address");
        this.host = props.getProperty("mail.smtp.host");
        this.subject = props.getProperty("mail.subject");
        this.bodyText = props.getProperty("mail.body.text");
    }

    /* Add one recipient address */
    public void addRecipient(String address) {
        if (recipent == null) {
            recipent = new HashSet<String>();
        }
        recipent.add(address);
    }

    /* Add a collection of recipiets */
    public void addRecipients(Collection<String> recipientsAddr) {
        if (recipent == null) {
            recipent = new HashSet<String>();
        }
        recipent.addAll(recipientsAddr);
    }

    /**
     * Sends an email
     */
    public void send() {
        if (from == null || from.length() == 0) {
            throw new IllegalStateException("An Email needs to have a sending address");
        }
        if (recipent == null || recipent.isEmpty()) {
            throw new IllegalStateException("An Email needs to have a recipent");
        }
        if (host == null || host.length() == 0) {
            throw new IllegalStateException("An SMTP host needs to be set to send a message");
        }
        if (subject == null || subject.length() == 0) {
            throw new IllegalStateException("An Email needs a non-null subject");
        }
        if (bodyText == null || bodyText.length() == 0) {
            throw new IllegalStateException("An Email needs a non-null body text");
        }


        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties, null);
        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));

            for (String addr : recipent) {
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(addr));
            }

            message.setSubject(subject);
            message.setContent(bodyText, "text/html; charset=utf-8");
            Transport.send(message);
        } catch(MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
