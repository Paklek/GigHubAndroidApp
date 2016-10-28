package com.gighub.app.ui.fragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.model.Genre;
import com.gighub.app.model.MResponse;
import com.gighub.app.model.MusicianModel;
import com.gighub.app.model.MusicianResponse;
import com.gighub.app.model.Musicians;
import com.gighub.app.model.ResponseMusician;
import com.gighub.app.model.SearchResultResponse;
import com.gighub.app.ui.activity.MainActivity;
import com.gighub.app.ui.activity.SearchResultActivity;
import com.gighub.app.util.BuildUrl;
import com.gighub.app.util.StaticFunction;
import com.gighub.app.util.StaticString;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverMusicianFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    Context mContext;
    private Button mButtonDatePicker, mButtonSearch;
    private int mYear, mMonth, mDay;
    private EditText mEditTextSelectGenre;
    private RadioGroup mRadioGroupTipe;
    private RadioButton mRadioButtonTipeGroup, mRadioButtonTipeSolo, mRadioButtonTipeAll;
    Spinner mSpinner1;
    Spinner mSpinner2;
    private String[] mListProvinsi ={"-", "Aceh", "Bali", "Banten", "Bengkulu", "Gorontalo", "Jakarta", "Jambi", "Jawa Barat", "Jawa Tengah", "Jawa Timur", "Kalimantan Barat", "Kalimantan Selatan", "Kalimantan Tengah", "Kalimantan Timur", "Kalimantan Utara", "Kepulauan Bangka Belitung", "Kepulauan Riau", "Lampung", "Maluku", "Maluku Utara", "Nusa Tenggara Barat", "Nusa Tenggara Timur", "Papua", "Papua Barat", "Riau", "Sulawesi Barat", "Sulawesi Selatan", "Sulawesi Tengah", "Sulawesi Tenggara", "Sulawesi Utara", "Sumatera Barat", "Sumatera Selatan", "Sumatera Utara", "Yogyakarta"};

    public final static int REQCODE = 1000;

    private String mListKota[][]={
            {
                "-"
            },
            {
                "-",
                "Banda Aceh",
                "Langsa",
                "Lhokseumawe",
                "Sabang",
                "Subulussalam"
            },

            {
                "Denpasar"
            },

            {
                "-",
                "Cilegon",
                "Serang",
                "Tangerang",
                "Tangerang Selatan"
            },
            {
                "Bengkulu"
            },
            {
                "Gorontalo"
            },
            {
                "-",
                "Jakarta Barat",
                "Jakarta Pusat",
                "Jakarta Selatan",
                "Jakarta Timur",
                "Jakarta Utara"
            },
            {
                "-",
                "Jambi",
                "Sungai Penuh"
            },
            {
                "-",
                "Bandung",
                "Banjar",
                "Bekasi",
                "Bogor",
                "Cimahi",
                "Cirebon",
                "Depok",
                "Sukabumi",
                "Tasikmalaya"
            },
            {
                "-",
                "Magelang",
                "Pekalongan",
                "Salatiga",
                "Semarang",
                "Surakarta",
                "Tegal"
            },
            {
                "-",
                "Batu",
                "Blitar",
                "Kediri",
                "Madiun",
                "Malang",
                "Mojokerto",
                "Pasuruan",
                "Probolinggo",
                "Surabaya"
            },
            {
                "-",
                "Pontianak",
                "Singkawang"
            },
            {
                "-",
                "Banjar Baru",
                "Banjarmasin"
            },
            {
                "Palangka Raya"
            },
            {
                "-",
                "Balikpapan",
                "Bontang",
                "Samarinda"
            },
            {
                "Tarakan"
            },
            {
                "Pangkal Pinang"
            },
            {
                "-",
                "Batam",
                "Tanjung Pinang"
            },
            {
                "-",
                "Bandar Lampung",
                "Metro"
            },
            {
                "-",
                "Ambon",
                "Tual"
            },
            {
                "-",
                "Ternate",
                "Tidore Kepulauan"
            },
            {
                "-",
                "Bima",
                "Mataram"
            },
            {
                "Kupang"
            },
            {
                "Jayapura"
            },
            {
                "Sorong"
            },
            {
                "-",
                "Dumai",
                "Pekanbaru"
            },
            {
                "Mamuju"
            },
            {
                "-",
                "Makassar",
                "Palopo",
                "Parepare"
            },
            {
                "Palu"
            },
            {
                "-",
                "Bau-Bau",
                "Kendari"
            },
            {
                "-",
                "Bitung",
                "Kotamobagu",
                "Manado",
                "Tomohon"
            },
            {
                "-",
                "Bukit Tinggi",
                "Padang",
                "Padang Panjang",
                "Pariaman",
                "Payakumbuh",
                "Sawahlunto",
                "Solok"
            },
            {
                "-",
                "Lubuklinggau",
                "Pagar Alam",
                "Palembang",
                "Prabumulih"
            },
            {
                "-",
                "Binjai",
                "Gunung Sitoli",
                "Medan",
                "Padang Sidempuan",
                "Pematang Siantar",
                "Sibolga",
                "Tanjung Balai",
                "Tebing Tinggi"
            },
            {
                "Yogyakarta"
            }};

    public DiscoverMusicianFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discover_musician_main, container, false);
        mButtonSearch = (Button)view.findViewById(R.id.btn_search);
        mButtonDatePicker = (Button)view.findViewById(R.id.btn_date_search);
        mEditTextSelectGenre = (EditText)view.findViewById(R.id.et_select_genre);

        mRadioGroupTipe = (RadioGroup)view.findViewById(R.id.radiogroup_tipe_discovermusician);

        mRadioButtonTipeGroup = (RadioButton)view.findViewById(R.id.radio_tipe_group);
        mRadioButtonTipeSolo = (RadioButton)view.findViewById(R.id.radio_tipe_solo);
        mRadioButtonTipeAll = (RadioButton)view.findViewById(R.id.radio_tipe_all);


        mContext = inflater.getContext();

        mButtonDatePicker.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
