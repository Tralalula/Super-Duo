// I used the guide created by Dharmang Soni to help create my collection widget
// http://dharmangsoni.blogspot.dk/2014/03/collection-widget-with-event-handling.html

package barqsoft.footballscores.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

@SuppressWarnings("NewApi")
public class FootballScoresWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        FootballScoresWidgetDataProvider widgetDataProvider = new FootballScoresWidgetDataProvider(
                getApplicationContext(),
                intent
        );

        return widgetDataProvider;
    }
}
