package com.smartzone.rxsearchobservable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.rxkotlin.flatMapIterable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var  disbos: Disposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        disbos=RxObservableSearch.fromView(findViewById<EditText>(R.id.search))
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { value ->
                return@filter value.isNotEmpty()
            }.distinctUntilChanged().switchMap { query ->
                dataSource(query)
            }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe({
                Log.e(MainActivity::class.java.name,it.toString())
            },{
                Log.e(MainActivity::class.java.name,it.toString())
            })

    }

    fun dataSource(query: String): Observable<List<String>> {
        val list = listOf("test", "test2", "joe", "mohamed", "ahmed", "3sam", "moatez")
        return Observable.fromArray(
            list
        ).flatMapIterable { t -> t }.filter { str -> str.contains(query) }.toList().toObservable()
    }

    override fun onDestroy() {
        super.onDestroy()
        disbos.dispose()
    }
}