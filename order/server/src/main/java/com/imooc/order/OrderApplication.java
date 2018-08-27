package com.imooc.order;

		import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
		import org.springframework.boot.SpringApplication;
		import org.springframework.boot.web.servlet.ServletRegistrationBean;
		import org.springframework.cloud.client.SpringCloudApplication;
		import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
		import org.springframework.cloud.openfeign.EnableFeignClients;
		import org.springframework.context.annotation.Bean;
		import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients(basePackages = "com.imooc.product.client")
@SpringCloudApplication
@ComponentScan(basePackages = "com.imooc")
@EnableHystrixDashboard
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean getServlet() {
		HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
		registrationBean.setLoadOnStartup(1);
		registrationBean.addUrlMappings("/hystrix.stream");
		registrationBean.setName("HystrixMetricsStreamServlet");
		return registrationBean;
	}
}