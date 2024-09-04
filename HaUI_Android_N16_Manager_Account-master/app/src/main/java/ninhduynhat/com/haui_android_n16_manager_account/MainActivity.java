package ninhduynhat.com.haui_android_n16_manager_account;







import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import ninhduynhat.com.haui_android_n16_manager_account.View.Cong_No_Fragment;
import ninhduynhat.com.haui_android_n16_manager_account.View.HomeFragment;
import ninhduynhat.com.haui_android_n16_manager_account.View.Plan_Fragment;
import ninhduynhat.com.haui_android_n16_manager_account.View.RegisterFragment;
import ninhduynhat.com.haui_android_n16_manager_account.View.Statistical_Fragment;

public class MainActivity extends AppCompatActivity {

    MeowBottomNavigation meowBottomNavigation;

    protected long thoatungdung;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        findId();
        buttonMeo();



    }
    private void findId(){
        meowBottomNavigation = findViewById(R.id.meoButtonNavigation);
    }
    private void buttonMeo(){


        meowBottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.icon_home));

        meowBottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.icon_thongke));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.icon_kehoach));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.icon_dangky_mon_hoc));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(5,R.drawable.icon_cong_no_2));

        // Set default selection
        meowBottomNavigation.show(1, true);


        meowBottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES
                int id= model.getId();
                int enterAnimation = R.anim.enter_from_right;
                int exitAnimation = R.anim.exit_to_left;
                if(id==1){

                    enterAnimation = R.anim.enter_from_right;
                    exitAnimation = R.anim.exit_to_left;
                    openFragment(new HomeFragment(), enterAnimation, exitAnimation);

                } else if (id==2) {
                    enterAnimation = R.anim.enter_from_right;
                    exitAnimation = R.anim.exit_to_left;
                    openFragment(new Statistical_Fragment(), enterAnimation, exitAnimation);

                } else if (id==3) {
                    enterAnimation = R.anim.enter_from_right;
                    exitAnimation = R.anim.exit_to_left;
                    openFragment(new Plan_Fragment(), enterAnimation, exitAnimation);

                }else if (id==4) {
                    enterAnimation = R.anim.enter_from_left;
                    exitAnimation = R.anim.exit_to_right;
                    openFragment(new RegisterFragment(), enterAnimation, exitAnimation);

                }else if (id==5) {
                    openFragment(new Cong_No_Fragment(), enterAnimation, exitAnimation);

                }
                return null;
            }
        });

        meowBottomNavigation.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                // YOUR CODES
                int id= model.getId();
                int enterAnimation = R.anim.enter_from_right;
                int exitAnimation = R.anim.exit_to_left;
                if(id==1){
                    enterAnimation = R.anim.enter_from_right;
                    exitAnimation = R.anim.exit_to_left;
                    openFragment(new HomeFragment(), enterAnimation, exitAnimation);
                } else if (id==2) {
                    enterAnimation = R.anim.enter_from_right;
                    exitAnimation = R.anim.exit_to_left;
                    openFragment(new Statistical_Fragment(), enterAnimation, exitAnimation);
                } else if (id==3) {
                    enterAnimation = R.anim.enter_from_right;
                    exitAnimation = R.anim.exit_to_left;
                    openFragment(new Plan_Fragment(), enterAnimation, exitAnimation);

                }else if (id==4) {
                    enterAnimation = R.anim.enter_from_left;
                    exitAnimation = R.anim.exit_to_right;
                    openFragment(new RegisterFragment(), enterAnimation, exitAnimation);
                }else if (id==5) {
                    openFragment(new Cong_No_Fragment(), enterAnimation, exitAnimation);

                }
                return null;
            }
        });
    }

    @Override
    public void onBackPressed() {

        if(thoatungdung+2000>System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else {
            Toast.makeText(this, "Nhấn lần nữa để thoát ứng dụng", Toast.LENGTH_SHORT).show();
        }
        thoatungdung=System.currentTimeMillis();
    }

    private void openFragment(Fragment fragment, int enterAnimation, int exitAnimation) {
        if (enterAnimation == -1 || exitAnimation == -1) {
            enterAnimation = R.anim.exit_to_left;
            exitAnimation = R.anim.enter_from_right;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(enterAnimation, exitAnimation)
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}