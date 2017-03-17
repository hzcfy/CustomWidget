package songxiaocai.smallgesturehelper.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.Button;

import songxiaocai.smallgesturehelper.R;


/**
 * 项目名称：小姿态cc
 * 类描述：自定义按钮背景色 不用每次写shape
 * 使用规则:
 * normalColor  pressColor radius 配合使用
 * normalDrawable  pressDrawable radius 配合使用
 * 创建人：cfy
 * 创建时间：17/3/8 10:27
 * 修改人：cfy
 * 修改时间：17/3/8 10:27
 * 修改备注：
 */
public class LButton extends Button {
    //默认颜色
    private int normalColor;
    //按钮按下时颜色
    private int pressColor;
    //默认资源
    private Drawable normalDrawable;
    //按钮按下时资源
    private Drawable pressDrawable;
    //按钮 默认0
    private float radius;

    public LButton(Context context) {
        super(context);
    }

    public LButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomAttributes(context, attrs);
        initBackground();
    }

    public LButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomAttributes(context, attrs);
        initBackground();
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
                R.styleable.LButton);
        normalColor = a.getColor(R.styleable.LButton_normalColor, Color.WHITE);
        pressColor = a.getColor(R.styleable.LButton_pressColor, Color.BLACK);
        normalDrawable = a.getDrawable(R.styleable.LButton_normalDrawable);
        pressDrawable = a.getDrawable(R.styleable.LButton_pressDrawable);
        radius = a.getDimension(R.styleable.LButton_buttonRadius, 0);
    }

    private void initBackground() {
        StateListDrawable sd = new StateListDrawable();
        if (normalDrawable == null || pressDrawable == null) {
            GradientDrawable normal = new GradientDrawable();
            normal.setColor(normalColor);
            normal.setCornerRadius(radius);
            GradientDrawable press = new GradientDrawable();
            press.setColor(pressColor);
            press.setCornerRadius(radius);

            sd.addState(new int[]{android.R.attr.state_pressed}, press);
            sd.addState(new int[]{android.R.attr.state_focused}, press);
            sd.addState(new int[]{}, normal);
        } else {
            sd.addState(new int[]{android.R.attr.state_pressed}, pressDrawable);
            sd.addState(new int[]{android.R.attr.state_focused}, pressDrawable);
            sd.addState(new int[]{}, normalDrawable);
        }
        setBackgroundDrawable(sd);
    }
}
