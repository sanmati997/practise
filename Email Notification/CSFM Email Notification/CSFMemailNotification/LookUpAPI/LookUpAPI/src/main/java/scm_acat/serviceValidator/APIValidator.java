package scm_acat.serviceValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;


public class APIValidator {

	ServiceProvider provider = new  ServiceProvider();
	Properties properties = provider.readProp();
	
	static String API_URL;
	static String USER_NAME;
	static String PASSWORD;
	
	public void validate()  {

		
		System.out.println("RUN schedular");
		String requestJsonString = "{\"application\": \"XXCSS_ESM\", \"data\": [{\"subRefId\": \"SubRefId\"}]}";
		
		String responsetoexclude = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
				+ "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n"
				+ "    <soapenv:Body>\r\n"
				+ "		<soapenv:Fault>\r\n"
				+ "			<faultcode>soapenv:Server</faultcode>\r\n"
				+ "			<faultstring>Policy Falsified</faultstring>\r\n"
				+ "			<faultactor>http://wsgi.cisco.com/saib/services/XXSCM_getESMSubrefContractInfo</faultactor>\r\n"
				+ "			<detail>\r\n"
				+ "				<l7:policyResult status=\"Assertion Falsified\" xmlns:l7=\"http://www.layer7tech.com/ws/policy/fault\"/>\r\n"
				+ "			</detail>\r\n"
				+ "		</soapenv:Fault>\r\n"
				+ "    </soapenv:Body>\r\n"
				+ "</soapenv:Envelope>";
		
		try {
			API_URL = properties.getProperty("apiURL");
			USER_NAME = properties.getProperty("authUserName");
			PASSWORD = properties.getProperty("authPass");
			
			URL url = new URL(API_URL);
			String auth = USER_NAME+":"+PASSWORD;

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            connection.setRequestProperty  ("Authorization", "Basic " + Base64.getEncoder().encodeToString(auth.getBytes()));
			
			OutputStream os = connection.getOutputStream();
            os.write(requestJsonString.getBytes());
            os.flush();

       //     connection.connect();
            int responsecode = connection.getResponseCode();
            String respmsg = connection.getResponseMessage();

            String line = null;
            StringBuilder response = new StringBuilder();
            InputStream responseStream;
            
            if (responsecode != 200) {
            	responseStream = connection.getErrorStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(responseStream));
    			while ((line = br.readLine()) != null) {
    				response.append(line);
    			}
    			//zensar-ib-support-core@cisco.com
    			
    			String tbl = "<table style='border-collapse: collapse'>"+
    					"<tr style='border: 1px solid black;padding: 8px;text-align: center;background-color: #47189c;color: white;'>"
    					+ "<th style='border: 1px solid black;'>WEBSERVICE NAME</th>"
    					+ "<th style='border: 1px solid black;'>WEBSERVICE</th>"
    					+ "<th style='border: 1px solid black;'>SOURCE</th>"
    					+ "<th style='border: 1px solid black;'>ERROR MESSAGE</th>"
    					+ "</tr>"
    					+ "<tr>"
    					+ "<td style='border: 1px solid black;'>esmBySubRefId</td>"
    					+ "<td style='border: 1px solid black;'>"+API_URL+"</td>"
    					+ "<td style='border: 1px solid black;'>ESM</td>"
    					+ "<td style='border: 1px solid black;'>"+respmsg+"</td>"
    					+ "</tr>"
    					+ "</table>";
    			
    			Date date = new Date();
    			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa");
    			String strDate= formatter.format(date);
    			String tempResp = response.toString();
    			if(tempResp.contains("Policy Falsified"))
            	{
            		System.out.println("got Pilicy error");
            	}
    			else
    			{
    				EmailSender.SendEmail("zensar-ib-support-core@cisco.com", tbl,"SAIB Webservice failure alert  "+strDate);
    			}
            	
            	System.out.println("Not 200   -   "+strDate +"   --   "+ responsecode);
            
            } else {
            	responseStream = connection.getInputStream();
            	
            	System.out.println("200");
            	
            	 BufferedReader br = new BufferedReader(new InputStreamReader(responseStream));
     			while ((line = br.readLine()) != null) {
     				response.append(line);
     			}
            }
            
			System.out.println(response);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	}
}
