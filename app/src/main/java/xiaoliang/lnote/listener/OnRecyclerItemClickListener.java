package xiaoliang.lnote.listener;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by LiuJ on 2016/6/20.
 * Recycler的Item点击事件，改自：http://blog.csdn.net/liaoinstan/article/details/51200600
 */

public class OnRecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private GestureDetectorCompat gestureDetector;
    private RecyclerView recyclerView;

    public OnRecyclerItemClickListener(Context context) {
        gestureDetector = new GestureDetectorCompat(context, new TouchGesture());
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        recyclerView = rv;
        gestureDetector.onTouchEvent(e);
    }

    private class TouchGesture extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            /**
             * 说明，之所以不用onDown方法，因为根据谷歌的说明
             * 只有在用户点击坐标和抬起坐标都在同一个view的情况下才算有效点击
             * 并且持续时间不能太长，否则算是长按
             * 期间一般不允许移动，或者移动范围在view内
             * 如果移动出去之后，算是点击无效
             * 这也就是我们经常按下按钮之后，才发现想要反悔
             * 只要把手指移动到按钮外,点击就不会生效了
             * 所以我这里用这个方法
             */
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            return child != null && onRecyclerItemClick(
                    recyclerView,
                    recyclerView.getChildViewHolder(child),
                    recyclerView.getChildAdapterPosition(child),
                    recyclerView.getChildLayoutPosition(child));
        }

        @Override
        public void onLongPress(MotionEvent e) {
            /**
             * 本来还想加一个成员变量来检测按下的持续时间
             * 但是看了些帖子，说触发长按后就不会触发上面的方法了
             * 这是个好消息，所以我直接重写这两个方法了
             * 本来还说考虑重写双击方法的，但是考虑到确实用不上，就没有重写
             * 如果需要，只要依葫芦画瓢，重写一个就好了
             */
            View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                onRecyclerItemLongClick(
                        recyclerView,
                        recyclerView.getChildViewHolder(child),
                        recyclerView.getChildAdapterPosition(child),
                        recyclerView.getChildLayoutPosition(child));
            }
        }
    }

    public boolean onRecyclerItemClick(RecyclerView rv, RecyclerView.ViewHolder vh, int adapterPosition, int layoutPosition) {
        return false;
    }

    public void onRecyclerItemLongClick(RecyclerView rv, RecyclerView.ViewHolder vh, int adapterPosition, int layoutPosition) {
    }

}
