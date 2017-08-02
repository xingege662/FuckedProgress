package cx.com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private FuckedProgress mFuckedProgress;
    private Button mBtnAdd;
    private Button mBtnDelete;
    private int mProgress = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mFuckedProgress = (FuckedProgress) findViewById(R.id.fuckedProgress);
        mFuckedProgress.setmProgress(mProgress+"");
        mBtnAdd = (Button) findViewById(R.id.btnAdd);
        mBtnDelete = (Button) findViewById(R.id.btnDelete);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress = mProgress + 5;
                mFuckedProgress.setmProgress(mProgress+"");
            }
        });
        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress = mProgress - 5;
                mFuckedProgress.setmProgress(mProgress+"");
            }
        });
    }
}
