package ca.on.conestogac.gl.gr03_groupproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "myDB";
    private static final int DB_VERSION = 8;

    // Orders Table
    private static final String TABLE_NAME_ORDER = "Orders";
    private static final String ID_COL_ORDER = "Id";
    private static final String STATUS_COL_ORDER = "Status";
    private static final String DATE_COL_ORDER = "Date";
    private static final String USER_ID_COL_ORDER = "UserId";
    private static final String TABLENUMBER_ORDER = "Table_Number";



    // Dish Table
    private static final String TABLE_NAME_DISHES = "Dishes";
    private static final String ID_COL_DISHES = "Id";
    private static final String NAME_COL_DISHES = "Name";
    private static final String DESC_COL_DISHES = "Description";
    private static final String PRICE_COL_DISHES = "Price";
    private static final String IMGPATH_COL_DISHES = "Img_Path";
    private static final String IS_AVAILABLE = "Available";


    // Order-Dish Link
    private static final String TABLE_NAME_LINK = "OrderDish";
    private static final String ID_COL_LINK = "Id";
    private static final String DISH_COL_LINK = "DishId";
    private static final String ORDER_COL_LINK = "OrderId";

    // Users Table
    private static final String TABLE_NAME_USERS = "Users";
    private static final String ID_COL_USERS = "Id";
    private static final String NAME_COL_USERS = "Name";
    private static final String EMAIL_COL_USERS = "Email";
    private static final String PASSWORD_COL_USERS = "Password";

    //Administrator login details
    private static final String ADMIN_USER = "Admin";
    private static final String ADMIN_PASSWORD = "Admin";



    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateDishesTable());
        db.execSQL(CreateUserTable());
        db.execSQL(CreateOrdersTable());
        db.execSQL(CreateOrderDishLinkTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LINK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ORDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DISHES);

        onCreate(db);
    }

    //Query to create order-dish link table
    public String CreateOrderDishLinkTable() {
        return "CREATE TABLE " + TABLE_NAME_LINK + " ("
                + ID_COL_LINK + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ORDER_COL_LINK + " INTEGER, "
                + DISH_COL_LINK + " INTEGER, "
                + "FOREIGN KEY(" + ORDER_COL_LINK + ") REFERENCES " + TABLE_NAME_ORDER + "(" + ID_COL_ORDER + "), "
                + "FOREIGN KEY(" + DISH_COL_LINK + ") REFERENCES " + TABLE_NAME_DISHES + "(" + ID_COL_DISHES + ")"
                + ")";
    }

    //Query to create user table
    public String CreateUserTable() {
        return "CREATE TABLE " + TABLE_NAME_USERS + " ("
                + ID_COL_USERS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL_USERS + " TEXT, "
                + EMAIL_COL_USERS + " TEXT, "
                + PASSWORD_COL_USERS + " TEXT)";
    }

    //Query to create dishes table
    public String CreateDishesTable() {
        return "CREATE TABLE " + TABLE_NAME_DISHES + " ("
                + ID_COL_DISHES + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL_DISHES + " TEXT, "
                + DESC_COL_DISHES + " TEXT, "
                + PRICE_COL_DISHES + " REAL,"
                + IMGPATH_COL_DISHES + " TEXT,"
                + IS_AVAILABLE + " INTEGER)";
    }

    //Query to create orders table
    public String CreateOrdersTable() {
        return "CREATE TABLE " + TABLE_NAME_ORDER + " ("
                + ID_COL_ORDER + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + STATUS_COL_ORDER + " TEXT, "
                + DATE_COL_ORDER + " TEXT, "
                + USER_ID_COL_ORDER + " INTEGER, "
                + TABLENUMBER_ORDER + " INTEGER, "
                + "FOREIGN KEY(" + USER_ID_COL_ORDER + ") REFERENCES " + TABLE_NAME_USERS + "(" + ID_COL_USERS + ")"
                + ")";
    }

    //Adding dish to the database
    public void AddDish(Dish dish){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL_DISHES,dish.getName());
        values.put(DESC_COL_DISHES,dish.getDescription());
        values.put(PRICE_COL_DISHES,dish.getPrice());
        values.put(IMGPATH_COL_DISHES,dish.getImgPath());
        values.put(IS_AVAILABLE,dish.getAvailable() == true ? 1:0);

        db.insert(TABLE_NAME_DISHES,null,values);
    }

    //Reusing the method above to add many dishes
    public void AddDish(ArrayList<Dish> ListOfDishes){
        for (Dish d:ListOfDishes) {
            AddDish(d);
        }
    }

    //Getting the list of all seeded dishes in the DB
    public ArrayList<Dish> ReadDishes(){
        ArrayList<Dish> dishList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursorDishes =db.rawQuery("SELECT * FROM " + TABLE_NAME_DISHES, null );

        if(cursorDishes.moveToFirst()){
            do{
                Dish dish = new Dish(cursorDishes.getString(1),cursorDishes.getString(2),
                        cursorDishes.getDouble(3),cursorDishes.getInt(4));
                dish.setAvailable(cursorDishes.getInt(5) == 1 ? true : false);
                dishList.add(dish);

            }while (cursorDishes.moveToNext());
        }
        cursorDishes.close();
        return dishList;
    }

    //Creates a dish object by retrieving it's info from db
    public Dish getDishById(int dishId){
        SQLiteDatabase db = getReadableDatabase();

        String args[] = {String.valueOf(dishId)};

        String query = "SELECT * FROM " + TABLE_NAME_DISHES + " WHERE "
                + ID_COL_DISHES + " =?";

        Cursor cursor = db.rawQuery(query,args);

        if(cursor.moveToFirst()){
            return new Dish(cursor.getString(1),cursor.getString(2),cursor.getDouble(3),cursor.getInt(4));
        }
        return null;
    }

    //Retrieves a list of dishes from an order passing the order id as param

    public ArrayList<Dish> ReadDishesFromOrder(int orderId){
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<Dish> dishesFromOrder = new ArrayList<>();

        String args[] = {String.valueOf(orderId)};

        String query = "SELECT * FROM " + TABLE_NAME_LINK + " WHERE "
                + ORDER_COL_LINK + " =?";

        Cursor cursor = db.rawQuery(query,args);

        if(cursor.moveToFirst()){
            do{
                Dish dish = getDishById(cursor.getInt(2));
                dishesFromOrder.add(dish);
            }while (cursor.moveToNext());
        }
        return dishesFromOrder;

    }

    //Getting the ID of the dish passing it's name as a parameter
    public int getDishId(Dish d){
        SQLiteDatabase db = getReadableDatabase();
        String dishName[] = {d.getName()};
        String query = "SELECT " + ID_COL_DISHES + " FROM " + TABLE_NAME_DISHES + " WHERE " + NAME_COL_DISHES + " = ?";

        Cursor cursor = db.rawQuery(query,dishName);
        if(cursor.moveToFirst()){
            int columnIndex = cursor.getColumnIndex(ID_COL_DISHES);
            if(columnIndex>=0){
                return cursor.getInt(columnIndex);
            }
        }
        cursor.close();
        return -1;
    }

    //Updated a given dish on db
    public void UpdateDishes(ArrayList<Dish> dishesToUpdate) {
        SQLiteDatabase db = getWritableDatabase();
        for (Dish d : dishesToUpdate) {
            int available = d.getAvailable() ? 1 : 0;
            String query = "UPDATE " + TABLE_NAME_DISHES +
                    " SET " + PRICE_COL_DISHES + " = " + d.getPrice() +
                    ", " + IS_AVAILABLE + " = " + available +
                    " WHERE " + NAME_COL_DISHES + " = '" + d.getName() + "'";
            db.execSQL(query);
        }
    }


    //Adding a user to the DB
    public void AddUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        if(CheckEmail(user)){
            return;
        }

        values.put(NAME_COL_USERS,user.getName());
        values.put(EMAIL_COL_USERS,user.getEmail());
        values.put(PASSWORD_COL_USERS,user.getPassword());

        db.insert(TABLE_NAME_USERS,null,values);
    }

    //Checking if the email provided is in the db already
    public Boolean CheckEmail(User user){
        SQLiteDatabase db = getReadableDatabase();
        String email[] = {user.getEmail()};
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME_USERS + " WHERE " + EMAIL_COL_USERS + " = ?";
        Cursor cursor = db.rawQuery(query,email);

        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;

    }

    //Getting the id of an user
    public int getUserId(User user){
        SQLiteDatabase db = getReadableDatabase();
        int id = -1;
        String email[] = {user.getEmail()};

        String query = "SELECT " + ID_COL_USERS + " FROM " + TABLE_NAME_USERS + " WHERE " + EMAIL_COL_USERS + " = ?";

        Cursor cursor = db.rawQuery(query,email);

        if(cursor.moveToFirst()){
            id = cursor.getInt(0);
        }
        cursor.close();

        return id;
    }

    //Retrieves an user passing it's Id as parameter
    public User getUserById(int userID){
        SQLiteDatabase db = getReadableDatabase();
        String args[] = {String.valueOf(userID)};

        String query = "SELECT * FROM " + TABLE_NAME_USERS + " WHERE "
                + ID_COL_USERS + " =?";
        Cursor cursor = db.rawQuery(query,args);

        if(cursor.moveToFirst()){
            return new User(cursor.getString(1),cursor.getString(2),cursor.getString(3));
        }
        return null;

    }

    //retrieving a user passing it's email and password as a parameter(Login info)
    public User getUserFromDb(String email, String password){
        SQLiteDatabase db = getReadableDatabase();
        String params[] = {email,password};
        User user;

        String query = "SELECT * FROM " + TABLE_NAME_USERS + " WHERE " + EMAIL_COL_USERS + " =? AND " + PASSWORD_COL_USERS + " =?";

        Cursor cursor = db.rawQuery(query,params);

        if(cursor.moveToFirst()){
            user  = new User(cursor.getString(1),cursor.getString(2),cursor.getString(3));
            cursor.close();
            return user;
        }
        cursor.close();
        return null;
    }


    //Adding an order to the db
    public boolean AddOrder(Order order){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        User user = order.getUser();
        long userId = getUserId(user);

        values.put(STATUS_COL_ORDER,order.getStatus());
        values.put(DATE_COL_ORDER,order.getDate());
        values.put(USER_ID_COL_ORDER,userId);
        values.put(TABLENUMBER_ORDER,order.getTable());

        if(userId != -1) {
            long orderId = db.insert(TABLE_NAME_ORDER, null, values);

            if (orderId != -1) {
                AddOrderDishLink(db,orderId,order.getDishes());
                return  true;
            }
        }
        return false;
    }

    //Getting a list of all orders ids from a given user
    public ArrayList<Integer> getOrderIdsByUser(User user){
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Integer> orderIds = new ArrayList<>();

        int userId = getUserId(user);

        String args[] = {String.valueOf(userId)};

        String query = "SELECT * FROM " + TABLE_NAME_ORDER + " WHERE " +
                        USER_ID_COL_ORDER + " =?";

        Cursor cursor = db.rawQuery(query,args);

        if(cursor.moveToFirst()){
            do{
                orderIds.add(cursor.getInt(0));
            }while (cursor.moveToNext());
        }

        return orderIds;

    }
        //Creating the order objects for all the orders of a given user
    public ArrayList<Order> getOrdersByUser(User user){
        SQLiteDatabase db = getReadableDatabase();

        ArrayList<Integer> orderIdsByUser = getOrderIdsByUser(user);

        ArrayList<Order> userOrders = new ArrayList<>();

        for (Integer orderId:orderIdsByUser) {
            String args[] = {String.valueOf(orderId)};
            String query = "SELECT * FROM " + TABLE_NAME_ORDER + " WHERE " + ID_COL_ORDER + " =?";
            Cursor cursor = db.rawQuery(query,args);
            if(cursor.moveToFirst()){
                //Order Constructor
                //dishList,Date,User,table
                Order order = new Order(ReadDishesFromOrder(orderId),cursor.getString(2),user,cursor.getInt(4));
                userOrders.add(order);
            }
        }
        //Order Table
        //Id,Status,date,user,table
        return userOrders;
    }

    //Retrieves the order passing and order id as parameter
    public Order getOrderById(int orderId){
        SQLiteDatabase db = getReadableDatabase();
        String args[] = {String.valueOf(orderId)};

        String query = "SELECT * FROM " + TABLE_NAME_ORDER + " WHERE "
                    + ID_COL_ORDER + " =?";
        Cursor cursor = db.rawQuery(query,args);

        if(cursor.moveToFirst()){
            User user = getUserById(cursor.getInt(3));
            Order order = new Order(ReadDishesFromOrder(orderId),cursor.getString(2),user,cursor.getInt(4));
            return order;
        }
        return null;
    }

    //Adding the order dish link.
    public void AddOrderDishLink(SQLiteDatabase db, long orderId, ArrayList<Dish> dishesToAdd){
        for(Dish d:dishesToAdd){
            ContentValues linkValues = new ContentValues();
            int dishId = getDishId(d);

            linkValues.put(ORDER_COL_LINK,orderId);
            linkValues.put(DISH_COL_LINK,dishId);

            db.insert(TABLE_NAME_LINK,null,linkValues);
        }
    }




}

