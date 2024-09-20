package com.eaut20210719.thuchanh1609;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CartAdapter extends BaseAdapter {

    private Context context;
    private List<CartItem> cartItems;

    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        }

        CartItem cartItem = cartItems.get(position);

        TextView productName = convertView.findViewById(R.id.cartProductName);
        TextView productQuantity = convertView.findViewById(R.id.cartProductQuantity);

        // Giả sử bạn có phương thức lấy tên sản phẩm từ id sản phẩm
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        Product product = dbHelper.getProductById(cartItem.getProductId());

        productName.setText(product.getName());
        productQuantity.setText(String.valueOf(cartItem.getQuantity()));

        return convertView;
    }
}
