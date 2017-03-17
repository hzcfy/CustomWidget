package songxiaocai.smallgesturehelper.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import songxiaocai.smallgesturehelper.R;


/**
 * 项目名称：小姿态cc
 * 类描述：多梯度进度条
 * 创建人：cfy
 * 创建时间：17/3/6 16:23
 * 修改人：cfy
 * 修改时间：17/3/6 16:23
 * 修改备注：
 */
public class LMultiProgressBar extends View {
    //梯度 >=0 由布局传入
    private int gradientNum;
    //梯度间隔
    private int divideLength;
    //梯度颜色 默认白色
    private int divideColor;

    //进度条背景 progressDrawable与startColor  endColor 设置期中之一就行
    private GradientDrawable progressDrawable;
    //进度条前景色
    private int startColor;
    //进度条前景色
    private int endColor;
    //进度条的圆角 默认5
    private float radius = 5;
    //默认进度
    private int progress = 0;
    //进度条宽度
    private int width;
    //进度条高度
    private int height;
    //画笔
    private Paint mPaint;
    //圆角
    private float[] leftRadius;
    //渐变方向
    private int orientation;
    //从左往右
    private final int LEFT_RIGHT = 1;
    //从上往下
    private final int TOP_BOTTOM = 2;


    public LMultiProgressBar(Context context) {
        super(context);
    }

    public LMultiProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomAttributes(context, attrs);
        init();
        initView();
    }

    public LMultiProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomAttributes(context, attrs);
        init();
        initView();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {
        Drawable background = getBackground();
        if (background instanceof ColorDrawable) {
            int color = ((ColorDrawable) background).getColor();
            GradientDrawable bg = new GradientDrawable();
            bg.setCornerRadius(radius);
            bg.setColor(color);
            setBackground(bg);
        }

    }

    /**
     * @param
     * @return
     * @throws
     * @Title: 从布局文件中获取 各个参数
     * @Description: ${todo}()
     * @author chengfy
     * @date 17/3/6 17:59
     */
    private void setCustomAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.LMultiProgressBar);
        progress = a.getInteger(
                R.styleable.LMultiProgressBar_progress, 0);
        gradientNum = a.getInteger(
                R.styleable.LMultiProgressBar_gradientNumber, 0);
        startColor = a.getColor(R.styleable.LMultiProgressBar_startColor, Color.WHITE);
        endColor = a.getColor(R.styleable.LMultiProgressBar_endColor, Color.BLACK);
        progressDrawable = (GradientDrawable) a.getDrawable(R.styleable.LMultiProgressBar_progressDrawable);

        divideColor = a.getColor(R.styleable.LMultiProgressBar_divideColor, Color.WHITE);
        divideLength = a.getInteger(R.styleable.LMultiProgressBar_divideLength, 4);

        orientation = a.getInteger(R.styleable.LMultiProgressBar_orientation, LEFT_RIGHT);

        radius = a.getDimension(R.styleable.LMultiProgressBar_radius, 5);
    }

    /**
     * @param
     * @return
     * @throws
     * @Title: 初始化变量
     * @Description: ${todo}()
     * @author chengfy
     * @date 17/3/6 18:00
     */
    private void init() {
        mPaint = new Paint();
        //分割线宽度
        mPaint.setStrokeWidth(divideLength);
        mPaint.setColor(divideColor);
        initProgressDrawable();

        leftRadius = new float[]{
                radius, radius,
                0, 0,
                0, 0,
                radius, radius};
    }

    /**
     * @param
     * @return
     * @throws
     * @Title: 初始化进度条颜色 如果资源文件里没有给progressDrawable
     * @Description: ${todo}()
     * @author chengfy
     * @date 17/3/6 17:54
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initProgressDrawable() {
        if (progressDrawable == null) {
            progressDrawable = new GradientDrawable();
            //渐变颜色
            progressDrawable.setColors(new int[]{startColor, endColor});
            //渐变方向
            switch (orientation) {
                case LEFT_RIGHT://从左往右
                    progressDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                    break;
                case TOP_BOTTOM://从上往下
                    progressDrawable.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                    break;
            }


        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取控件的宽 高
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //进度条
        drawProgress(canvas);
        //画分割线
        drawDivideLine(canvas);
    }

    private void drawProgress(Canvas canvas) {
        int progressWidth = (int) (progress / 100.0 * width);
        if (progress == 100) {
            progressDrawable.setCornerRadius(radius);
        } else {
            progressDrawable.setCornerRadii(leftRadius);
        }
        progressDrawable.setBounds(0, 0, progressWidth, height);
        progressDrawable.draw(canvas);
    }

    private void drawDivideLine(Canvas canvas) {
        int startX = 0, startY = 0;
        int endX = 0, endY = height;
        int divideSpace = width / gradientNum;
        int divideNumber = gradientNum - 1;
        while (divideNumber > 0) {
            startX += divideSpace;
            endX += divideSpace;
            canvas.drawLine(startX, startY, endX, endY, mPaint);
            divideNumber--;
        }
    }


    /**
     * @param
     * @return
     * @throws
     * @Title: 设置进度
     * @Description: ${todo}()
     * @author chengfy
     * @date 17/3/7 10:53
     */
    public void setProgress(int progress) {
        if (this.progress != progress) {
            this.progress = progress;
            invalidate();
        }

    }

    /**
     * @param
     * @return
     * @throws
     * @Title: 设置梯度
     * @Description: ${todo}()
     * @author chengfy
     * @date 17/3/7 10:54
     */
    public void setGradientNum(int gradientNum) {
        if (this.gradientNum != gradientNum) {
            this.gradientNum = gradientNum;
            invalidate();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void runProgress(int maxProgress) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, maxProgress);
        valueAnimator.setDuration(700);
        valueAnimator
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        //设置瞬时的数据值到界面上
                        setProgress((Integer) valueAnimator.getAnimatedValue());
                    }
                });
        valueAnimator.start();
    }

}
