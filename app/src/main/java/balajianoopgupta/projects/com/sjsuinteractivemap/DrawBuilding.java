package balajianoopgupta.projects.com.sjsuinteractivemap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by balaji.byrandurga on 11/1/16.
 */

class DrawBuilding extends ImageView {

    float x1;
    float y1;
    float x2;
    float y2;

    float xPix;
    float yPix;
    boolean location;

    public DrawBuilding(Context context) {
        super(context);
    }

    public DrawBuilding(Context context, float x1, float y1, float x2, float y2) {
        super(context);
        MapActivity.removeMarkings();
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public DrawBuilding(Context context, float xPix, float yPix) {
        super(context);
        MapActivity.removeMarkings();
        this.xPix = xPix;
        this.yPix = yPix;
        location = true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(location){
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(xPix, yPix, 25, paint);
        }
        else {
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10);
            canvas.drawRect(x1, y1, x2, y2, paint);
        }

    }


}