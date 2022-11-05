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

public class forBuyerProductAdapter extends RecyclerView.Adapter<forBuyerProductAdapter.productForBuyerHolder> {

    forBuyerProductModel forBuyerProductModel[];
    Context context;

    public forBuyerProductAdapter(thirdlaw.arkka.vyapar.forBuyerProductModel[] forBuyerProductModel, Context context) {
        this.forBuyerProductModel = forBuyerProductModel;
        this.context = context;
    }

    @NonNull
    @Override
    public productForBuyerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_buyer_item,parent,false);
        return new productForBuyerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull productForBuyerHolder holder, int position) {
        holder.bCatName.setText(forBuyerProductModel[position].getCname());
        Glide.with(holder.bCatName.getContext()).load("http://14.139.158.99/arkvyaper/api/"+forBuyerProductModel[position].getProduct_image()).into(holder.bImage);
        holder.bQantity.setText(forBuyerProductModel[position].getProduct_qty());
        holder.bRange.setText(forBuyerProductModel[position].getProduct_date());
        holder.bsellerName.setText(forBuyerProductModel[position].getUser_name());


        String lati=forBuyerProductModel[position].getLati();
        String longi=forBuyerProductModel[position].getLongi();

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

        String phone=forBuyerProductModel[position].getUser_phone();
        String phoneurl="tel:"+"+91"+phone;

        holder.bsellerPh.setOnClickListener(new View.OnClickListener() {
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
        return forBuyerProductModel.length;
    }

    public class productForBuyerHolder extends RecyclerView.ViewHolder{

        TextView bCatName,bQantity, bRange, bsellerName, bsellerPh, distance;
        ImageView bImage;

        public productForBuyerHolder(@NonNull View itemView) {
            super(itemView);
            bImage=itemView.findViewById(R.id.circleImageView11);
            bCatName=itemView.findViewById(R.id.category1);
            bQantity=itemView.findViewById(R.id.quantity1);
            bRange=itemView.findViewById(R.id.productDate1);
            bsellerName=itemView.findViewById(R.id.sellerName1);
           bsellerPh=itemView.findViewById(R.id.calltoseller1);
            distance=itemView.findViewById(R.id.distanceBuyer);
        }
    }
}
