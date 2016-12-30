# McoySnap
# V0.2
在v0,1的基础上，进行了以下功能扩展：<br>
1 可随意调用addSnapPage向布局中添加需要上下滑动的页面<br>
2 对ScrollView和WebView做区分处理：分别使用McoyScrollSnapPlage和McoyWebSnapPage来显示<br>

##注意<br>
如果是想使用ScrollView显示上下滑动的内容，可以使用McoyScrollSnapPlage。<br>
如果是先使用WebView显示网页内容可以使用McoyWebSnapPage
<br>
## 具体使用方法<br>
1 添加依赖<br>
在Project build.gradle中添加如下代码<br>
<br>
```
allprojects {

		repositories {
		
			...
			
			maven { url "https://jitpack.io" }
			
		}
		
}
```
<br>
在app的build.gradle中添加如下依赖：<br>
<br>
dependencies {

	        compile 'com.github.McoyJiang:McoySnap:v2.0'
	        
}
<br>
2 在MainActivity的布局文件中引用McoySnapPageLayout的全路径，如下所示<br>
```
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent" >

    <material.danny_jiang.com.mcoysnaplibrary.widget.McoySnapPageLayout
        android:id="@+id/flipLayout"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent" >
    </material.danny_jiang.com.mcoysnaplibrary.widget.McoySnapPageLayout>

</RelativeLayout>
```
<br>
3 在MainActivity中初始化McoySnapPageLayout控件，并调用addSnapPage方法添加需要单页显示的页面，代码如下:<br>
```
public class MainActivity extends AppCompatActivity {

    private McoySnapPageLayout mcoySnapPageLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mcoySnapPageLayout = (McoySnapPageLayout) findViewById(R.id.flipLayout);

        McoyScrollSnapPage m1 = new McoyScrollSnapPage(this, R.layout.test);
        McoyScrollSnapPage m2 = new McoyScrollSnapPage(this, R.layout.test);
        McoyScrollSnapPage m3 = new McoyScrollSnapPage(this, R.layout.test);

        mcoySnapPageLayout.addSnapPage(m1);
        mcoySnapPageLayout.addSnapPage(m2);
        mcoySnapPageLayout.addSnapPage(m3);

        McoyWebSnapPage w1 = new McoyWebSnapPage(this, "http://www.baidu.com");
        McoyWebSnapPage w2 = new McoyWebSnapPage(this, "http://blog.csdn.net/zxm317122667/article/details/47018357");

        mcoySnapPageLayout.addSnapPage(w1);
        mcoySnapPageLayout.addSnapPage(w2);
    }
}
```
<br>
4 运行显示如下效果
# V0.1
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
