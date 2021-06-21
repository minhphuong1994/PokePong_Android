package com.example.pokepong;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    // 1. The entire game is stored in the "game engine"
    GameEngine pongGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get size of the screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Initialize the GameEngine object
        // Pass it the screen size (height & width)
        pongGame = new GameEngine(this, size.x, size.y);

//        // Set the MainActivity's "view" to the GameEngine
//        setContentView(pongGame);

        // 1. Create a LinearLayout object (container to hold other objects)
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // 2. Create an ImageView, Button, Text view object
        Button btn = new Button(this);

        LayoutParams parameters =
                new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        btn.setLayoutParams(parameters);

        btn.setText("PUSH ME");

        Button b2 = new Button(this);
        b2.setText("PUSH ME #2");
        // button width = wrap_content, height= wrap_content
        b2.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t = Toast.makeText(getApplicationContext(), "HELLO WORLD!", Toast.LENGTH_LONG);
                t.show();
            }
        });


        // you can reuse your layout param variables
        TextView t = new TextView(this);
        t.setText("HERE IS SOME TEXT");
        t.setTextSize(50);
        t.setBackgroundColor(Color.MAGENTA);
        t.setTextColor(Color.WHITE);
        t.setLayoutParams(parameters);   // text view with w = matchParent, h = wrapContent

        // add an image
        ImageView iv1 = new ImageView(this);
        iv1.setImageResource(R.drawable.snorlax);
        iv1.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        ImageView iv2 = new ImageView(this);
        iv2.setImageResource(R.drawable.eevee);
        iv2.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        // Layout = LinearLayout
        // The order that you display things on the screen = the order you add them
        layout.addView(t);
        layout.addView(iv1);
        layout.addView(btn);
        layout.addView(b2);
        layout.addView(iv2);
        layout.addView(pongGame);

        setContentView(layout);


    }

    // onPause is called when the user switches to a different Android app
    @Override
    protected void onPause() {
        super.onPause();
        pongGame.pauseGame();
    }

    // onResume is called when the user comes back to your app
    @Override
    protected void onResume() {
        super.onResume();
        pongGame.startGame();
    }
}