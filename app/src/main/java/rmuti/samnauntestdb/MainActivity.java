package rmuti.samnauntestdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
     int sum = 5;
     String _name = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    //กดปุ่มแสดงรายชื่อ
    public void doNextList(View v){
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        intent.putExtra("Message",sum);
        intent.putExtra("Message2",_name);
        startActivity(intent);
    }

}
