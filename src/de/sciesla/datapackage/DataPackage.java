package de.sciesla.datapackage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

import de.sciesla.sender.Sender;

public abstract class DataPackage implements Serializable{

	private static final long serialVersionUID = -7179243493900073263L;
	
	private String id;
	private Object[] args;
	
	public DataPackage() {
		
	}
	
	public DataPackage(String id, Object... args) {
		
		this.id = id;
		this.args = args;
	}
	
	public abstract void onServer(Sender sender);
	public abstract void onClient(Sender sender);
	
	@Override
	public String toString() {
	    try {
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    ObjectOutputStream oos = new ObjectOutputStream( baos );
			oos.writeObject(this);
		    oos.close();
		    return Base64.getEncoder().encodeToString(baos.toByteArray()); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static DataPackage fromString(String s) {
		DataPackage o;
	   try {
		   byte [] data = Base64.getDecoder().decode(s);
	       ObjectInputStream ois = new ObjectInputStream( 
		   new ByteArrayInputStream(data) );
		   o = (DataPackage) ois.readObject();
		   ois.close();
		   return o;
	   } catch (ClassNotFoundException | IOException | NullPointerException e) {
		   //error counter
	   }
       return null;
   }

	public int getLength() {
		return args.length;
	}
	
	public String getString(int i){
		if(i < args.length && i >= 0)
			return (String) args[i];
		return "";
	}
	
	public Integer getInt(int i){
		if(i < args.length && i >= 0)
			return (Integer) args[i];
		return 0;
	}
	
	public Float getFloat(int i){
		if(i < args.length && i >= 0)
			return (Float) args[i];
		return 0f;
	}
	
	public Double getDouble(int i){
		if(i < args.length && i >= 0)
			return (Double) args[i];
		return 0d;
	}
	
	public Long getLong(int i){
		if(i < args.length && i >= 0)
			return (Long) args[i];
		return 0l;
	}
	
	public Boolean getBoolean(int i){
		if(i < args.length && i >= 0)
			return (Boolean) args[i];
		return false;
	}
	
	public Object getObject(int i){
		if(i < args.length && i >= 0)
			return args[i];
		return null;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the args
	 */
	public Object[] getArgs() {
		return args;
	}
}
