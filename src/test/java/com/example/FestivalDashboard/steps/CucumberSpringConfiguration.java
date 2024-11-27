package com.example.FestivalDashboard.steps;

import com.example.FestivalDashboard.FestivalDashboardApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;


@CucumberContextConfiguration
@SpringBootTest(classes = FestivalDashboardApplication.class) // Replace with your main application class
public class CucumberSpringConfiguration {
}
