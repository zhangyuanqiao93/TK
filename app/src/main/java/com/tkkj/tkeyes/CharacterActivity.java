package com.tkkj.tkeyes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.tkkj.tkeyes.utils.DialogUtil;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by dingjikerbo on 2016/9/6.
 */
public class CharacterActivity {


    @BindView(R.id.imgBtn_back)
    ImageButton imgBtnBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.read)
    Button mBtnRead;
    @BindView(R.id.write)
    Button mBtnWrite;
    @BindView(R.id.input)
    EditText mEtInput;
    @BindView(R.id.notify)
    Button mBtnNotify;
    @BindView(R.id.unnotify)
    Button mBtnUnnotify;


    private String mMac;
    private UUID mService;
    private UUID mCharacter;

    private Context context;


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.character_activity);
//        ButterKnife.bind(this);
//
//        Intent intent = getIntent();
//        mMac = intent.getStringExtra("mac");
//        mService = (UUID) intent.getSerializableExtra("service");
//        mCharacter = (UUID) intent.getSerializableExtra("character");
//
//        mTvTitle.setText(String.format("%s", mMac));
//        mBtnNotify.setEnabled(true);
//        mBtnUnnotify.setEnabled(false);
//        context = CharacterActivity.this;
//    }
}
