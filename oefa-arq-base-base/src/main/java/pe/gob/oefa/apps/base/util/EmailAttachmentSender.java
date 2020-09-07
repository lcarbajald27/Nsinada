package pe.gob.oefa.apps.base.util;
 
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import pe.gob.oefa.apps.base.util.properties.UtilProperties;
public class EmailAttachmentSender {
 
    public static void sendEmailWithAttachments(/*String host, String port,
            final String userName, final String password,*/ String toAddress,
            String subject, String message, List<String> attachFiles)
            throws AddressException, MessagingException {
    	
    	
    	  final String userName = UtilProperties.getString("mail.accountMail");
          final String password = UtilProperties.getString("mail.accountPass"); 
          final String personalName = UtilProperties.getString("mail.personalName");
          

          // sets SMTP server properties
          Properties properties = new Properties();
          properties.put("mail.smtp.host", UtilProperties.getString("mail.host"));
          properties.put("mail.smtp.port", UtilProperties.getString("mail.port"));
          properties.put("mail.smtp.auth", UtilProperties.getString("mail.auth"));
          properties.put("mail.smtp.starttls.enable", UtilProperties.getString("mail.starttls.enable"));
          properties.put("mail.user", userName);
          properties.put("mail.password", password);
//        // sets SMTP server properties
//        Properties properties = new Properties();
//        properties.put("mail.smtp.host", host);
//        properties.put("mail.smtp.port", port);
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.user", userName);
//        properties.put("mail.password", password);
 
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
 
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");
 
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
 
        // adds attachments
        if (attachFiles != null && attachFiles.size() > 0) {
            for (String filePath : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
 
                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
 
                multipart.addBodyPart(attachPart);
            }
        }
 
        // sets the multi-part as e-mail's content
        msg.setContent(multipart);
 
        // sends the e-mail
        Transport.send(msg);
 
    }
 
    
    
    public static void sendEmailWithAttachmentsFile(/*String host, String port,
            final String userName, final String password,*/ String toAddress,
            String subject, String message, List<File> attachFiles)
            throws AddressException, MessagingException {
    	
    	
    	  final String userName = UtilProperties.getString("mail.accountMail");
          final String password = UtilProperties.getString("mail.accountPass"); 
          final String personalName = UtilProperties.getString("mail.personalName");
          

          // sets SMTP server properties
          Properties properties = new Properties();
          properties.put("mail.smtp.host", UtilProperties.getString("mail.host"));
          properties.put("mail.smtp.port", UtilProperties.getString("mail.port"));
          properties.put("mail.smtp.auth", UtilProperties.getString("mail.auth"));
          properties.put("mail.smtp.starttls.enable", UtilProperties.getString("mail.starttls.enable"));
          properties.put("mail.user", userName);
          properties.put("mail.password", password);
//        // sets SMTP server properties
//        Properties properties = new Properties();
//        properties.put("mail.smtp.host", host);
//        properties.put("mail.smtp.port", port);
//        properties.put("mail.smtp.auth", "true");
//        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.user", userName);
//        properties.put("mail.password", password);
 
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
 
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");
 
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
 
        // adds attachments
        if (attachFiles != null && attachFiles.size() > 0) {
            for (File filePath : attachFiles) {
                MimeBodyPart attachPart = new MimeBodyPart();
 
                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
 
                multipart.addBodyPart(attachPart);
            }
        }
 
        // sets the multi-part as e-mail's content
        msg.setContent(multipart);
 
        // sends the e-mail
        Transport.send(msg);
 
    }
    /**
     * Test sending e-mail with attachments
     */
    public static void main(String[] args) {
        // SMTP info
      /*  String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = "jhongutierrezperalta@gmail.com";
        String password = "nivwqscrCHEBERExd";*/
 
        // message info
//        String mailTo = "jgutierrez@galaxybis.com";
//        String subject = "Problemática Ambiental no Encontrada";
//        String message = "I have some attachments for you.";
// 
//        // attachments
//        String[] attachFiles = new String[3];
//        attachFiles[0] = "C:/sinada/problematica/7/archivo/bibliografiasReporte (29).pdf";
//        attachFiles[1] = "C:/sinada/problematica/7/archivo/bibliografiasReporte (30).pdf";
//        attachFiles[2] = "C:/sinada/problematica/7/archivo/bibliografiasReporte (31).pdf";
// 
        try {
            sendEmailWithAttachments( "fsantos@oefa.gob.pe","Prueba", "Prueba", null);
            System.out.println("Email sent.");
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
    }
}