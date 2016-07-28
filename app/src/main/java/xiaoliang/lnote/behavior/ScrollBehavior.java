package xiaoliang.lnote.behavior;

import android.content.Context;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;

/**
 * Created by LiuJ on 2016/6/13.
 * 页面滚动就隐藏浮动按钮（暂弃）
 */

public class ScrollBehavior extends FloatingActionButton.Behavior {
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private boolean mIsAnimatingOut = false;

    public ScrollBehavior(Context context, AttributeSet attrs) {
        super();
    }

//    @Override
//    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
//        final CoordinatorLayout.LayoutParams lp =
//                (CoordinatorLayout.LayoutParams) child.getLayoutParams();//获取到layout参数
//        if (lp.getAnchorId() != dependency.getId()) {//对比当前view是不是指定的View
//            return false;
//        }
//        return true;
//    }

//    @Override
//    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
//        //执行移动操作
//        //用来关联位移
//        return super.onDependentViewChanged(parent, child, dependency);
//    }


    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;//返回值是告诉layout，我们是否关注当前的滑动
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        final CoordinatorLayout.LayoutParams lp =
                (CoordinatorLayout.LayoutParams) child.getLayoutParams();//获取到layout参数
        int translationY = Math.abs(dy);
        if (translationY < child.getHeight()+lp.getMarginEnd()+lp.getMarginStart())
            child.setTranslationY(translationY);
        else
            child.setTranslationY(child.getHeight());
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target) {
        super.onStopNestedScroll(coordinatorLayout, child, target);
        ViewCompat.animate(child).scaleX(1.0F).scaleY(1.0F).alpha(1.0F)
                             .setInterpolator(INTERPOLATOR).withLayer().setListener(null)
                             .start();
//        TranslateAnimation anim = new TranslateAnimation(1.0f,1.0f,1.0f,-1.0f);
//        child.startAnimation(anim);
    }
}