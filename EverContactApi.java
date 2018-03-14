/**
 *
 * @author senthuran
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.json.*;

public class EverContactApi {
 public static String sendPostRequest(String requestUrl, String data, String ApiKey) {
  try {
   URL url = new URL(requestUrl);
   HttpURLConnection connection = (HttpURLConnection) url.openConnection();
   connection.setDoInput(true);
   connection.setDoOutput(true);
   connection.setRequestMethod("POST");
   connection.setRequestProperty("Accept", "application/json");
   connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
   connection.setRequestProperty("x-api-key", ApiKey);
   OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
   writer.write(data);
   writer.close();
   BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
   StringBuffer jsonString = new StringBuffer();
   String response = "";
   String line;
   while ((line = br.readLine()) != null) {
    response += line;
    jsonString.append(line);
   }
   br.close();
   connection.disconnect();


   JsonReader reader = Json.createReader(new StringReader(response));

   JsonObject personObject = reader.readObject();

   reader.close();
   String Name = "";
   String Role = "";
   String Organization = "";
   String addvalue = "";
   String street = "";
   String zip = "";
   String city = "";
   String state = "";
   String country = "";
   String tel = "";
   String fax = "";
   String mobile = "";
   String emails = "";
   if (personObject.getJsonObject("everContact").containsKey("fullName")) {
    Name = personObject.getJsonObject("everContact").getString("fullName");
   }
   if (personObject.getJsonObject("everContact").containsKey("jobPosition")) {
    Role = personObject.getJsonObject("everContact").getString("jobPosition");
   }
   if (personObject.getJsonObject("everContact").containsKey("organization")) {
    Organization = personObject.getJsonObject("everContact").getString("organization");
   }
   if (personObject.getJsonObject("everContact").containsKey("addresses")) {

    JsonArray address = personObject.getJsonObject("everContact").getJsonArray("addresses");
    JsonObject objectInArray = address.getJsonObject(0);
    addvalue = objectInArray.getString("value");
    if (objectInArray.getJsonObject("splittedAddress").containsKey("street"))
     street = objectInArray.getJsonObject("splittedAddress").getString("street");
    if (objectInArray.getJsonObject("splittedAddress").containsKey("street"))
     street = objectInArray.getJsonObject("splittedAddress").getString("street");
    if (objectInArray.getJsonObject("splittedAddress").containsKey("street"))
     street = objectInArray.getJsonObject("splittedAddress").getString("street");
    if (objectInArray.getJsonObject("splittedAddress").containsKey("street"))
     street = objectInArray.getJsonObject("splittedAddress").getString("street");
    if (objectInArray.getJsonObject("splittedAddress").containsKey("zip"))
     zip = objectInArray.getJsonObject("splittedAddress").getString("zip");
    if (objectInArray.getJsonObject("splittedAddress").containsKey("city"))
     city = objectInArray.getJsonObject("splittedAddress").getString("city");
    if (objectInArray.getJsonObject("splittedAddress").containsKey("state"))
     state = objectInArray.getJsonObject("splittedAddress").getString("state");
    if (objectInArray.getJsonObject("splittedAddress").containsKey("country"))
     country = objectInArray.getJsonObject("splittedAddress").getString("country");

   }
   if (personObject.getJsonObject("everContact").containsKey("workPhones")) {
    JsonArray phone = personObject.getJsonObject("everContact").getJsonArray("workPhones");
    for (int i = 0; i < phone.size(); i++) {
     JsonObject objectInphone = phone.getJsonObject(i);
     tel += objectInphone.getString("value") + ",";
    }
   }
   if (personObject.getJsonObject("everContact").containsKey("workFaxes")) {
    JsonArray phone = personObject.getJsonObject("everContact").getJsonArray("workFaxes");
    for (int i = 0; i < phone.size(); i++) {
     JsonObject objectInphone = phone.getJsonObject(i);
     fax += objectInphone.getString("value") + ",";
    }
   }
   if (personObject.getJsonObject("everContact").containsKey("mobiles")) {
    JsonArray phone = personObject.getJsonObject("everContact").getJsonArray("mobiles");
    for (int i = 0; i < phone.size(); i++) {
     JsonObject objectInphone = phone.getJsonObject(i);
     mobile += objectInphone.getString("value") + ",";
    }
   }
   if (personObject.getJsonObject("everContact").containsKey("emails")) {
    JsonArray phone = personObject.getJsonObject("everContact").getJsonArray("emails");
    for (int i = 1; i < phone.size(); i++) {
     JsonObject objectInphone = phone.getJsonObject(i);
     emails += objectInphone.getString("value") + ",";
    }
   }
   return "{\"Name\":" + Name + ",\"Role\":" + Role + ",\"Organization\":" + Organization + ",\"FullAddress\":" + addvalue + ",\"Street\":" + street + ",\"Zip\":" + zip + ",\"City\":" + city + ",\"State\":" + state + ",\"Country\":" + country + ",\"Phone\":" + tel + ",\"Mobile\":" + mobile + ",\"Fax\":" + fax + ",\"Email\":" + emails + "}";

  } catch (Exception e) {
   throw new RuntimeException(e.getMessage());
  }

 }
 public static void main(String[] arg) {
  String data = "Ms H Williams\\nExecutive Officer\\nFinance and Accounting\\nAustralia Post\\n219-241 Cleveland St\\nSTRAWBERRY HILLS  NSW  1427\\ntel 077 51858515\\nphone 011336 15 51\\n sen@gmail.com";

  String payload = "{\"subject\":\"conf call\"," + " \"analysisStrategy\":\"WTN_EVERYWHERE\"," + "\"receivedDate\":\"2017-02-22T13:19:00.000+0000\"," + "\"from\":\"Hugo Halimi <halimi@evercontact.com>\"," + "\"content\":\"" + data + "\"," + "\"ccs\":[\"thomas@onemore.company\"]," + "\"tos\":[\"doctrinal@evercontact.com\"]}";
  String requestUrl = "https://contactapi.evercontact.com/v1/tag";
  String ApiKey = "API_KEY";  //Input a valid API key from an Evercontact API
  String output = sendPostRequest(requestUrl, payload, ApiKey);
  System.out.println(output);
 }

}