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

    public static Fragment getFragment(Class<? extends UltimateFragment> ultimateFragment) {
        final String simpleName = ultimateFragment.getSimpleName();
        if (BINDERS.containsKey(simpleName)) {
            return BINDERS.get(simpleName);
        } else {
            String fragmentName = ultimateFragment.getPackage().getName() + ".Generated" + simpleName + "Fragment";
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
                BINDERS.put(simpleName, currentFragment);
            }
            return currentFragment;
        }
    }
}
