package br.com.sailboat.flashcards.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.canoe.base.BaseSQLite;
import br.com.sailboat.canoe.helper.CreateTablesHelper;
import br.com.sailboat.canoe.helper.LogHelper;
import br.com.sailboat.flashcards.persistence.sqlite.CardHistorySQLite;
import br.com.sailboat.flashcards.persistence.sqlite.CardSQLite;
import br.com.sailboat.flashcards.persistence.sqlite.CardTagSQLite;
import br.com.sailboat.flashcards.persistence.sqlite.TagSQLite;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "flashcards.db";

    private static DatabaseOpenHelper instance;
    private Context context;

    private DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static synchronized DatabaseOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            CreateTablesHelper.createTables(db, getSQLiteTables());
        } catch (Exception e) {
            LogHelper.logException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO
    }

    private  List<BaseSQLite> getSQLiteTables() {
        List<BaseSQLite> tables = new ArrayList<>();
        tables.add(CardSQLite.newInstance(context));
        tables.add(TagSQLite.newInstance(context));
        tables.add(CardTagSQLite.newInstance(context));
        tables.add(CardHistorySQLite.newInstance(context));

        return tables;
    }

}
