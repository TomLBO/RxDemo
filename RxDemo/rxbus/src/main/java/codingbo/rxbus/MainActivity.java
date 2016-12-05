package codingbo.rxbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import codingbo.rxbus.core.RxBus;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private CompositeSubscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSubscription = new CompositeSubscription();

        mSubscription.add(RxBus.getDefault().toObserverable(BusEvent.class).subscribe(new Action1<BusEvent>() {
            @Override
            public void call(BusEvent event) {
                Toast.makeText(MainActivity.this, event.getContent(), Toast.LENGTH_SHORT).show();
            }
        }));
    }


    public void bt1(View view){
        RxBus.getDefault().post(new BusEvent(1, "one more thing..."));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mSubscription != null && mSubscription.isUnsubscribed()){
            mSubscription.unsubscribe();
        }
    }
}
