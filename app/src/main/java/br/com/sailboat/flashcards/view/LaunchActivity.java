package br.com.sailboat.flashcards.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import br.com.sailboat.canoe.helper.LogHelper;
import br.com.sailboat.flashcards.persistence.sqlite.CardSQLite;
import br.com.sailboat.flashcards.persistence.sqlite.TagSQLite;
import br.com.sailboat.flashcards.view.card.list.CardListActivity;
import br.com.sailboat.flashcards.view.tag.list.TagListActivity;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            boolean hasCardAdded = CardSQLite.newInstance(this).hasCardAdded();

            boolean hasTagAdded = TagSQLite.newInstance(this).hasTagAdded();

            if (hasCardAdded && hasTagAdded) {
                TagListActivity.start(this);
            } else {
                CardListActivity.start(this);
            }

        } catch (Exception e) {
            LogHelper.logException(e);
            CardListActivity.start(this);
        }

        finish();
    }

}
