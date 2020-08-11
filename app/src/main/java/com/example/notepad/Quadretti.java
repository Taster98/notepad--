package com.example.notepad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

public class Quadretti extends androidx.appcompat.widget.AppCompatEditText{
    int quad_dim;
    Paint righe = new Paint();

    public Quadretti(Context context){
        super(context);
        init();
    }

    public Quadretti(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Quadretti(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        righe.setARGB(97,0,0,140);
        quad_dim=99;
    }

    protected void onDraw(Canvas canvas){
        int w=getWidth();
        int h=getHeight()*27;
        for (int x=0;x<w;x+=quad_dim)
            canvas.drawLine(x, 0, x, h, righe);
        for (int y=0;y<h;y+=quad_dim)
            canvas.drawLine(0, y, w, y, righe);
        super.onDraw(canvas);
    }
}
