package com.example.yummyfood4lyfe.classes;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDBHelper {
    FirebaseDatabase database;
    DatabaseReference userRef, recipeRef, reviewRef, savedRef;

    public FirebaseDBHelper(){
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(MyFirestoreReferences.USERS_COLLECTION);
        recipeRef = database.getReference(MyFirestoreReferences.RECIPES_COLLECTION);
        reviewRef = database.getReference(MyFirestoreReferences.REVIEWS_COLLECTION);
        savedRef = database.getReference(MyFirestoreReferences.SAVED_COLLECTION);
    }

    public void insertUser(User user, OnDBOperationListener<String> listener) {
        checkUserExists(user.getUsername(), user.getEmail(), new OnDBOperationListener<String>() {
            @Override
            public void onSuccess(String exists) {
                if (exists != null) {
                    listener.onFailure(new Exception(exists + " already exists"));
                } else {
                    String userId = userRef.push().getKey();
                    user.setUserid(userId);

                    userRef.child(userId).setValue(user)
                            .addOnSuccessListener(aVoid -> listener.onSuccess(userId))
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

    public void checkLogin(String username, String password, OnDBOperationListener<User> listener) {
        userRef.orderByChild("username").equalTo(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                User user = userSnapshot.getValue(User.class);
                                if (user != null && user.getPassword().equals(password)) {
                                    listener.onSuccess(user);
                                    return;
                                }
                            }
                        }
                        listener.onSuccess(null);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onFailure(databaseError.toException());
                    }
                });
    }

    public void insertRecipe(Recipe recipe, OnDBOperationListener<Void> listener) {
        // Generate a new unique key for the recipe
        String recipeId = recipeRef.push().getKey();
        // Set the generated key as the recipeid for the new recipe
        recipe.setRecipeid(recipeId);
        // Save the new recipe to the database using the generated key
        recipeRef.child(recipeId).setValue(recipe)
                .addOnSuccessListener(aVoid -> listener.onSuccess(null))
                .addOnFailureListener(listener::onFailure);
    }

    public Query getLatestRecipes(int limit) {
        return recipeRef.orderByChild("timestamp").limitToLast(limit);
    }



    public void insertReview(Review review, OnDBOperationListener<Void> listener) {
        String reviewId = reviewRef.push().getKey();
        review.setReviewid(reviewId);
        reviewRef.child(reviewId).setValue(review)
                .addOnSuccessListener(aVoid -> listener.onSuccess(null))
                .addOnFailureListener(listener::onFailure);
    }

    public Query getReviewsForRecipe(String recipeId) {
        return reviewRef.orderByChild("recipeid").equalTo(recipeId);
    }

    public Query getReviewsByUser(String username) {
        return reviewRef.orderByChild("username").equalTo(username);
    }

    public Query getRecipesByUsername(String username) {
        return recipeRef.orderByChild("username").equalTo(username);
    }

    public Query getUserById(String userId) {
        return userRef.orderByChild("userid").equalTo(userId);
    }

    public void insertSavedRecipe(String userId, String recipeId, OnDBOperationListener<Void> listener) {
        savedRef.child(userId).child(recipeId).setValue(true)
                .addOnSuccessListener(aVoid -> listener.onSuccess(null))
                .addOnFailureListener(listener::onFailure);
    }

    public void removeSavedRecipe(String userId, String recipeId, OnDBOperationListener<Void> listener) {
        savedRef.child(userId).child(recipeId).removeValue()
                .addOnSuccessListener(aVoid -> listener.onSuccess(null))
                .addOnFailureListener(listener::onFailure);
    }

    public void toggleSavedRecipe(String userId, String recipeId, OnDBOperationListener<Void> listener) {
        savedRef.child(userId).child(recipeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Recipe is already saved, remove it
                    removeSavedRecipe(userId, recipeId, listener);
                } else {
                    // Recipe is not saved, add it
                    insertSavedRecipe(userId, recipeId, listener);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure(databaseError.toException());
            }
        });
    }

    public DatabaseReference getSavedRecipesRef(String userId) {
        return savedRef.child(userId);
    }

    public void getSavedRecipeIds(String userId, OnDBOperationListener<List<String>> listener) {
        savedRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> savedRecipeIds = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    savedRecipeIds.add(snapshot.getKey());
                }
                listener.onSuccess(savedRecipeIds);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure(databaseError.toException());
            }
        });
    }

    public Query getRecipeById(String recipeId) {
        return recipeRef.child(recipeId);
    }



    public interface OnDBOperationListener<T>{
        void onSuccess(T result);
        void onFailure(Exception e);
    }
}
