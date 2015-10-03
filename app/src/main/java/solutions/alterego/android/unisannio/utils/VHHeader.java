package solutions.alterego.android.unisannio.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import solutions.alterego.android.unisannio.R;

public class VHHeader extends RecyclerView.ViewHolder {

    @Bind(R.id.header_image)
    public ImageView header;

    public VHHeader(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}