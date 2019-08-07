package t0l1k.ttt;

import android.content.DialogInterface;
import android.os.Bundle;
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
        board = new Board();
        buttons = new Button[board.getSize()];
        for (int i = 0; i < board.getSize(); i++) {
            String btnId = "button" + i;
            int resId = getResources().getIdentifier(btnId, "id", getPackageName());
            buttons[i] = findViewById(resId);
            buttons[i].setOnClickListener(new MyClickListener(i));
        }
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
                            msg = "Win X";
                        } else if (board.win('O')) {
                            msg = "Win O";
                        } else {
                            msg = "Draw";
                        }
                        System.out.println(msg);
                        printMsg(msg);
                    }
                }
            }
        }

        private void printMsg(String msg) {
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
