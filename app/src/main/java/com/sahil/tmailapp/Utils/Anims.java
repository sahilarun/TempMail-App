package com.sahil.tmailapp.Utils;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Choreographer;
import android.view.animation.DecelerateInterpolator;

import androidx.recyclerview.widget.RecyclerView;

public class Anims extends Drawable implements Animatable, ValueAnimator.AnimatorUpdateListener {

    /* renamed from: s */
    private static final PorterDuffXfermode f5126s = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

    /* renamed from: t */
    private static ColorFilter f5127t = new ColorMatrixColorFilter(new float[]{0.264f, 0.472f, 0.088f, 0.0f, 0.0f, 0.264f, 0.472f, 0.088f, 0.0f, 0.0f, 0.264f, 0.472f, 0.088f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});

    /* renamed from: b */
    private Drawable f5128b;

    /* renamed from: c */
    private int f5129c;
    /* renamed from: d */
    private int f5130d;

    /* renamed from: l */
    private Paint f5138l;

    /* renamed from: m */
    private Bitmap f5139m;

    /* renamed from: e */
    private int f5131e = RecyclerView.UNDEFINED_DURATION;

    /* renamed from: f */
    private int f5132f = RecyclerView.UNDEFINED_DURATION;

    /* renamed from: g */
    private int f5133g = RecyclerView.UNDEFINED_DURATION;

    /* renamed from: h */
    private int f5134h = 0;

    /* renamed from: i */
    private int f5135i = 0;
    /* renamed from: j */
    private ValueAnimator f5136j = null;

    /* renamed from: k */
    private float f5137k = 0.3f;

    /* renamed from: n */
    private Matrix f5140n = new Matrix();

    /* renamed from: o */
    private boolean f5141o = false;

    /* renamed from: p */
    private boolean f5142p = false;

    /* renamed from: q */
    private ColorFilter f5143q = null;

    /* renamed from: r */
    private Choreographer.FrameCallback f5144r = new Choreographer$FrameCallbackC1820a();
    
