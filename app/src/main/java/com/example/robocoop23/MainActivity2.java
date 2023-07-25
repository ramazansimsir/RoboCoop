package com.example.robocoop23;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.ChatBuilder;
import com.aldebaran.qi.sdk.builder.QiChatbotBuilder;
import com.aldebaran.qi.sdk.builder.TopicBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.object.conversation.AutonomousReactionImportance;
import com.aldebaran.qi.sdk.object.conversation.AutonomousReactionValidity;
import com.aldebaran.qi.sdk.object.conversation.Bookmark;
import com.aldebaran.qi.sdk.object.conversation.BookmarkStatus;
import com.aldebaran.qi.sdk.object.conversation.Chat;
import com.aldebaran.qi.sdk.object.conversation.QiChatbot;
import com.aldebaran.qi.sdk.object.conversation.Topic;
import com.aldebaran.qi.sdk.object.locale.Language;
import com.aldebaran.qi.sdk.object.locale.Locale;
import com.aldebaran.qi.sdk.object.locale.Region;
import com.example.robocoop23.databinding.ActivityMain2Binding;

import java.util.Map;

public class MainActivity2 extends RobotActivity implements RobotLifecycleCallbacks {

    ActivityMain2Binding design;
    QiChatbot qiChatbot;

    Chat chat;
    BookmarkStatus bookmarkStatus;
    BookmarkStatus bookmarkStatus2;
    Bookmark first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        design=ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(design.getRoot());
        QiSDK.register(this, this);
    }

    @Override
    protected void onDestroy() {
        // Unregister the RobotLifecycleCallbacks for this Activity.
        QiSDK.unregister(this, this);
        super.onDestroy();
    }

    @Override
    public void onRobotFocusGained(QiContext qiContext) {
        // The robot focus is gained.

        // Create a topic.
        Topic topic = TopicBuilder.with(qiContext)
                .withResource(R.raw.ozet)
                .build();

            Locale locale = new Locale(Language.TURKISH, Region.TURKEY);
// Create a new QiChatbot.
        qiChatbot = QiChatbotBuilder.with(qiContext)
                .withTopic(topic)
                .withLocale(locale)
                .build();

        // Create a new Chat action.
        chat = ChatBuilder.with(qiContext)
                .withChatbot(qiChatbot)
                .withLocale(locale)
                .build();
        // Get the bookmarks from the topic.
        Map<String, Bookmark> bookmarks = topic.getBookmarks();
        // Get the proposal bookmark
        Bookmark first=bookmarks.get("sayProposal");
        bookmarkStatus=qiChatbot.bookmarkStatus(first);
        Bookmark finish=bookmarks.get("finish");
        bookmarkStatus2=qiChatbot.bookmarkStatus(finish);

        chat.addOnStartedListener(this::sayProposal);

        chat.async().run();


    }

    @Override
    public void onRobotFocusLost() {
        // The robot focus is lost.
        if(bookmarkStatus!=null){
            bookmarkStatus.removeAllOnReachedListeners();
        }
        if(bookmarkStatus2!=null){
            bookmarkStatus2.removeAllOnReachedListeners();
        }
        if(chat!=null){
            chat.removeAllOnStartedListeners();
        }
    }

    @Override
    public void onRobotFocusRefused(String reason) {
        // The robot focus is refused.
    }
    private void sayProposal() {
        qiChatbot.goToBookmark(first, AutonomousReactionImportance.HIGH, AutonomousReactionValidity.IMMEDIATE);
    }
}