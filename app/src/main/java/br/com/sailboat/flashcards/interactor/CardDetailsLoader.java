package br.com.sailboat.flashcards.interactor;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.canoe.recycler.item.LabelRecyclerItem;
import br.com.sailboat.canoe.recycler.item.LabelValueRecyclerItem;
import br.com.sailboat.canoe.recycler.item.TitleRecyclerItem;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.helper.ViewType;
import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.model.Tag;
import br.com.sailboat.flashcards.persistence.sqlite.CardSQLite;
import br.com.sailboat.flashcards.persistence.sqlite.TagSQLite;


public class CardDetailsLoader {

    public static List<RecyclerItem> loadCardDetails(Context context, long cardId) throws Exception {
        List<RecyclerItem> recyclerItems = new ArrayList<>();

        Card card = CardSQLite.newInstance(context).getCardById(cardId);

        addFront(recyclerItems, card);
        addBack(context, card, recyclerItems);
        addTags(context, cardId, recyclerItems);

        return recyclerItems;
    }

    private static void addFront(List<RecyclerItem> recyclerItems, Card card) {
        TitleRecyclerItem item = new TitleRecyclerItem(ViewType.TITLE);
        item.setTitle(card.getFront());

        recyclerItems.add(item);
    }

    private static void addTags(Context context, long cardId, List<RecyclerItem> recyclerItems) throws Exception {
        List<Tag> tags = TagSQLite.newInstance(context).getByCard(cardId);

        if (!tags.isEmpty()) {
            LabelRecyclerItem item = new LabelRecyclerItem(ViewType.LABEL);
            item.setLabel(context.getString(R.string.label_tags));
            recyclerItems.add(item);

            recyclerItems.addAll(tags);
        }
    }

    private static void addBack(Context context, Card card, List<RecyclerItem> recyclerItems) {
        LabelValueRecyclerItem item = new LabelValueRecyclerItem(ViewType.LABEL_VALUE);
        item.setLabel(context.getString(R.string.label_back));
        item.setValue(card.getBack());

        recyclerItems.add(item);
    }


}
