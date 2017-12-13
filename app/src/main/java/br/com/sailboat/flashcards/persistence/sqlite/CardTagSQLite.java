package br.com.sailboat.flashcards.persistence.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import br.com.sailboat.canoe.base.BaseSQLite;
import br.com.sailboat.flashcards.persistence.DatabaseOpenHelper;

public class CardTagSQLite extends BaseSQLite {


    public static CardTagSQLite newInstance(Context context) {
        return new CardTagSQLite(DatabaseOpenHelper.getInstance(context));
    }


    public CardTagSQLite(SQLiteOpenHelper database) {
        super(database);
    }

    @Override
    public String getCreateTableStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append(" CREATE TABLE CardTag ( ");
        sb.append(" id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" cardId INTEGER, ");
        sb.append(" tagId INTEGER, ");
        sb.append(" FOREIGN KEY(cardId) REFERENCES Card(id),");
        sb.append(" FOREIGN KEY(tagId) REFERENCES Tag(id) ");
        sb.append(" ); ");

        return  sb.toString();
    }

    public void deleteByCardId(long cardId) {
        String sql = "DELETE FROM CardTag WHERE CardTag.cardId = ?";
        SQLiteStatement statement = compileStatement(sql);
        statement.bindLong(1, cardId);

        delete(statement);
    }

}
