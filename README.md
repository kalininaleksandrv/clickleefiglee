# clickleefiglee
this app made as a part of the portfolio to demonstrate my abilities in java/android development to my potential employers

what stack of technologies and approaches I had used on this app for the current moment

1. RecycleView
1.1. Support of multi types of Views with AbstractWiewHolder and getItemViewType()
1.2. Swipe support with background drawing  and icons on swipe with custom Item Touch Helper callback 
    and implement a proper interface in Adapter, each element could change payload when swipe, depend on swipe direction
1.3. Custom ItemDecorator to draw divider and draw mark point depending on ViewType (also could depend on item payload)
1.4. Custom click listener with dynamically changing element (and its payload in the list) when click and show new element
1.5. Custom animations to different parts of ItemView (binding in Adapter)

2. Architecture approach
2.1. MVP approach when all data generate (autogenerating method) in Presenter
2.2. Context passing to the presenter with WeakRefference, activity bind and unbind with presenter along the lifecycle
2.3. Android ViewModel to create and hold presenter as a singleton and do not re-fetching data if activity re-create
2.4. Fetching new data in presenter on demand (FAB click)
2.5. Add NewsDataViewModel and DataInteractor to implement LiveData (subscribe and auto update) approach without Presenter this approach developing since commit d1665a0672426fc932a169a915c870b9fed74c7a

3. Multithreading
3.1. ThreadPoolExecutor to execute blocking method (fetching or generating data) in presenter
3.2. Handler to pass msg on Activity main thread when data fetched in presenter (with error handling support)

4. Custom view
4.1. Custom view with attributes, designated from xml and simple measuring and drawing
4.2. Managing state of custom view from activity or depending on state

5. Network
5.1. OkHttp3 implemented

BACKLOG
- nested RecycleView
- custom swap behavior in RV and custom clickable view on background when swipe
- customViewGroup above single custom view
- service
- content provider
- broadcastReciever
- DI
- RxJava or move to Kotlin and use coroutines
- Room to save data, fetching from network
