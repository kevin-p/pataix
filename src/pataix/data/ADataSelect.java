package pataix.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import android.location.Location;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class ADataSelect
{

	protected Vector<ADataItem> VectADataItem = new Vector<ADataItem>(); //Vector contenant les items dans MaxPerimeter
    
	public ADataSelect(Location Loc, int MaxPerimeter) {
   	 
   	 //On vas chercher les données
   	 String readData = new String (readData());
   	 
   	 try
   	 {
   		 JSONArray jsonArray = new JSONArray(readData);
   		  	 
   		 for (int i = 0; i < jsonArray.length(); i++)
   		 {
   	     	JSONObject jsonObject = jsonArray.getJSONObject(i);
   	    	 
   			 //On séléctionne les données dans le périmètre
   	     	double longitudeItem = Double.parseDouble (jsonObject.getString("longitude"));
   	     	double latitudeItem  = Double.parseDouble (jsonObject.getString("latitude"));

   	     	double longitude = Loc.getLongitude();
   	     	double latitude  = Loc.getLatitude();
   	    	 
   	    	 
 
   	     	if (getDistance(latitudeItem, longitudeItem, latitude, longitude) <= (MaxPerimeter * 1000))
   	     	{
   	    		 //On construit les objets ADataItem et on les ajoute dans le tableau
   		     	String localisation = new String(jsonObject.getString("ville"));
   		     	String entityId 	= new String(jsonObject.getString("entityid"));
   		     	String type     	= new String(jsonObject.getString("type"));
//   		     	entityId        	= entityId .substring(0, 8)   + entityId .substring(9, 12)   +
//   		    			           	entityId .substring(14, 17) + entityId .substring(19, 23 ) +
//   		    			           	entityId.substring(25, 36);
   		     	String nom      	= new String(jsonObject.getString("raisonsociale"));
   		    	 
   		     	VectADataItem.addElement(new ADataItem(entityId, nom, localisation, type, latitudeItem, longitudeItem));
   	     	}
   	     	
   	     	System.out.println(VectADataItem.size());
   	    	 
   	    	 
   		 }
   	 }
   	 catch (Exception e)
   	 {
   		 e.printStackTrace();
   	 }
   		 
    }

	private double getDistance(double lat1, double lon1, double lat2, double lon2)
	{
    	//code for Distance in Kilo Meter
    	double theta = lon1 - lon2;
    	double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
    	dist = Math.abs(Math.round(rad2deg(Math.acos(dist)) * 60 * 1.1515 * 1.609344 * 1000));
    	return (dist);
	}
	private double deg2rad(double deg)
	{
    	return (deg * Math.PI / 180.0);
	}
	private double rad2deg(double rad)
	{
    	return (rad / Math.PI * 180.0);
	}
    
    public String readData()
	{
    	StringBuilder builder = new StringBuilder();
    	HttpClient client 	= new DefaultHttpClient();
    	HttpGet httpGet   	= new HttpGet("http://dataprovence.cloudapp.net:8080/v1/dataprovencetourisme/OTSI/?format=json");
    	try
    	{
      	HttpResponse response = client.execute(httpGet);
      	HttpEntity entity 	= response.getEntity();
      	InputStream content   = entity.getContent();
      	BufferedReader reader = new BufferedReader(new InputStreamReader(content));
      	String line;
      	while ((line = reader.readLine()) != null)
      	{
        	builder.append(line);
      	}
    	}
    	catch (ClientProtocolException e)
    	{
      	e.printStackTrace();
    	}
    	catch (IOException e)
    	{
      	e.printStackTrace();
    	}
    	return builder.toString();
	}
    
    public Vector<ADataItem> getVectADataItem()
    {
   	 return VectADataItem;
    }
    
    
}

