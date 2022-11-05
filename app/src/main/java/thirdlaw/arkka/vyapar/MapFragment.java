package thirdlaw.arkka.vyapar;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import thirdlaw.arkka.vyapar.R;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;


public class MapFragment extends Fragment {

    FusedLocationProviderClient client;
    SupportMapFragment smf;
    TextView name, address;
    String user_id, pName, pAdd, ulat, ulong;
    String user_type;
    TextView nearYou;

    TextView se1, se2, se3, se4, se5, se6, se7;
    String seller1, seller2, seller3,seller4,seller5,seller6,seller7;


    TextView userPattern;
//    String sellerLatSt, sellerLongSt;
    Double sellerLatD, sellerLongD;


    private GoogleMap mMap;

    MarkerOptions marker;
    Vector<MarkerOptions> markerOptions;

    LatLng alorsetar;

    RequestQueue requestQueue;
    Gson gson;
    Mapdatum [] mapdata;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SharedPreferences sp= getContext().getSharedPreferences("details", MODE_PRIVATE);
        user_id=sp.getString("user_id","");
        ulat=sp.getString("user_lati","");
        ulong=sp.getString("user_longi","");
        user_type=sp.getString("buyer_seller","");

        gson= new GsonBuilder().create();



        sellerLatD=Double.parseDouble(ulat);
        sellerLongD= Double.parseDouble(ulong);



        fetchNearTwentyKmSeller();


        nearYou=view.findViewById(R.id.textNear);

        userPattern=view.findViewById(R.id.textView37);

        name=view.findViewById(R.id.textView26);
        address=view.findViewById(R.id.textView25);
        se1=view.findViewById(R.id.se1);
        se2=view.findViewById(R.id.se2);
        se3=view.findViewById(R.id.se3);
        se4=view.findViewById(R.id.se4);
        se5=view.findViewById(R.id.se5);
        se6=view.findViewById(R.id.se6);
        se7=view.findViewById(R.id.se7);

        se1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_type.equals("Buyer")){
                    startActivity(new Intent(getContext(),MapViewB1.class));

                }else if (user_type.equals("Seller")){

                   startActivity(new Intent(getContext(),MapViewSe1.class));

                }

            }
        });
        se2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_type.equals("Buyer")){
                    startActivity(new Intent(getContext(),MapViewB2.class));
                }else if (user_type.equals("Seller")){

                    startActivity(new Intent(getContext(),MapViewSe2.class));

                }
            }
        });
        se3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_type.equals("Buyer")){
                    startActivity(new Intent(getContext(),MapViewB3.class));
                }else if (user_type.equals("Seller")){

                    startActivity(new Intent(getContext(),MapViewSe3.class));

                }
            }
        });
        se4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_type.equals("Buyer")){
                    startActivity(new Intent(getContext(),MapViewB4.class));
                }else if (user_type.equals("Seller")){

                    startActivity(new Intent(getContext(),MapViewSe4.class));

                }
            }
        });
        se5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_type.equals("Buyer")){
                    startActivity(new Intent(getContext(),MapViewB5.class));
                }else if (user_type.equals("Seller")){

                    startActivity(new Intent(getContext(),MapViewSe5.class));

                }
            }
        });
        se6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_type.equals("Buyer")){
                    startActivity(new Intent(getContext(),MapViewB6.class));
                }else if (user_type.equals("Seller")){

                    startActivity(new Intent(getContext(),MapViewSe6.class));

                }
            }
        });
        se7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_type.equals("Buyer")){
                    startActivity(new Intent(getContext(),MapViewB7.class));
                }else if (user_type.equals("Seller")){

                    startActivity(new Intent(getContext(),MapViewSe7.class));

                }
            }
        });




        smf = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.google_map);
        client = LocationServices.getFusedLocationProviderClient(getContext());

        if (user_type.equals("Buyer")){

            userPattern.setText(user_type);
            fetchSellerDetailLocation5km();
            fetchSellerDetailLocation15km();
            fetchSellerDetailLocation15km();
            fetchSellerDetailLocation30km();
            fetchSellerDetailLocation45km();
            fetchSellerDetailLocation60km();
            fetchSellerDetailLocation75km();
            fetchSellerDetailLocation100km();




        }else if (user_type.equals("Seller")){
            userPattern.setText(user_type);
            fetchBuyerDetailLocation5km();
            fetchBuyerDetailLocation15km();
            fetchBuyerDetailLocation30km();
            fetchBuyerDetailLocation45km();
            fetchBuyerDetailLocation60km();
            fetchBuyerDetailLocation75km();
            fetchBuyerDetailLocation100km();


        }
