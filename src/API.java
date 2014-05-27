import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;

/**
 * A Java-wrapper to post and get data from reddapi.com (using REST)
 * 
 * @author Leonard Simonse
 * @version 1.0
 * 
 *          2014-04-09
 * 
 */
public class API {

	private String key_GET, key_POST;
	private String baseURL = "https://api.reddapi.com/v1/json/";
	private final String USER_AGENT = "Mozilla/5.0";
	private boolean debugMode = false;

	// Constructor //
	/**
	 * @param key_GET
	 *            API-key to perform GET-requests using HTTP(S).
	 * @param key_POST
	 *            API-key to perform POST-requests using HTTP(S).
	 */
	public API(String key_GET, String key_POST) {
		this.key_GET = key_GET;
		this.key_POST = key_POST;
	}

	// Getters AND Setters //
	/**
	 * @return A String containing the current API-key (required to GET).
	 */
	public String getKey_GET() {
		return key_GET;
	}

	/**
	 * @param key_GET
	 *            A String containing an API-key (required to GET) as a
	 *            parameter.
	 */
	public void setKey_GET(String key_GET) {
		this.key_GET = key_GET;
	}

	/**
	 * @return A String containing the current API-key (required to POST).
	 */
	public String getKey_POST() {
		return key_POST;
	}

	/**
	 * @param key_POST
	 *            A String containing an API-key (required to POST) as a
	 *            parameter.
	 */
	public void setKey_POST(String key_POST) {
		this.key_POST = key_POST;
	}

	// GET and POST templates //

	/**
	 * @param command
	 *            The command that will be executed on the API-server using a
	 *            GET-request.
	 * @param arg
	 *            A String containing an additional URL-parameter (null is
	 *            allowed).
	 * @return A JSON-object packaged in a String, containing the requested
	 *         info.
	 */
	private String requestGET(String command, String arg) {
		String addString = command + "/" + key_GET;
		if (arg != null) {
			StringBuilder builder = new StringBuilder(addString);
			builder.append("/" + arg);
			addString = builder.toString();
		}
		String url = baseURL + addString;
		URL obj = null;
		try {
			obj = new URL(url);
		} catch (MalformedURLException e) {
			if (debugMode)
			printError(e.toString());
		}
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) obj.openConnection();
		} catch (IOException e) {
			if (debugMode)
			printError(e.toString());
		}

		try {
			con.setRequestMethod("GET");
		} catch (ProtocolException e) {
			if (debugMode)
			printError(e.toString());
		}

		int responseCode;
		try {
			con.setRequestProperty("User-Agent", USER_AGENT);

			responseCode = con.getResponseCode();

			if (debugMode) {
				System.out.println("\nSending 'GET' request to URL : " + url);
				System.out.println("Response Code : " + responseCode);
			}

			InputStream stream = null;
			if (con.getResponseCode() >= 400) {
				stream = con.getErrorStream();
			} else {
				stream = con.getInputStream();
			}
			BufferedReader in = new BufferedReader(
					new InputStreamReader(stream));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			if (debugMode)
				System.out.println(response.toString());
			return response.toString();
		} catch (IOException e) {
			if (debugMode)
			printError(e.toString());
		}
		return null;

	}

	/**
	 * @param command
	 *            The command that will be executed on the API-server using a
	 *            POST-request.
	 * @param argMap
	 *            A HashMap containing RequestData.
	 * @return Return
	 */
	private String requestPOST(String command, HashMap<String, Object> argMap) {
		String url = baseURL + command;
		URL obj = null;
		try {
			obj = new URL(url);
		} catch (MalformedURLException e) {
			if (debugMode)
			printError(e.toString());
		}
		HttpsURLConnection con = null;
		try {
			con = (HttpsURLConnection) obj.openConnection();
		} catch (IOException e) {
			if (debugMode)
			printError(e.toString());
		}

		try {
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		} catch (ProtocolException e) {
			if (debugMode)
			printError(e.toString());
		}

		Gson gson = new Gson();
		String parameters = gson.toJson(argMap);

		con.setDoOutput(true);
		DataOutputStream wr;
		try {
			wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(parameters);
			wr.flush();
			wr.close();
			int responseCode;
			responseCode = con.getResponseCode();
			if (debugMode) {
				System.out.println("\nSending 'POST' request to URL : " + url);
				System.out.println("Post parameters : " + parameters);
				System.out.println("Response Code : " + responseCode);
			}
			InputStream stream = null;
			if (con.getResponseCode() >= 400) {
				stream = con.getErrorStream();
			} else {
				stream = con.getInputStream();
			}
			BufferedReader in = new BufferedReader(
					new InputStreamReader(stream));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			if (debugMode)
				System.out.println(response.toString());

			return response.toString();

		} catch (IOException e) {
			if (debugMode)
			printError(e.toString());
		}
		return null;
	}

	// GET-REQUESTS //
	/**
	 * @param userName
	 *            Username attached to a user.
	 * @return The current credit-balance of the given username as a String.
	 */
	public String getUserBalance(String userName) {
		return requestGET("GetUserBalance", userName);
	}
	
	/**
	 * @param userName
	 *            Username attached to a user.
	 * @return The current confirmed and unconfirmed credit-balance of the 
	 * given username as a String.
	 */
	public String getUserBalanceDetail(String userName) {
		return requestGET("GetUserBalanceDetail", userName);
	}
	
	/**
	 * @param userName
	 *            Username attached to a user.
	 * @return A JSON-object packaged in a String, containing UserInfo.
	 */
	public String getUserInfo(String userName) {
		return requestGET("GetUserInfo", userName);
	}

	/**
	 * @return A JSON-object packaged in a String, containing a UserList.
	 */
	public String getUserList() {
		return requestGET("GetUserList", null);
	}

	// POST-REQUESTS //
	/**
	 * @param userName
	 *            Username to attach to the newly created user.
	 * @return A JSON-object packaged in a String containing UserInfo about the
	 *         newly created user.
	 */
	public String createNewUser(String userName) {
		HashMap<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("APIKey", key_POST);
		argMap.put("Username", userName);
		return requestPOST("CreateNewUser", argMap);
	}

	/**
	 * @param userNameFrom
	 *            Username to send the amount from.
	 * @param userNameTo
	 *            Username to send the amount to.
	 * @param amount
	 *            The amount to send.
	 * @return A JSON-object packaged in a String containing information about
	 *         state of the requested transaction.
	 */
	public String moveToUser(String userNameFrom, String userNameTo,
			double amount) {
		HashMap<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("APIKey", key_POST);
		argMap.put("UsernameFrom", userNameFrom);
		argMap.put("UsernameTo", userNameTo);
		argMap.put("Amount", amount);
		return requestPOST("MoveToUser", argMap);
	}

	/**
	 * @param userNameFrom
	 *            Username to send the amount from.
	 * @param addressTo
	 *            Address to send the amount to.
	 * @param amount
	 *            The amount to send.
	 * @return A JSON-object packaged in a String containing information about
	 *         state of the requested transaction.
	 */
	public String sendToAddress(String userNameFrom, String addressTo,
			double amount) {
		HashMap<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("APIKey", key_POST);
		argMap.put("UsernameFrom", userNameFrom);
		argMap.put("AddressTo", addressTo);
		argMap.put("Amount", amount);
		return requestPOST("SendToAddress", argMap);
	}

	// Error-log-printer //
	/**
	 * @param e
	 *            A String containing a specific error-message.
	 */
	private void printError(String e) {
		if (debugMode)
			System.out.println(e);
	}
}
