package bg.mentormate.apdemo;

import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by tung.lam.nguyen on 04.07.2016
 */
@FragmentBuilder(R.layout.fragment_fragment1)
public class UltimateScreen implements UltimateFragment {

    private static final String TAG = "Blablabla";

    @Bind
    Button btnHello;

    @Override
    public void initListeners() {
        btnHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "on button hello clicked");
            }
        });
    }
}
