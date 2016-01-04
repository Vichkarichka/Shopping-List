package is.shoppinglist;


public class ShoppingItem {

    protected String item;
    protected int amount;
    protected boolean done;


    public ShoppingItem(String _item, int _amount){
        this.item   = _item;
        this.amount = _amount;
        this.done   = false;
    }

    public String getText(){
        return this.amount + " " + this.item;
    }

    public void addAmount(){
        this.amount += 1;
    }

    public void removeAmount(){
        this.amount -= 1;
    }

    public void setDone(){
        this.done = true;
    }

    public void removeDone(){
        this.done = false;
    }

}
