package br.com.sailboat.flashcards.helper;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import br.com.sailboat.flashcards.model.Card;
import br.com.sailboat.flashcards.model.Tag;

public class ExtrasHelper {

    private static final String CARD_ID = "CARD_ID";
    private static final String TAG_ID = "TAG_ID";
    private static final String CARD_LIST = "CARD_LIST";
    private static final String TAG_LIST = "TAG_LIST";

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

    public static void putProjects(List<Tag> projects, Intent intent) {
        intent.putExtra(TAG_LIST, (ArrayList<Tag>) projects);
    }

    public static void putProjects(List<Tag> projects, Bundle bundle) {
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
}
