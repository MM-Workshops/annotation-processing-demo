package bg.mentormate.apdemo.screens;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.EditText;

import bg.mentormate.apdemo.Bind;
import bg.mentormate.apdemo.Click;
import bg.mentormate.apdemo.R;
import bg.mentormate.apdemo.Screen;
import bg.mentormate.fragmentcreator.FragmentBuilder;

/**
 * Created by tung.lam.nguyen on 04.07.2016
 */
@Screen(R.layout.fragment_ultimate)
public class UltimateScreen implements FragmentBuilder {
    public static final String TAG = "UltimateScreen";

    @Bind
    Button btnHello;
    @Bind
    EditText editCool;

    private Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Click
    void btnHelloClicked() {
        btnHello.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_light));
        editCool.setText("Hello world");
    }
}
