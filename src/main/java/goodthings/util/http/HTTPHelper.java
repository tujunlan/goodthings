package goodthings.util.http;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipException;

public class HTTPHelper
{

	private HttpURLConnection connection = null;
	private URL url = null;
	private String charsetName = null;
	private boolean isGZip;

	private String cookie = "";

	public HTTPHelper(String url, String charsetName, boolean isGZip) throws IOException, MalformedURLException
	{
		this.charsetName = charsetName;
		this.isGZip = isGZip;
		this.url = new URL(url);
		if (null == this.url)
		{
			throw new IOException();
		}
		this.connection = (HttpURLConnection) (new URL(url).openConnection());
		this.connection.setDoInput(true);
		this.connection.setDoOutput(true);
		this.connection.setConnectTimeout(10000);
	}

	public InputStream get() throws ProtocolException, IOException
	{
		if (!"".equals(this.cookie))
			this.connection.setRequestProperty("Cookie", this.cookie);
		this.connection.connect();
		return this.connection.getInputStream();
	}

	public int connect() throws ProtocolException, IOException
	{
		this.connection.connect();
		return this.connection.getResponseCode();
	}

	public String getHtml() throws ProtocolException, IOException, SocketTimeoutException
	{
		this.connection.setRequestMethod("GET");
		this.connection.setRequestProperty("accept", "*/*");
		if (!"".equals(this.cookie))
			this.connection.setRequestProperty("Cookie", this.cookie);
		this.connection.connect();
		if (connection.getResponseCode() != 200)
			return "";
		InputStreamReader isr = null;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try
		{
			if (this.isGZip)
				isr = new InputStreamReader(new GZIPInputStream(this.connection.getInputStream()),
						Charset.forName(this.charsetName));
			else
				isr = new InputStreamReader(this.connection.getInputStream(), Charset.forName(this.charsetName));
			br = new BufferedReader(isr);
			String line = null;
			while (null != (line = br.readLine()))
				sb.append(line);
		}
		catch (ZipException e)
		{
			sb = new StringBuilder();
			isr = new InputStreamReader(this.connection.getInputStream(), Charset.forName(this.charsetName));
			br = new BufferedReader(isr);
			String line = null;
			while (null != (line = br.readLine()))
				sb.append(line);
		}
		finally
		{
			try
			{
				if (null != isr)
					isr.close();
				if (null != br)
					br.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public void setRequestProperty(String key, String value)
	{
		this.connection.setRequestProperty(key, value);
	}

	public InputStream post(String data) throws IOException
	{
		this.connection.setRequestMethod("POST");
		this.connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		this.connection.setRequestProperty("Content-Length", data.getBytes().length + "");
		this.connection.setDoOutput(true);
		if (!"".equals(this.cookie))
			this.connection.setRequestProperty("Cookie", this.cookie);
		OutputStream os = this.connection.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os);
		osw.write(data);
		os.flush();
		osw.close();
		os.close();
		this.connection.connect();
		return this.connection.getInputStream();
	}

	public String postHtml(String data) throws IOException
	{
		this.connection.setRequestMethod("POST");
		this.connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		this.connection.setRequestProperty("Content-Length", data.length() + "");
		if (!"".equals(this.cookie))
			this.connection.setRequestProperty("Cookie", this.cookie);
		OutputStream os = this.connection.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(os);
		osw.write(data);
		os.flush();
		osw.close();
		os.close();
		this.connection.connect();
		System.out.println(this.connection.getResponseCode());
		InputStreamReader isr = null;
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try
		{
			if (this.isGZip)
				isr = new InputStreamReader(new GZIPInputStream(this.connection.getInputStream()),
						Charset.forName(this.charsetName));
			else
				isr = new InputStreamReader(this.connection.getInputStream(), Charset.forName(this.charsetName));
			br = new BufferedReader(isr);
			String line = null;
			while (null != (line = br.readLine()))
				sb.append(line);
		}
		catch (ZipException e)
		{
			sb = new StringBuilder();
			isr = new InputStreamReader(this.connection.getInputStream(), Charset.forName(this.charsetName));
			br = new BufferedReader(isr);
			String line = null;
			while (null != (line = br.readLine()))
				sb.append(line);
		}
		catch (EOFException eof)
		{
			eof.printStackTrace();
			return "";
		}
		finally
		{
			try
			{
				if (null != isr)
					isr.close();
				if (null != br)
					br.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public void setCookie(String cookie)
	{
		if ("".equals(this.cookie))
			this.cookie += cookie;
		else
		{
			this.cookie += ";";
			this.cookie += cookie;
		}
	}

	public List<String> getCookie()
	{
		return this.connection.getHeaderFields().get("Set-Cookie");
	}

	public void setReferer(String referer)
	{
		this.connection.setRequestProperty("Referer", referer);
	}

	public void close()
	{
		this.connection.disconnect();
	}
}
