package br.com.sailboat.flashcards.view.tag.list;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.helper.ViewType;
import br.com.sailboat.flashcards.model.Tag;
import br.com.sailboat.flashcards.view.view_holder.TagViewHolder;


public class TagListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private TagListAdapter.Callback callback;

    public TagListAdapter(TagListAdapter.Callback callback) {
        setCallback(callback);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case ViewType.TAG: {
                return TagViewHolder.newInstance(parent, callback);
            }
            default: {
                throw new RuntimeException("ViewHolder not found");
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerItem item = getCallback().getRecyclerItemList().get(position);

        switch (item.getViewType()) {
            case ViewType.TAG: {
                ((TagViewHolder) holder).bindToView((Tag) item);
                return;
            }
            default: {
                throw new RuntimeException("ViewHolder not found");
            }
        }

    }

    @Override
    public int getItemCount() {
        return getCallback().getRecyclerItemList().size();
    }

    @Override
    public int getItemViewType(int position) {
        return getCallback().getRecyclerItemList().get(position).getViewType();
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback extends TagViewHolder.Callback {
        List<RecyclerItem> getRecyclerItemList();
    }

}
