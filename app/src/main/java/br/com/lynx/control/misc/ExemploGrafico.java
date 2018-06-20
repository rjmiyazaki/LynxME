package br.com.lynx.control.misc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import br.com.lynx.R;


/**
 * Created by Rogerio on 25/04/2016.
 */
public class ExemploGrafico extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_example);

        GraphView graph = (GraphView) findViewById(R.id.graph);

        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(1, 59413.10),
                new DataPoint(1, 43358.34),
                new DataPoint(2, 16054.76),
                new DataPoint(3, 57386.04)
        });

        graph.addSeries(series);
    }
}
