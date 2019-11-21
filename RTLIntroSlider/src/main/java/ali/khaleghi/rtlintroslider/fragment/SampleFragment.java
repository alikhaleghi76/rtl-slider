package ali.khaleghi.rtlintroslider.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import ali.khaleghi.rtlintroslider.R;

public class SampleFragment extends Fragment {

    String title, message;
    int backColor, icon, textColor;

    TextView titleText;
    TextView messageText;

    Typeface typeface;

    public SampleFragment(String title, String text, int textColor, int backgroundColor, int icon) {
        this.title = title;
        message = text;
        backColor = backgroundColor;
        this.textColor = textColor;
        this.icon = icon;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample, null);

        titleText= view.findViewById(R.id.title);
        messageText = view.findViewById(R.id.message);
        ImageView icon = view.findViewById(R.id.icon);
        RelativeLayout root = view.findViewById(R.id.root);

        if (typeface != null) {
            titleText.setTypeface(typeface);
            messageText.setTypeface(typeface);
        }


        titleText.setText(title);
        if (title == null || title.isEmpty())
            titleText.setVisibility(View.GONE);

        messageText.setText(message);
        messageText.setTextColor(textColor);

        titleText.setTextColor(textColor);

        root.setBackgroundColor(backColor);

        if (this.icon != -1)
            Glide.with(getContext()).load(this.icon).into(icon);
        else {
            icon.setVisibility(View.GONE);
        }

        return view;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;

        if (titleText != null) titleText.setTypeface(typeface);
        if (messageText != null) messageText.setTypeface(typeface);
    }
}
