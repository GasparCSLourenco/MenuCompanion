package ca.on.conestogac.gl.gr03_groupproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVAdapterMenu extends RecyclerView.Adapter<RVAdapterMenu.ViewHolder>{
    private ArrayList<Dish> dishList;

    private Context ctx;

    private DBHandler dbHandler;

    private ArrayList<ViewHolder> holders;

    public RVAdapterMenu(Context context, ArrayList<Dish> dishList){
        this.ctx = context;
        this.dishList = dishList;
        this.holders = new ArrayList<>();
    }

    @NonNull
    @Override
    public RVAdapterMenu.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_menu_manager,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAdapterMenu.ViewHolder holder, int position) {

        Dish dish = dishList.get(position);
        holder.name.setText(dish.getName());
        holder.desc.setText(dish.getDescription());
        holder.image.setImageResource(dish.getImgPath());
        holder.price.setText(dish.getPrice().toString());
        holder.dish = dish;
        holder.add.setChecked(dish.getAvailable());
        holders.add(holder);


    }

    @Override
    public int getItemCount() {
        return this.dishList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView name, desc;
        private EditText price;
        private CheckBox add, highlight;

        Dish dish;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.RVimageViewMenu);
            name =  itemView.findViewById(R.id.RVtextViewNameMenu);
            desc = itemView.findViewById(R.id.RVtextViewDescMenu);
            price = itemView.findViewById(R.id.editTextEditPrice);
            add = itemView.findViewById(R.id.checkBoxAdd);

            price.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    if(!hasFocus){
                        try{
                            double newPrice = Double.parseDouble(price.getText().toString());
                            dish.setPrice(newPrice);
                            String msg = dish.getName() + " price changed to: " + String.valueOf(newPrice);
                            Toast.makeText(ctx.getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

                        }
                        catch (Exception ex){
                            Toast.makeText(ctx.getApplicationContext(),"Enter a valid price for this dish",Toast.LENGTH_SHORT).show();
                            price.setText(String.valueOf(dish.getPrice()));
                        }
                    }
                }
            });

            add.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    dish.setAvailable(add.isChecked());
                }
            });
        }


    }
    public ArrayList<Dish> getDishOnRV(){
        ArrayList<Dish> dishesOnRv = new ArrayList<>();

        for (ViewHolder h: holders) {
            dishesOnRv.add(h.dish);
        }
        return dishesOnRv;
    }
}
