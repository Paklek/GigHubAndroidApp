package com.gighub.app.ui.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.text.method.CharacterPickerDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.Position;
import com.gighub.app.model.PositionResponse;
import com.gighub.app.model.SearchResultModel;
import com.gighub.app.ui.adapter.ListPositionAdapter;
import com.gighub.app.util.BuildUrl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DialogPositionCreatBandFragment extends DialogFragment implements AdapterView.OnItemClickListener {

//    private RecyclerView mRecyclerViewPositionDialog;
    private List<Position> mListPosition;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListPositionAdapter mListPositionAdapter;
//    private Button mButtonSubmitPosition;
    private RadioButton mRadioButtonPosition;
    private RadioGroup mRadioGroup;
    private Context mContext;

    public final static int REQ_POSITION_CODE = 2000;

    public DialogPositionCreatBandFragment() {
        // Required empty public constructor
    }

//    static DialogPositionCreatBandFragment newInstance(String isi){
//
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_dialog_position_creat_band,container, false);

        mContext = getActivity().getApplicationContext();

        mListPosition = new ArrayList<Position>();

        mLayoutManager = new LinearLayoutManager(getActivity());
//        mRecyclerViewPositionDialog = (RecyclerView) view.findViewById(R.id.rv_position);

//        mButtonSubmitPosition = (Button)view.findViewById(R.id.btn_submit_position_select);
//        mRadioButtonPosition = (RadioButton)view.findViewById(R.id.rb_position);
        mListPositionAdapter = new ListPositionAdapter(mListPosition);

//        mRecyclerViewPositionDialog.setHasFixedSize(true);
//        mRecyclerViewPositionDialog.setLayoutManager(mLayoutManager);
//        mRecyclerViewPositionDialog.setAdapter(mListPositionAdapter);
//        mRecyclerViewPositionDialog.getAdapter().notifyDataSetChanged();

        mRadioGroup = (RadioGroup)view.findViewById(R.id.rg_position);
//        Bundle bundle = this.getArguments();
//        if(bundle!=null){
//            final Type type = new TypeToken<List<Position>>(){}.getType();
//            mListPosition = ;
//        }
//        Intent i = getActivity().getIntent();


        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        buildUrl.serviceGighub.callPosition().enqueue(new Callback<PositionResponse>() {
            @Override
            public void onResponse(Call<PositionResponse> call, Response<PositionResponse> response) {
                if(response.code()==200){
                    if(response.body().getError()==0){
                        mListPosition = response.body().getPositions();
//                        mListPositionAdapter = new ListPositionAdapter(mListPosition);
//
//                        mRecyclerViewPositionDialog.setHasFixedSize(true);
//                        mRecyclerViewPositionDialog.setLayoutManager(mLayoutManager);
//                        mRecyclerViewPositionDialog.setAdapter(mListPositionAdapter);
//                        mRecyclerViewPositionDialog.getAdapter().notifyDataSetChanged();
                        ((ViewGroup)mRadioGroup).removeAllViews();
                        for(Position p: mListPosition){
                            addRadioButton(p);
                        }
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                Log.d("RadioButton",((RadioButton)mRadioGroup.findViewById(checkedId)).getText().toString());
                                mListPosition.get(mRadioGroup.indexOfChild(mRadioGroup.findViewById(checkedId)));
                                EventBus.getDefault().post(mListPosition.get(mRadioGroup.indexOfChild(mRadioGroup.findViewById(checkedId))));
                                getDialog().dismiss();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<PositionResponse> call, Throwable t) {

            }
        });

//        mRecyclerViewPositionDialog.getChildViewHolder(mRadioButtonPosition).itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //                    Log.d("position",""+mListPosition.get(i).getPosition_name()+" "+mListPosition.get(i).isSelected());
//                sendResult(DialogPositionCreatBandFragment.REQ_POSITION_CODE);
//                getDialog().dismiss();
//            }
//        });

//        mRadioButtonPosition.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });


//        mRecyclerViewPositionDialog.getChildViewHolder(mRadioButtonPosition).itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sendResult(DialogPositionCreatBandFragment.REQ_POSITION_CODE);
//                getDialog().dismiss();
//            }
//        });

        return view;
    }

    public void sendResult(int reqCode){
        Intent intent = new Intent();
        intent.putExtra("posisi",new Gson().toJson(mListPosition));
        Log.d("Data Log", new Gson().toJson(mListPosition));
        getTargetFragment().onActivityResult(getTargetRequestCode(),reqCode,intent);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_position_creat_band, new LinearLayout(getActivity()),false);

        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setContentView(view);

        return builder;
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == ListPositionAdapter.REQ_POSITION_CODE) {
//            List<Position> positionList = new ArrayList<>();
//            Type typePositionList = new TypeToken<List<Position>>() {
//            }.getType();
//            positionList = new Gson().fromJson(data.getStringExtra("posisi"), typePositionList);
//            String positions = "";
//            for (Position position : positionList) {
//                if (position.isSelected()) {
////                    if(!genres.equals("")){
////                        genres += ","+g.getGenre_name();
////                    }
////                    else {
////                        genres += g.getGenre_name();
////                    }
//                    positions += "" + position.getPosition_name();
//                }
//            }
////            mEditTextSelectPosition.setText(positions); modelnya g
//        }
//    }

//    @Override
//    public void onClick(View v) {
//        Toast.makeText(mContext, "onclick", Toast.LENGTH_SHORT).show();
//        Log.d("onclick","recycler di klik");
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(mContext, "onclick", Toast.LENGTH_SHORT).show();
        Log.d("onclick","recycler di klik");
    }
    private void addRadioButton(Position p){
        ViewGroup _viewParent = (ViewGroup)mRadioGroup;
        View _view = View.inflate(getContext(),R.layout.rb_position, null);
        RadioButton _radioButton = (RadioButton) _view.findViewById(R.id.rb_position);
        _radioButton.setId(_radioButton.getId()+p.getId());
        _radioButton.setText(p.getPosition_name());
        _viewParent.addView(_view);

    }
}
