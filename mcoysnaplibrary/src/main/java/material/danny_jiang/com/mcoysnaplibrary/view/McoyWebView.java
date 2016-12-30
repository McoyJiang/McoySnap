package material.danny_jiang.com.mcoysnaplibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by axing on 16/12/30.
 */

public class McoyWebView extends WebView {

    public McoyWebView(Context context) {
        super(context);
    }

    public McoyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);

        getSettings().setJavaScriptEnabled(true);
        setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        setWebViewClient(new WebViewClient());
    }
}
