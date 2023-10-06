package dathtph20511.poly.PNLib.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dathtph20511.poly.PNLib.Model.Sach;
import dathtph20511.poly.PNLib.R;

public class Top10RecycleAdapter extends RecyclerView.Adapter<Top10RecycleAdapter.userViewHolder> {
    private Context context;
    private ArrayList<Sach> arrayList;

    public Top10RecycleAdapter(Context context) {
        this.context = context;
    }
    public void setData(ArrayList<Sach> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top10,parent,false);
        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
        Sach sach = arrayList.get(position);
        if(sach == null){
            return;
        }
        holder.tv_maSach.setText("Mã sách: "+sach.getMaSach());
        holder.tv_tenSach.setText("Tên sách: "+sach.getTenSach());
        holder.tv_giaSach.setText("Giá tiền: "+ String.valueOf(sach.getGiaThue()));
        holder.tv_sl.setText("Số lượng: "+sach.getLoaiSach());
    }

    @Override
    public int getItemCount() {
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public class userViewHolder extends RecyclerView.ViewHolder{
        TextView tv_maSach,tv_tenSach,tv_giaSach,tv_sl;
        public userViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_maSach = itemView.findViewById(R.id.tv_top10_maSach);
            tv_tenSach = itemView.findViewById(R.id.tv_top10_tenSach);
            tv_giaSach = itemView.findViewById(R.id.tv_top10_gia);
            tv_sl = itemView.findViewById(R.id.tv_top10_soluong);

        }
    }
}
