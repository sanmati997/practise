package scm_acat.serviceValidator;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender 
{

	static String EMAILID;
	static String PASSWORD;
	
	public static void main(String[] args)
	{
		SendEmail("vsiraska@cisco.com", "Test Mail","ACAT");
	}

	public static void SendEmail(String eMAIL, String msg, String sub) {

		ServiceProvider provider = new  ServiceProvider();
		Properties properties = provider.readProp();
		
		EMAILID = properties.getProperty("EmailID");
		PASSWORD = properties.getProperty("emailPass");
		
		String mailBody = "<html>" +

				"<body>Hi All,<br><br>"
				+ "<p style='padding-left: 5%'> "
				+ "Please find the below web service(s) failure details and take the necessary action <br><br>"
				+"</p>"
				+ msg
				
				+ "</body>" + "</html>";
				
				
				//"Hi All,\n\n" + msg;
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "mail.cisco.com");
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "Basic Auth");
		//props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", "smtp.cisco.com");

		
		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(EMAILID, PASSWORD);
			}
		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress("cxit-ib-prod-alert@cisco.com"));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(eMAIL));

			// Set Subject: header field
			message.setSubject(sub);
			
			// Now set the actual message
			 //message.setText(mailBody);
			
			message.setContent(mailBody.toString(), "text/html");

			// Send message
			Transport.send(message);

			System.out.println(mailBody);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