    /* renamed from: c.b.a.a.a$a */
    /* loaded from: classes.dex */
    class Choreographer$FrameCallbackC1820a implements Choreographer.FrameCallback {
        Choreographer$FrameCallbackC1820a() {
        }

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            Anims.this.invalidateSelf();
            if (Anims.this.f5141o) {
                Choreographer.getInstance().postFrameCallback(this);
            }
        }
    }

    public Anims(Drawable drawable) {
        m14934a(drawable);
    }

    /* renamed from: a */
    private int m14938a() {
        int i = this.f5130d;
        return ((i - this.f5135i) * 10000) / (i + this.f5131e);
    }
    
    /* renamed from: a */
    private void m14937a(float f) {
        this.f5137k = f;
        int i = this.f5130d;
        this.f5135i = i - ((int) ((this.f5131e + i) * this.f5137k));
        invalidateSelf();
    }

    /* renamed from: a */
    private void m14936a(int i, int i2, int i3) {
        if (i <= 0 || i2 <= 0 || i3 <= 0) {
            Log.w("ContentValues", "updateMask: size must > 0");
            this.f5139m = null;
            return;
        }
        float f = i2;
        int ceil = (int) Math.ceil((i + i2) / f);
        Bitmap createBitmap = Bitmap.createBitmap(i2 * ceil, i3, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        int i4 = i3 / 2;
        Path path = new Path();
        float f2 = i4;
        path.moveTo(0.0f, f2);
        float f3 = f / 4.0f;
        float f4 = -i4;
        int i5 = 0;
        float f5 = 0.0f;
        while (i5 < ceil * 2) {
            float f6 = f5 + f3;
            float f7 = f6 + f3;
            path.quadTo(f6, f4, f7, f2);
            f4 = createBitmap.getHeight() - f4;
            i5++;
            f5 = f7;
        }
        float f8 = i3;
        path.lineTo(createBitmap.getWidth(), f8);
        path.lineTo(0.0f, f8);
        path.close();
        canvas.drawPath(path, paint);
        this.f5139m = createBitmap;
    }
    /* renamed from: a */
    private void m14935a(Rect rect) {
        if (rect.width() > 0 && rect.height() > 0) {
            if (this.f5129c < 0 || this.f5130d < 0) {
                this.f5129c = rect.width();
                this.f5130d = rect.height();
                if (this.f5131e == Integer.MIN_VALUE) {
                    this.f5131e = Math.max(8, (int) (this.f5130d * 0.2f));
                }
                if (this.f5132f == Integer.MIN_VALUE) {
                    this.f5132f = this.f5129c;
                }
                if (this.f5133g == Integer.MIN_VALUE) {
                    this.f5133g = Math.max(1, (int) (this.f5129c * 0.02f));
                }
                m14936a(this.f5129c, this.f5132f, this.f5131e);
            }
        }
    }

    /* renamed from: a */
    private void m14934a(Drawable drawable) {
        int i;
        this.f5128b = drawable;
        this.f5140n.reset();
        this.f5138l = new Paint();
        this.f5138l.setFilterBitmap(false);
        this.f5138l.setColor(-16777216);
        this.f5138l.setXfermode(f5126s);
        this.f5129c = this.f5128b.getIntrinsicWidth();
        this.f5130d = this.f5128b.getIntrinsicHeight();
        int i2 = this.f5129c;
        if (i2 > 0 && (i = this.f5130d) > 0) {
            this.f5132f = i2;
            this.f5131e = Math.max(8, (int) (i * 0.2f));
            this.f5133g = Math.max(1, (int) (this.f5129c * 0.02f));
            m14936a(this.f5129c, this.f5132f, this.f5131e);
        }
        m14937a(0.0f);
        start();
    }
    /* renamed from: b */
    private ValueAnimator m14931b() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setInterpolator(new DecelerateInterpolator());
        ofFloat.setRepeatMode(ValueAnimator.RESTART);
        ofFloat.setRepeatCount(ValueAnimator.INFINITE);
        ofFloat.setDuration(5000L);
        return ofFloat;
    }

    /* renamed from: a */
    public void m14932a(boolean z) {
        this.f5142p = z;
        if (this.f5142p) {
            if (this.f5136j == null) {
                this.f5136j = m14931b();
            }
            this.f5136j.addUpdateListener(this);
            this.f5136j.start();
            return;
        }
        ValueAnimator valueAnimator = this.f5136j;
        if (valueAnimator != null) {
            valueAnimator.removeUpdateListener(this);
            this.f5136j.cancel();
        }
        setLevel(m14938a());
    }
    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        this.f5128b.setColorFilter(f5127t);
        this.f5128b.draw(canvas);
        this.f5128b.setColorFilter(this.f5143q);
        if (this.f5137k > 0.001f) {
            int saveLayer = canvas.saveLayer(0.0f, 0.0f, this.f5129c, this.f5130d, null, Canvas.ALL_SAVE_FLAG);
            int i = this.f5135i;
            if (i > 0) {
                canvas.clipRect(0, i, this.f5129c, this.f5130d);
            }
            this.f5128b.draw(canvas);
            if (this.f5137k < 0.999f) {
                this.f5134h += this.f5133g;
                int i2 = this.f5134h;
                int i3 = this.f5132f;
                if (i2 > i3) {
                    this.f5134h = i2 - i3;
                }
                if (this.f5139m != null) {
                    this.f5140n.setTranslate(-this.f5134h, this.f5135i);
                    canvas.drawBitmap(this.f5139m, this.f5140n, this.f5138l);
                }
                canvas.restoreToCount(saveLayer);
            }
        }
    }
    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.f5130d;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.f5129c;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        return this.f5141o;
    }
    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        if (this.f5142p) {
            m14937a(valueAnimator.getAnimatedFraction());
            if (!this.f5141o) {
                invalidateSelf();
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        m14935a(rect);
    }
    @Override // android.graphics.drawable.Drawable
    protected boolean onLevelChange(int i) {
        m14937a(i / 10000.0f);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.f5128b.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        this.f5128b.setBounds(i, i2, i3, i4);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.f5143q = colorFilter;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        this.f5141o = true;
        Choreographer.getInstance().postFrameCallback(this.f5144r);
    }
    // This App is Created by Sahil
    @Override // android.graphics.drawable.Animatable
    public void stop() {
        this.f5141o = false;
        Choreographer.getInstance().removeFrameCallback(this.f5144r);
    }
}