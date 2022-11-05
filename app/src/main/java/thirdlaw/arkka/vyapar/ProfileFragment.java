package thirdlaw.arkka.vyapar;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import thirdlaw.arkka.vyapar.R;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {

    TextView userText, userAdr;
    ImageView userImage;
    EditText pNameText, pPhText,pEmailText, pUserTyText,pAddText,pCityText,pPinText,pCountryText;
    String pImage,pName, pPh, pEmail, pUserType, pAdd, pCity, pPin, pCountry, pLat, pLong;
    String userId, userBuyerId;
    String sName, sPh, sEmail, sUser, sAdd, sCity, sPin, sCoun;
    Button btnUpdate, btnDelete, btnChange;
    TextView userTTType;

    String userType;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        userText=view.findViewById(R.id.textView25);
        userAdr=view.findViewById(R.id.textView26);


        SharedPreferences sp= getContext().getSharedPreferences("details", MODE_PRIVATE);
        userId=sp.getString("user_id","");
        userBuyerId=sp.getString("userBuyer_id","");

        userType=sp.getString("buyer_seller","");

        userTTType=view.findViewById(R.id.textView38);
        if (userType.equals("Buyer")) {
            userTTType.setText(userType);


        }else if(userType.equals("Seller")){
            userTTType.setText(userType);
        }


        userImage=view.findViewById(R.id.circleImageView11);
        pNameText=view.findViewById(R.id.profi_name);
        pPhText=view.findViewById(R.id.profi_phone);
        pPhText.setEnabled(false);
        pEmailText=view.findViewById(R.id.profi_email);
        pUserTyText=view.findViewById(R.id.profi_user_type);
        pUserTyText.setEnabled(false);
        pAddText=view.findViewById(R.id.profi_addre);
        pCityText=view.findViewById(R.id.profi_city);
        pPinText=view.findViewById(R.id.profi_pin);
        pCountryText=view.findViewById(R.id.profi_country);
        btnUpdate=view.findViewById(R.id.buttonUpdate);
        btnDelete=view.findViewById(R.id.buttonDelete);
        btnChange=view.findViewById(R.id.buttonChange);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteProfile1();
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteProfile();
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sName=pNameText.getText().toString().trim();
                sPh=pPhText.getText().toString().trim();
                sEmail=pEmailText.getText().toString().trim();
                sUser=pUserTyText.getText().toString().trim();
                sAdd=pAddText.getText().toString().trim();
                sCity=pCityText.getText().toString().trim();
                sPin=pPinText.getText().toString().trim();
                sCoun=pCountryText.getText().toString().trim();
                updateProfile();
            }
        });




        fetechUserNameAndAddress();
        fetchUserDataSeller();

        return view;
    }

    private void deleteProfile() {


        Dialog dialog1 = new Dialog(getContext());
        dialog1.setContentView(R.layout.dialog_delete_layout);
        dialog1.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.dialogue_background));
        dialog1.setCancelable(false);
        dialog1.show();

        TextView yesText=dialog1.findViewById(R.id.textYes);
        TextView noText=dialog1.findViewById(R.id.textNo);

        yesText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updateUserdetail="http://14.139.158.99/arkvyaper/api/user_delete.php?uid="+userId;

                StringRequest stringRequest = new StringRequest(Request.Method.POST, updateUserdetail,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response)
                            {
                                try {
                                    JSONObject resObject=new JSONObject(response);
                                    String responseStr= resObject.getString("ResponseMsg");
                                    Toast.makeText(getContext(), responseStr, Toast.LENGTH_SHORT).show();


                                    SharedPreferences sp= getContext().getSharedPreferences("details",MODE_PRIVATE);
                                    SharedPreferences.Editor myEdit = sp.edit();
                                    myEdit.remove("user_id");
                                    myEdit.remove("buyer_seller");
                                    myEdit.remove("user_lati");
                                    myEdit.remove("user_longi");
                                    myEdit.commit();

                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(intent);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //displaying the error in toast if occur
                                Toast.makeText(getContext(), "error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("uid",userId);
                        return params;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(stringRequest);

            }
        });

        noText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog1.dismiss();


            }
        });






    }

    private void deleteProfile1() {

        String updateUserdetail="http://14.139.158.99/arkvyaper/api/user_delete.php?uid="+userId;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, updateUserdetail,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject resObject=new JSONObject(response);
                            String responseStr= resObject.getString("ResponseMsg");
                            Toast.makeText(getContext(), responseStr, Toast.LENGTH_SHORT).show();


                            SharedPreferences sp= getContext().getSharedPreferences("details",MODE_PRIVATE);
                            SharedPreferences.Editor myEdit = sp.edit();
                            myEdit.remove("user_id");
                            myEdit.remove("buyer_seller");
                            myEdit.remove("user_lati");
                            myEdit.remove("user_longi");
                            myEdit.commit();

                            Intent intent = new Intent(getActivity(), SignUpDetails.class);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occur
                        Toast.makeText(getContext(), "error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid",userId);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);


    }

    private void fetechUserNameAndAddress() {
        String userDetails="http://14.139.158.99/arkvyaper/api/user_list.php?uid="+userId;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, userDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("USERPROFILE",response);

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject profileDataObject=jsonArray.getJSONObject(0);
                    pName=profileDataObject.getString("uname");
                    pAdd=profileDataObject.getString("uaddress1");

                    userText.setText(pName);
                    userAdr.setText(pAdd);

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

    private void fetchUserDataSeller() {

        String fetchUserdetail="http://14.139.158.99/arkvyaper/api/user_list.php?uid="+userId;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, fetchUserdetail, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("USERPROFILE",response);

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject profileDataObject=jsonArray.getJSONObject(0);
                    pImage=profileDataObject.getString("u_img");
                    pName=profileDataObject.getString("uname");
                    pPh=profileDataObject.getString("uphone");
                    pEmail=profileDataObject.getString("uemail");
                    pUserType=profileDataObject.getString("buyer_seller");
                    pAdd=profileDataObject.getString("uaddress1");
                    pCity= profileDataObject.getString("uaddress2");
                    pPin= profileDataObject.getString("upin");
                    pCountry= profileDataObject.getString("ustate");

                    pLat = profileDataObject.getString("lati");
                    pLong =profileDataObject.getString("longi");

                    SharedPreferences sp= getContext().getSharedPreferences("details",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sp.edit();
                    myEdit.putString("user_lati",pLat);
                    myEdit.putString("user_longi",pLong);
                    myEdit.commit();

                    pNameText.setText(pName);
                    pPhText.setText(pPh);
                    pEmailText.setText(pEmail);
                    pUserTyText.setText(pUserType);
                    pAddText.setText(pAdd);
                    pCityText.setText(pCity);
                    pPinText.setText(pPin);
                    pCountryText.setText(pCountry);
                    Glide.with(getContext()).load(pImage).into(userImage);


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

    private void updateProfile() {

        String updateUserdetail="http://14.139.158.99/arkvyaper/api/user_update.php?uid="+userId;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, updateUserdetail,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject resObject=new JSONObject(response);
                            String responseStr= resObject.getString("ResponseMsg");
                            Toast.makeText(getContext(), responseStr, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occur
                        Toast.makeText(getContext(), "error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uname",sName);
                params.put("uphone", sPh);
                params.put("uemail", sEmail);
                params.put("uaddress1", sAdd);
                params.put("uaddress2", sCity);
                params.put("upin", sPin);
                params.put("ustate", sCoun);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);

    }
}