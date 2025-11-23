import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * Reproduces the following exception:
 * 
 * <pre>
 * java.io.IOException: Premature EOF
 * 	at sun.net.www.http.ChunkedInputStream.readAheadBlocking(ChunkedInputStream.java:538)
 * 	at sun.net.www.http.ChunkedInputStream.readAhead(ChunkedInputStream.java:582)
 * 	at sun.net.www.http.ChunkedInputStream.read(ChunkedInputStream.java:669)
 * 	at java.io.FilterInputStream.read(FilterInputStream.java:116)
 * 	at sun.net.www.protocol.http.HttpURLConnection$HttpInputStream.read(HttpURLConnection.java:2512)
 * 	at sun.net.www.protocol.http.HttpURLConnection$HttpInputStream.read(HttpURLConnection.java:2507)
 * 	at PrematureEofTest.testDownloadXml(PrematureEofTest.java:24)
 *     ...
 * </pre>
 * 
 * This seems to happen when Jenkins sends an HTTP response with the "Transfer-Encoding=[chunked]" header field.
 */
public class PrematureEofTest extends Assert {

	@Test
	public void testDownloadXml() throws Exception {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		try {
			URL jenkinsUrl = new URL("http://localhost:8080/jenkins/api/xml?depth=0");
			URLConnection connection = jenkinsUrl.openConnection();

			Map<String, List<String>> headerFields = connection.getHeaderFields();
			System.out.println("HeaderFields: " + headerFields);

			InputStream inputStream = connection.getInputStream();
			byte[] buf = new byte[1024];
			int length = 0;
			while ((length = inputStream.read(buf)) > -1) {
				byteOut.write(buf, 0, length);
			}
		} catch (Exception e) {
			if (byteOut.size() > 0) {
				// System.out.println("Data[" + new String(byteOut.toByteArray()) + "]");
			}
			e.printStackTrace();
			fail("An exception occurred");
		}
	}
}