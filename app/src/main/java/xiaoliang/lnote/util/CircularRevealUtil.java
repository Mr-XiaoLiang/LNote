package xiaoliang.lnote.util;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by LiuJ on 2016/7/18.
 * 揭示动画工具类
 */

public class CircularRevealUtil {
    /**
     * 开启按钮
     */
    private View openBtn;
    /**
     * 操纵的view
     */
    private View manipulationView;
    /**
     * 持续时间
     */
    private long duration = 500;
    /**
     * 动画类
     */
    private static Animator animator;

    public CircularRevealUtil(long duration, View manipulationView, View openBtn) {
        this.duration = duration;
        this.manipulationView = manipulationView;
        this.openBtn = openBtn;
    }

    /**
     * 执行开启动画
     */
    public void onOpen(){
        openView(manipulationView,openBtn,duration);
    }

    /**
     * 执行关闭动画
     */
    public void onClose(){
        closeView(manipulationView,openBtn,duration);
    }

    /**
     * 展开一个View
     * 理想使用情况：开启按钮位于操作展开View内部
     * @param mView 操作的view
     * @param oView 开启的按钮
     * @param durationTime 动画时间
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void openView(View mView, View oView, long durationTime){
        if(mView==null||oView==null)
            return;
        int cx,cy,startR,endR;
        cx = (oView.getLeft()+oView.getRight())/2;
        cy = (oView.getTop()+oView.getBottom())/2;
        startR = Math.min(oView.getWidth(),oView.getHeight())/2;
        int top,left,right,bottom;
        top = Math.abs(mView.getTop()-oView.getTop());
        left = Math.abs(mView.getLeft()-oView.getLeft());
        right = Math.abs(mView.getRight()-oView.getRight());
        bottom = Math.abs(mView.getBottom()-oView.getBottom());
        endR = maxInt(top,right,left,bottom);
        startAnimator(mView,cx,cy,startR,endR+startR,durationTime);
    }

    /**
     * 关闭一个View
     * 理想使用情况：开启按钮位于操作展开View内部
     * @param mView 操作的view
     * @param oView 开启的按钮
     * @param durationTime 动画时间
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void closeView(View mView, View oView, long durationTime){
        if(mView==null||oView==null)
            return;
        int cx,cy,startR,endR;
        cx = (oView.getLeft()+oView.getRight())/2;
        cy = (oView.getTop()+oView.getBottom())/2;
        startR = Math.min(oView.getWidth(),oView.getHeight())/2;
        int top,left,right,bottom;
        top = Math.abs(mView.getTop()-oView.getTop());
        left = Math.abs(mView.getLeft()-oView.getLeft());
        right = Math.abs(mView.getRight()-oView.getRight());
        bottom = Math.abs(mView.getBottom()-oView.getBottom());
        endR = maxInt(top,right,left,bottom);
        startAnimator(mView,cx,cy,endR+startR,startR,durationTime);
    }

    /**
     * 执行一个揭示动画
     * @param mView 操作的view
     * @param cx 动画开始的中心点X
     * @param cy 动画开始的中心点Y
     * @param startR 动画开始半径
     * @param endR 动画结束半径
     * @param durationTime 动画持续时间
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void startAnimator(View mView, int cx, int cy, int startR, int endR,long durationTime){
        animator = ViewAnimationUtils.createCircularReveal(
                mView,// 操作的视图
                cx,// 动画开始的中心点X
                cy,// 动画开始的中心点Y
                startR,// 动画开始半径
                endR);// 动画结束半径
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(durationTime);
        animator.start();
    }

    public static int maxInt(int... i){
        int max = 0;
        for(int m : i){
            if(m>max)
                max = m;
        }
        return max;
    }

}