//                Tanggal Sekarang
                                                 final Calendar c = Calendar.getInstance();
                                                 mYear = c.get(Calendar.YEAR);
                                                 mMonth = c.get(Calendar.MONTH);
                                                 mDay = c.get(Calendar.DAY_OF_MONTH);

                                                 DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                                                     @Override
                                                     public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                                         mButtonDatePicker.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                                     }
                                                 }, mYear, mMonth, mDay);
                                                 datePickerDialog.show();
                                             }
                                         });


        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticFunction staticFunction = new StaticFunction();
                staticFunction.buildProgressDialog(getContext());
                getSearchMusician();
            }
        });

        final Fragment this_fragment = this;
        mEditTextSelectGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DialogGenreFragment();
                dialogFragment.setTargetFragment(this_fragment,REQCODE);
                dialogFragment.show(getFragmentManager(),"Isi");

            }
        });



//        Spinner spinner = (Spinner) view.findViewById(R.id.provinsi_spinner);

//        masalah di getActivity, karena tadinya di activity work, trus pake "this" nah kalo di fragment gk work
        mSpinner1 = (Spinner)view.findViewById(R.id.provinsi_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,mListProvinsi);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner1.setAdapter(adapter);
        mSpinner1.setOnItemSelectedListener(this);



        mSpinner2 = (Spinner)view.findViewById(R.id.kota_spinner);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, mListKota[0]);
        mSpinner2.setAdapter(adapter2);
        mSpinner2.setOnItemSelectedListener(this);
        mRadioButtonTipeAll.setChecked(true);

        return view;
    }

    int mIndexProvinsi=0;
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        switch (parent.getId()){
        case R.id.provinsi_spinner:
            mIndexProvinsi=pos;
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, mListKota[pos]);
            mSpinner2.setAdapter(adapter2);
            mSpinner2.setOnItemSelectedListener(this);
            adapter2.notifyDataSetChanged();
            break;
            case R.id.kota_spinner:
                Toast.makeText(mContext,mListKota[mIndexProvinsi][pos],Toast.LENGTH_LONG).show();
                break;
        }

    }

    public void onNothingSelected(AdapterView<?> parent)
    {

    }
    void showDialog(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        DialogFragment dialogFragment = DialogGenreFragment.newInstance("isi");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==DiscoverMusicianFragment.REQCODE){
            List<Genre> genreList = new ArrayList<>();
            Type typeGenreList = new TypeToken<List<Genre>>(){}.getType();
            genreList = new Gson().fromJson(data.getStringExtra("kirim"),typeGenreList);
            String genres = "";
            for(Genre g:genreList) {
                if(g.getSelected()){
                    genres += g.getGenre_name()+" ";
                }
            }
            mEditTextSelectGenre.setText(genres);
        }
    }

    String mKotaSearch;

    private void getSearchMusician(){
        BuildUrl buildUrl = new BuildUrl();
        buildUrl.buildBaseUrl();

        mKotaSearch = mSpinner2.getSelectedItem().toString();

        Log.d("=","GetSearchMusician");
        if(mKotaSearch.equals("-") && mRadioButtonTipeAll.isChecked() && mEditTextSelectGenre.getText().toString().equals("Select Genres")|| mEditTextSelectGenre.getText().toString().equals("")){
//            buildUrl.serviceGighub.loadMusicians().enqueue(new Callback<MusicianResponse>() {
//                @Override
//                public void onResponse(Call<MusicianResponse> call, Response<MusicianResponse> response) {
//                    Intent i = new Intent(getActivity(),SearchResultActivity.class);
//                    Log.d("=",response.code()+"");
//                    Log.d("=",mKotaSearch);
//                    if(response.code()==200){
//                        if(response.body().getError()==0){
//                            i.putExtra("search",new Gson().toJson(response.body().getMusicians()));
//                            Log.d("kirim kota",mKotaSearch);
//                            Log.d("response",new Gson().toJson(response.body()));
////                            Log.d("musisi",new Gson().toJson(response.raw()));
//                            startActivity(i);
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<MusicianResponse> call, Throwable t) {
//
//                }
//            });

//            buildUrl.serviceGighub.sendLogin()


            buildUrl.serviceGighub.getSearchAllMusician().enqueue(new Callback<SearchResultResponse>() {
                @Override
                public void onResponse(Call<SearchResultResponse> call, Response<SearchResultResponse> response) {
                    Intent i = new Intent(getActivity(),SearchResultActivity.class);
                    Log.d("=",response.code()+"");
                    if(response.code()==200){
                        if(response.body().getError()==0){
                            i.putExtra("search",new Gson().toJson(response.body().getMusicians()));
                            Log.d("kirim kota",mKotaSearch);
                            Log.d("response",new Gson().toJson(response.body()));
//                            Log.d("musisi",new Gson().toJson(response.raw()));
                            startActivity(i);
                        }
                    }
                    Log.d("response",new Gson().toJson(response.body()));
                }

                @Override
                public void onFailure(Call<SearchResultResponse> call, Throwable t) {

                }
            });


        }

        if(mKotaSearch.equals("-") && mRadioButtonTipeSolo.isChecked()&& mEditTextSelectGenre.getText().toString().equals("Select Genres")|| mEditTextSelectGenre.getText().toString().equals("")) {
            buildUrl.serviceGighub.getSearchMusicianByRole(mRadioButtonTipeSolo.getText().toString()).enqueue(new Callback<SearchResultResponse>() {
                @Override
                public void onResponse(Call<SearchResultResponse> call, Response<SearchResultResponse> response) {
                    Intent i = new Intent(getActivity(),SearchResultActivity.class);
                    Log.d("=",response.code()+"");
                    if(response.code()==200){
                        if(response.body().getError()==0){
                            i.putExtra("search",new Gson().toJson(response.body().getMusicians()));
//                            Log.d("kirim kota",mKotaSearch);
                            Log.d("response",new Gson().toJson(response.body()));
//                            Log.d("musisi",new Gson().toJson(response.raw()));
                            startActivity(i);
                        }
                    }
                    Log.d("response",new Gson().toJson(response.body()));
                }

                @Override
                public void onFailure(Call<SearchResultResponse> call, Throwable t) {

                }
            });
        }

        if(mKotaSearch.equals("-") && mRadioButtonTipeGroup.isChecked()&& mEditTextSelectGenre.getText().toString().equals("Select Genres")|| mEditTextSelectGenre.getText().toString().equals("")) {
            buildUrl.serviceGighub.getSearchMusicianByRole(mRadioButtonTipeGroup.getText().toString()).enqueue(new Callback<SearchResultResponse>() {
                @Override
                public void onResponse(Call<SearchResultResponse> call, Response<SearchResultResponse> response) {
                    Intent i = new Intent(getActivity(),SearchResultActivity.class);
                    Log.d("=",response.code()+"");
                    if(response.code()==200){
                        if(response.body().getError()==0){
                            i.putExtra("search",new Gson().toJson(response.body().getMusicians()));
//                            Log.d("kirim kota",mKotaSearch);
                            Log.d("response",new Gson().toJson(response.body()));
//                            Log.d("musisi",new Gson().toJson(response.raw()));
                            startActivity(i);
                        }
                    }
                    Log.d("response",new Gson().toJson(response.body()));
                }

                @Override
                public void onFailure(Call<SearchResultResponse> call, Throwable t) {

                }
            });
        }

        if(!mKotaSearch.equals("-")) {
            buildUrl.serviceGighub.getSearchMusician(mKotaSearch).enqueue(new Callback<SearchResultResponse>() {
                @Override
                public void onResponse(Call<SearchResultResponse> call, Response<SearchResultResponse> response) {
                    Intent i = new Intent(getActivity(),SearchResultActivity.class);
                    Log.d("=",response.code()+"");
                    if(response.code()==200){
                        if(response.body().getError()==0){
                            i.putExtra("search",new Gson().toJson(response.body().getMusicians()));
                            Log.d("kirim kota",mKotaSearch);
                            Log.d("response",new Gson().toJson(response.body()));
//                            Log.d("musisi",new Gson().toJson(response.raw()));
                            startActivity(i);
                        }
                    }
                    Log.d("response",new Gson().toJson(response.body()));
                }

                @Override
                public void onFailure(Call<SearchResultResponse> call, Throwable t) {
                    Log.d("response", t.getMessage());
                    Toast.makeText(mContext,"Connection Fail. Check Your Connection",Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}
