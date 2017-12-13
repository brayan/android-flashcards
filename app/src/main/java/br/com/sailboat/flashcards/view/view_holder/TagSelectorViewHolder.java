package br.com.sailboat.flashcards.view.view_holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.model.Tag;


public class TagSelectorViewHolder extends TagViewHolder {

    private CheckBox cbSelected;

    private TagSelectorViewHolder.Callback callback;


    public static TagSelectorViewHolder newInstance(ViewGroup parent, TagSelectorViewHolder.Callback callback) {
        View view = inflateLayout(parent, R.layout.vh_tag_selector);
        return new TagSelectorViewHolder(view, callback);
    }


    public TagSelectorViewHolder(View itemView, TagSelectorViewHolder.Callback callback) {
        super(itemView, callback);
        this.callback = callback;

        cbSelected = (CheckBox) itemView.findViewById(R.id.vh_tag_selector__cb);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCallback().onClickTag(getAdapterPosition());
            }
        });

        cbSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCallback().onClickTag(getAdapterPosition());
            }
        });
    }

    @Override
    public void bindToView(Tag item) {
        super.bindToView(item);
        bindCheckboxSelected(item);
    }

    private void bindCheckboxSelected(Tag item) {
        boolean isSelected = callback.isTagSelected(item);
        cbSelected.setChecked(isSelected);
    }

    public interface Callback extends TagViewHolder.Callback {
        boolean isTagSelected(Tag item);
    }


}
