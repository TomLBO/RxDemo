package codingbo.rxdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Observable 创建
        // 1. create
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String content = "hello ReactiveX!";
                        Log.d(TAG, "call: content - " + content);
                        subscriber.onNext(content);
                        String content2 = "hello reactiveX again!";
                        Log.d(TAG, "call: content2 - " + content2);
                        subscriber.onNext(content2);
                        Log.d(TAG, "call: onCompleted");
                        subscriber.onCompleted();
                    }
                }).start();
            }
        });
        //2. just
        Observable<String> justObservable = Observable.just("just1", "just2");

        //3. from List

        final ArrayList<String> list = new ArrayList<>();
        list.add("item1");
        list.add("item2");
        list.add("item3");
        list.add("item4");


        //4. defer
        Observable<String> defer = Observable.defer(new Func0<Observable<String>>() {

            //注意此处的call方法没有Subscriber参数
            @Override
            public Observable<String> call() {
                return Observable.just("hahaha ");
            }
        });

        // 5. interval

        Observable<Long> interval = Observable.interval(1, TimeUnit.SECONDS);


        final Observer<String> receiver = new Observer<String>() {
            @Override
            public void onCompleted() {
                //数据接收完成
                Log.d(TAG, "onCompleted: ");

            }

            @Override
            public void onError(Throwable e) {
                //发生错误
                Log.d(TAG, "onError: ");
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
                //正常接收数据
                Log.d(TAG, "onNext s: " + s);
            }
        };

        observable.subscribe(receiver);
        justObservable.subscribe(receiver);

        defer.subscribe(receiver);


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

//                Observable<String> fromObservable = Observable.from(list);
//                fromObservable.subscribe(receiver);

                Observable.from(list)
                        .subscribe(receiver);

            }
        }).start();


        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.d(TAG, "Thread test, send Thread:" + Thread.currentThread().getName());
                subscriber.onNext("its a message");

                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.newThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Thread test, observer Thread:" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "Thread test, onNext s: " + s);
                        Log.d(TAG, "Thread test, observer Thread:" + Thread.currentThread().getName());
                    }
                });

    }
}
