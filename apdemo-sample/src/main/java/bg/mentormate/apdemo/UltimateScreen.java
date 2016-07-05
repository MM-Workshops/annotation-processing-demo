package bg.mentormate.apdemo;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by tung.lam.nguyen on 04.07.2016
 */
@FragmentBuilder(R.layout.fragment_fragment1)
public class UltimateScreen implements UltimateFragment {

    private static final String TAG = "Blablabla";

    @Bind
    Button btnHello;
    @Bind
    EditText editCool;

    @Click
    void btnHelloClicked(View view) {
        final int color = ContextCompat.getColor(view.getContext(), android.R.color.holo_green_light);
        view.setBackgroundColor(color);
        editCool.setText("Hello world");
    }
}
