package com.company;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.*;

import org.jfree.chart.ChartPanel;

public class ChartBuilder extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
    private final int width = 900, height = 500;

    private ChartPanel chartPanel1, chartPanel2, chartPanel3;
    private DatasetController controller;
    private JTabbedPane jTabbedPane;

    public ChartBuilder() {
        initComponents();
    }

    private void initComponents() {
        controller = new DatasetController();
        controller.test();
        add(getJTabbedPane(), BorderLayout.CENTER);
        setSize(width, height);
    }

    private JTabbedPane getJTabbedPane() {
        if (jTabbedPane == null) {
            jTabbedPane = new JTabbedPane();
            jTabbedPane.addTab("Avg waiting", getChartPanel1());
            jTabbedPane.addTab("Device downtime", getChartPanel2());
            jTabbedPane.addTab("Waiting dispersion", getChartPanel3());
        }
        return jTabbedPane;
    }

    public ChartPanel getChartPanel1() {
        if (chartPanel1 == null) {
            chartPanel1 = new ChartPanel(controller.createChart(controller
                    .createAvgWaitingDataset(), "Average waiting"));
            chartPanel1.setPreferredSize(new java.awt.Dimension(500, 370));
            chartPanel1.setMouseZoomable(true, false);
            chartPanel1.setBorder(BorderFactory.createEtchedBorder(
                    EtchedBorder.LOWERED, null, null));
            GroupLayout layout = new GroupLayout(chartPanel1);
            chartPanel1.setLayout(layout);
        }
        return chartPanel1;
    }

    public ChartPanel getChartPanel2() {
        if (chartPanel2 == null) {
            chartPanel2 = new ChartPanel(controller.createChart(controller.createIdleDataset(), "Device downtime"));
            chartPanel2.setPreferredSize(new java.awt.Dimension(width, height));
            chartPanel2.setMouseZoomable(true, false);
            chartPanel2.setBorder(BorderFactory.createEtchedBorder(
                    EtchedBorder.LOWERED, null, null));
        }
        return chartPanel2;
    }

    public ChartPanel getChartPanel3() {
        if (chartPanel3 == null) {
            chartPanel3 = new ChartPanel(controller.createChart(controller
                    .createDispersionDataset(), "Waiting dispersion"));
            chartPanel3.setPreferredSize(new java.awt.Dimension(width, height));
            chartPanel3.setMouseZoomable(true, false);
            chartPanel3.setBorder(BorderFactory.createEtchedBorder(
                    EtchedBorder.LOWERED, null, null));
        }
        return chartPanel3;
    }

    private static void installLookFeel() {
        try {
            UIManager.setLookAndFeel(PREFERRED_LOOK_AND_FEEL);
        } catch (Exception e) {
            System.err.println(PREFERRED_LOOK_AND_FEEL + "cannot be installed on this platform:" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        installLookFeel();
        SwingUtilities.invokeLater(() -> {
            ChartBuilder frame = new ChartBuilder();
            frame.setDefaultCloseOperation(ChartBuilder.EXIT_ON_CLOSE);
            frame.setTitle("Chart Builder");
            frame.getContentPane().setPreferredSize(frame.getSize());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
