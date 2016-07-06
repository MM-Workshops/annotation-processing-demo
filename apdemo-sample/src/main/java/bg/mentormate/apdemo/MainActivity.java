package bg.mentormate.apdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import bg.mentormate.apdemo.screens.UltimateScreen;
import bg.mentormate.fragmentcreator.FragmentCreator;

public class MainActivity extends AppCompatActivity implements UltimateScreen.UltimateListener{

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.grp_container,
                        FragmentCreator.create(UltimateScreen.class),
                        UltimateScreen.TAG)
                .addToBackStack(UltimateScreen.TAG)
                .commit();
    }

    @Override
    public void goToHelloScreen() {
        Log.d(TAG, "goToHelloScreen: ");
    }
}
