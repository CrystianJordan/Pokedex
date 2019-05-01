package com.example.pokedex;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;
public class PokedexCellAdapter extends ArrayAdapter{

    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private List<JSONEntry> aplicativos;

    public PokedexCellAdapter(Context context, int resource, List<JSONEntry> aplicativos) {
        super(context, resource);
        this.layoutResource = resource;
        this.layoutInflater = LayoutInflater.from(context);
        this.aplicativos = aplicativos;
    }

    @Override
    public int getCount() {
        return aplicativos.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        JSONEntry appAtual = aplicativos.get(position);

        viewHolder.Nome.setText(appAtual.getNome());
        viewHolder.height.setText(appAtual.getHeight());
        viewHolder.weight.setText(appAtual.getWeight());
        viewHolder.id.setText(appAtual.getId());

        try {
            new DownloadImageTask(viewHolder.img).execute(appAtual.getImgUrl().replace("http", "https")).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    private class ViewHolder {
        final TextView Nome;
        final TextView id;
        final TextView weight;
        final TextView height;
        final ImageView img;

        ViewHolder(View v) {
            this.Nome = v.findViewById(R.id.PokeName);
            this.id = v.findViewById(R.id.PokeId);
            this.weight = v.findViewById(R.id.pokeWeight);
            this.height = v.findViewById(R.id.pokeHeigh);
            this.img = v.findViewById(R.id.imgPokemonDetail);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
