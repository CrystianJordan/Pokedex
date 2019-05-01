package com.example.pokedex;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class PokemonDetail extends AppCompatActivity {
    JSONEntry pokemon;
    TextView name;
    TextView weight;
    TextView height;
    ImageView img;
    Button voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_detail);
        pokemon = (JSONEntry) getIntent().getSerializableExtra("pokemon");

        name = findViewById(R.id.name);
        weight = findViewById(R.id.weight);
        height = findViewById(R.id.height);
        img = findViewById(R.id.imgPokemonDetail);
        voltar = findViewById(R.id.btnVoltar);
        downloadImage(pokemon.getImgUrl());
        name.setText(pokemon.getNome());
        weight.setText(pokemon.getWeight());
        height.setText(pokemon.getHeight());
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PokemonDetail.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    public void downloadImage(String imageUrl) {
        ImageDownloader imageDownloader = new ImageDownloader();
        imageUrl = imageUrl.replace("http", "https");
        try {
            Bitmap imagem = imageDownloader.execute(imageUrl).get();
            img.setImageBitmap(imagem);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
