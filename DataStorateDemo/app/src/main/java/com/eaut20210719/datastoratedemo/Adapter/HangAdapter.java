package com.eaut20210719.datastoratedemo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eaut20210719.datastoratedemo.R;
import com.eaut20210719.datastoratedemo.models.Hang;

import java.util.List;

public class HangAdapter extends ArrayAdapter<Hang> {

    private Context context;
    private List<Hang> hangList;

    public HangAdapter(Context context, List<Hang> hangList) {
        super(context, 0, hangList);
        this.context = context;
        this.hangList = hangList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hang, parent, false);
        }

        Hang hang = getItem(position);

        TextView txtTenHang = convertView.findViewById(R.id.etTenHang);
        TextView txtSoLuong = convertView.findViewById(R.id.etSoLuong);
        TextView txtGia = convertView.findViewById(R.id.txtGia);

        txtTenHang.setText(hang.getTenHang());
        txtSoLuong.setText(String.valueOf(hang.getSoLuong()));
        txtGia.setText(String.valueOf(hang.getGia()));

        return convertView;
    }
}
