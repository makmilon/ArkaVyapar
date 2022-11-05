package thirdlaw.arkka.vyapar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import thirdlaw.arkka.vyapar.R;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton floatingActionButton;
    TextView userType;
    String uuSerType;
    String user_id;
    AlertDialog.Builder callDialogBuilder;
    AlertDialog.Builder ExitDialog;
    AppBarLayout appBarLayout;

    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bnv);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        nav=(NavigationView)findViewById(R.id.navdrawermenu);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        appBarLayout=(AppBarLayout)findViewById(R.id.appBar);


        SharedPreferences sp= getSharedPreferences("details", MODE_PRIVATE);
        String userTy=sp.getString("buyer_seller","");
        user_id=sp.getString("user_id","");

        SharedPreferences sp1= getSharedPreferences("pref", MODE_PRIVATE);
        boolean firstStar= sp1.getBoolean("firstStart",true);

        View navheader= nav.getHeaderView(0);
        userType=(TextView)navheader.findViewById(R.id.textView22);
        fetechUserNameAndAddress();

        if (firstStar) {
            tapTarget();
        }


        floatingActionButton=findViewById(R.id.fab);

        callDialogBuilder= new AlertDialog.Builder(this);



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userTy.equals("Buyer")){
                    startActivity(new Intent(MainActivity.this,ProductListForBuyer.class));
                }else if (userTy.equals("Seller")){
                    startActivity(new Intent(MainActivity.this,UploadProduct.class));
                }

            }
        });



       //toolbar
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setElevation(0);
        toolbar.setTitle("Arka Vyapar");






        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.my_nav_host_fragment,new HomeFragment()).commit();
        nav.setCheckedItem(R.id.menu_home);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {   Fragment frag1=null;
                switch (menuItem.getItemId())
                {
                    case R.id.menu_pfoile_nav:
                        frag1=new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.my_nav_host_fragment,frag1).addToBackStack(null).commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_notification:
                        frag1=new NotificationFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.my_nav_host_fragment,frag1).addToBackStack(null).commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menu_listed_product:

                        if (userTy.equals("Buyer")){
                            startActivity(new Intent(MainActivity.this,MoreProductUploadBuyerActivity.class));
                        }else if (userTy.equals("Seller")){
                            startActivity(new Intent(MainActivity.this,MoreProductUploadSeller.class));
                        }

                        break;

                    case R.id.menu_R_R_R_List:
                        if (userTy.equals("Buyer")){
                            startActivity(new Intent(MainActivity.this,RRRequestListActivity.class));
                        }else if (userTy.equals("Seller")){
                            startActivity(new Intent(MainActivity.this,ProductRequestForSeller.class));
                        }

                        break;

                    case R.id.menu_C_R_List:
                        startActivity(new Intent(MainActivity.this,CommingRequrmentList.class));

                        break;

                    case R.id.menu_Fav_list:
                        startActivity(new Intent(MainActivity.this,FaviriteListActivity.class));

                        break;

                    case R.id.menu_Trans_List:
                        startActivity(new Intent(MainActivity.this,TransactionHistory.class));

                        break;

                    case R.id.menu_Track_list:
                        startActivity(new Intent(MainActivity.this,TrackerListActivity.class));

                        break;

                    case R.id.menu_privacy:
                        startActivity(new Intent(MainActivity.this,PrivacyPolicyActivity.class));

                        break;

                    case R.id.menu_C_Care:

                        Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.dialog_layout);
                        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogue_background));
                        dialog.show();

                        TextView emailText=dialog.findViewById(R.id.textEmail);
                        TextView msgText=dialog.findViewById(R.id.textMessage);
                        String phoneurl="tel:"+"919851870036";
                        String emialaddre="arkavyapaar@gmail.com";

                        emailText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent1= new Intent(Intent.ACTION_SEND);
                                intent1.setData(Uri.parse(emialaddre));
                                intent1.setType("text/plain");
                                intent1.putExtra(Intent.EXTRA_EMAIL, new String[]{emialaddre});
                                startActivity(Intent.createChooser(intent1,"Choose Email"));
                            }
                        });

                        msgText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                               startActivity(new Intent(MainActivity.this,MessageToAdmin.class));

                                /*Intent intent=new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse(phoneurl));
                               startActivity(intent);*/
                            }
                        });

                        break;

                    case R.id.menu_logout:

                        Dialog dialog1 = new Dialog(MainActivity.this);
                        dialog1.setContentView(R.layout.dialog_logout_layout);
                        dialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialogue_background));
                        dialog1.setCancelable(false);
                        dialog1.show();

                        TextView yesText=dialog1.findViewById(R.id.textYes);
                        TextView noText=dialog1.findViewById(R.id.textNo);

                        yesText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences sp= getSharedPreferences("details",MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sp.edit();
                                myEdit.remove("user_id");
                                myEdit.remove("buyer_seller");
                                myEdit.remove("user_lati");
                                myEdit.remove("user_longi");
                                myEdit.commit();
                                Toast.makeText(MainActivity.this, "Log out is Successful", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                        });

                        noText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialog1.dismiss();


                            }
                        });



                        break;

                }
                return true;
            }
        });
    }

    private void tapTarget() {
        new TapTargetSequence(this)
                .targets(
                        TapTarget.forView(appBarLayout,"Smart Solution","User can search also by Pin code")
                                .outerCircleColor(R.color.app_color)
                                .outerCircleAlpha(0.96f)
                                .targetCircleColor(R.color.white)
                                .titleTextSize(20)
                                .titleTextColor(R.color.white)
                                .descriptionTextSize(20)
                                .descriptionTextColor(R.color.white)
                                .textColor(R.color.white)
                                .textTypeface(Typeface.SANS_SERIF)
                                .dimColor(R.color.black)
                                .drawShadow(true)
                                .cancelable(false)
                                .tintTarget(true)
                                .transparentTarget(true)
                                .targetRadius(60),
                        TapTarget.forView(bottomNavigationView,"Now You Can Start","Arka Vyapar is all fulfill your needs")
                                .outerCircleColor(R.color.app_color)
                                .outerCircleAlpha(0.96f)
                                .targetCircleColor(R.color.white)
                                .titleTextSize(20)
                                .titleTextColor(R.color.white)
                                .descriptionTextSize(20)
                                .descriptionTextColor(R.color.white)
                                .textColor(R.color.white)
                                .textTypeface(Typeface.SANS_SERIF)
                                .dimColor(R.color.black)
                                .drawShadow(true)
                                .cancelable(false)
                                .tintTarget(true)
                                .transparentTarget(true)
                                .targetRadius(60)).listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        SharedPreferences sp1= getSharedPreferences("pref",MODE_PRIVATE);
                        SharedPreferences.Editor editor =sp1.edit();
                        editor.putBoolean("firstStart", false);
                        editor.apply();
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {


                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {

                    }
                }).start();

    }

    private void fetechUserNameAndAddress() {
        String userDetails="http://14.139.158.99/arkvyaper/api/user_list.php?uid="+user_id;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, userDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("USERPROFILE",response);

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject profileDataObject=jsonArray.getJSONObject(0);
                    uuSerType=profileDataObject.getString("buyer_seller");

                    userType.setText(uuSerType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment=null;
            switch (item.getItemId())
            {
                case R.id.menu_home:
                    fragment=new HomeFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.my_nav_host_fragment,fragment).addToBackStack("tag").commit();

                    break;
                case R.id.menu_notification:
                    fragment=new NotificationFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.my_nav_host_fragment,fragment).addToBackStack(null).commit();

                    break;
                case R.id.menu_profile:
                    fragment=new ProfileFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.my_nav_host_fragment,fragment).addToBackStack(null).commit();

                    break;
                case R.id.menu_setting:
                    drawerLayout.openDrawer(Gravity.START);

                    break;

            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.appbar_menu, menu);

        MenuItem menuItem= menu.findItem(R.id.menu_appbar_noti);
        View actionView= menuItem.getActionView();
        NotificationBadge cartBadgetext= actionView.findViewById(R.id.noti_badge_text);
        cartBadgetext.setText("5");
        cartBadgetext.setVisibility(View.GONE);

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Fragment appBarfragment=null;
        switch (item.getItemId())
        {
            case R.id.menu_appbar_noti:
                appBarfragment=new NotificationFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.my_nav_host_fragment,appBarfragment).addToBackStack("tag").commit();

                break;
            case R.id.menu_appbar_location:
                appBarfragment=new MapFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.my_nav_host_fragment,appBarfragment).addToBackStack(null).commit();


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

            ExitDialog =new AlertDialog.Builder(this);
            ExitDialog.setMessage("Do you really want to Exit ?");
            ExitDialog.setTitle("Arka Vyapar");
            ExitDialog.setIcon(R.drawable.icon);
            ExitDialog.setCancelable(false);
            ExitDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            ExitDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   dialog.cancel();
                }
            });

            AlertDialog alertDialog=ExitDialog.create();
            alertDialog.show();
        }

}