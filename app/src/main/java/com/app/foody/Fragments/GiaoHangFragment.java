package com.app.foody.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.app.foody.Adapters.AdapterFragment_GiaoNhanh;
import com.app.foody.Adapters.GanToiListViewBaseAdapter_GiaoNhanh;
import com.app.foody.Adapters.GiaoNhanhRecyclerViewAdapter_khuyenmai;
import com.app.foody.Adapters.GiaoNhanhRecyclerViewAdapter_monan;
import com.app.foody.Model.GanToiListViewBean_GiaoNhanh;
import com.app.foody.Model.RecyclerViewBean_GiaoNhanh;
import com.app.foody.R;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GiaoHangFragment extends Fragment implements GiaoNhanhRecyclerViewAdapter_khuyenmai.ItemClickListener {
    TextView selection;
    ListView lv1;
    GanToiListViewBaseAdapter_GiaoNhanh listViewBaseAdapterGanToi;
    ArrayList<GanToiListViewBean_GiaoNhanh> arrayListGanToi;
    private GiaoNhanhRecyclerViewAdapter_khuyenmai adapter_KHUYENMAI;
    List<RecyclerViewBean_GiaoNhanh> recyclerViewBeanList, recyclerViewBeanList_1 ;
    private GiaoNhanhRecyclerViewAdapter_monan adapter_MONAN;
    private AdapterFragment_GiaoNhanh adapterFragment;
    ViewPager viewPager;
    TabLayout tabLayout;
    ArrayList<String> arrayList, arrayList_1;
    ImageView gn_back;
    RecyclerView recyclerView, recyclerView_1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.giaonhanh_activity, container, false);
        //slide banner
        final ImageSlider imageSlider = v.findViewById(R.id.slide1); // init imageSlider
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
                });
        recyclerView = v.findViewById(R.id.rvKhuyenMai);
        recyclerView_1 = v.findViewById(R.id.rvMonAn);
        viewPager = (ViewPager) v.findViewById(R.id.viewpagerMonAn);
        tabLayout = (TabLayout) v.findViewById(R.id.tablayoutMonAn);
        selection =(TextView) v.findViewById(R.id.selection);
        gn_back=(ImageView) v.findViewById(R.id.giaonhanh_back);
        viewPagerAndTablayout();
        arrayList = new ArrayList<String>();
        arrayList.add("Đà Nẵng");
        arrayList.add("HCM");
        arrayList.add("Hà Nội");
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_single_choice,arrayList);
        Spinner spChiNhanh=(Spinner) v.findViewById(R.id.spinnerChiNhanh);
        spChiNhanh.setAdapter(arrayAdapter);
        //Bắt sự kiện cho Spinner, khi chọn phần tử nào thì hiển thị lên Toast
        spChiNhanh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //đối số postion là vị trí phần tử trong list Data
                selection.setText(arrayList.get(position));
                Toast.makeText(getActivity(),  arrayList.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        }); //
        gn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        khuyenMAi();
        monAn();
        AdapterFragment_GiaoNhanh adapterViewPagerTrangChu = new AdapterFragment_GiaoNhanh(getChildFragmentManager());
        return v;
    }
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), "You clicked " + adapter_KHUYENMAI.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), "You clicked " + adapter_MONAN.getItem(position) + " on item position " + position, Toast.LENGTH_SHORT).show();
    }


    private void viewPagerAndTablayout(){
        adapterFragment = new AdapterFragment_GiaoNhanh(getChildFragmentManager());
        viewPager.setAdapter(adapterFragment);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void GanToi(){

    }

    private void khuyenMAi(){
        recyclerViewBeanList = new ArrayList<>();
        recyclerViewBeanList.add(new RecyclerViewBean_GiaoNhanh(R.drawable.sieusale_2,""));
        recyclerViewBeanList.add(new RecyclerViewBean_GiaoNhanh(R.drawable.sieusale,""));
        recyclerViewBeanList.add(new RecyclerViewBean_GiaoNhanh(R.drawable.sieusale_2,""));
        recyclerViewBeanList.add(new RecyclerViewBean_GiaoNhanh(R.drawable.sieusale,""));
        recyclerViewBeanList.add(new RecyclerViewBean_GiaoNhanh(R.drawable.sieusale_2,""));
        recyclerViewBeanList.add(new RecyclerViewBean_GiaoNhanh(R.drawable.sieusale,""));
        // set up the RecyclerView

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter_KHUYENMAI = new GiaoNhanhRecyclerViewAdapter_khuyenmai(getActivity(), recyclerViewBeanList);
        adapter_KHUYENMAI.setClickListener(this);
        recyclerView.setAdapter(adapter_KHUYENMAI);
    }

    private void monAn(){
        recyclerViewBeanList_1 = new ArrayList<>();
        recyclerViewBeanList_1.add(new RecyclerViewBean_GiaoNhanh(R.drawable.pho,"Phở"));
        recyclerViewBeanList_1.add(new RecyclerViewBean_GiaoNhanh(R.drawable.lau,"Lẩu"));
        recyclerViewBeanList_1.add(new RecyclerViewBean_GiaoNhanh(R.drawable.comhop,"Cơm hộp"));
        recyclerViewBeanList_1.add(new RecyclerViewBean_GiaoNhanh(R.drawable.sup,"Súp"));
        recyclerViewBeanList_1.add(new RecyclerViewBean_GiaoNhanh(R.drawable.sushi,"Sushi"));
        recyclerViewBeanList_1.add(new RecyclerViewBean_GiaoNhanh(R.drawable.pho,"Phở"));
        recyclerViewBeanList_1.add(new RecyclerViewBean_GiaoNhanh(R.drawable.lau,"Lẩu"));
        recyclerViewBeanList_1.add(new RecyclerViewBean_GiaoNhanh(R.drawable.comhop,"Cơm hộp"));
        recyclerViewBeanList_1.add(new RecyclerViewBean_GiaoNhanh(R.drawable.sup,"Súp"));
        recyclerViewBeanList_1.add(new RecyclerViewBean_GiaoNhanh(R.drawable.sushi,"Sushi"));
        // set up the RecyclerView

        recyclerView_1.setHasFixedSize(true);
        LinearLayoutManager horizontalLayoutManager_1
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_1.setLayoutManager(horizontalLayoutManager_1);
        adapter_MONAN = new GiaoNhanhRecyclerViewAdapter_monan(getActivity(), recyclerViewBeanList_1);
        adapter_MONAN.setClickListener(this);
        recyclerView_1.setAdapter(adapter_MONAN);
    }
}
