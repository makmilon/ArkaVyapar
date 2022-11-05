package thirdlaw.arkka.vyapar;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;



public class slideradapter extends RecyclerView.Adapter<slideradapter.sliderviewholder>{

    slidermodel slide[];


    public slideradapter(slidermodel[] slide) {
        this.slide = slide;
    }

    @NonNull
    @Override
    public sliderviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new sliderviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull sliderviewholder holder, int position) {
        Glide.with(holder.img.getContext()).load("http://14.139.158.99/arkvyaper/"+slide[position].getSlimg()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return slide.length;
    }

    public  class sliderviewholder extends  RecyclerView.ViewHolder{

        ImageView img;

        public sliderviewholder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.img);
        }
    }

}
