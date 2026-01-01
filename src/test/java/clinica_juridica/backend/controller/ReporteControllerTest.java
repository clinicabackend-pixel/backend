package clinica_juridica.backend.controller;

import clinica_juridica.backend.service.ReporteService;
import clinica_juridica.backend.service.UsuarioService;
import clinica_juridica.backend.security.JwtUtil;
import clinica_juridica.backend.security.CustomUserDetailsService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReporteController.class)
public class ReporteControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ReporteService reporteService;

        @MockBean
        private UsuarioService usuarioService;

        @MockBean
        private JwtUtil jwtUtil;

        @MockBean
        private CustomUserDetailsService customUserDetailsService;

        @MockBean
        private DataSource dataSource;

        @Test
        @org.springframework.security.test.context.support.WithMockUser(username = "testuser", roles = { "USER" })
        public void testGetReporte() throws Exception {
                // Mock ReporteService behavior
                try (Workbook workbook = new XSSFWorkbook()) {
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        workbook.createSheet("Reporte");
                        workbook.write(outputStream);
                        Mockito.when(reporteService.generarReporte()).thenReturn(outputStream.toByteArray());
                }

                MvcResult result = mockMvc.perform(get("/api/reportes"))
                                .andExpect(status().isOk())
                                .andExpect(header().string("Content-Type",
                                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                                .andExpect(header().string("Content-Disposition",
                                                "form-data; name=\"attachment\"; filename=\"reporte.xlsx\""))
                                .andReturn();

                byte[] content = result.getResponse().getContentAsByteArray();
                assertNotNull(content);
                assertTrue(content.length > 0);
        }
}
