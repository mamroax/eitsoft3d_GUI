package ru.app.form.monitoring3D.model;

import ru.app.factories.ServicesFactory;
import ru.app.form.monitoring3D.controllers.Controller3D;
import ru.app.services.device.Connectible;
import ru.app.services.device.Device;
import ru.app.utils.DeviceBuilder;
import ru.app.form.monitoring3D.view.GUI; // дратути тут я беру данные из формочки с кнопчей

import java.util.ArrayList;
import java.util.List;

public class Drawer {
    private static final int SUCCESS_CODE_START = 0;
    private Device device;

    public static boolean isUpdate = false; // default params for MainWindow if start not click

    private List<DrawerThread> drawerThreads;

    private Connectible connectible;
    public String kek; // данные по количеству поясов(пока не используется)

    public Drawer(Controller3D controller3D) {
        device = DeviceBuilder.buildDevice();
        //GUI gui = new GUI();
        //gui.start();
        //kek = "";// в kek мы передадим текст из формы нашего GUI
        //device = DeviceBuilder.buildDevice(kek);// а сюды мы запишем данные, полученные из формочки под кнопчу
        connectible = ServicesFactory.getConnection();

        initThreadImages(controller3D);
    }


    private void initThreadImages(Controller3D controller3D) {
        drawerThreads = new ArrayList<>();
        for (int i = 0; i < getNumberOfBelts(); i++) {
            drawerThreads.add(new DrawerThread(controller3D, i));
        }
    }


    public void startDraw() {
        if (connectible.start(device) == SUCCESS_CODE_START) {
            startThreadUpdateMeasurement();
        } else {
            System.err.println("Ошибка инициализации");
        }

    }

    private void startThreadUpdateMeasurement() {
        isUpdate = true;

        new Thread(() -> {
            while (isUpdate)
                updateData();

        }).start();
    }

    private synchronized void updateData() {

        long start1 = System.currentTimeMillis();
        List<List<Double>> dataList = connectible.updateMeas();
        System.out.println(System.currentTimeMillis() - start1 + " - " + "connection");


        // Wait finish last operation
        checkWorkThreads();
        waitDrawingImageAndChart();

        long start = System.currentTimeMillis();
        for (int i = 0; i < dataList.size(); i++) {
            drawerThreads.get(i).setDataList(dataList.get(i));
            drawerThreads.get(i).run();
        }

        System.out.println(System.currentTimeMillis() - start + " - " + "recon");

    }

    /**
     * Check that all thread dead
     */
    private void checkWorkThreads() {
        new Thread(() -> {
            int countLiveThread = 1;

            while(countLiveThread > 0) {
                countLiveThread = 0;
                for (DrawerThread drawerThread : drawerThreads) {
                    if (drawerThread.isAlive()) {
                        countLiveThread = 1;
                    }
                }

            }

            continueUpdateDataMeas();
        }).start();
    }

    private synchronized void continueUpdateDataMeas() {
        this.notify();
    }

    private synchronized void waitDrawingImageAndChart() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopDraw() {
        isUpdate = false;
        synchronized (this) {
            connectible.stop();
        }
    }

    public int getNumberOfBelts() {
        return Integer.parseInt(device.getNumberOfBelts());
    }
}
