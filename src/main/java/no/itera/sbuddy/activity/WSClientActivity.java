package no.itera.sbuddy.activity;

import java.io.StringWriter;
import java.util.List;

import no.itera.sbuddy.activity.R;
import no.itera.sbuddy.model.Product;
import no.itera.sbuddy.ws.ProductWSClientRest;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class WSClientActivity extends Activity {
	
	private final ProductWSClientRest clientRS = ProductWSClientRest.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.service);
		TextView lblResult = (TextView) findViewById(R.id.wsresult);
		super.onCreate(savedInstanceState);

		List<Product> products = clientRS.getProductsFromService();

		if (products != null) {
			StringWriter writer = new StringWriter();
			for (Product product : products) {
				writer.append(product.toString());
			}
			lblResult.setText(writer.toString() + " SIZE " + products.size());

		}
	}
}
