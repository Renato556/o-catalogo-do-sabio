package com.f1rst.ocatalogodosabio.resources;

import com.f1rst.ocatalogodosabio.domain.entities.User;
import com.f1rst.ocatalogodosabio.dto.UserDTO;
import com.f1rst.ocatalogodosabio.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserResourceTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserResource userResource;

    @Test
    void findByIdSuccessTest() {
        User user = new User("123", "Renato");

        // GIVEN
        when(userService.findById("123")).thenReturn(user);
        // WHEN
        ResponseEntity<UserDTO> response = userResource.findById("123");
        // THEN
        assertAll(
                () -> verify(userService, times(1)).findById("123"),
                () -> assertEquals(response.getStatusCode(), HttpStatus.OK),
                () -> assertThat(response.getBody())
                        .usingRecursiveComparison()
                        .isEqualTo(user)
        );
    }
}