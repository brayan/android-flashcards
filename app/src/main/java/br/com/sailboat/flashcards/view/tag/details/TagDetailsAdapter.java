package br.com.sailboat.flashcards.view.tag.details;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.canoe.recycler.item.LabelRecyclerItem;
import br.com.sailboat.canoe.recycler.item.LabelValueRecyclerItem;
import br.com.sailboat.canoe.recycler.item.TitleRecyclerItem;
import br.com.sailboat.canoe.recycler.view_holder.LabelValueViewHolder;
import br.com.sailboat.canoe.recycler.view_holder.LabelViewHolder;
import br.com.sailboat.canoe.recycler.view_holder.TitleViewHolder;
import br.com.sailboat.flashcards.helper.ViewType;
import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.view.view_holder.CardViewHolder;


public class TagDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private TagDetailsAdapter.Callback callback;

    public TagDetailsAdapter(Callback callback) {
        this.callback = callback;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case  ViewType.TITLE: {
                return TitleViewHolder.newInstance(parent);
            }
            case ViewType.LABEL: {
                return LabelViewHolder.newInstance(parent);
            }
            case ViewType.LABEL_VALUE: {
                return LabelValueViewHolder.newInstance(parent);
            }
            case ViewType.CARD: {
                return CardViewHolder.newInstance(parent, callback);
            }
            default: {
                throw new RuntimeException("ViewHolder not found");
            }
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerItem item = callback.getRecyclerItemList().get(position);

        switch (item.getViewType()) {
            case  ViewType.TITLE: {
                ((TitleViewHolder) holder).bindItem((TitleRecyclerItem) item);
                return;
            }
            case ViewType.LABEL: {
                ((LabelViewHolder) holder).bindItem(((LabelRecyclerItem) item));
                return;
            }
            case ViewType.LABEL_VALUE: {
                ((LabelValueViewHolder) holder).bindItem((LabelValueRecyclerItem) item);
                return;
            }
            case ViewType.CARD: {
                ((CardViewHolder) holder).bindToView((Card) item);
                return;
            }
            default: {
                throw new RuntimeException("ViewHolder not found");
            }
        }

    }

    @Override
    public int getItemCount() {
        return callback.getRecyclerItemList().size();
    }

    @Override
    public int getItemViewType(int position) {
        return callback.getRecyclerItemList().get(position).getViewType();
    }


    public interface Callback extends CardViewHolder.Callback {
        List<RecyclerItem> getRecyclerItemList();
    }

}
