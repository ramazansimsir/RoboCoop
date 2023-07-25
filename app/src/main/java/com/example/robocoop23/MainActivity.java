package com.example.robocoop23;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;

import com.aldebaran.qi.sdk.QiContext;
import com.aldebaran.qi.sdk.QiSDK;
import com.aldebaran.qi.sdk.RobotLifecycleCallbacks;
import com.aldebaran.qi.sdk.builder.AnimateBuilder;
import com.aldebaran.qi.sdk.builder.AnimationBuilder;
import com.aldebaran.qi.sdk.builder.ChatBuilder;
import com.aldebaran.qi.sdk.builder.QiChatbotBuilder;
import com.aldebaran.qi.sdk.builder.TopicBuilder;
import com.aldebaran.qi.sdk.design.activity.RobotActivity;
import com.aldebaran.qi.sdk.object.actuation.Animate;
import com.aldebaran.qi.sdk.object.actuation.Animation;
import com.aldebaran.qi.sdk.object.conversation.AutonomousReactionImportance;
import com.aldebaran.qi.sdk.object.conversation.AutonomousReactionValidity;
import com.aldebaran.qi.sdk.object.conversation.Bookmark;
import com.aldebaran.qi.sdk.object.conversation.BookmarkStatus;
import com.aldebaran.qi.sdk.object.conversation.Chat;
import com.aldebaran.qi.sdk.object.conversation.QiChatExecutor;
import com.aldebaran.qi.sdk.object.conversation.QiChatbot;
import com.aldebaran.qi.sdk.object.conversation.Topic;
import com.aldebaran.qi.sdk.object.locale.Language;
import com.aldebaran.qi.sdk.object.locale.Locale;
import com.aldebaran.qi.sdk.object.locale.Region;
import com.example.robocoop23.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends RobotActivity implements RobotLifecycleCallbacks {

    ActivityMainBinding design;
    private QiChatbot qiChatbot;
    private Chat chat;

    QiContext qiContext;

    private BookmarkStatus firstStatus;
    private BookmarkStatus secondStatus;
    private BookmarkStatus thirdStatus;
    private BookmarkStatus fourthStatus;

    private BookmarkStatus finishFirstStatus;
    private Bookmark first;
    private Bookmark second;
    private Bookmark third;
    private Bookmark fourth;
    private Bookmark finishfirst;
    BookmarkStatus finish2s;
    BookmarkStatus finish3s;
    BookmarkStatus finish4s;




    // Store the proposal bookmark.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register the RobotLifecycleCallbacks to this Activity.
        design=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(design.getRoot());
        QiSDK.register(this, this);
        design.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent =new Intent(MainActivity.this, MainActivity2.class);
//                startActivity(intent);


                qiChatbot.async().goToBookmark(first, AutonomousReactionImportance.HIGH, AutonomousReactionValidity.IMMEDIATE);

                     }
        });

        design.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qiChatbot.async().goToBookmark(second,AutonomousReactionImportance.HIGH,AutonomousReactionValidity.IMMEDIATE);
            }
        });
        design.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qiChatbot.async().goToBookmark(third,AutonomousReactionImportance.HIGH,AutonomousReactionValidity.IMMEDIATE);

            }
        });
        design.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qiChatbot.async().goToBookmark(fourth,AutonomousReactionImportance.HIGH,AutonomousReactionValidity.IMMEDIATE);
            }
        });


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
        // Create a topi
        // create locale fort turkish language
        this.qiContext=qiContext;
        Locale locale = new Locale(Language.TURKISH, Region.TURKEY);
        Topic topic = TopicBuilder.with(qiContext)
                .withResource(R.raw.summary)
                .build();

