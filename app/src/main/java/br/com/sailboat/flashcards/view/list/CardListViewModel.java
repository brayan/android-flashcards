package br.com.sailboat.flashcards.view.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.persistence.filter.Filter;


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
