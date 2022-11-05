package thirdlaw.arkka.vyapar;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class buyerRequestAdapter extends RecyclerView.Adapter<buyerRequestAdapter.buyerRequestViewHolder> {

    buyerRequestModel requestModel[];
    Context context;

    public buyerRequestAdapter(buyerRequestModel[] requestModel, Context context) {
        this.requestModel = requestModel;
        this.context = context;
    }

    @NonNull
    @Override
    public buyerRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_requrment_list_buyer,parent,false);
        return new buyerRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull buyerRequestViewHolder holder, int position) {
        holder.pCatName.setText(requestModel[position].getCname());
        Glide.with(holder.pCatName.getContext()).load("http://14.139.158.99/arkvyaper/api/"+requestModel[position].getProduct_image()).into(holder.pImage);
        holder.pQantity.setText(requestModel[position].getProduct_qty());
        holder.pRange.setText(requestModel[position].getProduct_date());
        holder.sellerName.setText(requestModel[position].getUser_name());

        String lati=requestModel[position].getLati();
        String longi=requestModel[position].getLongi();

        Double a =Double.valueOf(lati);
        Double b=Double.valueOf(longi);

        SharedPreferences sp= context.getSharedPreferences("details", MODE_PRIVATE);
        String user_lati=sp.getString("user_lati","");
        String user_longi=sp.getString("user_longi","");
        Double c=Double.valueOf(user_lati);
        Double d=Double.valueOf(user_longi);

        float[] results= new float[1];
        Location.distanceBetween(a,b,c,d,results);
        float distance= results[0];
        int kilometer= (int) distance/1000;
        String cc=String.valueOf(kilometer);
        holder.distance.setText(String.format(cc+" Km"));


        String phone=requestModel[position].getUser_phone();
        String phoneurl="tel:"+"+91"+phone;

        String requestId=requestModel[position].getBpid();


        holder.delteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext())
                        .setTitle("Delete Requirment")
                        .setMessage("Are you sure want to delete ?")
                        .setIcon(R.drawable.icon)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String userDetails="http://14.139.158.99/arkvyaper/api/buyer_product_delete.php?bpid="+requestId;
                                StringRequest stringRequest=new StringRequest(Request.Method.POST, userDetails, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("USERPROFILE",response);
                                        Toast.makeText(context, "Data deleted", Toast.LENGTH_SHORT).show();
                                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                                        Intent intent=new Intent(context, MoreProductUploadBuyerActivity.class);
                                        activity.startActivity(intent);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(v.getContext(), "error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
                                requestQueue.add(stringRequest);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                alertbox.show();



            }
        });


    }

    @Override
    public int getItemCount() {
        return requestModel.length;
    }

    public class buyerRequestViewHolder extends RecyclerView.ViewHolder{
        TextView pCatName,pQantity, pRange, sellerName, sellerPh, distance;
        ImageView pImage, delteImage;

        public buyerRequestViewHolder(@NonNull View itemView) {
            super(itemView);
            pImage=itemView.findViewById(R.id.circleImageView11);
            pCatName=itemView.findViewById(R.id.category1);
            pQantity=itemView.findViewById(R.id.quantity1);
            pRange=itemView.findViewById(R.id.productDate1);
            sellerName=itemView.findViewById(R.id.sellerName1);
            distance=itemView.findViewById(R.id.distanceBuyer);
            delteImage=itemView.findViewById(R.id.deleteBuyerRequest);

            }

         }


}
