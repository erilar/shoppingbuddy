package no.andsim.recipes.activity;

import java.io.IOException;

import no.andsim.recipes.activity.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class VareServiceClient extends Activity {
	private static final String NAMESPACE = "http://service.andsim.no/";
	private static String URL = "http://172.16.9.177:9090/vareservice-1.0-SNAPSHOT/HelloWorld?wsdl";
	private static final String METHOD_NAME = "sayHi";
	private static final String SOAP_ACTION = "http://service.andsim.no/sayHi";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.service);
		TextView lblResult = (TextView) findViewById(R.id.wsresult);
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		PropertyInfo propInfo = new PropertyInfo();
		propInfo.name = "arg0";
		propInfo.type = PropertyInfo.STRING_CLASS;
		propInfo.setValue("John Smith");
		request.addProperty(propInfo);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);

		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive  resultsRequestSOAP = (SoapPrimitive) envelope.getResponse();
			String returnMessage = resultsRequestSOAP.toString();
			lblResult.setText(returnMessage);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		
		

	}
}
