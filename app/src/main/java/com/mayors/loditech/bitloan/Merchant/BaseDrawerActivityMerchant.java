package com.mayors.loditech.bitloan.Merchant;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mayors.loditech.bitloan.BillsDue;
import com.mayors.loditech.bitloan.Home_Menu;
import com.mayors.loditech.bitloan.Loan_MGT;
import com.mayors.loditech.bitloan.Login;
import com.mayors.loditech.bitloan.Models.Transaction;
import com.mayors.loditech.bitloan.NFC;
import com.mayors.loditech.bitloan.R;
import com.mayors.loditech.bitloan.Rewards;
import com.mayors.loditech.bitloan.Settings_Menu;
import com.mayors.loditech.bitloan.Transactions;

public class BaseDrawerActivityMerchant extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FrameLayout frameLayout;
    int defHeight = 566, defWidth = 383, ScreenHeight, ScreenWidth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basedraweractivity_merchant);

        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        navigationView = (NavigationView) findViewById(R.id.navItemsMerchant);
        setDrawerParams();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(item.isChecked()){
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return false;
                }
                switch (id){
                    case R.id.navigationa: startActivity(new
                            Intent(getApplicationContext(), Home_Menu.class)); break;
                    case R.id.navigationb: startActivity(new
                            Intent(getApplicationContext(), NFC.class)); break;
                    case R.id.navigationd: startActivity(new
                            Intent(getApplicationContext(), Transactions.class)); break;
                    case R.id.navigationg: startActivity(new
                            Intent(getApplicationContext(), Settings_Menu.class)); break;
                    case R.id.navigationh: startActivity(new
                            Intent(getApplicationContext(), Home_Menu.class)); break;
                    case R.id.navigationi: startActivity(new
                            Intent(getApplicationContext(), Login.class)); break;
                    default: break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(item.isChecked()){
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        }
        switch (id){
            case R.id.navigationa: startActivity(new
                    Intent(getApplicationContext(), Home_Menu.class)); break;
            case R.id.navigationg: startActivity(new
                    Intent(getApplicationContext(), Settings_Menu.class)); break;
            default: break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public int PxToDp(int a){
        a = (int)(a/ Resources.getSystem().getDisplayMetrics().density);
        return a;
    }

    public int Scale(float margin, float size, float scale){
        margin = size*(margin/scale);
        margin = (int)(margin * Resources.getSystem().getDisplayMetrics().density);
        return (int)margin;
    }

    public void setDrawerParams()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        ViewGroup.LayoutParams layoutParams = navigationView.getLayoutParams();
        layoutParams.width = size.x/2;
        navigationView.setLayoutParams(layoutParams);
    }

    public void Navigate(View v){
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }


}

