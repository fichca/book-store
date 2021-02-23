package com.company.configuration;

import com.company.ioc.annotation.ComponentScan;
import com.company.ioc.annotation.Configuration;

@Configuration
@ComponentScan(basePackage = "com.company")
public class RootConfiguration {
}
