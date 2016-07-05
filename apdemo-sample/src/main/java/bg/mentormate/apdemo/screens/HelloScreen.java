package bg.mentormate.apdemo.screens;

import android.content.Context;
import android.widget.TextView;

import bg.mentormate.apdemo.Bind;
import bg.mentormate.apdemo.Screen;
import bg.mentormate.apdemo.R;

/**
 * Created by tung.lam.nguyen on 05.07.2016
 */
@Screen(R.layout.hello_fragment)
public class HelloScreen implements bg.mentormate.fragmentcreator.FragmentBuilder {

    public static final String TAG = "HelloScreen";

    @Bind
    TextView txtWelcome;

    @Override
    public void init(Context context) {
        txtWelcome.setText("Hello there");
    }
}
