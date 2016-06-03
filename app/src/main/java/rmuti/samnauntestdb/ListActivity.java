package rmuti.samnauntestdb;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ListActivity extends AppCompatActivity {
    //database
    private SQLiteDatabase db;
    private Cursor c;
    int id;
    private ArrayAdapter adapter;
    String sql;

    //data
    String _name,score;
    int sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_list);

        // Database
        db = this.openOrCreateDatabase("mydatabase",MODE_PRIVATE,null);
         sql = ""
                + " CREATE TABLE IF NOT EXISTS db_Test("
                + "   id INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + "   name VARCHAR," + " score VARCHAR" + " )";
        db.execSQL(sql);


        //รับข้อมูลมาจาก MainActivity
         sum  = getIntent().getExtras().getInt("Message");
        score = Integer.toString(sum);
        _name = getIntent().getExtras().getString("Message2");

        // เพิ่มข้อมูลลง database
         sql = "";
            sql = "INSERT INTO db_Test VALUES(null, ':name', ':score')";
            sql = sql.replace(":name",_name);
            sql = sql.replace(":score",score);
            db.execSQL(sql);

        //refresh listView
        bindData();
    }
    //-------------------------------------------------------------------------

    //refresh listView
    private void bindData(){
         sql = "";
         sql = "SELECT * FROM db_Test";
        c = db.rawQuery(sql, null);

        int item = android.R.layout.simple_list_item_1;
        ArrayList data = new ArrayList();

        while(c.moveToNext()){
            int index = c.getColumnIndex("name"); //ต้องการส่วน name ใน dababase
            data.add(c.getString(index));
        }

        adapter = new ArrayAdapter(this, item, data);

        ListView myList = (ListView) findViewById(R.id.myList); //เรียก listView
        myList.setAdapter(adapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int i, long l) {
                //เมื่อมีการคลิกรายชื่อใน listView
                itemClick(i);
            }
        });
    }
    //-------------------------------------------------------------------------

    //เมื่อมีการคลิกรายชื่อใน listView
    public void itemClick(int index){
        c.moveToPosition(index);
        id = c.getInt(c.getColumnIndex("id"));
        setContentView(R.layout.edit_list);   //ไปที่หน้า edit_list

        TextView name = (TextView)findViewById(R.id.name);
        TextView score = (TextView)findViewById(R.id.score);

        name.setText(c.getString(c.getColumnIndex("name")));
        score.setText(c.getString(c.getColumnIndex("score")));
    }
    //-------------------------------------------------------------------------


    //กดปุ่ม delete
    public void doDelete(View v){
        db.delete("db_Test", "id = " + id, null);
        setContentView(R.layout.show_list);
        bindData();
    }

    public void doHome(View v){
        //กดปุ่ม home
    }

}
