package com.example.qq12cvhj.chihuo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mancj.slideup.SlideUp;
import com.mancj.slideup.SlideUpBuilder;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by qq12cvhj on 2018/4/22.
 */

public class HomeContent extends Fragment implements OnBannerListener, View.OnClickListener {
    private Banner homebanner;
    private ImageView homenewbtn,homehotbtn,homeuserbtn,homecookbookbtn,slideCloseBtn;
    private View testView;
    private SlideUp slideUp;
    private List<UserAction> actionList = new ArrayList<>();
    private Gson gson;
    private FragmentManager fm;
    private FragmentTransaction ft;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.home_content,container,false);
        return view;
    }
    private void initBannerViews(){
        gson = new Gson();
        actionList = getActionList();
        homebanner = (Banner) getActivity().findViewById(R.id.home_banner);
        homebanner.setImageLoader(new homeImgLoader());
        homebanner.setImages(actionList);
        homebanner.setBannerAnimation(Transformer.Default);
        homebanner.setOnBannerListener(this);
        homebanner.start();
    }
    @SuppressLint("CommitTransaction")
    private void initviews(){
        initBannerViews();
        homenewbtn = getActivity().findViewById(R.id.homenewbtn);
        homenewbtn.setOnClickListener(this);
        homehotbtn = getActivity().findViewById(R.id.homehotbtn);
        homehotbtn.setOnClickListener(this);
        homeuserbtn = getActivity().findViewById(R.id.homeuserbtn);
        homeuserbtn.setOnClickListener(this);
        homecookbookbtn = getActivity().findViewById(R.id.homecookbookbtn);
        homecookbookbtn.setOnClickListener(this);
        slideCloseBtn = getActivity().findViewById(R.id.slideCloseBtn);
        slideCloseBtn.setOnClickListener(this);
        testView = getActivity().findViewById(R.id.slideView);
        slideUp = new SlideUpBuilder(testView)
                .withStartState(SlideUp.State.HIDDEN)
                .withStartGravity(Gravity.BOTTOM)
                .build();
    }

    @Override
    public void onResume() {
        initviews();
        super.onResume();
    }

    private List<UserAction> getActionList(){
        List<UserAction> list ;
        try{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(commonInfo.httpUrl("getActionList"))
                    .build();
            Response response = client.newCall(request).execute();
            String resonseData = response.body().string();
            Log.d("responseData1",resonseData);
            list = gson.fromJson(resonseData,new TypeToken<List<UserAction>>(){}.getType());
        }catch (Exception e){
            e.printStackTrace();
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public void OnBannerClick(int position) {
        UserAction action = actionList.get(position);
        switch(action.actionType){
            case 1:
                Intent intent1 = new Intent(getActivity(),FoodDetailActivity.class);
                intent1.putExtra("trFoodid",action.objectId);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(getActivity(),ShareDetailActivity.class);
                intent2.putExtra("trShareId",action.objectId);
                startActivity(intent2);
                break;
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.homenewbtn:
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                HomeSlideNew hsn = new HomeSlideNew();
                ft.replace(R.id.slide_fragment,hsn);
                ft.commit();
                slideUp.show();
                break;
            case R.id.homeuserbtn:
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                HomeSlideUser hsu = new HomeSlideUser();
                ft.replace(R.id.slide_fragment,hsu);
                ft.commit();
                slideUp.show();
                break;
            case R.id.homehotbtn:
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                HomeSlideHot hsh = new HomeSlideHot();
                ft.replace(R.id.slide_fragment,hsh);
                ft.commit();
                slideUp.show();
                break;
            case R.id.homecookbookbtn:
                break;
            case R.id.slideCloseBtn:
                slideUp.hide();
                break;
        }
    }

    private void toastShow(String str){
        Toast.makeText(getContext(),str,Toast.LENGTH_SHORT).show();
    }
}
