package org.cnodejs.android.md.storage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import org.cnodejs.android.md.model.entity.LoginInfo;
import org.cnodejs.android.md.model.entity.TopicSimple;
import org.cnodejs.android.md.model.entity.User;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public final class LoginShared {

    private LoginShared() {}

    private static final String TAG = LoginShared.class.getSimpleName();

    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_ID = "id";
    private static final String KEY_LOGIN_NAME = "login_name";
    private static final String KEY_AVATAR_URL = "avatar_url";
    private static final String KEY_GITHUB_USERNAME = "github_username";
    private static final String KEY_CREATE_AT = "create_at";
    private static final String KEY_SCORE = "score";
    private static final String KEY_COLLECT_TOPIC_ID_LIST = "collect_topic_id_list";

    public static void login(Context context, String accessToken, @NonNull LoginInfo loginInfo) {
        SharedWrapper.with(context, TAG).setString(KEY_ACCESS_TOKEN, accessToken);
        SharedWrapper.with(context, TAG).setString(KEY_ID, loginInfo.getId());
        SharedWrapper.with(context, TAG).setString(KEY_LOGIN_NAME, loginInfo.getLoginName());
        SharedWrapper.with(context, TAG).setString(KEY_AVATAR_URL, loginInfo.getAvatarUrl());
    }

    public static void update(Context context, @NonNull User user) {
        SharedWrapper.with(context, TAG).setString(KEY_LOGIN_NAME, user.getLoginName());
        SharedWrapper.with(context, TAG).setString(KEY_AVATAR_URL, user.getAvatarUrl());
        SharedWrapper.with(context, TAG).setString(KEY_GITHUB_USERNAME, user.getGithubUsername());
        SharedWrapper.with(context, TAG).setString(KEY_CREATE_AT, user.getCreateAt().toString());
        SharedWrapper.with(context, TAG).setInt(KEY_SCORE, user.getScore());

        //保存收藏的话题
        List<String> collectTopicIdList = new ArrayList<>();
        for (TopicSimple topic : user.getCollectTopics()) {
            collectTopicIdList.add(topic.getId());
        }
        setCollectTopicIdList(context, collectTopicIdList);
    }

    public static void logout(Context context) {
        SharedWrapper.with(context, TAG).clear();
    }

    public static String getAccessToken(Context context) {
        return SharedWrapper.with(context, TAG).getString(KEY_ACCESS_TOKEN, "");
    }

    public static String getId(Context context) {
        return SharedWrapper.with(context, TAG).getString(KEY_ID, null);
    }

    public static String getLoginName(Context context) {
        return SharedWrapper.with(context, TAG).getString(KEY_LOGIN_NAME, null);
    }

    public static String getAvatarUrl(Context context) {
        return SharedWrapper.with(context, TAG).getString(KEY_AVATAR_URL, null);
    }

    public static String getGithubUsername(Context context) {
        return SharedWrapper.with(context, TAG).getString(KEY_GITHUB_USERNAME, null);
    }

    public static DateTime getCreateAt(Context context) {
        String time = SharedWrapper.with(context, TAG).getString(KEY_CREATE_AT, null);
        if (TextUtils.isEmpty(time)) {
            return null;
        } else {
            return new DateTime(time);
        }
    }

    public static int getScore(Context context) {
        return SharedWrapper.with(context, TAG).getInt(KEY_SCORE, 0);
    }

    public static List<String> getCollectTopicIdList(Context context) {
        List<String> collectTopicIdList = SharedWrapper.with(context, TAG).getObject(KEY_COLLECT_TOPIC_ID_LIST, new TypeToken<List<String>>() {}.getType());
        return collectTopicIdList == null ? new ArrayList<String>() : collectTopicIdList;
    }

    public static void setCollectTopicIdList(Context context, List<String> collectTopicIdList) {
        SharedWrapper.with(context, TAG).setObject(KEY_COLLECT_TOPIC_ID_LIST, collectTopicIdList);
    }

}
