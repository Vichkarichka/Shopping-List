package is.tskoli.alexander.shoppinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    ListView list;
    TextView itemStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShoppingHelper.add(new ShoppingItem("Angel Eyes", 2));

        ShoppingHelper.add(new ShoppingItem("Eggs", 10));

        //get the actual list
        list = (ListView) findViewById(R.id.ShoppingList);

        itemStatus = (TextView) findViewById(R.id.itemStatus);

        //if the shopping list is not empty, we a status
        if(!ShoppingHelper.isEmpty()){
            itemStatus.setText(ShoppingHelper.getStatus());
        }



        //create the adapter for the list
        final ArrayAdapter<ShoppingItem> arrayAdapter = new ShoppingAdapter();

        //connect the adapter to the list
        list.setAdapter(arrayAdapter);

        Button addBtn = (Button) findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText item = (EditText) findViewById(R.id.addText);
                String text = item.getText().toString();


                if(text.length() > 0){
                    ShoppingItem newItem = new ShoppingItem(text, 1);
                    ShoppingHelper.add(newItem);
                    item.setText("");
                }

                list.invalidateViews();


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ShoppingAdapter extends ArrayAdapter<ShoppingItem> {

        public ShoppingAdapter(){
            super(MainActivity.this, R.layout.shopping_item, ShoppingHelper.getAll());
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent){

            View shoppingView = convertView;

            //make sure we have a view
            if (shoppingView == null){
                shoppingView = getLayoutInflater().inflate(R.layout.shopping_item, parent, false);
            }

            final ShoppingItem item = ShoppingHelper.find(position);
            TextView itemName       = (TextView) shoppingView.findViewById(R.id.itemName);
            final TextView itemStatus     = (TextView) findViewById(R.id.itemStatus);
            Button addBtn           = (Button) shoppingView.findViewById(R.id.itemAdd);
            Button removeBtn        = (Button) shoppingView.findViewById(R.id.itemRemove);
            Button deleteBtn        = (Button) shoppingView.findViewById(R.id.itemDelete);
            CheckBox checkBox       = (CheckBox) shoppingView.findViewById(R.id.itemDone);

            itemName.setText(item.getText());

            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //add 1 to the item amount
                    item.addAmount();
                    //update the actual list object item
                    ShoppingHelper.update(position, item);
                    //update the list
                    list.invalidateViews();
                }
            });

            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //if the item amount is over 1, so we
                    if(item.amount > 1){
                        //-1 the item and uncheck the done
                        item.removeAmount();
                        //update the list object
                        ShoppingHelper.update(position, item);
                        //update the list
                        list.invalidateViews();
                    }

                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //remove the list object
                    ShoppingHelper.remove(position);
                    //update the status text
                    itemStatus.setText(ShoppingHelper.getStatus());
                    //update the listview
                    list.invalidateViews();
                }
            });

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get the checkbox object we clicked
                    CheckBox box = (CheckBox) v;

                    //if the box is checked we set the item to done
                    if(box.isChecked()){
                        item.setDone();
                    }
                    else{
                        item.removeDone();
                    }

                    ShoppingHelper.update(position, item);

                    //update the status text
                    itemStatus.setText(ShoppingHelper.getStatus());
                }
            });


            return shoppingView;

        }

    }
}
