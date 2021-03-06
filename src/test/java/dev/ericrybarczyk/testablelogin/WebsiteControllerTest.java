package dev.ericrybarczyk.testablelogin;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(WebsiteController.class)
class WebsiteControllerTest {

    private static final String PATH_SECURE_PAGE = "/securePage";
    private static final String EXPECTED_SECURE_VIEW_NAME = "securePage";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserDetailsService userDetailsService;

    @WithMockCustomUser
    @Test
    void getSecurePage_AsAuthenticatedUser_IsOk() throws Exception {
        mockMvc.perform(get(PATH_SECURE_PAGE))
                .andExpect(status().isOk())
                .andExpect(view().name(EXPECTED_SECURE_VIEW_NAME));
    }

    @Test
    void getSecurePage_AsNonAuthenticatedUser_IsRedirectToLogin() throws Exception {
        mockMvc.perform(get(PATH_SECURE_PAGE))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrlPattern("**/login"));
    }

}
