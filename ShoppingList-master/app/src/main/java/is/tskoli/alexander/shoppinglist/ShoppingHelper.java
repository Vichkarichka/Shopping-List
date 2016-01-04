package is.shoppinglist;

import java.util.ArrayList;


public class ShoppingHelper {

    private static ArrayList<ShoppingItem> items = new ArrayList<ShoppingItem>();

    public static ShoppingItem find(int idx){
        return items.get(idx);
    }

    public static ArrayList<ShoppingItem> getAll(){
        return items;
    }

    public static void add(ShoppingItem item){
        items.add(item);
    }

    public static void update(int idx, ShoppingItem item){
        items.set(idx, item);
    }

    public static void remove(int idx){
        items.remove(idx);
    }

    public static boolean isEmpty(){
        return items.size() == 0;
    }

    public static String getStatus(){

        //get the total amount of items in the list
        int total = items.size();

        int done = getDone();

        return done + " / " + total;
    }

    //get the total amount of checked items
    private static int getDone(){
        int done = 0;

        //check every item if its checked
        for(ShoppingItem item : items){
            if(item.done){
                done +=1;
            }
        }

        return done;
    }


}
