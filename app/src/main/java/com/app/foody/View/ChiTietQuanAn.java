package com.app.foody.View;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.foody.Adapters.AdapterBinhLuan;
import com.app.foody.Adapters.DuocDatNhieuAdapter;
import com.app.foody.Model.DuocDatNhieuItem;
import com.app.foody.Model.QuanAnModel;
import com.app.foody.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChiTietQuanAn extends AppCompatActivity {
    ListView lv;
    DuocDatNhieuAdapter adapter;
    ArrayList<DuocDatNhieuItem> arr_bean;
    ImageView chitiet_back, imgHinhAnhQuan;
    TextView tvTenQuanAn, tvThoiGianHoatDong, tvTrangThaiHoatDong, tvDiaChiQuanAn, tvTongSoHinhAnh,
            tvTongSoCheckIn, tvTongSoBinhLuan, tvTongSoLuuLai, tvTenquananToolBar, tvTongSoBinhLuan1;
    QuanAnModel quanAnModel;
    Toolbar toolbarODau;
    RecyclerView rcvListBinhLuan;
    AdapterBinhLuan binhLuanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitietquanan);

        quanAnModel = getIntent().getParcelableExtra("quanan");

        chitiet_back=findViewById(R.id.chitiet_back);
        imgHinhAnhQuan = findViewById(R.id.img_hinh_anh_quan);
        tvTenQuanAn = findViewById(R.id.tv_ten_quanan);
        tvTenquananToolBar = findViewById(R.id.tv_ten_quanan_toolbar);
        tvThoiGianHoatDong = findViewById(R.id.tv_thoigian_hoat_dong);
        tvTrangThaiHoatDong = findViewById(R.id.tv_trang_thai_hoatdong);
        tvDiaChiQuanAn = findViewById(R.id.tv_dia_chi_quan);

        tvTongSoHinhAnh = findViewById(R.id.tv_tongso_hinhanh);
        tvTongSoBinhLuan = findViewById(R.id.tv_tongso_binhluan);
        tvTongSoCheckIn = findViewById(R.id.tv_tongso_checkin);
        tvTongSoLuuLai = findViewById(R.id.tv_tongso_luulai);
        tvTongSoBinhLuan1 = findViewById(R.id.tv_tongso_binhluan1);

        rcvListBinhLuan = findViewById(R.id.rcv_list_binhluan_chitietquanan);

        //Log.d("kiemtra", quanAnModel.getTenquanan());

        chitiet_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //Listview
        lv = (ListView) findViewById(R.id.list_dn);
        arr_bean=new ArrayList<DuocDatNhieuItem>();
        arr_bean.add(new DuocDatNhieuItem(R.drawable.loading, "Gà sốt cay Hàn Quốc","Bán chạy nhất của quán","46,000đ"));
        arr_bean.add(new DuocDatNhieuItem(R.drawable.loading, "Gà sốt cay Hàn Quốc","Bán chạy nhất của quán","46,000đ"));
        arr_bean.add(new DuocDatNhieuItem(R.drawable.loading, "Gà sốt cay Hàn Quốc","Bán chạy nhất của quán","46,000đ"));
        arr_bean.add(new DuocDatNhieuItem(R.drawable.loading, "Gà sốt cay Hàn Quốc","Bán chạy nhất của quán","46,000đ"));
        arr_bean.add(new DuocDatNhieuItem(R.drawable.loading, "Gà sốt cay Hàn Quốc","Bán chạy nhất của quán","46,000đ"));
        arr_bean.add(new DuocDatNhieuItem(R.drawable.loading, "Gà sốt cay Hàn Quốc","Bán chạy nhất của quán","46,000đ"));
        adapter=new DuocDatNhieuAdapter(arr_bean,this);
        lv.setAdapter(adapter);

        //slide banner
        /*final ImageSlider imageSlider = findViewById(R.id.slide_anhquanan); // init imageSlider
        final List<SlideModel> imageList = new ArrayList<>(); // Create image list
        FirebaseDatabase.getInstance().getReference().child("slide")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data:dataSnapshot.getChildren())
                            imageList.add(new SlideModel(data.child("url").getValue().toString(), ScaleTypes.CENTER_CROP));
                        imageSlider.setImageList(imageList,ScaleTypes.CENTER_CROP);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String giohientai = dateFormat.format(calendar.getTime());
        String giomocua = quanAnModel.getGiomocua();
        String giodongcua = quanAnModel.getGiomocua();
        //Log.d("kiemtra", giohientai + "");
        //giohientai.split("\\s");


        try {
            Date datehientai = dateFormat.parse(giohientai);
            Date dategiomocua = dateFormat.parse(giomocua);
            Date dategiodongcua = dateFormat.parse(giodongcua);

            Resources res = getResources();
            if (datehientai.after(dategiomocua) && datehientai.before(dategiodongcua)){
                tvTrangThaiHoatDong.setText("Đang mở cửa");
                tvTrangThaiHoatDong.setTextColor(res.getColor(R.color.green));
            }else{
                tvTrangThaiHoatDong.setText("Đã đóng cửa");
                tvTrangThaiHoatDong.setTextColor(res.getColor(R.color.colorRed));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvTenQuanAn.setText(quanAnModel.getTenquanan());
        tvTenquananToolBar.setText(quanAnModel.getTenquanan());
        tvDiaChiQuanAn.setText(quanAnModel.getChiNhanhQuanAnList().get(0).getDiaChi());
        tvThoiGianHoatDong.setText(quanAnModel.getGiomocua() + " - " +quanAnModel.getGiodongcua());
        tvTongSoHinhAnh.setText(quanAnModel.getHinhanhquanan().size() + "");
        tvTongSoBinhLuan.setText(quanAnModel.getBinhLuanModelList().size() + "");
        tvTongSoBinhLuan1.setText(quanAnModel.getBinhLuanModelList().size() + " Bình luận");


        StorageReference storageHinhAnhQuan = FirebaseStorage.getInstance().getReference().child("hinhquanan").child(quanAnModel.getHinhanhquanan().get(0));
        final long ONE_MEGABYTE=1024*1024;
        storageHinhAnhQuan.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                imgHinhAnhQuan.setImageBitmap(bitmap);
            }
        });

        //Load danh sách bình luận
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcvListBinhLuan.setLayoutManager(layoutManager);

        binhLuanAdapter = new AdapterBinhLuan(this, R.layout.custom_layout_binhluan, quanAnModel.getBinhLuanModelList());
        rcvListBinhLuan.setAdapter(binhLuanAdapter);
        binhLuanAdapter.notifyDataSetChanged();
    }
}
