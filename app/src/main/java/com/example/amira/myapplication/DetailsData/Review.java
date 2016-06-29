package com.example.amira.myapplication.DetailsData;

import com.example.amira.myapplication.Adapters.ListAdpater;
import com.example.amira.myapplication.InterFace.Type;

/**
 * Created by amira on 4/23/2016.
 */
public class Review  implements Type {



    private String id;
    private String author;
    private String content;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public int get_Type()
    {
        return ListAdpater.choosetype.REVIEW_ITEM.ordinal();
    }


    }

