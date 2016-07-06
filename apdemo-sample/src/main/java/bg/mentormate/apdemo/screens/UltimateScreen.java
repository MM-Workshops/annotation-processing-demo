package bg.mentormate.apdemo.screens;

import android.content.Context;
import android.widget.Button;

import bg.mentormate.apdemo.Bind;
import bg.mentormate.apdemo.Click;
import bg.mentormate.apdemo.R;
import bg.mentormate.apdemo.Screen;
import bg.mentormate.apdemo.Listener;
import bg.mentormate.fragmentcreator.FragmentScreen;

/**
 * Created by tung.lam.nguyen on 04.07.2016
 */
@Screen(R.layout.fragment_ultimate)
public class UltimateScreen implements FragmentScreen <UltimateScreen.UltimateListener> {
    public static final String TAG = "UltimateScreen";

    @Bind(R.id.btn_hello)
    Button btnHello;

    private Context context;
    private UltimateListener listener;

    @Click(R.id.btn_hello)
    void onHelloButtonClicked() {

    }

    @Override
    public void init(Context context, UltimateListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Listener
    public interface UltimateListener {
        void goToHelloScreen();
    }
}
