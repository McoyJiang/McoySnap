package material.danny_jiang.com.mcoysnapproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import material.danny_jiang.com.mcoysnaplibrary.page.McoyProductContentPage;
import material.danny_jiang.com.mcoysnaplibrary.page.McoyProductDetailInfoPage;
import material.danny_jiang.com.mcoysnaplibrary.page.McoyScrollSnapPage;
import material.danny_jiang.com.mcoysnaplibrary.page.McoyWebSnapPage;
import material.danny_jiang.com.mcoysnaplibrary.widget.McoySnapPageLayout;

public class MainActivity extends AppCompatActivity {

    private McoySnapPageLayout mcoySnapPageLayout = null;

    private McoyProductContentPage bottomPage = null;
    private McoyProductDetailInfoPage topPage = null;

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
