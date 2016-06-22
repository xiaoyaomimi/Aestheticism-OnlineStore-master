package com.caidongdong.aestheticism.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.caidongdong.aestheticism.R;
import com.caidongdong.aestheticism.fragment.ItemIntroduceFragment;

import butterknife.ButterKnife;

public class ItemIntroduceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_item_introduce);
        ButterKnife.bind(this);
        ItemIntroduceFragment fragment = new ItemIntroduceFragment();
        getFragmentManager().beginTransaction().add(R.id.fragment_layout_content,fragment).commit();

    }

}
