package com.example.sofuser;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Retrofit;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sofuser.Adapter.UsersListAdapter;

import com.example.sofuser.Model.Root;
import com.example.sofuser.Model.User;
import com.example.sofuser.Retrofit.ApiServices;
import com.example.sofuser.Retrofit.RetrofitClient;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.support.v7.widget.LinearLayoutManager.*;

public class MainActivity extends AppCompatActivity {


    CompositeDisposable compositeDisposable;
    ApiServices apiServices;

    RecyclerView myRecyclerview;

    List<User> rootUserList = new ArrayList<>();

    UsersListAdapter listAdapter = new UsersListAdapter(this, rootUserList);
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        apiServices = retrofit.create(ApiServices.class);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Users List");


        myRecyclerview = (RecyclerView) findViewById(R.id.myRecyclerview);

        myRecyclerview.setHasFixedSize(true);
        myRecyclerview.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));
        myRecyclerview.setAdapter(listAdapter);

        getUsersInfo();


    }

    private void initSearchSystem() {

        compositeDisposable.add(rxSearchfromview(searchView).debounce(300, TimeUnit.MILLISECONDS)
                .filter(text -> !text.isEmpty())
                .map(text -> text.toLowerCase().trim())
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        inputName -> Observable.fromIterable(rootUserList)
                                .filter(user -> user.getDisplayName().toLowerCase().startsWith(inputName))
                                .toList()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(userList -> listAdapter.setUserList(userList), throwable -> Toast.makeText(this ,throwable.getMessage(), Toast.LENGTH_LONG).show()),
                        throwable -> Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show()));
    }


    private void getUsersInfo() {
        compositeDisposable.add(apiServices.getApiCall("desc", "reputation", "stackoverflow")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Root>() {
                               @Override
                               public void accept(Root root) throws Exception {
                                   displayUserList(root.getItems());
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Log.d("ERROR", "" + throwable.getMessage());
                               }
                           }
                ));


    }

    private void displayUserList(List<User> userList) {
        this.rootUserList = userList;
        this.listAdapter.setUserList(userList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        initSearchSystem();


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Observable<String> rxSearchfromview(final SearchView searchView) {
        final PublishSubject<String> subject = PublishSubject.create();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                subject.onComplete();
                searchView.clearFocus(); //if you want to close keyboard

                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                subject.onNext(text);
                if (text.equals("")) {
                    listAdapter.setUserList(rootUserList);
                    listAdapter.notifyDataSetChanged();
                }else {

                    initSearchSystem();
                    listAdapter.notifyDataSetChanged();

                }
                return false;
            }
        });

        return subject;
    }

}








