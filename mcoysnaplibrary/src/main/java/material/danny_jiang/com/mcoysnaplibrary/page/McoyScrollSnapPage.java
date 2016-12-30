package material.danny_jiang.com.mcoysnaplibrary.page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import material.danny_jiang.com.mcoysnaplibrary.view.McoyScrollView;
import material.danny_jiang.com.mcoysnaplibrary.widget.McoySnapPageLayout;
import material.danny_jiang.com.mcoysnaplibrary.R;

/**
 * Created by axing on 16/12/29.
 */

public class McoyScrollSnapPage implements McoySnapPageLayout.McoySnapPage {

    private LayoutInflater inflater;

    private McoyScrollView mcoyScrollView;
    private LinearLayout linearLayout;

    public McoyScrollSnapPage(Context context) {
        this(context, R.layout.mcoy_scroll_snap_layout, -1);
    }

    public McoyScrollSnapPage(Context context, int childLayoutId) {
        this(context, R.layout.mcoy_scroll_snap_layout, childLayoutId);
    }

    public McoyScrollSnapPage(Context context, int parentLayoutId, int childLayoutId) {
        inflater = LayoutInflater.from(context);

        mcoyScrollView = ((McoyScrollView) inflater.inflate(parentLayoutId, null));

        linearLayout = ((LinearLayout) mcoyScrollView.findViewById(R.id.linearLayout));

        if (childLayoutId != -1)
            addChild(childLayoutId);
    }

    public void addChild(View childView) {
        if (linearLayout != null) {
            linearLayout.addView(childView);
        }
    }

    public void addChild(View childView, LinearLayout.LayoutParams params) {
        if (linearLayout != null) {
            linearLayout.addView(childView, params);
        }
    }

    public void addChild(int childLayoutId) {
        addChild(inflater.inflate(childLayoutId, null));
    }

    @Override
    public View getRootView() {
        return mcoyScrollView;
    }

    @Override
    public boolean isAtTop() {
        int scrollY = mcoyScrollView.getScrollY();

        if (scrollY == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isAtBottom() {
        int scrollY = mcoyScrollView.getScrollY();
        int height = mcoyScrollView.getHeight();
        int scrollViewMeasuredHeight = mcoyScrollView.getChildAt(0).getMeasuredHeight();

        if ((scrollY + height) >= scrollViewMeasuredHeight) {
            return true;
        }
        return false;
    }

}
