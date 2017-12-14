package br.com.sailboat.flashcards.view.tag;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import br.com.sailboat.canoe.base.BaseDialogFragment;
import br.com.sailboat.canoe.helper.LogHelper;
import br.com.sailboat.canoe.helper.StringHelper;
import br.com.sailboat.flashcards.R;
import br.com.sailboat.flashcards.model.Tag;
import br.com.sailboat.flashcards.persistence.sqlite.TagSQLite;


public class InsertTagDialog extends BaseDialogFragment {

    private Tag flashcardTag;
    private EditText etInput;

    private InsertTagDialog.Callback callback;

    public static void show(FragmentManager manager, Tag tag, InsertTagDialog.Callback callback) {
        InsertTagDialog dialog = new InsertTagDialog();
        dialog.setFlashcardTag(tag);
        dialog.setCallback(callback);

        dialog.show(manager, InsertTagDialog.class.getCanonicalName());
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.dlg_input_text, null);
        initViews(view);
        return buildDialog(view);
    }

    @Override
    protected void onResumeFirstSession() {
        if (flashcardTag != null && StringHelper.isNotEmpty(flashcardTag.getName())) {
            etInput.setText(flashcardTag.getName());
            etInput.setSelection(flashcardTag.getName().length());
        }

    }

    private void initViews(View view) {
        etInput = view.findViewById(R.id.dlg_input_text__et__input);
    }

    private Dialog buildDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    String text = etInput.getText().toString().trim();
                    if (StringHelper.isNotEmpty(text)) {

                        if (getFlashcardTag() != null) {
                            getFlashcardTag().setName(text);
                            TagSQLite.newInstance(getContext()).update(getFlashcardTag());
                        } else {
                            Tag tag = new Tag();
                            tag.setName(text);
                            TagSQLite.newInstance(getContext()).save(tag);
                        }

                        callback.onClickOk();

                    } else {
                        Toast.makeText(getActivity(), getString(R.string.msg_insert_tag), Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    LogHelper.logException(e);
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton(android.R.string.cancel, null);


        if (flashcardTag != null && StringHelper.isNotEmpty(flashcardTag.getName())) {
            builder.setTitle(getString(R.string.edit_tag));
        } else {
            builder.setTitle(getString(R.string.new_tag));
        }

        Dialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    public Tag getFlashcardTag() {
        return flashcardTag;
    }

    public void setFlashcardTag(Tag flashcardTag) {
        this.flashcardTag = flashcardTag;
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public interface Callback {
        void onClickOk();
    }

}
