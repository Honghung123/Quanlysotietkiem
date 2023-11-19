package com.earntogether.qlysotietkiem;

import com.earntogether.qlysotietkiem.repository.CustomerRepository;
import com.earntogether.qlysotietkiem.service.CustomerService;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@SpringBootTest
class StarterProjectApplicationTests {
    @Autowired
    CustomerRepository customerRepository;
    @Test
    void contextLoads() {
    }

    @Test
    void testDate(){
        LocalDate targetDate = LocalDate.parse("2023-10-11");
        LocalDate now = LocalDate.now();
        System.out.println(now);
        long days = ChronoUnit.DAYS.between(targetDate, now);
        System.out.println(days);
    }

    @Test
    void testCustomerService(){
        var nameKh = customerRepository.findByCustomerCode(4);
        System.out.println(nameKh);
        var kh = customerRepository.findByCustomerCode(4);
        System.out.println(kh);
    }
}
