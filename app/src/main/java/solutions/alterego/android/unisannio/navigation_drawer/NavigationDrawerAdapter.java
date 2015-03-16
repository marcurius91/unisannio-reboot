package solutions.alterego.android.unisannio.navigation_drawer;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import solutions.alterego.android.unisannio.R;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {

    private List<NavigationItem> mData;

    private NavigationDrawerCallbacks mNavigationDrawerCallbacks;

    private int mSelectedPosition;

    private int mTouchedPosition = -1;

    public NavigationDrawerAdapter(List<NavigationItem> data) {
        mData = data;
    }

    public NavigationDrawerCallbacks getNavigationDrawerCallbacks() {
        return mNavigationDrawerCallbacks;
    }

    public void setNavigationDrawerCallbacks(NavigationDrawerCallbacks navigationDrawerCallbacks) {
        mNavigationDrawerCallbacks = navigationDrawerCallbacks;
    }

    @Override
    public NavigationDrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
        View v;

        switch (type) {
            case ItemType.SECTION:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_section_row, viewGroup, false);
                break;
            default:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_item_row, viewGroup, false);
                break;
        }
        return new ViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getType();
    }

    @Override
    public void onBindViewHolder(NavigationDrawerAdapter.ViewHolder viewHolder, final int i) {
        viewHolder.textView.setText(mData.get(i).getText());
        viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(mData.get(i).getDrawable(), null, null, null);

        viewHolder.itemView.setOnTouchListener((v, event) -> {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            touchPosition(i);
                            return false;
                        case MotionEvent.ACTION_CANCEL:
                            touchPosition(-1);
                            return false;
                        case MotionEvent.ACTION_MOVE:
                            return false;
                        case MotionEvent.ACTION_UP:
                            touchPosition(-1);
                            return false;
                    }
                    return true;
                }
        );
        viewHolder.itemView.setOnClickListener(v -> {
                    if (mNavigationDrawerCallbacks != null && getItem(i).getType() == ItemType.ITEM) {
                        mNavigationDrawerCallbacks.onNavigationDrawerItemSelected(i);
                    }
                }
        );

        //TODO: selected menu position, change layout accordingly
        if (mSelectedPosition == i || mTouchedPosition == i) {
            viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(R.color.selected_gray));
        } else {
            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private NavigationItem getItem(int i) {
        return mData.get(i);
    }

    private void touchPosition(int position) {
        int lastPosition = mTouchedPosition;
        mTouchedPosition = position;
        if (lastPosition >= 0) {
            notifyItemChanged(lastPosition);
        }
        if (position >= 0) {
            notifyItemChanged(position);
        }
    }

    public void selectPosition(int position) {
        int lastPosition = mSelectedPosition;
        mSelectedPosition = position;
        notifyItemChanged(lastPosition);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_name);
        }
    }
}
