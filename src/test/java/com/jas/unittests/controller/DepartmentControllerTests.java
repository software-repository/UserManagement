package com.jas.unittests.controller;

import com.jas.service.DepartmentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Transactional
public class DepartmentControllerTests {

    private static MockHttpServletRequest mockHttpServletRequest;
    @PersistenceContext
    private EntityManager entityManager;

    @Mock
    DepartmentService departmentService;

    
}
