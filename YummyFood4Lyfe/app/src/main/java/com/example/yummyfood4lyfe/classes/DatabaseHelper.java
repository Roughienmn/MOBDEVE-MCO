package com.example.yummyfood4lyfe.classes;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "yummyfood4lyfe.db";
    private Context context;

    /*
    private static final String USERS_TABLE = "users";
    private static final String[] USERS_COLUMNS = {
            "id INTEGER PRIMARY KEY AUTOINCREMENT",
            "username TEXT UNIQUE",
            "password TEXT",
            "birthday DATE",
            "email TEXT",
            "profile_image INTEGER",
            "bio TEXT",
            "name TEXT"
    };
    */

    private static final String RECIPES_TABLE = "recipes";
    private static final String[] RECIPES_COLUMNS = {
            "id INTEGER PRIMARY KEY AUTOINCREMENT",
            "recipeid TEXT UNIQUE",
            "username TEXT",
            "title TEXT",
            "cookingTime TEXT",
            "servings INTEGER",
            "recipe_image TEXT",
            "ingredients TEXT",
            "instructions TEXT",
            "timestamp LONG"
    };

    private static final String SAVED_RECIPES_TABLE = "saved";
    private static final String[] SAVED_RECIPES_COLUMNS = {
            "recipeid TEXT",
            "userid TEXT",
            "PRIMARY KEY (recipeid, userid)"
    };

    /*
    private static final String REVIEWS_TABLE = "reviews";
    private static final String[] REVIEWS_COLUMNS = {
            "id INTEGER PRIMARY KEY AUTOINCREMENT",
            "recipe_id INTEGER",
            "user_id INTEGER",
            "review_text TEXT",
            "FOREIGN KEY (recipe_id) REFERENCES recipes(id)",
            "FOREIGN KEY (user_id) REFERENCES users(id)"
    };
    */

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //createTable(db, USERS_TABLE, USERS_COLUMNS);
        createTable(db, RECIPES_TABLE, RECIPES_COLUMNS);
        //createTable(db, REVIEWS_TABLE, REVIEWS_COLUMNS);
        createTable(db, SAVED_RECIPES_TABLE, SAVED_RECIPES_COLUMNS);
    }

    private void createTable(SQLiteDatabase db, String tableName, String[] columns) {
        StringBuilder createTableQuery = new StringBuilder("CREATE TABLE " + tableName + " (");
        for (int i = 0; i < columns.length; i++) {
            createTableQuery.append(columns[i]);
            if (i < columns.length - 1) {
                createTableQuery.append(", ");
            }
        }
        createTableQuery.append(")");
        db.execSQL(createTableQuery.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RECIPES_TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + REVIEWS_TABLE);
        onCreate(db);
    }
    /*
    public long insertUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("email", user.getEmail());
        contentValues.put("birthday", user.getBirthday());

        return db.insert(USERS_TABLE, null, contentValues);
    }*/

    /*
    public boolean searchExistingUsername(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + USERS_TABLE + " WHERE username = ?";
        String[] selectionArgs = {username};
        return db.rawQuery(query, selectionArgs).getCount() > 0;
    }

    public boolean searchExistingEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + USERS_TABLE + " WHERE email = ?";
        String[] selectionArgs = {email};
        return db.rawQuery(query, selectionArgs).getCount() > 0;
    }

    public int getUserId(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT id FROM " + USERS_TABLE + " WHERE username = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            cursor.close();
            return userId;
        } else {
            return -1;
        }
    }

    public User getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + USERS_TABLE + " WHERE username = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            String userUsername = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String userPassword = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            String userEmail = cursor.getString(cursor.getColumnIndexOrThrow("email"));
            String userBirthday = cursor.getString(cursor.getColumnIndexOrThrow("birthday"));
            String userBio = cursor.getString(cursor.getColumnIndexOrThrow("bio"));
            String userName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            int userProfileImage = cursor.getInt(cursor.getColumnIndexOrThrow("profile_image"));
            cursor.close();
            return null;
            // return new User(userUsername, userEmail, userBirthday, userPassword, userName, userBio, userProfileImage);
        } else {
            return null;
        }
    }

    public boolean checkLogin(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + USERS_TABLE + " WHERE username = ? AND password = ?";
        String[] selectionArgs = {username, password};
        return db.rawQuery(query, selectionArgs).getCount() > 0;
    }
    */

    public long storeRecipe(Recipe recipe) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        String userid = sharedPreferences.getString("userid", null);
        long savedTableResult = -1, recipeTableResult = -1;

        //check if already stored
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + SAVED_RECIPES_TABLE + " WHERE recipeid = ? AND userid = ?";
        String[] selectionArgs = {recipe.getRecipeid(), userid};
        if (db.rawQuery(query, selectionArgs).getCount() <= 0) {
            db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("recipeid", recipe.getRecipeid());
            contentValues.put("username", recipe.getUsername());
            contentValues.put("title", recipe.getTitle());
            contentValues.put("cookingTime", recipe.getCookingTime());
            contentValues.put("servings", recipe.getServings());
            contentValues.put("recipe_image", recipe.getRecipeImage());
            contentValues.put("ingredients", recipe.getIngredients());
            contentValues.put("instructions", recipe.getInstructions());
            contentValues.put("timestamp", recipe.getTimestamp());

            savedTableResult = db.insert(RECIPES_TABLE, null, contentValues);
        }
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("recipeid", recipe.getRecipeid());
        contentValues2.put("userid", userid);
        recipeTableResult = db.insert(SAVED_RECIPES_TABLE, null, contentValues2);

        return recipeTableResult;
    }

    public void deleteRecipe(String recipeid) {
        SQLiteDatabase db = this.getWritableDatabase();
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        String userid = sharedPreferences.getString("userid", null);
        db.delete(SAVED_RECIPES_TABLE, "recipeid = ? AND userid = ?", new String[]{recipeid, userid});

        //check if recipe is saved by any other user
        Cursor cursor = db.rawQuery("SELECT * FROM " + SAVED_RECIPES_TABLE + " WHERE recipeid = ?", new String[]{recipeid});
        if (cursor.getCount() == 0) {
            db.delete(RECIPES_TABLE, "recipeid = ?", new String[]{recipeid});
        }
        db.delete(RECIPES_TABLE, "recipeid = ?", new String[]{recipeid});
    }

    public List<String> getSavedRecipeIds() {
        SQLiteDatabase db = this.getReadableDatabase();
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        String userid = sharedPreferences.getString("userid", null);
        Cursor cursor = db.rawQuery("SELECT recipeid FROM " + SAVED_RECIPES_TABLE + " WHERE userid = ?", new String[]{userid});

        List<String> recipeIds = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                recipeIds.add(cursor.getString(cursor.getColumnIndexOrThrow("recipeid")));
            }
            cursor.close();
        }
        return recipeIds;
    }

    public Recipe getRecipeById(String recipeid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + RECIPES_TABLE + " WHERE recipeid = ?", new String[]{recipeid});
        if (cursor != null && cursor.moveToFirst()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String cookingTime = cursor.getString(cursor.getColumnIndexOrThrow("cookingTime"));
            int servings = cursor.getInt(cursor.getColumnIndexOrThrow("servings"));
            String recipeImage = cursor.getString(cursor.getColumnIndexOrThrow("recipe_image"));
            String ingredients = cursor.getString(cursor.getColumnIndexOrThrow("ingredients"));
            String instructions = cursor.getString(cursor.getColumnIndexOrThrow("instructions"));
            long timestamp = cursor.getLong(cursor.getColumnIndexOrThrow("timestamp"));
            cursor.close();
            return new Recipe(recipeid, username, title, cookingTime, servings, recipeImage, ingredients, instructions, timestamp);
        } else {
            return null;
        }
    }
}
