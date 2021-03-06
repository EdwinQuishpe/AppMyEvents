package com.example.edwin.appmyevents.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.edwin.appmyevents.R;
import com.example.edwin.appmyevents.interfaz.Modelo.Local;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;


public class LocalAdapter extends BaseAdapter {


    public class ViewHolder {
        TextView tvnombres, tvDescripcion;
        ImageView ivimagen;
    }

    public List<Local> listaLocal=new ArrayList<Local>();

    public Context context;

    public LocalAdapter(List<Local> listaLocal, Context context) {
        this.listaLocal = listaLocal;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaLocal.size();
    }

    @Override
    public Object getItem(int position) {
        return listaLocal.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.layout_adapter_local, null);
            viewHolder = new ViewHolder();
            viewHolder.tvnombres = rowView.findViewById(R.id.nombress);
            viewHolder.tvDescripcion =rowView.findViewById(R.id.descripcion);
            viewHolder.ivimagen = rowView.findViewById(R.id.imageViewLocal);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Local local = listaLocal.get(position);
        viewHolder.tvnombres.setText(local.getNombre());
        viewHolder.tvDescripcion.setText(local.getDescripcion()+"");
        System.out.println(local.getFotoPerfil());
        Picasso.with(context).load(local.getFotoPerfil()).fit().centerInside().into(viewHolder.ivimagen);

        return rowView;
    }

}