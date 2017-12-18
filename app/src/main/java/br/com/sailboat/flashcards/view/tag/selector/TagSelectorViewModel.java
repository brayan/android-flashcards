package br.com.sailboat.flashcards.view.tag.selector;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import br.com.sailboat.canoe.filter.Filter;
import br.com.sailboat.canoe.recycler.RecyclerItem;

public class TagSelectorViewModel {

    private final LinkedHashMap<Long, Long> selectedItems = new LinkedHashMap<>();
    private final List<RecyclerItem> recyclerItemList = new ArrayList<>();
    private Filter filter;

    public LinkedHashMap<Long, Long> getSelectedItems() {
        return selectedItems;
    }

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
