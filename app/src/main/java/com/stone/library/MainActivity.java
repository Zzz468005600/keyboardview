package com.stone.library;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.stone.keyboardview.model.Key;
import com.stone.keyboardview.widget.KeyboardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        KeyboardView kv = findViewById(R.id.keyboard);
        kv.setOnKeyClickListener(new KeyboardView.OnKeyClickListener() {
            @Override
            public void onKeyClicked(String key, Key.Action action) {
                Toast.makeText(MainActivity.this, key, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
