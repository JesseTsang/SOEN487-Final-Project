package soen487.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.Scanner;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import soen487.entity.Retailer;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Moh
 */
public class Driver {
    public static void main(String[] args) throws Exception {
        System.out.println("Welcome to Warehouse Java Client\n");
        // run process
        run();
    } 
    
    // the process
    private static void run() throws Exception {
        
        // uri endpoints
        String uri1 = "(1) warehouses/{YYY}/product/{PPP}";
        String uri2 = "(2) warehouses/{YYY}/products/{TTT}";
        String uri3 = "(3) warehouses/{YYY}/manufacturers/{ZZZ}";
        String uri4 = "(4) warehouses/{YYY}/manufacturers/{ZZZ}/products/{PPP}";
        String uri5 = "(5) warehouses/{YYY}/retailer/add/{RRR}";
        String uri6 = "(6) warehouses/{YYY}/retailer/delete/{RRR}";
       
        // welcome message
        System.out.println("**********************************");
        System.out.println("Avaliable URI endpoints");
        System.out.println("(1) warehouses/{YYY}/product/{PPP}");
        System.out.println("(2) warehouses/{YYY}/products/{TTT}");
        System.out.println("(3) warehouses/{YYY}/manufacturers/{ZZZ}");
        System.out.println("(4) warehouses/{YYY}/manufacturers/{ZZZ}/products/{PPP}");
        System.out.println("(5) warehouses/{YYY}/retailer/add/{RRR}");
        System.out.println("(6) warehouses/{YYY}/retailer/delete/{RRR}");
        System.out.println("**********************************\n");
        
        // client enters uri selection
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the URI #(1...6) you wish to run, enter 'exit' to exit: ");
        String clientInput = scan.next();        
        
        // handle uri response 
        if (clientInput.equals("exit")) {
            System.exit(0);
        }
        else {
            // valid uri selection
            uriHandler(clientInput, uri1, uri2, uri3, uri4, uri5, uri6);
        }
    }
    
