package br.com.sailboat.flashcards.view.tag.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.canoe.filter.Filter;
import br.com.sailboat.canoe.recycler.RecyclerItem;


public class TagListViewModel implements Serializable{

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
