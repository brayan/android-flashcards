package br.com.sailboat.flashcards.view.card.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.canoe.filter.Filter;
import br.com.sailboat.canoe.recycler.RecyclerItem;


public class CardListViewModel implements Serializable{

    private final List<RecyclerItem> recyclerItemList = new ArrayList<>();
    private Filter filter;

    public List<RecyclerItem> getRecyclerItemList() {
        return recyclerItemList;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

}
