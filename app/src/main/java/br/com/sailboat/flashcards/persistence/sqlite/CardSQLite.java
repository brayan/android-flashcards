package br.com.sailboat.flashcards.persistence.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.canoe.base.BaseSQLite;
import br.com.sailboat.canoe.exception.EntityNotFoundException;
import br.com.sailboat.canoe.helper.StringHelper;
import br.com.sailboat.canoe.recycler.RecyclerItem;
import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.persistence.DatabaseOpenHelper;
import br.com.sailboat.flashcards.persistence.filter.Filter;


public class CardSQLite extends BaseSQLite {


    public static CardSQLite newInstance(Context context) {
        return new CardSQLite(DatabaseOpenHelper.getInstance(context));
    }


    public CardSQLite(SQLiteOpenHelper database) {
        super(database);
    }

    @Override
    public String getCreateTableStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append(" CREATE TABLE Card ( ");
        sb.append(" id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" front TEXT NOT NULL, ");
        sb.append(" back TEXT NOT NULL, ");
        sb.append(" enabled INTEGER ");
        sb.append(" ); ");

        return sb.toString();
    }

    public Card getCardById(long cardId) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT Card.* FROM Card ");
        sb.append(" WHERE Card.id = " + cardId);

        Cursor cursor = performQuery(sb.toString());

        if (cursor.moveToNext()) {
            Card card = buildFromCursor(cursor);
            cursor.close();
            return card;
        }

        throw new EntityNotFoundException();
    }

    public List<RecyclerItem> getAll(Filter filter) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT Card.* FROM Card ");

        if (filter != null) {

            sb.append(" WHERE 1=1 ");

            if (StringHelper.isNotEmpty(filter.getSearchText())) {
                sb.append(" AND Card.front LIKE '%" + filter.getSearchText() + "%' ");
            }

        }

        return new ArrayList<RecyclerItem>(getCardList(sb.toString()));
    }

    public void save(Card card) throws Exception {
        StringBuilder sql = new StringBuilder();

        sql.append(" INSERT INTO Card ");
        sql.append(" (front, back, enabled) ");
        sql.append(" VALUES (?, ?, ?); ");

        SQLiteStatement stmt = compileStatement(sql.toString());
        stmt.bindString(1, card.getFront());
        stmt.bindString(2, card.getBack());
        stmt.bindLong(3, parseBooleanToInt(card.isEnabled()));

        long id = insert(stmt);
        card.setId(id);
    }

    public void update(Card card) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE Card SET ");
        sql.append(" front = ?, ");
        sql.append(" back = ?, ");
        sql.append(" enabled = ? ");
        sql.append(" WHERE id = ? ");

        SQLiteStatement statement = compileStatement(sql.toString());
        statement.bindString(1, card.getFront());
        statement.bindString(2, card.getBack());
        statement.bindLong(3, parseBooleanToInt(card.isEnabled()));
        statement.bindLong(4, card.getId());

        update(statement);
    }

    private Card buildFromCursor(Cursor cursor) throws Exception {
        Card task = new Card();
        task.setId(getLong(cursor, "id"));
        task.setFront(getString(cursor, "front"));
        task.setBack(getString(cursor, "back"));
        task.setEnabled(getBoolean(cursor, "enabled"));

        return task;
    }

    @NonNull
    private List<Card> getCardList(String query) throws Exception {
        Cursor cursor = performQuery(query.toString());
        List<Card> tasks = new ArrayList<>();

        while (cursor.moveToNext()) {
            Card card = buildFromCursor(cursor);
            tasks.add(card);
        }

        cursor.close();

        return tasks;
    }


}
