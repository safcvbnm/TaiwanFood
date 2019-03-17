package com.example.taiwanfood;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button show;
    private String[] Menu = {"臭豆腐($85)","米血糕($35)","蚵仔煎($50)","棺材板($70)","珍珠奶茶($30)","檸檬紅茶($40)","烏龍茶($40)","冬瓜茶($25)"};
    private int[] Price = {85,35,50,70,30,40,40,25};
    private int[] Picture = {R.drawable.food1,R.drawable.food2,R.drawable.food3,R.drawable.food4,
            R.drawable.drink1,R.drawable.drink2,R.drawable.drink3,R.drawable.drink4};
    private ListView list;
    private ImageView imageItem1;
    private GridView gridView;
    private String showItem="";
    private int cost,count;

    //ListView Adapter
    class listAdapter extends BaseAdapter{
        Boolean chk;

        @Override
        public int getCount() {
            return Menu.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            Holder holder;
            if(v == null){
                v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_layout, null);
                holder = new Holder();
                holder.image = (ImageView)v.findViewById(R.id.imageView);
                holder.checkBox = (CheckBox)v.findViewById(R.id.checkBox);
                holder.text1 = (TextView)v.findViewById(R.id.item);
                holder.text2 = (TextView)v.findViewById(R.id.price);
                v.setTag(holder);
            }else{
                holder = (Holder) v.getTag();
            }

            holder.image.setImageResource(Picture[position]);
            holder.text1.setText("\t"+Menu[position]);
            holder.text2.setText("\t"+"$"+Integer.toString(Price[position]));
            holder.checkBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        chk = true;
                    }else{
                        chk = false;
                    }
                }
            });

            holder.checkBox.setChecked(chk);
            return v;
        }

        class Holder{
            ImageView image;
            CheckBox checkBox;
            TextView text1;
            TextView text2;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView)findViewById(R.id.menu);
        show = (Button)findViewById(R.id.show);
        gridView = (GridView)findViewById(R.id.foodShow);
        imageItem1 = (ImageView)findViewById(R.id.imageView2);

        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imageItem1.setImageResource(Picture[position]);
            }
        });

        ArrayAdapter<String> listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_multiple_choice,Menu);
        list.setAdapter(listAdapter);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        count = list.getCount();

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResult();
                CreateDialog();
            }
        });
    }

    public void showResult(){
        for(int i=0; i<count;i++){
            if(list.isItemChecked(i)){
                showItem += Menu[i]+" ";
            }

        }

        for(int j=0; j<count;j++){
            if(list.isItemChecked(j)){
                cost += Price[j];
            }

        }
    }

    public void CreateDialog(){
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("結算")
                .setMessage("品項 :"+showItem+"\n"+"總價 : "+cost)
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri phone = Uri.parse("tel:0227156888");
                        Intent Phone = new Intent(Intent.ACTION_DIAL, phone);
                        startActivity(Phone);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener()  {
                    @Override
                    public void onClick(DialogInterface d, int which) {
                        d.dismiss();
                        showItem = "";
                        cost = 0;
                    }
                })
                .show();
    }
}
