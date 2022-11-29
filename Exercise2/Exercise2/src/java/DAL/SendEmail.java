/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author LEGION
 */
public class SendEmail {
        
    public String randomString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length()* Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
        
    public boolean sendEmail(String Email, String typeMail, String htmlText){
        boolean test = false;
        String toEmail = Email;
        String fromEmail="linhtqhe161230@fpt.edu.vn";
        String password = "yansfqhgheqnozxe";
        
        try {
            Properties pr  = new Properties();
            pr.setProperty("mail.smtp.host", "smtp.gmail.com");
            pr.setProperty("mail.smtp.port", "587");
            pr.setProperty("mail.smtp.auth", "true");
            pr.setProperty("mail.smtp.starttls.enable", "true");
            pr.setProperty("mail.smtp.socketFactory.port", "587");
            pr.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            
            Session session = Session.getInstance(pr, new Authenticator(){
                @Override
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(fromEmail, password);
                }
            });
            Message mess = new MimeMessage(session);
            mess.setFrom(new InternetAddress(fromEmail));
            mess.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            if(typeMail.equals("ForgorPassword")){
            mess.setSubject("User Email Verification");
            String text = "<p>Registered success.please verify your account using this code:<strong>" + htmlText  + "</strong><br> And don't forgot change your password after SignIn!</p>";
            mess.setContent(text, "text/html; charset=utf-8");
            }else{
                mess.setSubject("Order Verification");
                mess.setContent(htmlText, "text/html; charset=utf-8");
            }
            Transport.send(mess);
            test = true;
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return test;
    }
}
