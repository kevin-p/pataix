package pataix.data;

import java.io.IOException;
import java.util.Vector;

import android.location.Location;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ADataSelect
{

    protected Vector <ADataItem> VectADataItem = new Vector<ADataItem>();; //Vector contenant les items dans MaxPerimeter
    protected String url = new String();
    
    public ADataSelect(String url) 
    {
    	this.url = url;
    }
    
    public Vector<ADataItem> SearchItem(Location Loc, int maxPerimeter) throws JSONException, IOException 
    {
		if (VectADataItem.isEmpty()) VectADataItem.clear(); // On vide le vector précédement remplis
    	//On vas chercher les données
    	JSONArray donnees = jsonArrayFromUrl();
    	
	    	for(int i = 0; i < donnees.length(); ++i)
	        {
	            JSONObject jsonObject = donnees.getJSONObject(i);
	
	            double latitudeItem  = Double.parseDouble(jsonObject.getString("longitude"));
	            double longitudeItem = Double.parseDouble(jsonObject.getString("latitude"));
	            
		        double longitude = Loc.getLongitude();
		        double latitude  = Loc.getLatitude();
	   
		        if (getDistance(latitudeItem, longitudeItem, latitude, longitude) <= (maxPerimeter * 1000))
		        {
		        	//On construit les objets ADataItem et on les ajoute dans le tableau
			        String localisation = new String(jsonObject.getString("ville"));
			        String entityId     = new String(jsonObject.getString("entityid"));
			        String type         = new String(jsonObject.getString("type"));
	//		        entityId            = entityId .substring(0, 8)   + entityId .substring(9, 12)   + 
	//		        		              entityId .substring(14, 17) + entityId .substring(19, 23 ) +
	//		        		              entityId.substring(25, 36); 
			        String nom          = new String(jsonObject.getString("raisonsociale"));
			        
			        VectADataItem.add(new ADataItem(entityId, nom, localisation, type, latitudeItem, longitudeItem));
		        }
		    
		        if (VectADataItem.size() == 0) VectADataItem.add(new ADataItem("NULL", "", "", "", 0, 0));
	    
	        }
	        return VectADataItem;
        
	}

    JSONArray   jsonArrayFromUrl() throws IOException
    {  
        // On récupère un client capable d'envoyer des requêtes
        HttpClient client = new DefaultHttpClient();
        HttpGet getAction = new HttpGet(url);
        HttpResponse response = client.execute(getAction);
     
        // on récupère le payload de la réponse en String
        HttpEntity entity = response.getEntity();
        String entityStr = (entity == null) ? null : EntityUtils.toString(entity);
     
        // on transforme en JSON
        try 
        {
             return new JSONArray(entityStr);
        } 
        catch (Exception ex) 
        {
            throw new IOException("Invalid response from server !",ex);
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

    public static void main(String[] args) {
		
	}
    
}
