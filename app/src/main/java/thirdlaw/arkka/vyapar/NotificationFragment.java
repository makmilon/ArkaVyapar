package thirdlaw.arkka.vyapar;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import thirdlaw.arkka.vyapar.R;


public class NotificationFragment extends Fragment {


    RecyclerView notiRecView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notification, container, false);

        notiRecView=view.findViewById(R.id.recviewNotifation);
        notiRecView.setLayoutManager(new LinearLayoutManager(getContext()));


        SharedPreferences sp= getContext().getSharedPreferences("details", MODE_PRIVATE);
        String userId=sp.getString("user_id","");

        String sliderimageurl="http://14.139.158.99/arkvyaper/api/message_show.php?uid="+userId;
        StringRequest request= new StringRequest(sliderimageurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder gsonBuilder1 = new GsonBuilder();
                Gson gson1=gsonBuilder1.create();

                notificationmodel slide[]=gson1.fromJson(response,notificationmodel[].class);
                notificationadapter nadapter = new notificationadapter(slide);
                notiRecView.setAdapter(nadapter);

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


        return view;
    }


}