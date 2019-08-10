package t0l1k.ttt;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Board board;
    private Button[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(MainActivity.class.getName(), "onCreate");
        load(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(MainActivity.class.getName(), "onSaveInstanceState");
        outState.putCharArray("board", board.getBoard());
        outState.putChar("turn", board.getTurn());
    }

    private void load(Bundle state) {
        if (state != null) {
            board = new Board(state.getCharArray("board"), state.getChar("turn"));
        } else {
            board = new Board();
        }
        setupButtons(board);
    }

    private void setupButtons(Board board) {
        buttons = new Button[board.getSize()];
        for (int i = 0; i < board.getSize(); i++) {
            String btnId = "button" + i;
            int resId = getResources().getIdentifier(btnId, "id", getPackageName());
            buttons[i] = findViewById(resId);
            buttons[i].setOnClickListener(new MyClickListener(i));
            if (board.isBegin()) {
                if (board.getBoard()[i] == ' ') {
                    buttons[i].setEnabled(true);
                    buttons[i].setText(" ");
                } else {
                    buttons[i].setEnabled(false);
                    buttons[i].setText("" + board.getBoard()[i]);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MainActivity.class.getName(), "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MainActivity.class.getName(), "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(MainActivity.class.getName(), "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.class.getName(), "onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(MainActivity.class.getName(), "onStop");
    }

    private void reset() {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(" ");
            buttons[i].setEnabled(true);
            board = new Board();
        }
    }

    private void move(int idx) {
        board = board.move(idx);
    }

    private class MyClickListener implements View.OnClickListener {
        int idx;

        public MyClickListener(int idx) {
            this.idx = idx;
        }

        @Override
        public void onClick(View view) {
            if (!board.gameEnd()) {
                Button button = buttons[idx];
                if (button.isEnabled()) {
                    button.setEnabled(false);
                    button.setText("" + board.getTurn());
                    move(idx);
                    if (!board.gameEnd()) {
                        int best = board.bestMove();
                        buttons[best].setText("" + board.getTurn());
                        move(best);
                    }
                    if (board.gameEnd()) {
                        String msg;
                        if (board.win('X')) {
                            msg = "Win You";
                        } else if (board.win('O')) {
                            msg = "Win Computer";
                        } else {
                            msg = "Draw";
                        }
                        showMsg(msg);
                    }
                }
            }
        }

        private void showMsg(String msg) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Game Ended")
                    .setMessage(msg)
                    .setCancelable(false)
                    .setNegativeButton("Start New Game",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    reset();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
