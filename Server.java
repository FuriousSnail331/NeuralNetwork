package httpserver;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import NumbersRecognition.Utils;

public class Server {
	static DecimalFormat df = new DecimalFormat("0.00");
	static Element getelem(Document doc,String name) {
		Elements e = doc.getAllElements();
		return doc.getElementById(name);
	}
	static void save(Document doc,String file) {
        BufferedWriter  writer = null;
        try
        {
            writer = new BufferedWriter( new FileWriter(file));
            writer.write(doc.toString());
            //System.out.println(doc.toString());
            writer.close();
        }
        catch ( IOException e)
        {
        	e.printStackTrace();
        }

	}
	static String prediction(File file, boolean mobile) throws IOException{
		Document doc = null;
		if(!mobile) {
		doc = Jsoup.parse(new File("lol.html"),"UTF-8","");
		}else {
			doc = Jsoup.parse(new File("lol copy.html"),"UTF-8","");
		}
		BufferedImage img = Utils.changedimension(28,28,ImageIO.read(file));
		Map<String, Double> perc = NN.getpercentage(img);
		//System.out.println(perc);
		for(Object s:perc.keySet().toArray()) {
			String str = (String) s; 
			Element e = doc.getElementById(str);
			e.text(String.valueOf(df.format(perc.get(str))));
		}
		return doc.toString();
	}
	public static void mn(String[] args) {
		try {
			// URL url = new URL("http://alam-l/images/-onlinepngtools/fir-trees.png");
			// Socket s = new
			// Socket(InetAddress.getByName("https://onlinepngtools.com/images/examples-onlinepngtools/fir-trees.png"),80);
			Socket s = new Socket(InetAddress.getByName("alam-l"), 80);// 8080

			// System.out.println(url.getHost());
			BufferedReader is = new BufferedReader(new InputStreamReader(s.getInputStream()));
			BufferedWriter os = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

			os.write("GET /images/examples-onlinepngtools/fir-trees.png HTTP/1.1");
			os.newLine();
			os.write("Host: alam-l\r\n" + "");
			os.write("Connection: keep-alive\r\n" + "");
			os.write("Upgrade-Insecure-Requests: 1\r\n" + "");
			os.write(
					"User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/106.0.0.0 Safari/537.36\r\n"
							+ "");
			os.write(
					"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n"
							+ "");
			os.write("Accept-Encoding: gzip, deflate\r\n" + "");
			os.write("Accept-Language: en-US,en;q=0.9\r\n" + "");

			try {
				int len = 0;
				String line = is.readLine();
				while (!line.isEmpty()) {
					if(line.length()<5000)
					System.err.println(line);
					line = is.readLine();
					len++;
					// if(len>=10)break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (MalformedURLException e) {
			// XXX Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// XXX Auto-generated catch block
			e.printStackTrace();
		}

	}
	private static String decode(String value) {
	    try {
			return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			// XXX Auto-generated catch block
			e.printStackTrace();
		}
	    return null;
	}
	public static void mai(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException {
	    TrustManager[] trustAllCerts = new TrustManager[] {
	    	       new X509TrustManager() {
	    	          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	    	            return null;
	    	          }

	    	          public void checkClientTrusted(X509Certificate[] certs, String authType) {  }

	    	          public void checkServerTrusted(X509Certificate[] certs, String authType) {  }

	    	       }
	    	    };

	    	    SSLContext sc = SSLContext.getInstance("SSL");
	    	    sc.init(null, trustAllCerts, new java.security.SecureRandom());
	    	    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

	    	    // Create all-trusting host name verifier
	    	    HostnameVerifier allHostsValid = new HostnameVerifier() {
	    	        public boolean verify(String hostname, SSLSession session) {
	    	          return true;
	    	        }
	    	    };
	    	    // Install the all-trusting host verifier
	    	    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

		//Jsoup.newSession().
	}

	public static void main(String[] args) {
		int port = 26608;
		File storage = new File("storage");
		storage.mkdir();
		if(port == 80) {
			System.out.println("you have to change the prottttttttttttttttt");
		}
		try {
			//System.out.println(Arrays.toString(Utils.to1d2image(Utils.changedimension(28, 28, (ImageIO.read(new File("data.png")) )),true)));
			//System.exit(0);
			//InetAddress ip1 = InetAddress.getByName("ALAM-L");/// convert host name to ip address
			// http://192.168.1.103:5432/
			System.out.println("starting server");
			InetAddress ip = InetAddress.getLocalHost();
			System.out.println(ip.toString());
			// ServerSocket ss = new ServerSocket(port);//, 50, ip1);
			ServerSocket ss = new ServerSocket(port, 50);
			System.out.println(ss.getLocalPort());
			System.out.println(ss.getInetAddress().toString());
			System.out.println("started server");
			while (!ss.isClosed()) {
				// System.out.println("port using: "+port);

				Socket s = ss.accept();
				System.out.println("client came");
				// Socket socket = new Socket("www.youtube.com", 80);

				// s.connect(socket.getLocalSocketAddress());
				System.out.println(s.getInetAddress().toString());
				// System.out.println(InetAddress.getByAddress(s.getInetAddress().getAddress()).toString());

				BufferedReader is = new BufferedReader(new InputStreamReader(s.getInputStream()));
				BufferedWriter os = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
				
				String methodname = "";
				boolean mobile = false;
								
				try {//// bad exception placement kills the code other one
					for(int i = 0;i<5;i++) {
						String li = is.readLine();

						if(li==null)li="null";
						System.err.println(li);
						if(i==0) {
					String[] firstLine = li.split("\\s+"); // line is like: GET /pic HTTP/1.1
					String method = firstLine[0];

					if (method.equalsIgnoreCase("GET")) {
						String resourceNme = firstLine[1];
						if (resourceNme.equals("/lol/")) {
							methodname = "lol";
							
						}else if(resourceNme.contains("pic?fname=data%3Aimage%2Fpng%3Bbase64%2C")) {
							//resourceNme.lastIndexOf("pic?fname=data%3Aimage%2Fpng%3Bbase64%2C");

							String data = decode(resourceNme.substring("/pic?fname=data%3Aimage%2Fpng%3Bbase64%2C".length()));
							//System.out.println(data);

							//System.out.println(data);
							byte[] b = Base64.getDecoder().decode((data.getBytes( StandardCharsets.UTF_8.toString())));
							System.out.println("eee");
							File file = new File("data.png");
							//if(b==null)
							//System.out.println("tttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
							//System.out.println(Arrays.toString(b));
							file.createNewFile();
							Files.write(file.toPath() , b, StandardOpenOption.WRITE);

							File da = new File("data.png");
							
							int n = storage.listFiles().length+1;
							Files.write(new File(storage,(n)+".png").toPath() , b, StandardOpenOption.CREATE);

							//System.out.println(NN.getpercentage(ImageIO.read(da)));
							//methodname = "prediction";
						}else if(resourceNme.contains("/favicon.ico")) {
							methodname = "favicon.ico";
						}else if(resourceNme.contains("/prediction")) {
							methodname = "prediction";
						}else if(resourceNme.contains("/aboutit")) {
							methodname="aboutit";
						}
					}
						}else{
							if(li.contains("User-Agent")) {
							if(li.contains("Mobile")||li.contains("Android")) {
								mobile = true;
								System.out.println("MOBILE VERSION");
							}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					// XXX: handle exception
				}

				new Thread(new Runnable() {

					@Override
					public void run() {
						try {

							String line = is.readLine();
							while (line!=null&&!line.isEmpty()) {
								System.err.println(line);
								if (s.isClosed()) {
									break;
								} else
									line = is.readLine();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				}).start();

				String crlf = "\r\n";
				
				if (methodname.equalsIgnoreCase("")) {
					byte[] str = null;
					if(!mobile) {
						str = Files.readAllBytes(new File("lol2.html").toPath()); //str = Files.readAllBytes(new File("lol.html").toPath());
					}else {
						str = Files.readAllBytes(new File("lol copy.html").toPath());
					}
					String response = "";
					response = ("HTTP/1.1 200 OK" + crlf +("Content-type: text/html"+crlf)+ "Content-Length: " + str.length + crlf + crlf );
					os.write(response);
					os.flush();
					s.getOutputStream().write(str);
					s.getOutputStream().flush();
					os.write(crlf + crlf);
					os.flush();
				}else if(methodname.equalsIgnoreCase("favicon.ico")) {
					os.write("HTTP/1.1 200 OK"+
							crlf+"Content-type: image/png"+
							crlf+crlf);
						os.flush();
						//if(new String(Files.readAllBytes(new File("correct.png").toPath())).contains("\\"))//it containsSystem.out.println("tttttttttttttttt");
						s.getOutputStream().write(Files.readAllBytes(new File("data2.png").toPath()));
						s.getOutputStream().flush();
						os.write(crlf+crlf);
						os.flush();
				}else if(methodname.equalsIgnoreCase("prediction")) {
					//byte[] str = Files.readAllBytes(new File("lol.html").toPath());
					String predic = prediction(new File("data.png"),mobile);
					
					String response = "";
					response = ("HTTP/1.1 200 OK" + crlf +("Content-type: text/html"+crlf)+ "Content-Length: " + predic.getBytes().length + crlf + crlf );
					os.write(response);
					os.flush();
					os.write(predic);
					os.flush();
					//s.getOutputStream().write(str);
					//s.getOutputStream().flush();
					os.write(crlf + crlf);
					os.flush();
				}else if(methodname.equalsIgnoreCase("aboutit")) {
					byte[] str = Files.readAllBytes(new File("about.html").toPath());
					String response = "";
					response = ("HTTP/1.1 200 OK" + crlf +("Content-type: text/html"+crlf)+ "Content-Length: " + str.length + crlf + crlf );
					os.write(response);
					os.flush();
					s.getOutputStream().write(str);
					s.getOutputStream().flush();
					os.write(crlf + crlf);
					os.flush();
				}

				// os.write(response.getBytes());
				os.close();
				is.close();
				s.close();
			}
			ss.close();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}

}
