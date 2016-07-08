package material.danny_jiang.com.mcoysnaplibrary;


import android.content.Context;
import android.view.View;

public class McoyProductDetailInfoPage implements McoySnapPageLayout.McoySnapPage{
	
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
		return true;
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
