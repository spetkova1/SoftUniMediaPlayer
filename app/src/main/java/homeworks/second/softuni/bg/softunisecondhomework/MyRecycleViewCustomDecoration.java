package homeworks.second.softuni.bg.softunisecondhomework;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by spetkova on 9/8/16.
 */

public class MyRecycleViewCustomDecoration extends RecyclerView.ItemDecoration {

    int mOffset;
    private Drawable mDivider;

    //Set Constructor
    public MyRecycleViewCustomDecoration(Drawable divider) {
        mOffset = 10;
        mDivider = divider;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) == 0) {
            return;
        }
        outRect.set(mOffset, mDivider.getIntrinsicHeight(), mOffset, mOffset);

    }

    @Override
    public void onDraw(Canvas c, final RecyclerView parent, RecyclerView.State state) {

        final RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int childCount = parent.getChildCount();
        int dividerLeft = parent.getPaddingLeft();
        int dividerRight = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount; i++) {

            final View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int dividerTop = child.getBottom() + params.bottomMargin;
            int dividerBottom = dividerTop + mDivider.getIntrinsicHeight();

            mDivider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
            mDivider.draw(c);

        }

    }
}