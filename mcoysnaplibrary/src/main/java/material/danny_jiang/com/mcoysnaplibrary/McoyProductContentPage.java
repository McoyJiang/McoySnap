package material.danny_jiang.com.mcoysnaplibrary;

import android.content.Context;
import android.view.View;

public class McoyProductContentPage implements McoySnapPageLayout.McoySnapPage {
	
	private Context context;

	private View rootView = null;

	public McoyProductContentPage(Context context,View rootView) {
		this.context = context;
		this.rootView = rootView;
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
		return false;
	}

}
