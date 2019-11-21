package ali.khaleghi.rtlintroslider.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class DimensionConverter {

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }

}
