package solutions.alterego.android.unisannio.interfaces;

import android.support.annotation.NonNull;

import java.util.List;

public interface IRetriever<T> {

    List<T> retrieve(@NonNull String url);
}
