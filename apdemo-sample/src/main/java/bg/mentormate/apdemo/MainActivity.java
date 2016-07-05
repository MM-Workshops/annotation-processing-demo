package bg.mentormate.apdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import bg.mentormate.apdemo.screens.HelloScreen;
import bg.mentormate.apdemo.screens.UltimateScreen;
import bg.mentormate.fragmentcreator.FragmentCreator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.grp_container,
                        FragmentCreator.create(HelloScreen.class),
                        HelloScreen.TAG)
                .commit();
    }
}
