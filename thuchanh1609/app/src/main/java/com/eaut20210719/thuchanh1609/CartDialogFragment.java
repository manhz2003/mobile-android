package com.eaut20210719.thuchanh1609;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.List;

public class CartDialogFragment extends DialogFragment {

    DatabaseHelper databaseHelper;
    ListView cartListView;
    Button completeOrderButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_cart, container, false);

        databaseHelper = new DatabaseHelper(getContext());
        cartListView = view.findViewById(R.id.cartListView);
        completeOrderButton = view.findViewById(R.id.completeOrderButton);

        loadCartItems();

        completeOrderButton.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc chắn muốn hoàn tất đơn hàng không?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        databaseHelper.clearCart();
                        Toast.makeText(getContext(), "Đơn hàng đã được hoàn tất!", Toast.LENGTH_SHORT).show();
                        dismiss();
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });

        return view;
    }

    private void loadCartItems() {
        List<CartItem> cartItems = databaseHelper.getCartItems();
        CartAdapter adapter = new CartAdapter(getContext(), cartItems);
        cartListView.setAdapter(adapter);
    }
}