// Create a QiChatbot
                qiChatbot = QiChatbotBuilder.with(qiContext)
                .withTopic(topic)
                .withLocale(locale)
                .build();
   /*     // Create the executor
        QiChatExecutor myExecutor = new MyQiChatExecutor(qiContext);

        // Set the executor to the qiChatbot
        Map<String, QiChatExecutor> executors = new HashMap<>();
        executors.put("myExecutor", myExecutor);
        qiChatbot.setExecutors(executors);
*/
        // Create a new Chat action.
                chat = ChatBuilder.with(qiContext)
                .withChatbot(qiChatbot)
                .withLocale(locale)
                .build();

        Map<String, Bookmark> bookmarks = topic.getBookmarks();
        first=bookmarks.get("first");
        second=bookmarks.get("second");
        third=bookmarks.get("third");
        fourth=bookmarks.get("fourth");
        finishfirst=bookmarks.get("firstEnd");
        Bookmark finish2=bookmarks.get("secondFinish");
        Bookmark finish3=bookmarks.get("thirdFinish");
        Bookmark finish4=bookmarks.get("fourthFinish");

        firstStatus=qiChatbot.bookmarkStatus(first);
        secondStatus=qiChatbot.bookmarkStatus(second);
        thirdStatus=qiChatbot.bookmarkStatus(third);
        fourthStatus=qiChatbot.bookmarkStatus(fourth);
        finishFirstStatus=qiChatbot.bookmarkStatus(finishfirst);
        finish2s=qiChatbot.bookmarkStatus(finish2);
        finish3s=qiChatbot.bookmarkStatus(finish3);
        finish4s=qiChatbot.bookmarkStatus(finish4);

        firstStatus.addOnReachedListener(()->{

            runOnUiThread(()->{
                design.imageView6.setImageResource(R.drawable.projeozer);
                design.layout1.setVisibility(View.GONE);
                design.layout2.setVisibility(View.VISIBLE);

            });

        });
        finishFirstStatus.addOnReachedListener(()->{
            runOnUiThread(()->{
                design.layout2.setVisibility(View.INVISIBLE);
                design.layout1.setVisibility(View.VISIBLE);
            });
        });

        secondStatus.addOnReachedListener(()->{
            runOnUiThread(()->{
                design.imageView6.setImageResource(R.drawable.projehedef);
                design.layout1.setVisibility(View.INVISIBLE);
                design.layout2.setVisibility(View.VISIBLE);

            });
        });
        finish2s.addOnReachedListener(()->{
            runOnUiThread(()->{

                design.layout2.setVisibility(View.GONE);
                design.layout1.setVisibility(View.VISIBLE);
            });

        });


        thirdStatus.addOnReachedListener(()->{
            runOnUiThread(()->{

                design.layout1.setVisibility(View.GONE);
                design.layout2.setVisibility(View.VISIBLE);
                design.imageView6.setImageResource(R.drawable.projeaktivite);

            });
        });

        finish3s.addOnReachedListener(()->{

           runOnUiThread(()->{

               design.layout2.setVisibility(View.GONE);
               design.layout1.setVisibility(View.VISIBLE);

           });
        });

        fourthStatus.addOnReachedListener(()->{
            runOnUiThread(()->{
           design.layout1.setVisibility(View.GONE);
           design.layout2.setVisibility(View.VISIBLE);
           design.imageView6.setImageResource(R.drawable.projesonuc);
            });
        });

        finish4s.addOnReachedListener(()->{
            runOnUiThread(()->{
                design.layout2.setVisibility(View.GONE);
                design.layout1.setVisibility(View.VISIBLE);
            });
        });

        chat.async().run();

    }

    @Override
    public void onRobotFocusLost() {
        // The robot focus is lost.
        if(firstStatus!=null){
            firstStatus.removeAllOnReachedListeners();
        }
        if(secondStatus!=null){
            secondStatus.removeAllOnReachedListeners();
        }
        if(thirdStatus!=null){
            thirdStatus.removeAllOnReachedListeners();
        }
        if(fourthStatus!=null){
            fourthStatus.removeAllOnReachedListeners();
        }
        if(finishFirstStatus!=null){
            finishFirstStatus.removeAllOnReachedListeners();
        }
        // Remove on started listeners from the Chat action.
        if (chat != null) {
            chat.removeAllOnStartedListeners();
        }

        if(finish2s!=null){
            finish2s.removeAllOnReachedListeners();
        }
        if(finish3s!=null){
            finish3s.removeAllOnReachedListeners();
        }
        if(finish4s!=null){
            finish4s.removeAllOnReachedListeners();
        }

    }
    private void sayProposal() {
        qiChatbot.goToBookmark(first, AutonomousReactionImportance.HIGH, AutonomousReactionValidity.IMMEDIATE);
    }
    @Override
    public void onRobotFocusRefused(String reason) {
        // The robot focus is refused.
    }

  /*  private class MyQiChatExecutor implements QiChatExecutor {
        public MyQiChatExecutor(QiContext qiContext) {

        }

        @Override
        public Async async() {
            return null;
        }

        @Override
        public void runWith(List<String> params) {

        }

        @Override
        public void stop() {

        }
    }*/
}