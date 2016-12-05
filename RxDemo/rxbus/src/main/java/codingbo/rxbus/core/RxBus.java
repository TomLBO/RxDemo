package codingbo.rxbus.core;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by bob
 * on 16.11.19.
 */

public class RxBus {
    static volatile RxBus defaultInstance;

    private final Subject<Object, Object> bus;

    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getDefault() {
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new RxBus();
                }
            }
        }
        return defaultInstance;
    }

    public void post(Object obj){
        bus.onNext(obj);
    }

    public <T>Observable<T> toObserverable(Class<T> eventType){
        return bus.ofType(eventType);
    }


    public void register(Object obj){}
    public void unRegister(Object obj){}


}
