package com.example.yummyfood4lyfe.classes;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FirebaseDBHelper {
    FirebaseDatabase database;
    DatabaseReference userRef, recipeRef, reviewRef;

    public FirebaseDBHelper(){
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(MyFirestoreReferences.USERS_COLLECTION);
        recipeRef = database.getReference(MyFirestoreReferences.RECIPES_COLLECTION);
        reviewRef = database.getReference(MyFirestoreReferences.REVIEWS_COLLECTION);
    }

    public void insertUser(User user, OnDBOperationListener<Void> listener) {
        checkUserExists(user.getUsername(), user.getEmail(), new OnDBOperationListener<String>() {
            @Override
            public void onSuccess(String exists) {
                if (exists != null) {
                    listener.onFailure(new Exception(exists + " already exists"));
                } else {
                    userRef.push().setValue(user)
                            .addOnSuccessListener(aVoid -> listener.onSuccess(null))
                            .addOnFailureListener(listener::onFailure);
                }
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure(e);
            }
        });
    }

    public void checkUserExists(String username, String email, OnDBOperationListener<String> listener) {
        userRef.orderByChild("username").equalTo(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            listener.onSuccess("Username");
                            return;
                        }
                        userRef.orderByChild("email").equalTo(email)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            listener.onSuccess("Email");
                                        } else {
                                            listener.onSuccess(null);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        listener.onFailure(databaseError.toException());
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onFailure(databaseError.toException());
                    }
                });
    }

    public void checkLogin(String username, String password, OnDBOperationListener<Boolean> listener) {
    userRef.orderByChild("username").equalTo(username)
        .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
                        if (user != null && user.getPassword().equals(password)) {
                            listener.onSuccess(true);
                            return;
                        }
                    }
                }
                listener.onSuccess(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure(databaseError.toException());
            }
        });
    }

    public void insertRecipe(Recipe recipe, OnDBOperationListener<Void> listener) {
        recipeRef.push().setValue(recipe)
                .addOnSuccessListener(aVoid -> listener.onSuccess(null))
                .addOnFailureListener(listener::onFailure);
    }

    public void getLatestRecipes(int limit, OnDBOperationListener<List<Recipe>> listener) {
        recipeRef.orderByChild("timestamp").limitToLast(limit)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Recipe> recipes = new ArrayList<>();
                        for (DataSnapshot recipeSnapshot : dataSnapshot.getChildren()) {
                            Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                            recipes.add(recipe);
                        }
                        Collections.reverse(recipes);
                        listener.onSuccess(recipes);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onFailure(databaseError.toException());
                    }
                });
    }

    public interface OnDBOperationListener<T>{
        void onSuccess(T result);
        void onFailure(Exception e);
    }
}
