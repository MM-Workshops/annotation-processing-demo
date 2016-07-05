package bg.mentormate.fragmentcreator;

import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tung.lam.nguyen on 05.07.2016
 */
public class FragmentCreator {
    private static final String TAG = "FragmentCreator";
    private static final Map<String, Fragment> BINDERS = new HashMap<>();

    public static Fragment create(Class<? extends FragmentScreen> builderClass) {
        final String builderName = builderClass.getSimpleName();
        if (BINDERS.containsKey(builderName)) {
            return BINDERS.get(builderName);
        } else {
            String fragmentName = builderClass.getPackage().getName() + ".Generated" + builderName + "Fragment";
            Fragment currentFragment = null;
            try {
                Class fragmentClass = Class.forName(fragmentName);
                if (fragmentClass != null) {
                    currentFragment = (Fragment) fragmentClass.newInstance();
                }
            } catch (ClassNotFoundException e) {
                Log.e(TAG, "Class " + fragmentName + " not found", e);
            } catch (InstantiationException e) {
                Log.e(TAG, "InstantiationException", e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, "IllegalAccessException ", e);
            }
            if (currentFragment != null) {
                BINDERS.put(builderName, currentFragment);
            }
            return currentFragment;
        }
    }
}
