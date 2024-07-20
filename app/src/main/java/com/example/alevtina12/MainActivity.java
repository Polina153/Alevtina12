package com.example.alevtina12;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPref = null;
    public static final String KEY = "key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        sharedPref = getPreferences(MODE_PRIVATE);

        final ArrayList<UserNote> userNotes = new ArrayList<>();
        final NotesAdapter notesAdapter = new NotesAdapter(userNotes);

       /* for (int i = 0; i < 3; i++) {
            String index = String.valueOf(i);
            userNotes.add(new UserNote(index, new Date(), index));
        }*/
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(notesAdapter);

        String savedNotes = sharedPref.getString(KEY, null);
        if (savedNotes == null || savedNotes.isEmpty()) {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        } else {
            try {
                Type type = new TypeToken<ArrayList<UserNote>>() {
                }.getType();
                notesAdapter.setNewData(new GsonBuilder().create().fromJson(savedNotes, type));
            } catch (JsonSyntaxException e) {
                Toast.makeText(this, "Ошибка трансформации", Toast.LENGTH_SHORT).show();
            }
        }

        findViewById(R.id.fab).setOnClickListener(view -> {
            //final UserNote note = new UserNote("New note", new Date(), "New note");
            userNotes.add(new UserNote("New note", new Date(), "New note"));
            notesAdapter.setNewData(userNotes);
            String jsonNote = new GsonBuilder().create().toJson(userNotes);
            sharedPref.edit().putString(KEY, jsonNote).apply();
        });
               /* String jsonNote = new GsonBuilder().create().toJson(note);
        sharedPref.edit().putString(KEY, jsonNote).apply();
*/
    }
}
