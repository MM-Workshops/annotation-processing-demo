package bg.mentormate.apdemo.screens;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import bg.mentormate.apdemo.Bind;
import bg.mentormate.apdemo.LifeCycle;
import bg.mentormate.apdemo.R;
import bg.mentormate.apdemo.Screen;
import bg.mentormate.apdemo.State;
import bg.mentormate.fragmentcreator.FragmentScreen;

/**
 * Created by tung.lam.nguyen on 05.07.2016
 */
@Screen(R.layout.hello_fragment)
public class HelloScreen implements FragmentScreen{

    public static final String TAG = "HelloScreen";

    @Bind(R.id.txt_welcome)
    TextView txtWelcome;

    @LifeCycle(State.ON_RESUME)
    public void onResume() {
        Log.d(TAG, "onResume: ");
    }

    @LifeCycle(State.ON_PAUSE)
    public void onPause() {
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void init(Context context, Object listener) {

    }
}
