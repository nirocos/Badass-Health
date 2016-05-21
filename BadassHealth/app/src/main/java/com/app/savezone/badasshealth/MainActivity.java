package com.app.savezone.badasshealth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.savezone.badasshealth.fragment.EditFragment;
import com.app.savezone.badasshealth.fragment.MainFragment;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements MainFragment.FragmentListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    Button abountBTN;
    TextView aa;
    MainFragment mainFragment;
    String re_username,re_height,re_weight,re_years,re_sex,re_bmi,re_bmr,re_wfw,status,grasses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInstance();
        Intent intent = getIntent();

        EditFragment editFragment = new EditFragment();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer,new MainFragment(),"ProfileFragment")
                    .commit();

        }


    }

    private void initInstance() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        aa = (TextView)findViewById(R.id.aa);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this,
                drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        abountBTN = (Button)findViewById(R.id.about);
        abountBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity
                        .this,AboutActivity.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            if(resultCode == Activity.RESULT_OK){
                if(requestCode==12345){

                        re_username = data.getStringExtra("username");
                        re_sex = data.getStringExtra("sex");
                        re_weight = data.getStringExtra("weight");
                        re_height = data.getStringExtra("height");
                        re_years = data.getStringExtra("years");
                        re_bmi = data.getStringExtra("bmi");
                        re_bmr = data.getStringExtra("bmr");
                        re_wfw = data.getStringExtra("wfw");
                     grasses= String.valueOf(Math.round(Float.parseFloat(re_wfw)) / 240);
                        if(Float.parseFloat(re_bmi)<17){
                            status = getString(R.string.statusTin);
                        }else if(Float.parseFloat(re_bmi)>=17 && Float.parseFloat(re_bmi)<=18.4){
                            status = getString(R.string.statusTin1);
                        }else if(Float.parseFloat(re_bmi)>=18.5 && Float.parseFloat(re_bmi)<=24.9){
                            status = getString(R.string.statusStartfat);
                        }else if(Float.parseFloat(re_bmi)>=25 && Float.parseFloat(re_bmi)<=29.9){
                            status = getString(R.string.statusFat);
                        }else if(Float.parseFloat(re_bmi)>=30){
                            status = getString(R.string.statusOverfat);
                        }
                        Toast.makeText(MainActivity.this,status,Toast.LENGTH_LONG).show();
                         mainFragment = (MainFragment)
                                getSupportFragmentManager().findFragmentByTag("ProfileFragment");
                    mainFragment.saveProfile(re_username,re_weight,re_height,re_years,re_sex,re_bmi,re_bmr,re_wfw,status,grasses);
                    mainFragment.setInputText(re_username,
                            getString(R.string.height)+"\n" + re_weight +" "+ getString(R.string.unitKg),
                            getString(R.string.Weight)+"\n" + re_height +" " + getString(R.string.unitCm),
                            getString(R.string.age)+"\n" + re_years + " "+getString(R.string.unitYearold),
                            getString(R.string.inputBMI) + re_bmi +" " +status,
                            getString(R.string.inputBMR) + re_bmr+" "+getString(R.string.unitCal),
                            getString(R.string.textWater)+re_wfw+getString(R.string.unitNeedwater)+" "+getString(R.string.or)+" "+grasses+" "+getString(R.string.unitGlasses));
                            if(re_sex.equals(getString(R.string.fullmale))){
                                mainFragment.setImgProfile(R.drawable.maleicon);
                            }else if(re_sex.equals(getString(R.string.fullfemale))){
                                mainFragment.setImgProfile(R.drawable.femaleicon);
                        }

                    }
                }
            }}


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
        if(savedInstanceState == null){

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClicked(ImageView a) {
        Intent intent = new Intent(MainActivity.this,SecondActivity.class);
        startActivityForResult(intent, 12345);
    }

    @Override
    public void sendSaveState(String username, String sex, String weight, String height, String yearsold) {
        Intent returnintent = new Intent();
        returnintent.putExtra("username",username);
        returnintent.putExtra("sex",sex);
        returnintent.putExtra("height",height);
        returnintent.putExtra("weight",weight);
        returnintent.putExtra("years", yearsold);

    }


}
