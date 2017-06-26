package org.askdn.emoai.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;

public class AutoFitTextureView extends TextureView {

    private int widthRatio;
    private int heightRatio;

    public AutoFitTextureView(Context context) {
        super(context);
    }

    public AutoFitTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAspectRatio(int width, int height) {
        if (width < 0 || height < 0) {
           throw new IllegalArgumentException(String.format("Width / Height cannot be zero, width = %s, height = %s", width, height));
        }

        widthRatio = width;
        heightRatio = height;
        requestLayout(); // Notify that the layout has changed.
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int aspectRatio = widthRatio / heightRatio;
        if (0 == widthRatio || 0 == heightRatio) {
            setMeasuredDimension(width, height);
        } else if (width < height * aspectRatio) {
            setMeasuredDimension(width, width * aspectRatio);
        } else {
            setMeasuredDimension(height * aspectRatio, height);
        }
    }

}
