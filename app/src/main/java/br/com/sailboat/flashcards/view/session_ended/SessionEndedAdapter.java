package br.com.sailboat.flashcards.view.session_ended;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import br.com.sailboat.flashcards.helper.ViewType;
import br.com.sailboat.flashcards.model.view.CardPlay;
import br.com.sailboat.flashcards.view.view_holder.CardPlayViewHolder;


public class SessionEndedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private SessionEndedAdapter.Callback callback;

    public SessionEndedAdapter(SessionEndedAdapter.Callback callback) {
        setCallback(callback);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case ViewType.CARD_PLAY: {
                return CardPlayViewHolder.newInstance(parent);
            }
            default: {
                throw new RuntimeException("ViewHolder not found");
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CardPlay item = getCallback().getCardPlayList().get(position);

        switch (item.getViewType()) {
            case ViewType.CARD_PLAY: {
                ((CardPlayViewHolder) holder).bindData(item);
                return;
            }
            default: {
                throw new RuntimeException("ViewHolder not found");
            }
        }

    }

    @Override
    public int getItemCount() {
        return getCallback().getCardPlayList().size();
    }

    @Override
    public int getItemViewType(int position) {
        return getCallback().getCardPlayList().get(position).getViewType();
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }


    public interface Callback {
        List<CardPlay> getCardPlayList();
    }


}
