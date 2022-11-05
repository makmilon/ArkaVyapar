package thirdlaw.arkka.vyapar;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import thirdlaw.arkka.vyapar.R;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner  spinner1;
    TextView price_name_home,price_name_home1,price_name_home2,price_name_home3,price_name_home4;
    RecyclerView sellerecyclerView;
    TextView userName_home, user_addd;
    RecyclerView homeimageSlider, homeTpriceSlider;
    EditText seartchText;
    Button searchBtn;
    TextView todayMarketRate;

    AlertDialog.Builder ExitDialog;

    TextView selerText, buyerText, userTTyype;

    RecyclerView buyerRecview;

    String userNName, userAdress, userType, user_id;
    String pName, pAdd, pLat, pLong;

    Spinner spinner;
    ArrayList<String> countryList = new ArrayList<>();
    RequestQueue requestQueue;
    TextView userPattern, noSellerF, noBuyerF;
    View sellerV, buyerV;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

       /* spinner=view.findViewById(R.id.home_spinner);*/

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_layout1);
        dialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.dialogue_background1));
        dialog.setCancelable(false);
        dialog.show();

        sellerV=view.findViewById(R.id.viewSeller);
        buyerV=view.findViewById(R.id.viewBuyer);
        todayMarketRate=view.findViewById(R.id.textView28);


        userPattern=view.findViewById(R.id.textView27);
        seartchText=view.findViewById(R.id.editTextTextPersonName7);
        searchBtn=view.findViewById(R.id.searchBtn);


        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (seartchText.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getContext(), "Please enter Pin Code", Toast.LENGTH_SHORT).show();
                    return;
                }
                String uSearchPin= seartchText.getText().toString().trim();
                SharedPreferences sp= getContext().getSharedPreferences("details",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sp.edit();
                myEdit.putString("user_search_Pin",uSearchPin);
                myEdit.commit();
                startActivity(new Intent(getContext(),SearchResult.class));
                seartchText.setText("");
            }
        });


        selerText=view.findViewById(R.id.sellerText);
        sellerecyclerView=view.findViewById(R.id.sellerRecview);



        user_addd=view.findViewById(R.id.user_address);
        homeimageSlider=view.findViewById(R.id.imageSlider);
        homeTpriceSlider=view.findViewById(R.id.fPriceSlider);



        userName_home=view.findViewById(R.id.home_userName_show);


        SharedPreferences sp= getContext().getSharedPreferences("details", MODE_PRIVATE);
        user_id=sp.getString("user_id","");
        userType=sp.getString("buyer_seller","");
        pLat=sp.getString("user_lati","");
        pLong=sp.getString("user_longi","");




/*

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });


            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
*/






        fetechUserNameAndAddress();

        //text marque effect

        //text marque effect


        //buyer list
        buyerText=view.findViewById(R.id.buyer_recylerText);
        buyerRecview=view.findViewById(R.id.buyer_recview);
        buyerRecview.setLayoutManager(new LinearLayoutManager(getContext()));

        //buyer list

        //recycler_view

        sellerecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //horizantal image slider

        //tap target




        //tap target

        //recycler_view

        //user category
;

        if (userType.equals("Buyer")) {
             userPattern.setText(userType);
            sellerdata();


        }else if(userType.equals("Seller")){
            userPattern.setText(userType);
            buyerdata();
        }




             //user category

        //search list
       /* processpinner(view);*/
        //search list

        //slider image
         sliderImage();
        //slider image

        //tprice slider
        tpriceSlider();
        //tprice slider

        return view;
    }

    private void tpriceSlider() {

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        homeTpriceSlider.setLayoutManager(linearLayoutManager);
        String tPriceurl="http://14.139.158.99/arkvyaper/api/tproductlist.php";
        StringRequest request= new StringRequest(tPriceurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                GsonBuilder gsonBuilder2 = new GsonBuilder();
                Gson gson2=gsonBuilder2.create();

                tpricemodel tprice[]=gson2.fromJson(response,tpricemodel[].class);
                tpriceadapter tdapter = new tpriceadapter(tprice, getContext());
                homeTpriceSlider.setAdapter(tdapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }

    private void tapTarget() {
        new TapTargetSequence((Activity) getContext())
                .targets(
                        TapTarget.forView(searchBtn,"Search Button","User can search by Pin code")
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

                        TapTarget.forView(todayMarketRate,"Latest Market Rate","You Can see market Rate")
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
                        TapTarget.forView(selerText,"Product Images","Product Images")
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
                        SharedPreferences sp1= getContext().getSharedPreferences("pref",MODE_PRIVATE);
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
                    pName=profileDataObject.getString("uname");
                    pAdd=profileDataObject.getString("uaddress1");

                    user_addd.setText(pAdd);
                    userName_home.setText(pName);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void sellerdata() {
        String url="http://14.139.158.99/arkvyaper/api/user_seller_show.php";
        StringRequest request= new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson=gsonBuilder.create();

                model data[]=gson.fromJson(response,model[].class);
                myadapter adapter= new myadapter(data, getContext());


                sellerecyclerView.setAdapter(adapter);
                selerText.setVisibility(View.VISIBLE);
                sellerecyclerView.setVisibility(View.VISIBLE);
                sellerV.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }
    private void buyerdata() {
        String url="http://14.139.158.99/arkvyaper/api/user_buyer_show.php";
        StringRequest request= new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson=gsonBuilder.create();

                buyermodel data[]=gson.fromJson(response,buyermodel[].class);
                buyeradapter buyadapter= new buyeradapter(data, getContext());
                buyerRecview.setAdapter(buyadapter);
                buyerText.setVisibility(View.VISIBLE);
                buyerRecview.setVisibility(View.VISIBLE);
                buyerV.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);
    }


    private void sliderImage() {

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        homeimageSlider.setLayoutManager(linearLayoutManager);
        String sliderimageurl="http://14.139.158.99/arkvyaper/api/sliderlist.php";
        StringRequest request= new StringRequest(sliderimageurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog.dismiss();

                GsonBuilder gsonBuilder1 = new GsonBuilder();
                Gson gson1=gsonBuilder1.create();

                slidermodel slide[]=gson1.fromJson(response,slidermodel[].class);
                slideradapter sadapter = new slideradapter(slide);
                homeimageSlider.setAdapter(sadapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        );

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(request);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

/*
    private void processpinner(View view) {
        requestQueue = Volley.newRequestQueue(getContext());
        String url = "https://3rdlaw.net/api/cat.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String countryName = jsonObject.optString("cname");
                        countryList.add(countryName);
                        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(view.getContext(),
                                android.R.layout.simple_spinner_item, countryList);
                        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(countryAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
        spinner.setOnItemSelectedListener(this);
    }*/


}