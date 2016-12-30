package material.danny_jiang.com.mcoysnaplibrary.page;


import android.content.Context;
import android.view.View;

import material.danny_jiang.com.mcoysnaplibrary.view.McoyScrollView;
import material.danny_jiang.com.mcoysnaplibrary.widget.McoySnapPageLayout;
import material.danny_jiang.com.mcoysnaplibrary.R;

public class McoyProductDetailInfoPage implements McoySnapPageLayout.McoySnapPage {
	
	private Context context;
	
	private View rootView = null;
	private McoyScrollView mcoyScrollView = null;
	
	public McoyProductDetailInfoPage (Context context, View rootView) {
		this.context = context;
		this.rootView = rootView;
		mcoyScrollView = (McoyScrollView) this.rootView
				.findViewById(R.id.product_scrollview);
	}
	
	@Override
	public View getRootView() {
		return rootView;
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
