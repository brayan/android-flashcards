package br.com.sailboat.flashcards.view.view_holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.model.Card;


public class CardSelectorViewHolder extends CardViewHolder {

    private CheckBox cbSelected;

    private CardSelectorViewHolder.Callback callback;


    public static CardSelectorViewHolder newInstance(ViewGroup parent, CardSelectorViewHolder.Callback callback) {
        View view = inflateLayout(parent, R.layout.vh_card_selector);
        return new CardSelectorViewHolder(view, callback);
    }


    public CardSelectorViewHolder(View itemView, CardSelectorViewHolder.Callback callback) {
        super(itemView, callback);
        this.callback = callback;

        cbSelected = itemView.findViewById(R.id.vh_selector__cb);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCallback().onClickCard(getAdapterPosition());
            }
        });

        cbSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCallback().onClickCard(getAdapterPosition());
            }
        });
    }

    @Override
    public void bindToView(Card item) {
        super.bindToView(item);
        bindCheckboxSelected(item);
    }

    private void bindCheckboxSelected(Card item) {
        boolean isSelected = callback.isItemSelected(item);
        cbSelected.setChecked(isSelected);
    }

    public interface Callback extends CardViewHolder.Callback {
        boolean isItemSelected(Card item);
    }


}
