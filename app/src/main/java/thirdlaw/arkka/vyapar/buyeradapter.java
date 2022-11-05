package thirdlaw.arkka.vyapar;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

public class buyeradapter extends RecyclerView.Adapter<buyeradapter.buyerholder>{

    buyermodel data[];
    Context context;

    public buyeradapter(buyermodel[] data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public buyerholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.buyer_list_item,parent,false);
        return new buyerholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull buyerholder holder, int position) {
        holder.nameText.setText(data[position].getUname());
        holder.addressText.setText(data[position].getUaddress1());
        Glide.with(holder.nameText.getContext()).load("http://14.139.158.99/arkvyaper/api/"+data[position].getU_img()).into(holder.photoImg);

        String phone=data[position].getUphone();
        String phoneurl="tel:"+"+91"+phone;


        SharedPreferences sp= context.getSharedPreferences("details", MODE_PRIVATE);
        String user_id=sp.getString("user_id","");

        String listedid=data[position].getUid();

        holder.callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userDetails="http://14.139.158.99/arkvyaper/api/add_transation2.php?buid="+user_id+"&suid="+listedid;
                StringRequest stringRequest=new StringRequest(Request.Method.POST, userDetails, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("USERPROFILE",response);
                        Toast.makeText(context, "Data Inserted", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
                requestQueue.add(stringRequest);
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phoneurl));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    class buyerholder extends RecyclerView.ViewHolder {

        ImageView photoImg;
        TextView nameText, addressText;
        Button callbtn;

        public buyerholder(@NonNull View itemView) {
            super(itemView);
            photoImg=itemView.findViewById(R.id.circleImageView1);
            callbtn=itemView.findViewById(R.id.call1);
            nameText=itemView.findViewById(R.id.nameText1);
            addressText=itemView.findViewById(R.id.addres1);

        }
    }
}
