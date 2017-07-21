package com.example.mayc.openmind;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mayc.openmind.models.Article;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleDetailActivity extends AppCompatActivity {

    Context context;
    Article article;

    @BindView (R.id.tvPublisher) TextView tvPublisher;
    @BindView(R.id.tvArticleTitle) TextView tvArticleTitle;
    @BindView(R.id.tvAuthor) TextView tvAuthor;
    @BindView(R.id.tvImageCaption) TextView tvImageCaption;
    @BindView(R.id.tvArticleUrl) TextView tvArticleUrl;
    @BindView(R.id.ivArticleImage) ImageView ivArticleImage;
    @BindView(R.id.ivOpenIcon) ImageView ivOpenIcon;
    @BindView(R.id.tvArticleDescription) TextView tvArticleDescription;
    @BindView(R.id.tvDatePublished) TextView tvDatePublished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_article);
        ButterKnife.bind(this);
        article = Parcels.unwrap(getIntent().getParcelableExtra(Article.class.getSimpleName()));
        Log.d("ArticleDetailActivity", String.format("Showing details for '%s'", article.getID()));

        //setting data to the views
        tvArticleTitle.setText(article.title);
        tvAuthor.setText(article.author);
        tvArticleDescription.setText(article.bodySnippet);
        tvImageCaption.setText("Need to find image caption");//Todo find image caption
        tvArticleUrl.setText(article.sourceUrl);
        tvDatePublished.setText(article.datePublished);

        context = this;

        int placeholderId = R.drawable.face_profile;


        Glide.with(context)
                .load(article.getImageUrl())
                .placeholder(placeholderId)
                .into(ivArticleImage);


    }
}