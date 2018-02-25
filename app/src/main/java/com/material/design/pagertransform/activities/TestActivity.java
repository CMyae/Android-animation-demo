package com.material.design.pagertransform.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.material.design.pagertransform.R;

public class TestActivity extends AppCompatActivity {

    int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

//        /*
//        * create 4 threads to do 4 operations
//        * */
//        long start = System.currentTimeMillis();
//        for(int i=0;i<Integer.MAX_VALUE;i++){
//            result = i;
//        }
//        for(int i=0;i<Integer.MAX_VALUE;i++){
//            result = i;
//        }
//        for(int i=0;i<Integer.MAX_VALUE;i++){
//            result = i;
//        }
//        long end = System.currentTimeMillis();
//        Log.i("TAG",(end-start) + " ");
//        Toast.makeText(this,result+"",Toast.LENGTH_SHORT).show();



    }

    public void withThread(){
        final Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("TAG","1");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } // do operation 1
        });
        final Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("TAG","2");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } // do operation 2
        });
        final Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("TAG","3");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } // do operation 3
        });
        final Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("TAG","4");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } // do operation 4
        });

        Thread masterThread = new Thread(new Runnable() {
            @Override
            public void run() {
                thread1.start();
                thread2.start();
                thread3.start();
                thread4.start();
                while (true) {
                    try {
                        thread1.join();
                        thread2.join();
                        thread3.join();
                        thread4.join();
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("TAG","All thred complete");
            }
        });
        masterThread.start();
    }
}
