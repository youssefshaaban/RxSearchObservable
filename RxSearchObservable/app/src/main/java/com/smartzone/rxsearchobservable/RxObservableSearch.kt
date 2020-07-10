package com.smartzone.rxsearchobservable

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.widget.SearchView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object RxObservableSearch {

    /**
     * using publish  subject to send observable of String
     *
     * */

    fun fromView(searchView: SearchView): Observable<String> {
        val subject: PublishSubject<String> = PublishSubject.create()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                subject.onComplete()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                subject.onNext(newText!!)
                return true
            }
        })
        return subject
    }

    /**
     * using EditText and set imeOptions actionSearch
     *
     * */
    fun fromView(editText: EditText): Observable<String> {
        val subject: PublishSubject<String> = PublishSubject.create()
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                subject.onNext(p0.toString())
            }
        })
        editText.setOnEditorActionListener { _, i: Int, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                subject.onComplete()
            }
            false
        }
        return subject
    }
}