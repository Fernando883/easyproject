package EasyProject.ejb;

import java.util.Date;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aitorpagan
 */
@Stateless
public class Mail {
        String servidorSMTP = "smtp.gmail.com";
        String puerto = "587";
        String usuario = "easyproyectjsf@gmail.com";
        String password = "proyectoweb";
   
        String destino = null;
        String asunto = null;
        String mensaje = null;

    public Mail() {
    }

    public Mail(String subject, String message, String destiny){
        this.destino = destiny;
        this.asunto = subject;
        this.mensaje = message;
    }
    
    public void sendMail(){
          Properties props = new Properties();
 
          props.put("mail.debug", "true");
          props.put("mail.smtp.auth", true);
          props.put("mail.smtp.starttls.enable", true);
          props.put("mail.smtp.host", servidorSMTP);
          props.put("mail.smtp.port", puerto);

          Session session = Session.getInstance(props, null);

          try {
           MimeMessage message = new MimeMessage(session);
           message.addRecipient(Message.RecipientType.TO, new InternetAddress(
             destino));
           message.setSubject(asunto);
           message.setSentDate(new Date());
           message.setText(mensaje);

           Transport tr = session.getTransport("smtp");
           tr.connect(servidorSMTP, usuario, password);
           message.saveChanges();   
           tr.sendMessage(message, message.getAllRecipients());
           tr.close();

          } catch (MessagingException e) {
           e.printStackTrace();
          }
    }
    
}
