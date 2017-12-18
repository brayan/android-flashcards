package br.com.sailboat.flashcards.persistence.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import java.util.Calendar;

import br.com.sailboat.canoe.base.BaseSQLite;
import br.com.sailboat.flashcards.model.CardHistory;
import br.com.sailboat.flashcards.model.view.CardAnswer;
import br.com.sailboat.flashcards.persistence.DatabaseOpenHelper;

public class CardHistorySQLite extends BaseSQLite {


    public static CardHistorySQLite newInstance(Context context) {
        return new CardHistorySQLite(DatabaseOpenHelper.getInstance(context));
    }


    public CardHistorySQLite(SQLiteOpenHelper database) {
        super(database);
    }

    @Override
    public String getCreateTableStatement() {
        StringBuilder sb = new StringBuilder();
        sb.append(" CREATE TABLE CardHistory ( ");
        sb.append(" id INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" fkCardId INTEGER, ");
        sb.append(" answer INTEGER, ");
        sb.append(" insertingDate TEXT, ");
        sb.append(" FOREIGN KEY(fkCardId) REFERENCES Card(id) ");
        sb.append(" ); ");

        return sb.toString();
    }

    public void save(CardHistory cardHistory) throws Exception {
        StringBuilder sql = new StringBuilder();

        sql.append(" INSERT INTO CardHistory ");
        sql.append(" (fkCardId, answer, insertingDate) ");
        sql.append(" VALUES (?, ?, ?); ");

        SQLiteStatement statement = compileStatement(sql.toString());
        statement.bindLong(1, cardHistory.getCardId());
        statement.bindLong(2, cardHistory.getAnswer());
        statement.bindString(3, parseCalendarToString(Calendar.getInstance()));

        long id = insert(statement);
        cardHistory.setId(id);
    }

    public int getAmountOfRightAnswers(long cardId) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT 1 FROM CardHistory ");
        sb.append(" WHERE CardHistory.fkCardId = " + cardId);
        sb.append(" AND CardHistory.answer = " + CardAnswer.RIGHT);

        return getCountFromQuery(sb.toString());
    }

    public int getAmountOfWrongAnswers(long cardId) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT 1 FROM CardHistory ");
        sb.append(" WHERE CardHistory.fkCardId = " + cardId);
        sb.append(" AND CardHistory.answer = " + CardAnswer.WRONG);

        return getCountFromQuery(sb.toString());
    }


    public void deleteByCard(long cardId) {
        String sql = "DELETE FROM CardHistory WHERE CardHistory.fkCardId = ?";
        SQLiteStatement statement = compileStatement(sql);
        statement.bindLong(1, cardId);

        delete(statement);
    }
}
