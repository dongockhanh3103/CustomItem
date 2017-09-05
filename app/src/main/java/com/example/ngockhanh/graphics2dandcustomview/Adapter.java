package com.example.ngockhanh.graphics2dandcustomview;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class Adapter extends BaseDynamicGridAdapter  {

    public Adapter(Context context, List<String> items, int columnCount) {
        super(context, items, columnCount);
    }

/*

    public Adapter(Context context, List<Logo> items, int columnCount) {
        super(context, items, columnCount);
    }
*/



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheeseViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
            holder = new CheeseViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CheeseViewHolder) convertView.getTag();
        }
        holder.build(getItem(position).toString());

        return convertView;
    }



    private class CheeseViewHolder  {
        private ItemView itemView;

        private CheeseViewHolder(View view) {
            itemView = (ItemView) view.findViewById(R.id.itemView);
           // image = (ImageView) view.findViewById(R.id.item_img);
        }

        void build(final String title) {
           /* titleText.setText(title);
            image.setImageResource(R.drawable.ic_launcher);*/
            itemView.setItemText(title);
          /*  Drawable drawable=getContext().getResources().getDrawable(image);
            itemView.setDrawable(drawable);*/
            itemView.callbackDelete=new CallbackDelete() {
                @Override
                public void onDelete(String object) {
                   // Logo lg=new Logo(title,image);
                    Adapter.this.remove(title);

                }
            };
        }





    }
}
