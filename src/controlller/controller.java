package controlller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;


public class controller {

	private static controller instancia;
	//private String ip="127.0.0.1";
	private String ip="192.168.1.101";
	
	
	public static controller getInstancia(){
		if(instancia==null)
			instancia = new controller();
		return instancia;
	}
	
	public String altaBien(String[] datos) throws Exception {
 
		String uri = "http://"+ip+":8080/api/v1/bienes";
        String jsonBody = createJson(datos);
        String respuesta = null;
        URL url = new URL(uri);
      
        String host = url.getHost();
        try {
			InetAddress inetadress = InetAddress.getByName(host);
			boolean isReachable = inetadress.isReachable(5000);
			if(isReachable) {
				System.out.println("srv OK");
			} else {
				System.out.println("Srv no disponible");
			}
		} catch (UnknownHostException e) {
				System.out.println("No se puede resolver el nombre del srv");
		} catch (Exception e) {
			e.printStackTrace();
		}
               
        System.out.println("host "+host);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        
        try {

        	OutputStream os = connection.getOutputStream();
        	byte[] input = jsonBody.getBytes("utf-8");
        	os.write(input, 0, input.length);


        	int responseCode = connection.getResponseCode();
        	String responseMsg = connection.getResponseMessage();

        	if(responseCode == HttpURLConnection.HTTP_OK) {
        		System.out.println("Response code: " + responseCode);
        		System.out.println("mensaje:" + responseMsg);

        		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        		StringBuilder response = new StringBuilder();
        		String responseLine = null;
        		while ((responseLine = br.readLine()) != null) {
        			response.append(responseLine.trim());
        		}
        		respuesta = response.toString();
        		System.out.println(response.toString());

        	} else {
        		// Handle error response
        		BufferedReader err = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        		String errorResponse = err.readLine();
        		respuesta = String.valueOf(errorResponse);
        		System.out.println("Error: " + errorResponse);
        		err.close();
        	}
        } catch (Exception e) {
        	// TODO: handle exception
        }
      
        connection.disconnect();
		return respuesta;
	}
	
    private static String createJsonWithId(String[] datos) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        
        json.put("id", Integer.parseInt(datos[0]));
        json.put("modelo", datos[1]);
        json.put("serie", datos[2]);
        json.put("estado", datos[3]);
        json.put("pallet", datos[4]);
        json.put("piso", Integer.parseInt(datos[5]));
        json.put("responsables", datos[6]);

        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        
        System.out.println("json creado: "+jsonString);
        
