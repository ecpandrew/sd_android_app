package com.example.sdmonitorapp.ui.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sdmonitorapp.R;
import com.example.sdmonitorapp.models.Sensor;
import com.example.sdmonitorapp.ui.main.details.SensorDetailFragment;

import java.util.ArrayList;

public class MainFragment extends Fragment implements SensorRecyclerViewAdapter.ItemClickListener {

    private MainViewModel mViewModel;
    private SensorRecyclerViewAdapter adapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_fragment, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.rv1);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SensorRecyclerViewAdapter(getContext(), new Sensor[]{});
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        return root;



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.setContext(getContext());
        mViewModel.loadDataFromIDComponent();
        mViewModel.getSensores().observe(this, new Observer<Sensor[]>() {
            @Override
            public void onChanged(Sensor[] sensors) {
                adapter.changeDataSet(sensors);
            }
        });
        // TODO: Use the ViewModel
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public void onItemClick(View view, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("sensor", adapter.getItem(position));
        Fragment fragment = new SensorDetailFragment();
        fragment.setArguments(bundle);
        loadFragment(fragment);

    }
}