# McoySnap

实现仿淘宝上下两页进行滑动切换的效果：当第一页滑动到底部，继续上拉则滑动到第二页,如下效果

![image](https://github.com/McoyJiang/McoySnap/raw/master/IMAGE/McoySnap.gif)

如何使用：

1 添加依赖：
在Project build.gradle中添加如下代码

allprojects {

		repositories {
		
			...
			
			maven { url "https://jitpack.io" }
			
		}
		
}

在app的build.gradle中添加如下依赖：

dependencies {

	        compile 'com.github.McoyJiang:McoySnap:v1.0'
	        
}


2 在xml布局文件中如下引用
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="com.mcoy.snapscrolldemo.MainActivity" >

    <TextView
        android:id="@+id/tv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/hello_world" />

    <com.mcoy.snapscrollview.McoySnapPageLayout
        android:id="@+id/flipLayout"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:layout_below="@id/tv" >
    </com.mcoy.snapscrollview.McoySnapPageLayout>

</RelativeLayout>

3 在java代码中初始化McoySnapPageLayout，并添加第一页和第二页的内容
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

大功告成
