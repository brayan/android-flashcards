package br.com.sailboat.flashcards.view.view_holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.sailboat.canoe.base.BaseViewHolder;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.model.Card;


public class CardViewHolder extends BaseViewHolder {

    private CardViewHolder.Callback callback;

    private TextView tvName;


    public static CardViewHolder newInstance(ViewGroup parent, CardViewHolder.Callback callback) {
        View view = inflateLayout(parent, R.layout.vh_card);
        return new CardViewHolder(view, callback);
    }


    public CardViewHolder(View itemView, CardViewHolder.Callback callback) {
        super(itemView);
        setCallback(callback);
        initViews();
    }

    private void initViews() {
        inflateViews();
        bindListeners();
    }

    public void bindToView(Card item) {
        bindTaskName(item.getFront());
    }

    private void bindTaskName(String taskName) {
        tvName.setText(taskName);
    }

    private void inflateViews() {
        tvName = itemView.findViewById(R.id.card__tv__name);
    }

    private void bindListeners() {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClickCard(getAdapterPosition());
            }
        });
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void onClickCard(int position);
    }

}
