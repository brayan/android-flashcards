package br.com.sailboat.flashcards.helper;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.model.Tag;
import br.com.sailboat.flashcards.model.view.CardMetrics;
import br.com.sailboat.flashcards.model.view.CardPlay;

public class ExtrasHelper {

    private static final String CARD_PLAY = "CARD_PLAY";
    private static final String CARD_ID = "CARD_ID";
    private static final String TAG_ID = "TAG_ID";
    private static final String CARD_LIST = "CARD_LIST";
    private static final String TAG_LIST = "TAG_LIST";
    private static final String CARD_METRICS = "CARD_METRICS";
    private static final String CARD_PLAY_LIST = "CARD_PLAY_LIST";

    public static void putCardPlay(CardPlay cardPlay, Intent intent) {
        intent.putExtra(CARD_PLAY, cardPlay);
    }

    public static CardPlay getCardPlay(Intent intent) {
        return (CardPlay) intent.getSerializableExtra(CARD_PLAY);
    }

    public static void putCardPlay(CardPlay cardPlay, Bundle bundle) {
        bundle.putSerializable(CARD_PLAY, cardPlay);
    }

    public static CardPlay getCardPlay(Bundle bundle) {
        return (CardPlay) bundle.getSerializable(CARD_PLAY);
    }

    public static void putCardId(long taskId, Intent intent) {
        intent.putExtra(CARD_ID, taskId);
    }

    public static long getCardId(Intent intent) {
        return intent.getLongExtra(CARD_ID, -1);
    }

    public static void putCardId(long taskId, Bundle bundle) {
        bundle.putLong(CARD_ID, taskId);
    }

    public static long getCardId(Bundle bundle) {
        return bundle.getLong(CARD_ID, -1);
    }

    public static void putTagId(long projectId, Intent intent) {
        intent.putExtra(TAG_ID, projectId);
    }

    public static long getTagId(Intent intent) {
        return intent.getLongExtra(TAG_ID, -1);
    }

    public static void putTagId(long projectId, Bundle bundle) {
        bundle.putLong(TAG_ID, projectId);
    }

    public static boolean hasProjectId(Intent intent) {
        return intent.hasExtra(TAG_ID);
    }

    public static long getTagId(Bundle bundle) {
        return bundle.getLong(TAG_ID, -1);
    }

    public static void putTags(List<Tag> projects, Intent intent) {
        intent.putExtra(TAG_LIST, (ArrayList<Tag>) projects);
    }

    public static void putTags(List<Tag> projects, Bundle bundle) {
        bundle.putSerializable(TAG_LIST, (ArrayList<Tag>) projects);
    }

    public static void putTasks(List<Card> tasks, Intent intent) {
        intent.putExtra(CARD_LIST, (ArrayList<Card>) tasks);
    }

    public static void putTasks(List<Card> tasks, Bundle bundle) {
        bundle.putSerializable(CARD_LIST, (ArrayList<Card>) tasks);
    }

    public static void putTaskViewList(List<Card> tasks, Intent intent) {
        intent.putExtra(CARD_LIST, (ArrayList<Card>) tasks);
    }

    public static void putTaskViewList(List<Card> tasks, Bundle bundle) {
        bundle.putSerializable(CARD_LIST, (ArrayList<Card>) tasks);
    }

    public static List<Card> getTaskViewList(Intent intent) {
        return (List<Card>) intent.getSerializableExtra(CARD_LIST);
    }

    public static List<Card> getTaskViewList(Bundle bundle) {
        return (List<Card>) bundle.getSerializable(CARD_LIST);
    }

    public static List<Card> getTasks(Intent intent) {
        return (List<Card>) intent.getSerializableExtra(CARD_LIST);
    }

    public static List<Card> getTasks(Bundle bundle) {
        return (List<Card>) bundle.getSerializable(CARD_LIST);
    }

    public static List<Tag> getTags(Intent intent) {
        return (List<Tag>) intent.getSerializableExtra(TAG_LIST);
    }

    public static List<Tag> getTags(Bundle bundle) {
        return (List<Tag>) bundle.getSerializable(TAG_LIST);
    }

    public static boolean hasTaskId(Intent intent) {
        return intent.hasExtra(CARD_ID);
    }

    public static void putCardMetrics(CardMetrics cardMetrics, Intent intent) {
        intent.putExtra(CARD_METRICS, cardMetrics);
    }

    public static void putCardMetrics(CardMetrics cardMetrics, Bundle bundle) {
        bundle.putSerializable(CARD_METRICS, cardMetrics);
    }

    public static CardMetrics getCardMetrics(Intent intent) {
        return (CardMetrics) intent.getSerializableExtra(CARD_METRICS);
    }

    public static CardMetrics getCardMetrics(Bundle bundle) {
        return (CardMetrics) bundle.getSerializable(CARD_METRICS);
    }

    public static void putCardPlayList(List<CardPlay> cards, Intent intent) {
        intent.putExtra(CARD_PLAY_LIST, (ArrayList<CardPlay>) cards);
    }

    public static void putCardPlayList(List<CardPlay> cards, Bundle bundle) {
        bundle.putSerializable(CARD_PLAY_LIST, (ArrayList<CardPlay>) cards);
    }

    public static List<CardPlay> getCardPlayList(Intent intent) {
        return (List<CardPlay>) intent.getSerializableExtra(CARD_PLAY_LIST);
    }

    public static List<CardPlay> getCardPlayList(Bundle bundle) {
        return (List<CardPlay>) bundle.getSerializable(CARD_PLAY_LIST);
    }

}
