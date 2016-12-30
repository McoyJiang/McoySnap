package material.danny_jiang.com.mcoysnaplibrary.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import material.danny_jiang.com.mcoysnaplibrary.widget.McoySnapPageLayout;
import material.danny_jiang.com.mcoysnaplibrary.view.McoyWebView;
import material.danny_jiang.com.mcoysnaplibrary.R;

/**
 * Created by axing on 16/12/30.
 */

public class McoyWebSnapPage implements McoySnapPageLayout.McoySnapPage {

    private McoyWebView mcoyWebView;
    private LayoutInflater inflater;

    public McoyWebSnapPage(Context context) {
        this(context, R.layout.mcoy_web_snap_layout);
    }

    public McoyWebSnapPage(Context context, int layoutId) {
        this(context, layoutId, null);
    }

    public McoyWebSnapPage(Context context, String webUrl) {
        this(context, R.layout.mcoy_web_snap_layout, webUrl);
    }

    public McoyWebSnapPage(Context context, int layoutId, String webUrl) {
        inflater = LayoutInflater.from(context);

        mcoyWebView = ((McoyWebView) inflater.inflate(layoutId, null));

        if (webUrl != null)
            mcoyWebView.loadUrl(webUrl);
    }

    @Override
    public View getRootView() {
        return mcoyWebView;
    }

    @Override
    public boolean isAtTop() {
        int scrollY = mcoyWebView.getScrollY();

        if (scrollY == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isAtBottom() {
        int scrollY = mcoyWebView.getScrollY();
        int height = mcoyWebView.getHeight();
        int webViewMeasuredHeight = mcoyWebView.getContentHeight();

        if ((scrollY + height) >= webViewMeasuredHeight) {
            return true;
        }
        return false;
    }
}
