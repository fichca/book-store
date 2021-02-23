package com.company.console;

import com.company.configuration.RootConfiguration;
import com.company.ioc.core.Container;

public class Bootstrap {
    public static void main(String[] args) {
        Container container = new Container(RootConfiguration.class);
        ConsoleApplication consoleApplication = (ConsoleApplication) container.getComponent("consoleApplication");
//        ConsoleApplication consoleApplication = new ConsoleApplication();
        consoleApplication.run();
    }
}
