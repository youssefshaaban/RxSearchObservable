# RxSearchObservable

# first create SubjectPublisher to observe for searchView or EditText 
It is just that you will have to make that view observable by implementing the text change listener.

# Debounce 
provide call network for each character change The debounce will wait for the provided time for doing anything, if any other search query comes in between that time,
debounce only emit an item from an Observable if a particular timespan has passed without it emitting an another item.

