package com.example.pokedex;

public class JSONEntry {
    private String id;
    private String num;
    private String nome;
    private String imgUrl;
    private String height;
    private String weight;

    public JSONEntry(String id, String num, String nome, String imgUrl, String height, String weight) {
        this.id = id;
        this.num = num;
        this.nome = nome;
        this.imgUrl = imgUrl;
        this.height = height;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public String getNum() {
        return num;
    }

    public String getNome() {
        return nome;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }
}
