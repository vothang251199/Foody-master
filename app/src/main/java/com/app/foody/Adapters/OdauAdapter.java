package com.app.foody.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.foody.Model.ChiNhanhQuanAn;
import com.app.foody.View.ChiTietQuanAn;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.app.foody.Model.BinhLuanModel;
import com.app.foody.Model.QuanAnModel;
import com.app.foody.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OdauAdapter extends RecyclerView.Adapter<OdauAdapter.ViewHolder> {
    List<QuanAnModel>quanAnModelList;
    int resource;
    Context context;

    public OdauAdapter(Context context, List<QuanAnModel>quanAnModelList, int resource){
              this.quanAnModelList=quanAnModelList;
            this.resource=resource;
            this.context = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenQuanAnOdau,txtTieuDeBinhLuan,txtTieuDeBinhLuan2,txtNoiDungBinhLuan,
                txtNoiDungBinhLuan2,txtchamdiembinhluan,txtchamdiembinhluan2,txttongbinhluan,txtTonghinhbinhluan,txtDiemTrungBinhQuanAn,
                txtDiaChi,txtKhoangCach;
        Button btnDatMonOdau;
        ImageView imageViewHinhQuanAnOdau;
        CircleImageView cicleImageUser,cicleImageUser2;
        LinearLayout containerBinhLuan2,containerBinhLuan;
        View viewItemQuanAnODau;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenQuanAnOdau=(TextView)itemView.findViewById(R.id.txtTenQuanAnOdau);
            btnDatMonOdau=itemView.findViewById(R.id.btnDatMonOdau);
            imageViewHinhQuanAnOdau=itemView.findViewById(R.id.imageHinhQuanAnOdau);
            txtNoiDungBinhLuan=itemView.findViewById(R.id.txtNoiDungBinhLuan);
            txtNoiDungBinhLuan2=itemView.findViewById(R.id.txtNoiDungBinhLuan2);
            txtTieuDeBinhLuan=itemView.findViewById(R.id.txtTieuDeBinhLuan);
            txtTieuDeBinhLuan2=itemView.findViewById(R.id.txtTieuDeBinhLuan2);
            cicleImageUser=itemView.findViewById(R.id.cicleImageUser);
            cicleImageUser2=itemView.findViewById(R.id.cicleImageUser2);
            containerBinhLuan=itemView.findViewById(R.id.containerBinhLuan);
            containerBinhLuan2=itemView.findViewById(R.id.containerBinhLuan2);
            txtchamdiembinhluan=itemView.findViewById(R.id.txtchamdiembinhluan);
            txtchamdiembinhluan2=itemView.findViewById(R.id.txtchamdiembinhluan2);
            txttongbinhluan=itemView.findViewById(R.id.txttongbinhluan);
            txtTonghinhbinhluan=itemView.findViewById(R.id.txtTonghinhbinhluan);
            txtDiemTrungBinhQuanAn=itemView.findViewById(R.id.txtDiemTrungBinhQuanAn);
            txtDiaChi=itemView.findViewById(R.id.txtDiaChi);
            txtKhoangCach=itemView.findViewById(R.id.txtKhoangCach);
            viewItemQuanAnODau = itemView.findViewById(R.id.view_item_odau);
        }
    }
    @NonNull
    @Override
    public OdauAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder  ;
    }

    @Override
    public void onBindViewHolder(@NonNull final OdauAdapter.ViewHolder holder, int position) {
        final QuanAnModel quanAnModel=quanAnModelList.get(position);
        holder.txtTenQuanAnOdau.setText(quanAnModel.getTenquanan());
            if (quanAnModel.isGiaohang()){
                holder.btnDatMonOdau.setVisibility(View.VISIBLE);
            }
            if (quanAnModel.getHinhanhquanan().size()>0){
                holder.imageViewHinhQuanAnOdau.setImageBitmap(quanAnModel.getBitmaps().get(0));
            }

        holder.viewItemQuanAnODau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ChiTietQuanAn.class);
                i.putExtra("quanan", quanAnModel);
                context.startActivity(i);
            }
        });
        //lấy danh sách bình luận của quán ăn
        if (quanAnModel.getBinhLuanModelList().size()>0){
            BinhLuanModel binhLuanModel=quanAnModel.getBinhLuanModelList().get(0);
            holder.txtTieuDeBinhLuan.setText(binhLuanModel.getTieude());
            holder.txtNoiDungBinhLuan.setText(binhLuanModel.getNoidung());
            holder.txtchamdiembinhluan.setText(binhLuanModel.getChamdiem()+"");
            setHinhAnhBinhLuan(holder.cicleImageUser,binhLuanModel.getThanhVienModel().getHinhanh());
            if (quanAnModel.getBinhLuanModelList().size()>2){
                BinhLuanModel binhLuanModel2=quanAnModel.getBinhLuanModelList().get(1);
                holder.txtTieuDeBinhLuan2.setText(binhLuanModel2.getTieude());
                holder.txtNoiDungBinhLuan2.setText(binhLuanModel2.getNoidung());
                holder.txtchamdiembinhluan2.setText(binhLuanModel2.getChamdiem()+"");
                setHinhAnhBinhLuan(holder.cicleImageUser2,binhLuanModel2.getThanhVienModel().getHinhanh());
            }
            holder.txttongbinhluan.setText(quanAnModel.getBinhLuanModelList().size()+"");

            int Tongsohinhbinhluan=0;
            double tongdiem=0;
            //tính tổng điểm trung bình của quán và đếm tổng số hình
            for (BinhLuanModel binhLuanModel1:quanAnModel.getBinhLuanModelList()){
                Tongsohinhbinhluan +=binhLuanModel1.getHinhanhBinhLuanlist().size();
                tongdiem +=binhLuanModel1.getChamdiem();
            }
            double diemtrungbinh=tongdiem/quanAnModel.getBinhLuanModelList().size();
            holder.txtDiemTrungBinhQuanAn.setText(String.format("%.1f",diemtrungbinh));
            if (Tongsohinhbinhluan>0){
                holder.txtTonghinhbinhluan.setText(Tongsohinhbinhluan +"");
            }
        }
        else {
            holder.containerBinhLuan.setVisibility(View.GONE);
            holder.containerBinhLuan2.setVisibility(View.GONE);
        }

        if(quanAnModel.getChiNhanhQuanAnList().size()>0){
            ChiNhanhQuanAn chiNhanhQuanAn=quanAnModel.getChiNhanhQuanAnList().get(0);
            for(ChiNhanhQuanAn i: quanAnModel.getChiNhanhQuanAnList()){
                if(chiNhanhQuanAn.getKhoangCach() > i.getKhoangCach()){
                    chiNhanhQuanAn=i;
                }
            }
            holder.txtDiaChi.setText(chiNhanhQuanAn.getDiaChi());
            holder.txtKhoangCach.setText(String.format("%.2f",chiNhanhQuanAn.getKhoangCach())+"km");
        }
    }
    private  void setHinhAnhBinhLuan(final CircleImageView circleImageView , String linkhinh){
        StorageReference storageHinhuser=FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkhinh);
        final long ONE_MEGABYTE=1024*1024;
        storageHinhuser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });

        //
    }

    @Override
    public int getItemCount() {
        return quanAnModelList.size();
    }



}
