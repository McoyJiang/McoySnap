package material.danny_jiang.com.mcoysnaplibrary.page;

import android.content.Context;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import material.danny_jiang.com.mcoysnaplibrary.widget.McoySnapPageLayout;
import material.danny_jiang.com.mcoysnaplibrary.R;

public class McoyProductContentPage implements McoySnapPageLayout.McoySnapPage {
	
	private Context context;

	private View rootView = null;

	private WebView webView;

	public McoyProductContentPage(Context context,View rootView) {
		this.context = context;
		this.rootView = rootView;

		webView = (WebView) rootView.findViewById(R.id.webView_Content);

		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient());
		webView.setWebViewClient(new WebViewClient());
		webView.loadUrl("http://www.baidu.com");
	}

	@Override
	public View getRootView() {
		return rootView;
	}

	@Override
	public boolean isAtTop() {
		int scrollY = webView.getScrollY();

		if (scrollY == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isAtBottom() {
		int scrollY = webView.getScrollY();
		int height = webView.getHeight();
		int webViewMeasuredHeight = webView.getContentHeight();

		if ((scrollY + height) >= webViewMeasuredHeight) {
			return true;
		}
		return false;
	}

}
