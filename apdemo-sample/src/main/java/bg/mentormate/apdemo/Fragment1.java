package bg.mentormate.apdemo;

import android.view.View;
import android.widget.Button;

/**
 * Created by tung.lam.nguyen on 04.07.2016
 */
@Fragment(layoutId = R.layout.fragment_fragment1)
public class Fragment1 {

    @Bind
    Button btnHello;
    @Bind
    View view;

    String hello;
}
