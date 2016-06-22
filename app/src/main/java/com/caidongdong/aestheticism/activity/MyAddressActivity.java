package com.caidongdong.aestheticism.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.caidongdong.aestheticism.R;
import com.caidongdong.aestheticism.adapter.AddressAdapter;
import com.caidongdong.aestheticism.config.LinkContext;
import com.caidongdong.aestheticism.entity.Address;
import com.caidongdong.aestheticism.net.okhttp.callback.ResultCallback;
import com.caidongdong.aestheticism.net.okhttp.request.OkHttpRequest;
import com.caidongdong.aestheticism.view.ToastView;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyAddressActivity extends Activity {

    @Bind(R.id.txt_add_address)
    TextView txtAddAddress;
    @Bind(R.id.address_list_view)
    ListView addressListView;
    private AddressAdapter addressAdapter;
//    private ArrayList<HashMap<String,Object>> listItems;
    private List<Address> list = new ArrayList<Address>();
//    private List<Address> resultList = new ArrayList<Address>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_address);
        ButterKnife.bind(this);
        Address address1 = new Address("夏晓培","13564673224","000000","成都市成华区","龙潭市永兴街","508号",0,"000001","A");
        Address address2 = new Address("夏晓培","13564673224","000000","成都市成华区","龙潭市永兴街","508号",1,"000001","B");
        Address address3 = new Address("夏晓培","13564673224","000000","成都市成华区","龙潭市永兴街","508号",0,"000001","C");
        list.add(address1);
        list.add(address2);
        list.add(address3);
        addressListView.setAdapter(initAdapter(list));
        txtAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MyAddressActivity.this, AddAddressActivity.class);
                startActivity(intent);
            }
        });
        addressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Address address = (Address)addressAdapter.getItem(position);
//                RadioButton rb = (RadioButton) view.findViewById(R.id.address_list_item_radiobtn);
                if (address.getActive() == 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (i != position){
                            list.get(i).setActive(0);
                        }else {
                            list.get(i).setActive(1);
//                            rb.setChecked(true);
                        }
                    }
                    //TODO 改变数据库
                    addressAdapter.updateListView(list);
                }
                Toast.makeText(getApplication(),"opint"+ position,Toast.LENGTH_SHORT).show();
                changeCommonAddress(position+"");
            }
        });


    }

//    private SimpleAdapter initListAdapter(List<Address> list){
//        listItems = new ArrayList<HashMap<String, Object>>();
//        SimpleAdapter simpleAdapter = new SimpleAdapter(this,listItems,R.layout.address_list_item,
//                new String[]{"user_name","user_phone","user_address"},
//                new int[]{R.id.address_user_name,R.id.address_user_phone,R.id.address_user_address});
//                for (int i = 0; i < list.size(); i++) {
//                    HashMap<String ,Object> item = new HashMap<String, Object>();
//                    item.put("user_name",list.get(i).getUserName());
//                    item.put("user_phone",list.get(i).getPhoneNum());
//                    item.put("user_address",list.get(i).getZoneName() + list.get(i).getStreetName() + list.get(i).getDetailAddress());
//                    listItems.add(item);
//                }
//        return simpleAdapter;
//    }
    private AddressAdapter initAdapter(List<Address> list) {
        addressAdapter = new AddressAdapter(MyAddressActivity.this,list);
        return addressAdapter;
    }

    private void changeCommonAddress(String addressIndex) {
        new OkHttpRequest.Builder().url(LinkContext.CHANGE_ADDRESS).addParams("chose_address_index", addressIndex).post(new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                showMsg("网络连接失败，请检查网络");
            }

            @Override
            public void onResponse(String response) {
                showMsg(response);
            }
        });
    }
    private void showMsg(String str) {
        ToastView toastView = new ToastView(this, str);
        toastView.setGravity(Gravity.CENTER, 0, 0);
        toastView.show();
    }
}
