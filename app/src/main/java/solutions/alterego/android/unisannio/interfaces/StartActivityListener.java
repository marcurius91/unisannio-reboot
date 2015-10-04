package solutions.alterego.android.unisannio.interfaces;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

public interface StartActivityListener {

    void startActivity(@NonNull Intent intent, @NonNull RecyclerView.ViewHolder holder);
}
