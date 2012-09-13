package com.primeranks.bme.fs_replay.Activity;

import android.content.Intent;
import android.graphics.*;
import android.os.Bundle;
import android.widget.ProgressBar;
import com.google.android.maps.*;
import com.primeranks.bme.fs_replay.DAO.GetFlightSnapshots;
import com.primeranks.bme.fs_replay.Data_Pojo.Flight;
import com.primeranks.bme.fs_replay.Data_Pojo.FlightSnapshot;
import com.primeranks.bme.fs_replay.R;

import java.util.List;

public class GoogleMaps extends MapActivity {
    private Projection _projection;
    private MapView _view;
    private Boolean drawAll = true;

    private Flight _flight;
    private List<FlightSnapshot> _path;
    MapController _mc;
    public ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        _flight = (Flight) i.getSerializableExtra(SelectFromListActivity.FlightString);

        setContentView(R.layout.map_view);

        progressBar =(ProgressBar) findViewById(R.id.ProgressBarMaps);

        _view = (MapView) findViewById(R.id.mapView);//Creating an instance of map_view
        _view.setBuiltInZoomControls(true);//Enabling the built-in Zoom Controls

        _mc = _view.getController();
        _projection = _view.getProjection();
        /*
      Called when the activity is first created.
     */
        List<Overlay> _overlays = _view.getOverlays();
        _overlays.add(new GoogleMaps.PathOverlay());

        new GetFlightSnapshots(this).execute(_flight);
    }

    public void redrawMap(boolean drawAll) {
        this.drawAll = drawAll;
        if(drawAll && _path != null && _path.size() > 0)
        {
            FlightSnapshot f = _path.get(0);
            _mc.setCenter(new GeoPoint((int) (f.getLatitude() * 1e6), (int) (f.getLongitude() * 1e6)));
            _mc.setZoom(19);
        }
        _view.postInvalidate();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setFlightSnapshot(List<FlightSnapshot> l) {
        _path = l;
        redrawMap(true);
    }

    public static Paint MapLineStyle;

    static {
        //Configuring the paint brush
        MapLineStyle = new Paint();
        MapLineStyle.setDither(true);
        MapLineStyle.setColor(Color.RED);
        MapLineStyle.setStyle(Paint.Style.FILL_AND_STROKE);
        MapLineStyle.setStrokeJoin(Paint.Join.ROUND);
        MapLineStyle.setStrokeCap(Paint.Cap.ROUND);
        MapLineStyle.setStrokeWidth(4);
    }

    class PathOverlay extends Overlay {
        private Path path = new Path();

        public void draw(Canvas canvas, MapView mV, boolean shadow) {
            super.draw(canvas, mV, shadow);
            if(_path == null)
                return;
            if (drawAll) {
                Point p = null, c = null;
                for (FlightSnapshot f : _path) {
                    _projection.toPixels(new GeoPoint((int) (f.getLatitude() * 1e6), (int) (f.getLongitude() * 1e6)), c);
                    if (p == null) {
                        if (c != null) {
                            p = c;
                            path.moveTo(c.x, c.y);
                        }
                        continue;
                    }
                    path.lineTo(c.x, c.y);
                }
            } else {
                Point c = null;
                FlightSnapshot f = _path.get(_path.size() - 1);
                _projection.toPixels(new GeoPoint((int) (f.getLatitude() * 1e6), (int) (f.getLongitude() * 1e6)), c);
                path.lineTo(c.x, c.y);
            }
            canvas.drawPath(path, MapLineStyle);
        }

    }
}

