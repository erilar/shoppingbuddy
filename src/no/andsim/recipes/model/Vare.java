package no.andsim.recipes.model;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

public class Vare implements KvmSerializable {

	private String barcode;
	private String name;
	
	
	public Vare(){
		super();
	}
	
	public Vare(String barcode, String name) {
		super();
		this.barcode = barcode;
		this.name = name;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object getProperty(int arg0) {
		if(arg0==0) return barcode;
		else return name;
	}

	@Override
	public int getPropertyCount() {
		
		return 2;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo info) {
		if(arg0 == 0){
			info.type = PropertyInfo.LONG_CLASS;
            info.name = "barcode";
		}
		else{
			info.type= PropertyInfo.STRING_CLASS;
			info.name="name";
		}
		
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		if(arg0 == 0){
			barcode = arg1.toString();
		}
		else name = arg1.toString();
		
	}

	@Override
	public String toString() {
		return "Vare [barcode=" + barcode + ", name=" + name + "]";
	}

}
