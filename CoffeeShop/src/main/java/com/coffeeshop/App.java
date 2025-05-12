package com.coffeeshop;

import com.coffeeshop.controllers.MainController;
import com.coffeeshop.views.MainView;

public class App {
    public static void main(String[] args) {
        MainView view = new MainView();
        MainController controller = new MainController(view);
        controller.initController();
    }
}