        return jsonString;
	}

	private static String createJson(String [] datos) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();

        json.put("modelo", datos[0]);
        json.put("serie", datos[1]);
        json.put("estado", datos[2]);
        json.put("pallet", datos[3]);
        json.put("piso", Integer.parseInt(datos[4]));
        json.put("responsables", datos[5]);

        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        
        System.out.println(jsonString);
        
        return jsonString;
    }

	public String updateContador(String pallet) throws IOException {
		 
	    String uri = "http://"+ip+":8080/api/v1/bienes/cantidad"+"?pallet="+pallet;
	    
        String respuesta = "response initial";

        URL url = new URL(uri);
              
        String host = url.getHost();
        System.out.println("host "+host);
        try {
			InetAddress inetadress = InetAddress.getByName(host);
			boolean isReachable = inetadress.isReachable(5000);
			if(isReachable) {
				System.out.println("srv OK");
						      
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setRequestMethod("GET");
		        
		        /* leer response usando InputStream y BufferedReader */
		        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		        String inputLine;
		        StringBuilder response = new StringBuilder();

		        while ((inputLine = reader.readLine()) != null) {
		            response.append(inputLine);
		        }

		        reader.close();

		        respuesta=response.toString();
		        
		        connection.disconnect();
		        
			} else {
				respuesta = "Srv no disponible";
			}
		} catch (UnknownHostException e) {
				System.out.println("No se puede resolver el nombre del srv");
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		return respuesta;
		
	}
	
	public String getByNumeroSerie(String serie) throws IOException {
		
		String uri = "http://"+ip+":8080/api/v1/bienes/ByNumeroSerie"+"?serie="+serie;
	    
        String respuesta = "response initial";

        URL url = new URL(uri);
              
        String host = url.getHost();
        System.out.println("host "+host);
        try {
			InetAddress inetadress = InetAddress.getByName(host);
			boolean isReachable = inetadress.isReachable(5000);
			if(isReachable) {
				System.out.println("srv OK");
						      
		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		        connection.setRequestMethod("GET");
		        
		        /* leer response usando InputStream y BufferedReader */
		        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		        String inputLine;
		        StringBuilder response = new StringBuilder();

		        while ((inputLine = reader.readLine()) != null) {
		            response.append(inputLine);
		        }

		        reader.close();

		        respuesta=response.toString();
		        
		        connection.disconnect();
		        
			} else {
				respuesta = "Srv no disponible";
			}
		} catch (UnknownHostException e) {
				System.out.println("No se puede resolver el nombre del srv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("la respuesta es: "+respuesta+ " GG");
		return respuesta;
	}

	public String actualizarRegistro(String[] datos) throws Exception {
		
		String uri = "http://"+ip+":8080/api/v1/bienes/update";
        String jsonBody = createJsonWithId(datos);
        String respuesta = null;
        URL url = new URL(uri);
      
        String host = url.getHost();
        try {
			InetAddress inetadress = InetAddress.getByName(host);
			boolean isReachable = inetadress.isReachable(5000);
			if(isReachable) {
				System.out.println("srv OK");
			} else {
				System.out.println("Srv no disponible");
			}
		} catch (UnknownHostException e) {
				System.out.println("No se puede resolver el nombre del srv");
		} catch (Exception e) {
			e.printStackTrace();
		}
               
        System.out.println("host "+host);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        
        try {

        	OutputStream os = connection.getOutputStream();
        	byte[] input = jsonBody.getBytes("utf-8");
        	os.write(input, 0, input.length);


        	int responseCode = connection.getResponseCode();
        	String responseMsg = connection.getResponseMessage();

        	if(responseCode == HttpURLConnection.HTTP_OK) {
        		System.out.println("Response code: " + responseCode);
        		System.out.println("mensaje:" + responseMsg);

        		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        		StringBuilder response = new StringBuilder();
        		String responseLine = null;
        		while ((responseLine = br.readLine()) != null) {
        			response.append(responseLine.trim());
        		}
        		respuesta = response.toString();
        		System.out.println(response.toString());

        	} else {
        		// Handle error response
        		BufferedReader err = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        		String errorResponse = err.readLine();
        		respuesta = String.valueOf(errorResponse);
        		System.out.println("Error: " + errorResponse);
        		err.close();
        	}
        } catch (Exception e) {
        	// TODO: handle exception
        }
      
        connection.disconnect();
		return respuesta;	

	}

	public String cantidadPorPiso(String pallet) throws IOException {
		
		String uri = "http://"+ip+":8080/api/v1/bienes/cantidadPorPiso"+"?pallet="+pallet;

		String respuesta = "";

		URL url = new URL(uri);

		String host = url.getHost();
		System.out.println("host "+host);
		try {
			InetAddress inetadress = InetAddress.getByName(host);
			boolean isReachable = inetadress.isReachable(5000);
			if(isReachable) {
				System.out.println("srv OK");

				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");

				/* leer response usando InputStream y BufferedReader */
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();

				while ((inputLine = reader.readLine()) != null) {
					response.append(inputLine);
				}

				reader.close();

				respuesta=response.toString();

				connection.disconnect();

			} else {
				respuesta = "Srv no disponible";
			}
		} catch (UnknownHostException e) {
			System.out.println("No se puede resolver el nombre del srv");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return respuesta;
	}	
}
