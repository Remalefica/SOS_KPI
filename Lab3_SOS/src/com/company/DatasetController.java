package com.company;

import java.util.stream.IntStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;

public class DatasetController {
    int n = 3;
    double lambdaInit = 0.1, lambdaInc = 0.2;

    private final int m = 30;
    private final double[][] avgWaitingArray = new double[n][m];
    private final double[][] dispersionArray = new double[n][m];
    private final double[][] idleArray = new double[n][m];

    public String[] getColumnKeys() {
        String[] columnKeys = new String[m];
        double lambda = lambdaInit;

        for (int i = 0; i < m; i++) {
            columnKeys[i] = Double.toString(lambda).substring(0, 3);
            lambda += lambdaInc;
        }
        return columnKeys;
    }

    public CategoryDataset createAvgWaitingDataset() {
        String[] columnKeys = getColumnKeys();
        double[][] data = new double[1][m];
        data[0] = avgWaitingArray[0];
        return DatasetUtilities.createCategoryDataset(new String[]{"Values" }, columnKeys, data);
    }

    public CategoryDataset createDispersionDataset() {
        String[] columnKeys = getColumnKeys();
        return DatasetUtilities.createCategoryDataset(
                new String[]{"1", "2", "3" }, columnKeys, dispersionArray);
    }

    public CategoryDataset createIdleDataset() {
        String[] columnKeys = getColumnKeys();
        double[][] data = new double[1][m];
        data[0] = idleArray[0];
        return DatasetUtilities.createCategoryDataset(new String[]{"Values" }, columnKeys, data);
    }

    public JFreeChart createChart(CategoryDataset categoryDataset, String name) {
        return ChartFactory.createBarChart(name, "Lambda", "Value",
                categoryDataset, PlotOrientation.VERTICAL, true,
                true, false
        );
    }

    public void test() {
        double lambda = lambdaInit;
        int minTime = 1;
        int maxTime = 5;
        int scale = 50;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                Main s = new Main(lambda, minTime, maxTime);

                IntStream.range(0, scale).forEach(l -> {
                    s.nextIteration();
                    System.out.println("#&" + (l + 1) + " CPU: " + s.getCPU() + " Queue: " + s.getQueue());
                });

                System.out.println("waiting: " + s.getWaiting());
                System.out.println("idle: " + s.getIdle());

                double avg = s.averageWaiting();
                double disp = 0;

                for (Integer x : s.getWaiting())
                    disp += Math.pow(avg - x.doubleValue(), 2);
                disp /= s.getWaiting().size();

                avgWaitingArray[j][i] = avg;
                dispersionArray[j][i] = disp;

                idleArray[j][i] = 100 * ((double) s.getIdle() / (double) scale);
            }
            lambda += lambdaInc;
        }
    }
}
