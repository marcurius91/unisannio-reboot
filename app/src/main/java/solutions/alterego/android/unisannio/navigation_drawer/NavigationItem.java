package solutions.alterego.android.unisannio.navigation_drawer;

import android.graphics.drawable.Drawable;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(prefix = "m")
public class NavigationItem {

    private int mType;

    private String mText;

    private Drawable mDrawable;

    public NavigationItem(String text, Drawable drawable, int type) {
        mText = text;
        mDrawable = drawable;
        mType = type;
    }
}
