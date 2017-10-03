package solutions.alterego.android.unisannio.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import solutions.alterego.android.unisannio.R;

public class VHHeader extends RecyclerView.ViewHolder {

    public ImageView header;

    public VHHeader(View view) {
        super(view);
        header = (ImageView) view.findViewById(R.id.header_image);
    }
}