//        fetechUserNameAndAddress();


        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getUserLocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

        return view;
    }
    //buyer

    private void fetchBuyerDetailLocation5km() {
        String userDetails="http://14.139.158.99/arkvyaper/api/longlatb.php?uid="+user_id+"&longi="+ulong+"&lati="+ulat;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, userDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("USERPROFILE",response);

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject profileDataObject=jsonArray.getJSONObject(0);
                    seller1=profileDataObject.getString("count");

                    se1.setText("5Km: "+seller1+ " "+"Buyer");

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
    private void fetchBuyerDetailLocation15km() {
        String userDetails="http://14.139.158.99/arkvyaper/api/longlatb2.php?uid="+user_id+"&longi="+ulong+"&lati="+ulat;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, userDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("USERPROFILE",response);

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject profileDataObject=jsonArray.getJSONObject(0);
                    seller2=profileDataObject.getString("count");

                    se2.setText("15Km: "+seller2+ " "+"Buyer");

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
    private void fetchBuyerDetailLocation30km() {
        String userDetails="http://14.139.158.99/arkvyaper/api/longlatb3.php?uid="+user_id+"&longi="+ulong+"&lati="+ulat;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, userDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("USERPROFILE",response);

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject profileDataObject=jsonArray.getJSONObject(0);
                    seller3=profileDataObject.getString("count");

                    se3.setText("30Km: "+seller3+ " "+"Buyer");

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
    private void fetchBuyerDetailLocation45km() {
        String userDetails="http://14.139.158.99/arkvyaper/api/longlatb4.php?uid="+user_id+"&longi="+ulong+"&lati="+ulat;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, userDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("USERPROFILE",response);

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject profileDataObject=jsonArray.getJSONObject(0);
                    seller4=profileDataObject.getString("count");

                    se4.setText("45Km: "+seller4+ " "+"Buyer");

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
    private void fetchBuyerDetailLocation60km() {
        String userDetails="http://14.139.158.99/arkvyaper/api/longlatb5.php?uid="+user_id+"&longi="+ulong+"&lati="+ulat;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, userDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("USERPROFILE",response);

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject profileDataObject=jsonArray.getJSONObject(0);
                    seller5=profileDataObject.getString("count");

                    se5.setText("60Km: "+seller5+ " "+"Buyer");

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
    private void fetchBuyerDetailLocation75km() {
        String userDetails="http://14.139.158.99/arkvyaper/api/longlatb6.php?uid="+user_id+"&longi="+ulong+"&lati="+ulat;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, userDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("USERPROFILE",response);

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject profileDataObject=jsonArray.getJSONObject(0);
                    seller6=profileDataObject.getString("count");

                    se6.setText("75Km: "+seller6+ " "+"Buyer");

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
    private void fetchBuyerDetailLocation100km() {
        String userDetails="http://14.139.158.99/arkvyaper/api/longlatb7.php?uid="+user_id+"&longi="+ulong+"&lati="+ulat;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, userDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("USERPROFILE",response);

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject profileDataObject=jsonArray.getJSONObject(0);
                    seller7=profileDataObject.getString("count");

                    se7.setText("100Km: "+seller7+ " "+"Buyer");

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

    //buyer

    //seller
    private void fetchSellerDetailLocation5km() {
        String userDetails="http://14.139.158.99/arkvyaper/api/longlat.php?uid="+user_id+"&longi="+ulong+"&lati="+ulat;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, userDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("USERPROFILE",response);

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject profileDataObject=jsonArray.getJSONObject(0);
                    seller1=profileDataObject.getString("count");

                    se1.setText("5Km: "+seller1+ " "+"Seller");

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

    private void fetchSellerDetailLocation15km() {
        String userDetails="http://14.139.158.99/arkvyaper/api/longlat2.php?uid="+user_id+"&longi="+ulong+"&lati="+ulat;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, userDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("USERPROFILE",response);

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject profileDataObject=jsonArray.getJSONObject(0);
                    seller2=profileDataObject.getString("count");

                    se2.setText("15Km: "+seller2+ " "+"Seller");

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

    private void fetchSellerDetailLocation30km() {
        String userDetails="http://14.139.158.99/arkvyaper/api/longlat3.php?uid="+user_id+"&longi="+ulong+"&lati="+ulat;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, userDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("USERPROFILE",response);

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject profileDataObject=jsonArray.getJSONObject(0);
                    seller3=profileDataObject.getString("count");

                    se3.setText("30Km: "+seller3+ " "+"Seller");

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

    private void fetchSellerDetailLocation45km() {
        String userDetails="http://14.139.158.99/arkvyaper/api/longlat4.php?uid="+user_id+"&longi="+ulong+"&lati="+ulat;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, userDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("USERPROFILE",response);

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject profileDataObject=jsonArray.getJSONObject(0);
                    seller4=profileDataObject.getString("count");

                    se4.setText("45Km: "+seller4+ " "+"Seller");

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

    private void fetchSellerDetailLocation60km() {
        String userDetails="http://14.139.158.99/arkvyaper/api/longlat5.php?uid="+user_id+"&longi="+ulong+"&lati="+ulat;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, userDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("USERPROFILE",response);

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject profileDataObject=jsonArray.getJSONObject(0);
                    seller5=profileDataObject.getString("count");

                    se5.setText("60Km: "+seller5+ " "+"Seller");

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

    private void fetchSellerDetailLocation75km() {
        String userDetails="http://14.139.158.99/arkvyaper/api/longlat6.php?uid="+user_id+"&longi="+ulong+"&lati="+ulat;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, userDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("USERPROFILE",response);

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject profileDataObject=jsonArray.getJSONObject(0);
                    seller6=profileDataObject.getString("count");

                    se6.setText("70Km: "+seller6+ " "+"Seller");

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

    private void fetchSellerDetailLocation100km() {
        String userDetails="http://14.139.158.99/arkvyaper/api/longlat7.php?uid="+user_id+"&longi="+ulong+"&lati="+ulat;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, userDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("USERPROFILE",response);

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject profileDataObject=jsonArray.getJSONObject(0);
                    seller7=profileDataObject.getString("count");

                    se7.setText("100Km: "+seller7+ " "+"Seller");

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

    //seller


    public void getUserLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                smf.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {

                        mMap = googleMap;
                        // Add a marker in Sydney and move the camera

                        //mMap.addMarker(marker);

                       if (user_type.equals("Seller")){
                           sendRequest();
                        }else if (user_type.equals("Buyer")){
                           sendRequestforSeller();
                       }

                        LatLng latLng = new LatLng(sellerLatD, sellerLongD);
                        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_marker));
                        markerOptions.rotation(location.getBearing());

                        CircleOptions circleOptions = new CircleOptions();
                        circleOptions.center(latLng);

                        circleOptions.radius(1500);
                        // Border color of the circle
                        circleOptions.strokeColor(0x3050C878);
                        // Fill color of the circle
                        circleOptions.fillColor(0x3093FFE8);
                       // Border width of the circle
                        circleOptions.strokeWidth(80);

                        googleMap.addCircle(circleOptions);
                        googleMap.addMarker(markerOptions);
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12));


                    }
                });
            }
        });
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

                    name.setText(pAdd);
                    address.setText(pName);


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

    private void fetchNearTwentyKmSeller(){
        String userDetails="http://14.139.158.99/arkvyaper/api/longlat20k.php?uid="+user_id+"&longi="+ulong+"&lati="+ulat;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, userDetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("USERPROFILE",response);

                try {
                    JSONArray jsonArray=new JSONArray(response);


                    for (int i=0; i<jsonArray.length();i++){
                       JSONObject sellerLatJ=jsonArray.getJSONObject(i);

//                        sellerLatSt=sellerLatJ.getString("llong");
//                        sellerLongSt=sellerLatJ.getString("lat");
//
//                        sellerLatD=Double.valueOf(sellerLatSt);
//                        sellerLongD=Double.valueOf(sellerLongSt);


                    }



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


    public void sendRequest(){

        String url="http://14.139.158.99/arkvyaper/api/longlat20k.php?uid="+user_id+"&longi="+ulong+"&lati="+ulat;

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                mapdata=gson.fromJson(response,Mapdatum[].class);

                for (Mapdatum info: mapdata){
                    Double lat= Double.parseDouble(info.lat);
                    Double lng= Double.parseDouble(info.llong);
                    String title=info.uname;
                    String snipet=info.uphone;


                    MarkerOptions marker= new MarkerOptions().position(new LatLng(lat,lng))
                            .title("Buyer= "+title)
                            .snippet("Phone= "+snipet);

                    mMap.addMarker(marker);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);




    }

    public void sendRequestforSeller(){

        String url="http://14.139.158.99/arkvyaper/api/longlat20k_seller.php?uid="+user_id+"&longi="+ulong+"&lati="+ulat;

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                mapdata=gson.fromJson(response,Mapdatum[].class);

                for (Mapdatum info: mapdata){
                    Double lat= Double.parseDouble(info.lat);
                    Double lng= Double.parseDouble(info.llong);
                    String title=info.uname;
                    String snipet=info.uphone;

                    MarkerOptions marker= new MarkerOptions().position(new LatLng(lat,lng))
                            .title("Seller= "+title)
                            .snippet("Phone= "+snipet);

                    mMap.addMarker(marker);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);




    }

  }