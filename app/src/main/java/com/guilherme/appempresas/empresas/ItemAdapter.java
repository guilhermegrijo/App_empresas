package com.guilherme.appempresas.empresas;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.guilherme.appempresas.R;
import com.guilherme.appempresas.domain.model.Enterprise;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Enterprise> mItemList;
    private OnItemClickListener listener;

    private TextView emptyList_textView;


    void setItemList(final List<Enterprise> itemList) {
        mItemList = itemList;

        notifyDataSetChanged();

        Log.d("RECYCLERVIEW", "setData");
        checkIfEmpty();
    }

    final private RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };


    void setEmptyList_textView(TextView textView) {
        this.emptyList_textView = textView;
    }

    void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enterprise_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).populateItemRows((ItemViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }


    public interface OnItemClickListener {
        void onItemClick(Enterprise item, ImageView sharedImageView);
    }

    void checkIfEmpty() {
        if (emptyList_textView != null && mItemList != null) {
            final boolean emptyViewVisible = mItemList.isEmpty();
            emptyList_textView.setVisibility(emptyViewVisible ? VISIBLE : INVISIBLE);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.enterprise_name)
        TextView name;
        @BindView(R.id.business)
        TextView business;
        @BindView(R.id.country)
        TextView country;
        @BindView(R.id.companyPhoto)
        ImageView companyPhoto;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        void populateItemRows(ItemViewHolder viewHolder, int position) {
            Log.d("name", mItemList.get(position).enterpriseName);
            viewHolder.name.setText(mItemList.get(position).enterpriseName);
            viewHolder.business.setText(mItemList.get(position).enterpriseType.enterpriseTypeName);
            viewHolder.country.setText(mItemList.get(position).country);
            Glide.with(companyPhoto.getContext()).load("https://empresas.ioasys.com.br" + mItemList.get(position).photo).placeholder(new ColorDrawable(Color.GREEN)).into(companyPhoto);

            itemView.setOnClickListener(v -> listener.onItemClick(mItemList.get(position), companyPhoto));
        }
    }
}