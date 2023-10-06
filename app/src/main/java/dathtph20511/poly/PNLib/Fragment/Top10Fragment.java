package dathtph20511.poly.PNLib.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import dathtph20511.poly.PNLib.Adapter.Top10RecycleAdapter;
import dathtph20511.poly.PNLib.DataBase.PhieuMuonDAO;
import dathtph20511.poly.PNLib.Model.Sach;
import dathtph20511.poly.PNLib.R;


public class Top10Fragment extends Fragment {
    private ArrayList<Sach> list = new ArrayList<>();
    private PhieuMuonDAO phieuMuonDAO;
    private Top10RecycleAdapter adapter;
    private RecyclerView recyclerView;


    public Top10Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Top10Fragment newInstance( ) {
        Top10Fragment fragment = new Top10Fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top10, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.Top10_recycleView);
        phieuMuonDAO = new PhieuMuonDAO(getActivity());
        list.clear();
        list = phieuMuonDAO.top10();
        adapter = new Top10RecycleAdapter(getActivity());
        adapter.setData(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}