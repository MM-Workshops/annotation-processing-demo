package bg.mentormate.fragmentcreator;

import android.content.Context;

/**
 * Created by TL on 7/4/2016.
 */
public interface FragmentScreen <T> {
    void init(Context context, T listener);
}