    // handles which uri endpoint to process
    private static void uriHandler(String clientInput, String uri1, String uri2, String uri3, String uri4, String uri5, String uri6) throws Exception {
        switch (clientInput) {
            case "1":
                {
                    System.out.println("Selected URI: " + uri1);
                    Scanner scan1 = new Scanner(System.in);
                    System.out.print("YYY: ");
                    int warehouseID = scan1.nextInt();
                    System.out.print("PPP: ");
                    int productID = scan1.nextInt();
                    // URI
                    String uriGenerated = "warehouses/" + warehouseID + "/product/" + productID;
                    System.out.println("Generated uri: " + uriGenerated);
                    // print response
                    printResponse(uriGenerated);
                    break;
                }
            case "2":
                {
                    System.out.println("Selected URI: " + uri2);
                    Scanner scan1 = new Scanner(System.in);
                    System.out.print("YYY: ");
                    int warehouseID = scan1.nextInt();
                    System.out.print("TTT: ");
                    String productType = scan1.next();
                    // URI
                    String uriGenerated = "warehouses/" + warehouseID + "/products/" + productType;
                    System.out.println("Generated uri: " + uriGenerated);
                    // print response
                    printResponse(uriGenerated);
                    break;
                }
            case "3":
                {
                    System.out.println("Selected URI: " + uri3);
                    Scanner scan1 = new Scanner(System.in);
                    System.out.print("YYY: ");
                    int warehouseID = scan1.nextInt();
                    System.out.print("ZZZ: ");
                    String manufacturerID = scan1.next();
                    // URI
                    String uriGenerated = "warehouses/" + warehouseID + "/manufacturers/" + manufacturerID;
                    System.out.println("Generated uri: " + uriGenerated);
                    // print response
                    printResponse(uriGenerated);
                    break;
                }
            case "4":
                {
                    System.out.println("Selected URI: " + uri4);
                    Scanner scan1 = new Scanner(System.in);
                    System.out.print("YYY: ");
                    int warehouseID = scan1.nextInt();
                    System.out.print("ZZZ: ");
                    String manufacturerID = scan1.next();
                    System.out.print("PPP: ");
                    int productID = scan1.nextInt();
                    // URI
                    String uriGenerated = "warehouses/" + warehouseID + "/manufacturers/" + manufacturerID + "/products/" + productID;
                    System.out.println("Generated uri: " + uriGenerated);
                    // print response
                    printResponse(uriGenerated);
                    break;
                }
            case "5":
                {
                    System.out.println("Selected URI: " + uri5);
                    Scanner scan1 = new Scanner(System.in);
                    System.out.print("YYY: ");
                    int warehouseID = scan1.nextInt();
                    System.out.print("RRR: ");
                    int retailerID = scan1.nextInt();
                    // URI
                    String uriGenerated = "warehouses/" + warehouseID + "/retailer/add/" + retailerID;
                    WarehouseClient client = new WarehouseClient();
                    Response response = client.webTarget.path(uriGenerated).request().post(javax.ws.rs.client.Entity.entity("/warehouses/" + warehouseID + "/retailer/add/" + "retailerID", MediaType.APPLICATION_XML), Response.class);
                    // print response
                    if (response.getStatus() == 204) {
                        System.out.println(response);
                        System.out.println("The server successfully processed the request and is not returning any content. Check Retailer table for new entry\n");
                    }
                    else {
                        System.out.println(response.getStatus());
                        System.out.println("Enter valid data!");
                    }       break;
                }
            case "6":
                {
                    System.out.println("Selected URI: " + uri6);
                    Scanner scan1 = new Scanner(System.in);
                    System.out.print("YYY: ");
                    int warehouseID = scan1.nextInt();
                    System.out.print("RRR: ");
                    int retailerID = scan1.nextInt();
                    WarehouseClient client = new WarehouseClient();
                    Retailer r = new Retailer();
                    r.setRetailerid(retailerID);
                    r.setWarehouseid(warehouseID);
                    String uriGenerated = "warehouses/" + warehouseID + "/retailer/delete/" + retailerID;
                    Response response = client.webTarget.path(uriGenerated).request().delete();
                    // print response
                    if (response.getStatus() == 204) {
                        System.out.println(response);
                        System.out.println("The server successfully processed the request and is not returning any content. Check Retailer table for updated retailer/warehouse relationship\n");
                    }
                    else {
                        System.out.println(response.getStatus());
                        System.out.println("Enter valid data!");
                    }       break;
                }
            default:
                break;
        }
         
        // continue process
        run();
    }
    
    // prints uri endpoint response
    private static void printResponse(String uri) throws Exception {
        WarehouseClient client = new WarehouseClient();        
        String xmlFormatedResponse;
        // GET XML response
        String xmlAppResponseUri = client.webTarget.path(uri).request()
            .accept(MediaType.APPLICATION_XML).get(String.class);
            
        // GET JSON response 
        String jsonResponseUri = client.webTarget.path(uri).request()
            .accept(MediaType.APPLICATION_JSON).get(String.class); 

        // format responses 
        if (xmlAppResponseUri.equals("")) {
            xmlFormatedResponse = "null";
        }
        else {
            xmlFormatedResponse = prettyFormat(xmlAppResponseUri, 2);
        }
        
        String jsonFormatedResponse = formatJson(jsonResponseUri);
        
        // print responses
        System.out.println("\nXML Response\n" + xmlFormatedResponse);
        System.out.println("\nJSON Response\n" + jsonFormatedResponse + "\n");
    }
    
    // formats xml string response into xml response with indentations
    // source: http://javakafunda.blogspot.ca/2012/04/how-to-format-xml-string-in-java.html
    public static String prettyFormat(String input, int indent) throws Exception {
        Source xmlInput = new StreamSource(new StringReader(input));
        StringWriter stringWriter = new StringWriter();
        StreamResult xmlOutput = new StreamResult(stringWriter);
        System.out.println(xmlOutput);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute("indent-number", indent);
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(xmlInput, xmlOutput);
        return xmlOutput.getWriter().toString();
    }
    
    // formats json string response into json response with indentations
    // source : https://stackoverflow.com/questions/8596161/json-string-tidy-formatter-for-java
    public static String formatJson(String jsonString) {
        JsonParser parser = new JsonParser();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement el = parser.parse(jsonString);
        jsonString = gson.toJson(el); 
        return jsonString;
    }
}