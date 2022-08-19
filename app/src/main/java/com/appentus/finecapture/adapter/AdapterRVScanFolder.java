package com.appentus.finecapture.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appentus.finecapture.R;
import com.appentus.finecapture.activity.MainActivity;
import com.appentus.finecapture.models.ModelRvScanFolder;

import java.util.List;

public class AdapterRVScanFolder extends RecyclerView.Adapter<AdapterRVScanFolder.VH> {

    MainActivity mainActivity;
    List<ModelRvScanFolder> modelRvScanFolderList;
    public AdapterRVScanFolder(MainActivity mainActivity, List<ModelRvScanFolder> modelRvScanFolderList) {
        this.mainActivity =mainActivity;
        this.modelRvScanFolderList = modelRvScanFolderList;
    }

    @NonNull
    @Override
    public AdapterRVScanFolder.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_scan_folder,parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRVScanFolder.VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public static class VH extends RecyclerView.ViewHolder {
        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
