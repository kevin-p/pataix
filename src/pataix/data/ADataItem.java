package pataix.data;


public class ADataItem 
{

	protected String EntityId;	
	protected String Nom;
	protected String Localisation;
	protected String Type;
	
	protected double Latitude;
	protected double Longitude;
	
	public ADataItem(String EntityId, String Nom, String Localisation, String Type, double Latitude, double Longitude) 
	{
		this.EntityId    	= new String(EntityId);
		this.Nom	   	 	= new String(Nom);
		this.Localisation 	= new String(Localisation);
		this.Type			= new String(Type);
		this.Latitude		= Latitude;
		this.Longitude      = Longitude;
	}
	
	public String getEntityId() 
	{
		return EntityId;
	}
	public double getLatitude() 
	{
		return Latitude;
	}
	public String getLocalisation() 
	{
		return Localisation;
	}
	public double getLongitude() 
	{
		return Longitude;
	}
	public String getType() 
	{
		return Type;
	}
	public String getNom() 
	{
		return Nom;
	}
	

}

