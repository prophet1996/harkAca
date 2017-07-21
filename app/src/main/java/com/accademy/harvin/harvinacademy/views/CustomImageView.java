package com.accademy.harvin.harvinacademy.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by ishank on 7/7/17.
 */

public class CustomImageView extends android.support.v7.widget.AppCompatImageView {
    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        int w = getWidth(), h = getHeight();

        Bitmap roundBitmap = getCroppedBitmap(bitmap, w,h);
        canvas.drawBitmap(roundBitmap, 0, 0, null);



    }
    public static Bitmap getRoundedCroppedBitmap(Bitmap bitmap, int radius) {
        Bitmap finalBitmap;
        if (bitmap.getWidth() != radius || bitmap.getHeight() != radius)
            finalBitmap = Bitmap.createScaledBitmap(bitmap, radius, radius,
                    false);
        else
            finalBitmap = bitmap;
        Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(),
                finalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, finalBitmap.getWidth(),
                finalBitmap.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));


        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        canvas.drawBitmap(finalBitmap, rect, rect, paint);

        return output;
    }


    @SuppressLint("NewApi")

    private Bitmap getCroppedBitmap(Bitmap bitmap,int width,int height){
        Bitmap finalBitmap;
        if (bitmap.getWidth() != width || bitmap.getHeight() != height)
            finalBitmap = Bitmap.createScaledBitmap(bitmap, width, height,
                    false);
        else
            finalBitmap = bitmap;
        Bitmap output = Bitmap.createBitmap(finalBitmap.getWidth(),
                finalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, finalBitmap.getWidth(),
                finalBitmap.getHeight());

        Point point0_draw = new Point(0, 0);
        Point point1_draw = new Point(0, height*2/6);
        Point point2_draw = new Point(width/2, height);
        Point point3_draw = new Point(width, height*4/6);
        Point point4_draw = new Point(width, 0);
        final RectF oval= new RectF(0,height,width,height*4/6);



        Path path = new Path();
//
//        path.lineTo(point1_draw.x, point1_draw.y);
//        path.moveTo(point0_draw.x,point0_draw.y);
//        path.lineTo(point2_draw.x, point2_draw.y);
//        path.lineTo(point3_draw.x, point3_draw.y);
//        path.lineTo(point4_draw.x, point4_draw.y);
//        path.lineTo(point0_draw.x, point0_draw.y);
        final RectF oval2=new RectF();
     path.moveTo(0,0);
//        path.lineTo(0,height*3/7);
//        path.lineTo(width,height*3/7);
//        path.lineTo(width,0);
//        path.lineTo(0,0);
      oval2.set(0+(width/25.0f),(3/40.0f)*height,width-(width/25.0f),height);
       path.arcTo(oval2,0,180,true);




        path.close();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        canvas.drawARGB(0,0,0,0);
        paint.setColor(Color.parseColor("#BAB399"));
       canvas.drawArc(oval,0,180,true,paint);
       canvas.drawPath(path,paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        canvas.drawBitmap(finalBitmap, rect, rect, paint);
        return output;






    }

}
