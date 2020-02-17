//https://www.ibm.com/support/knowledgecenter/en/SSPLFC_7.2.1/com.ibm.taddm.doc_721/SDKDevGuide/t_cmdbsdk_restapi_java.html

package team16.API;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Base64;

public class API_Connection
{
	public static final void main(String[] args) throws IOException
	{
		
		ProcessBuilder pb = new ProcessBuilder(
	            "curl",
	            "-X",
	            "GET",
	            "--header",
	            "Accept: application/json",
	            "https://api.weather.gov/alerts/active?status=actual&area=MT");

	    pb.directory(new File("D:\\test"));
	    pb.redirectErrorStream(true);
	    Process p = pb.start();
	    InputStream is = p.getInputStream();

	    FileOutputStream outputStream = new FileOutputStream("D:\\test/test.json");

	    BufferedInputStream bis = new BufferedInputStream(is);
	    
	    byte[] bytes = new byte[100];
	    int numberByteReaded;
	    while ((numberByteReaded = bis.read(bytes, 0, 100)) != -1) {

	        outputStream.write(bytes, 0, numberByteReaded);
	        Arrays.fill(bytes, (byte) 0);

	    }
	  

	    outputStream.flush();
	    outputStream.close();

	}
}