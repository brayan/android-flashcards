package br.com.sailboat.flashcards.interactor.loader;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.canoe.recycler.item.LabelRecyclerItem;
import br.com.sailboat.canoe.recycler.item.TitleRecyclerItem;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.helper.ViewType;
import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.model.Tag;
import br.com.sailboat.flashcards.persistence.sqlite.CardSQLite;
import br.com.sailboat.flashcards.persistence.sqlite.TagSQLite;

public class TagDetailsLoader {

    public static List<RecyclerItem> loadDetails(Context context, long tagId) throws Exception {
        List<RecyclerItem> recyclerItems = new ArrayList<>();

        Tag tag = TagSQLite.newInstance(context).getTagById(tagId);

        addTitle(recyclerItems, tag);
        addCards(context, tagId, recyclerItems);

        return recyclerItems;
    }

    private static void addTitle(List<RecyclerItem> recyclerItems, Tag tag) {
        TitleRecyclerItem item = new TitleRecyclerItem(ViewType.TITLE);
        item.setTitle(tag.getName());

        recyclerItems.add(item);
    }

    private static void addCards(Context context, long tagId, List<RecyclerItem> recyclerItems) throws Exception {

        List<Card> cards = CardSQLite.newInstance(context).getAllByTag(tagId);

        if (!cards.isEmpty()) {
            LabelRecyclerItem item = new LabelRecyclerItem(ViewType.LABEL);
            item.setLabel(context.getString(R.string.label_cards));
            recyclerItems.add(item);

            recyclerItems.addAll(cards);
        }

    }
}
