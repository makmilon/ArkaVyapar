package thirdlaw.arkka.vyapar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class tpriceadapter extends RecyclerView.Adapter<tpriceadapter.tpriceviewholder> {

    tpricemodel tprice[];
    Context context;

    public tpriceadapter(tpricemodel[] tprice, Context context) {
        this.tprice = tprice;
        this.context = context;
    }

    @NonNull
    @Override
    public tpriceviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tproduct_layout,parent,false);
        return new tpriceviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull tpriceviewholder holder, int position) {

        holder.tname.setText(tprice[position].getTname());
        holder.tname1.setText(tprice[position].getTname());
        holder.tpricee.setText(tprice[position].getTprice());

    }

    @Override
    public int getItemCount() {
        return tprice.length;
    }


    public class tpriceviewholder extends RecyclerView.ViewHolder{

        TextView tname, tname1,tpricee;

        public tpriceviewholder(@NonNull View itemView) {
            super(itemView);

            tname=itemView.findViewById(R.id.price_name_home);
            tname1=itemView.findViewById(R.id.price_name_home1);
            tpricee=itemView.findViewById(R.id.tprice);

        }
    }

}
