package com.utkuyavuz.songfinder.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Runnable class for SpringBoot project.
 * Extends <code>SpringBootServletInitializer</code> to support WAR deployment.
 * @see SpringBootServletInitializer
 *
 * @author Utku Yavuz
 * @version 1.0
 * @since 2018-04-16
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.utkuyavuz.songfinder.restcontroller ", " com.utkuyavuz.songfinder.service " })
public class SongFinder extends SpringBootServletInitializer {

    /**
     * In order to create a deployable WAR file, configure method of
     * <code>SpringBootApplicationBuilder</code> class should be overriden
     *
     * @param builder <code>SpringApplicationBuilder</code>
     * @return <code>SpringApplicationBuilder</code> for WAR deployment
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SongFinder.class);
    }

    /**
     * Application to run
     *
     * @param args Input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SongFinder.class, args);
    }
}
