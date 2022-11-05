package thirdlaw.arkka.vyapar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class notificationadapter extends RecyclerView.Adapter<notificationadapter.notiViewHolder> {

    notificationmodel notimodel[];

    public notificationadapter(notificationmodel[] notimodel) {
        this.notimodel = notimodel;
    }

    @NonNull
    @Override
    public notiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list,parent,false);
        return new notiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull notiViewHolder holder, int position) {
        holder.messagetxt.setText(notimodel[position].getAdmin_message());
        holder.dateNoti.setText(notimodel[position].getMadate());
    }

    @Override
    public int getItemCount() {
        return notimodel.length;
    }

    public class notiViewHolder extends RecyclerView.ViewHolder{

      TextView messagetxt, dateNoti;

      public notiViewHolder(@NonNull View itemView) {
          super(itemView);
          messagetxt=itemView.findViewById(R.id.notification);
          dateNoti=itemView.findViewById(R.id.date_Noti);
      }
  }
}
