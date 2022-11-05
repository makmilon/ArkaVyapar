package thirdlaw.arkka.vyapar;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class productListAdapter extends RecyclerView.Adapter<productListAdapter.productViewHolder> {

    productListModel listModel[];
    Context context;

    public productListAdapter(productListModel[] listModel, Context context) {
        this.listModel = listModel;
        this.context = context;
    }

    @NonNull
    @Override
    public productViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item,parent,false);
        return new productViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull productViewHolder holder, int position) {
        holder.pCatName.setText(listModel[position].getCname());
        Glide.with(holder.pCatName.getContext()).load("http://14.139.158.99/arkvyaper/api/"+listModel[position].getProduct_image()).into(holder.pImage);
        holder.pQantity.setText(listModel[position].getProduct_qty());
        holder.pRange.setText(listModel[position].getProduct_date());
        holder.sellerName.setText(listModel[position].getUser_name());


        String lati=listModel[position].getLati();
        String longi=listModel[position].getLongi();

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


        String phone=listModel[position].getUser_phone();
        String phoneurl="tel:"+"+91"+phone;

       holder.sellerPh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phoneurl));
                activity.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return listModel.length;
    }

    public class productViewHolder extends RecyclerView.ViewHolder
    {

        TextView pCatName,pQantity, pRange, sellerName, sellerPh, distance;
        ImageView pImage;

        public productViewHolder(@NonNull View itemView) {
            super(itemView);
            pImage=itemView.findViewById(R.id.circleImageView1);
            pCatName=itemView.findViewById(R.id.category);
            pQantity=itemView.findViewById(R.id.quantity);
            pRange=itemView.findViewById(R.id.productDate);
            sellerName=itemView.findViewById(R.id.sellerName);
           sellerPh=itemView.findViewById(R.id.calltoseller);
            distance=itemView.findViewById(R.id.distance);


        }
    }

/*
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }


    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
*/

  }
