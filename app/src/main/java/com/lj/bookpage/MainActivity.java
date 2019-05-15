package com.lj.bookpage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

//    private BookPageView bookPageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        bookPageView =findViewById(R.id.view_book_page);
//        bookPageView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        if(event.getY() < bookPageView.getViewHeight()/2 && event.getX() < bookPageView.getViewWidth() / 2){
//                            bookPageView.setTouchPoint(event.getX(),event.getY(),bookPageView.STYLE_TOP_LEFT);
//                        }else if(event.getY() < bookPageView.getViewHeight()/2 && event.getX() > bookPageView.getViewWidth() / 2){
//                            bookPageView.setTouchPoint(event.getX(),event.getY(),bookPageView.STYLE_TOP_RIGHT);
//                        } else if(event.getY() > bookPageView.getViewHeight()/2 && event.getX() < bookPageView.getViewWidth() / 2){
//                            bookPageView.setTouchPoint(event.getX(),event.getY(),bookPageView.STYLE_BOTTOM_LEFT);
//                        } else if(event.getY() > bookPageView.getViewHeight()/2 && event.getX() > bookPageView.getViewWidth() / 2){
//                            bookPageView.setTouchPoint(event.getX(),event.getY(),bookPageView.STYLE_BOTTOM_RIGHT);
//                        }
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        bookPageView.setTouchPoint(event.getX(),event.getY(), -1);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        bookPageView.setDefaultPath();//回到默认状态
//                        break;
//                }
//                return false;
//            }
    }
}
