package com.mcoy.snapscrolldemo;

import com.mcoy.snapscrollview.McoyProductContentPage;
import com.mcoy.snapscrollview.McoyProductDetailInfoPage;
import com.mcoy.snapscrollview.McoySnapPageLayout;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	private McoySnapPageLayout mcoySnapPageLayout = null;
	
	private McoyProductContentPage bottomPage = null;
	private McoyProductDetailInfoPage topPage = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mcoySnapPageLayout = (McoySnapPageLayout) findViewById(R.id.flipLayout);
		
		topPage = new McoyProductDetailInfoPage(MainActivity.this,
				getLayoutInflater().inflate(
				R.layout.mcoy_produt_detail_layout, null));
		bottomPage = new McoyProductContentPage(MainActivity.this,
						getLayoutInflater().inflate(
						R.layout.mcoy_product_content_page, null));
		
		mcoySnapPageLayout.setSnapPages(topPage, bottomPage);
		
	}

}
