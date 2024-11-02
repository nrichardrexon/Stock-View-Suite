package com.warehouse;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        // Specify the port to run the server on
        int port = 8080;
        // Set the web application directory path
        String webAppDirLocation = "src/main/webapp/";

        // Create a Tomcat server instance
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);

        // Add the web application context
        tomcat.addWebapp("", new File(webAppDirLocation).getAbsolutePath());

        try {
            // Start the Tomcat server
            tomcat.start();
            System.out.println("Server started on http://localhost:" + port);
            // Keep the server running
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
}
