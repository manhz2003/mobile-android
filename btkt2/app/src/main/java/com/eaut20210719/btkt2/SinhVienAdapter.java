package com.eaut20210719.btkt2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SinhVienAdapter extends RecyclerView.Adapter<SinhVienAdapter.ViewHolder> {
    private List<SinhVien> sinhVienList;

    public SinhVienAdapter(List<SinhVien> sinhVienList) {
        this.sinhVienList = sinhVienList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sinhvien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SinhVien sinhVien = sinhVienList.get(position);
        holder.bind(sinhVien);
    }

    @Override
    public int getItemCount() {
        return sinhVienList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMsv;
        private TextView tvHoTen;
        private TextView tvNgaySinh;
        private TextView tvLop;
        private TextView tvDiemTrungBinh1;
        private TextView tvDiemTrungBinh2;
        private TextView tvDiemTrungBinh3;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMsv = itemView.findViewById(R.id.tvMsv);
            tvHoTen = itemView.findViewById(R.id.tvHoTen);
            tvNgaySinh = itemView.findViewById(R.id.tvNgaySinh);
            tvLop = itemView.findViewById(R.id.tvLop);
            tvDiemTrungBinh1 = itemView.findViewById(R.id.tvDiemTrungBinh1);
            tvDiemTrungBinh2 = itemView.findViewById(R.id.tvDiemTrungBinh2);
            tvDiemTrungBinh3 = itemView.findViewById(R.id.tvDiemTrungBinh3);
        }

        public void bind(SinhVien sinhVien) {
            tvMsv.setText("Mã sinh viên: " + sinhVien.getMsv());
            tvHoTen.setText("Họ và tên: " + sinhVien.getHoTen());
            tvNgaySinh.setText("Ngày sinh: " + sinhVien.getNgaySinh());
            tvLop.setText("Lớp: " + sinhVien.getLop());
            tvDiemTrungBinh1.setText("Điểm môn C: " + String.valueOf(sinhVien.getDiemTrungBinh1()));
            tvDiemTrungBinh2.setText("Điểm môn .net" + String.valueOf(sinhVien.getDiemTrungBinh2()));
            tvDiemTrungBinh3.setText("Điểm môn CSDL" + String.valueOf(sinhVien.getDiemTrungBinh3()));
        }

    }
}
