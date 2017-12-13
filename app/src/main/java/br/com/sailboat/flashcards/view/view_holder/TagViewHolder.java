package br.com.sailboat.flashcards.view.view_holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.sailboat.canoe.base.BaseViewHolder;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.model.Tag;


public class TagViewHolder extends BaseViewHolder {

    private TagViewHolder.Callback callback;

    private TextView tvName;


    public static TagViewHolder newInstance(ViewGroup parent, TagViewHolder.Callback callback) {
        View view = inflateLayout(parent, R.layout.vh_tag);
        return new TagViewHolder(view, callback);
    }


    public TagViewHolder(View itemView, TagViewHolder.Callback callback) {
        super(itemView);
        setCallback(callback);
        initViews();
    }

    private void initViews() {
        inflateViews();
        bindListeners();
    }

    public void bindToView(Tag item) {
        bindName(item.getName());
    }

    private void bindName(String taskName) {
        tvName.setText(taskName);
    }

    private void inflateViews() {
        tvName = itemView.findViewById(R.id.vh_tag__tv__name);
    }

    private void bindListeners() {

        if (callback != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onClickTag(getAdapterPosition());
                }
            });
        }
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void onClickTag(int position);
    }

}
