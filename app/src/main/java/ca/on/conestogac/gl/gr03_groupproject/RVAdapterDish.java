package ca.on.conestogac.gl.gr03_groupproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class RVAdapterDish extends RecyclerView.Adapter<RVAdapterDish.ViewHolder> implements Serializable {


    private ArrayList<Dish> dishList;
    private ArrayList<Dish> dishToCart;

    private Context ctx;

    //Constructor for Adapter
    //First list is of seeded dishes that will be available on menu
    //Second list is items put in cart by customer
    public RVAdapterDish(Context context, ArrayList<Dish> list,ArrayList<Dish> cart){
        this.ctx = context;
        this.dishList = list;
        if(cart != null){
            this.dishToCart = cart;
        }
        else{
            this.dishToCart = new ArrayList<>();
        }
    }


    @NonNull
    @Override
    public RVAdapterDish.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_menu_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapterDish.ViewHolder holder, int position) {
        Dish dish = dishList.get(position);
        holder.name.setText(dish.Name);
        holder.description.setText(dish.Description);
        holder.price.setText("$ " + String.valueOf(dish.Price));
        holder.img.setImageResource(dish.ImagePath);
        holder.dish = dish;

    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name,description,price,quantity;
        private ImageView img;
        Button btnAdd;
        Dish dish;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.RVtextViewName);
            description = itemView.findViewById(R.id.RVtextViewDesc);
            price = itemView.findViewById(R.id.RVtextViewPrice);
            quantity = itemView.findViewById(R.id.editTextNumberQt);
            img = itemView.findViewById(R.id.RVimageView);
            btnAdd = itemView.findViewById(R.id.buttonAdd);

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try{
                        for(int i = 0;i<Integer.parseInt(quantity.getText().toString());i++)
                            dishToCart.add(dish);
                    }
                    catch (Exception ex){

                    }
                    Toast.makeText(ctx,"Items in cart: " + String.valueOf(ReturnCartSize()),Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public ArrayList<Dish> ReturnCart(){
        return dishToCart;
    }
    public int ReturnCartSize(){
        return dishToCart.size();
    }

}
