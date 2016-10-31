package ru.advantum.fedosov.numericmethods;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

public class DrawActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_draw);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.layout);
        ArrayList<Point> numbericResults = new ArrayList() ;
        ArrayList<Point> realResults = new ArrayList();
        MainDrawPanel mainDrawPanel = new MainDrawPanel(this,numbericResults,realResults);
        viewGroup.addView(mainDrawPanel);
    }
}
