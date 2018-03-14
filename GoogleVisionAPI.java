package newcalais;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 *
 * @author senthuran
 */
public class GoogleVisionAPI {

 public static String sendPostRequest(String requestUrl, String payload) {
  try {
   URL url = new URL(requestUrl);
   HttpURLConnection connection = (HttpURLConnection) url.openConnection();
   connection.setDoInput(true);
   connection.setDoOutput(true);
   connection.setRequestMethod("POST");
   connection.setRequestProperty("Accept", "application/json");
   connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
   OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
   writer.write(payload);
   writer.close();
   int code = connection.getResponseCode();
   BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
   String jsonString = "";
   String line;
   while ((line = br.readLine()) != null) {
    jsonString += line;
   }
   br.close();
   connection.disconnect();
   JSONObject obj = new JSONObject(jsonString);
   JSONArray arr = obj.getJSONArray("responses");
   JSONObject jsonobject = arr.getJSONObject(0);
   JSONArray text = jsonobject.getJSONArray("textAnnotations");
   JSONObject array = text.getJSONObject(0);
   String result = array.getString("description");

   return result;
  } catch (Exception e) {
   throw new RuntimeException(e.getMessage());
  }

 }
 public static void main(String[] args) throws FileNotFoundException, IOException {

  String reqURL = "https://vision.googleapis.com/v1/images:annotate?key=API_KEY";  //Input a valid Api key from Google Vision API
  File file = new File("D:\\ImageProcessing\\download.jpg");
  FileInputStream imageInFile = new FileInputStream(file);
  byte imageData[] = new byte[(int) file.length()];
  imageInFile.read(imageData);
  // Converting Image byte array into Base64 String
  byte[] imageDataString = Base64.encodeBase64(imageData);
  String encodedString = new String(imageDataString, StandardCharsets.US_ASCII);
  String payload = "{\n" +
   "  \"requests\": [\n" +
   "    {\n" +
   "      \"image\": {\n" +
   "        \"content\": \"" + encodedString + "\"\n" +
   "      },\n" +
   "      \"features\": [\n" +
   "        {\n" +
   "          \"type\": \"TEXT_DETECTION\"\n" +
   "        }\n" +
   "      ]\n" +
   "    }\n" +
   "  ]\n" +
   "}";
  String output = sendPostRequest(reqURL, payload);
  System.out.println(output);
 }

}