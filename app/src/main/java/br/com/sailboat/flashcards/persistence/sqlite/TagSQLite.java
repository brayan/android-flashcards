package br.com.sailboat.flashcards.persistence.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.canoe.base.BaseFilter;
import br.com.sailboat.canoe.base.BaseSQLite;
import br.com.sailboat.canoe.exception.EntityNotFoundException;
import br.com.sailboat.canoe.helper.StringHelper;
import br.com.sailboat.flashcards.model.Tag;
import br.com.sailboat.flashcards.persistence.DatabaseOpenHelper;

public class TagSQLite extends BaseSQLite {


    public static TagSQLite newInstance(Context context) {
        return new TagSQLite(DatabaseOpenHelper.getInstance(context));
    }


    public TagSQLite(SQLiteOpenHelper database) {
        super(database);
    }

    @Override
    public String getCreateTableStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append(" CREATE TABLE Tag ( ");
        sb.append(" id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" name TEXT NOT NULL ");
        sb.append(" ); ");

        return sb.toString();
    }

    public List<Tag> getAll(BaseFilter filter) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT Tag.* FROM Tag ");

        if (filter != null && StringHelper.isNotEmpty(filter.getSearchText())) {
            sb.append(" WHERE Tag.name LIKE '%" + filter.getSearchText() +"%'");
        }

        sb.append(" ORDER BY Tag.name ");

        return getTagsFromCursor(sb);
    }

    public List<Tag> getByCard(long cardId) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT Tag.* FROM Tag ");
        sb.append(" INNER JOIN CardTag ");
        sb.append(" ON (Tag.id = CardTag.tagId) ");
        sb.append(" WHERE CardTag.cardId = " + cardId + " ");
        sb.append(" ORDER BY Tag.name ");

        return getTagsFromCursor(sb);
    }

    public Tag getTagById(long tagId) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT Tag.* FROM Tag ");
        sb.append(" WHERE Tag.id = " + tagId);

        Cursor cursor = performQuery(sb.toString());

        if (cursor.moveToNext()) {
            Tag tag = buildFromCursor(cursor);
            cursor.close();
            return tag;
        }

        throw new EntityNotFoundException();
    }



    public void save(Tag tag) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(" INSERT INTO Tag ");
        sb.append(" (name) ");
        sb.append(" VALUES (?); ");

        SQLiteStatement statement = compileStatement(sb.toString());
        statement.bindString(1, tag.getName());

        long id = insert(statement);

        tag.setId(id);
    }

    public void update(Tag tag) throws Exception {

        StringBuilder sb = new StringBuilder();
        sb.append(" UPDATE Tag SET ");
        sb.append(" name = ? ");
        sb.append(" WHERE id = ? ");

        SQLiteStatement statement = compileStatement(sb.toString());
        statement.bindString(1, getValueOrEmptyString(tag.getName()));
        statement.bindLong(2, tag.getId());

        update(statement);

    }

    @NonNull
    private List<Tag> getTagsFromCursor(StringBuilder query) throws Exception {
        Cursor cursor = performQuery(query.toString());
        List<Tag> tags = new ArrayList<>();

        while (cursor.moveToNext()) {
            Tag tag = buildFromCursor(cursor);
            tags.add(tag);
        }

        cursor.close();

        return tags;
    }

    public void deleteRelationshipsByCard(long cardId) {
        String sql = "DELETE FROM CardTag WHERE CardTag.cardId = ?";
        SQLiteStatement statement = compileStatement(sql);
        statement.bindLong(1, cardId);

        delete(statement);
    }

    public void deleteTag(long tagId) {
        String sql = "DELETE FROM Tag WHERE Tag.id = ?";
        SQLiteStatement statement = compileStatement(sql);
        statement.bindLong(1, tagId);

        delete(statement);
    }

    public void addCardTag(long cardId, long tagId) {
        StringBuilder sb = new StringBuilder();

        sb.append(" INSERT INTO CardTag ");
        sb.append(" (cardId, tagId) ");
        sb.append(" VALUES (?, ?, ?); ");

        SQLiteStatement statement = compileStatement(sb.toString());
        statement.bindLong(1, cardId);
        statement.bindLong(2, tagId);

        insert(statement);
    }

    private Tag buildFromCursor(Cursor cursor) throws Exception {
        Tag tag = new Tag();
        tag.setId(getLong(cursor, "id"));
        tag.setName(getString(cursor, "name"));

        return tag;
    }


}
