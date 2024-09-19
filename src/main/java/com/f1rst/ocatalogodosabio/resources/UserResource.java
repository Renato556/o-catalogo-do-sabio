package com.f1rst.ocatalogodosabio.resources;

import com.f1rst.ocatalogodosabio.domain.entities.User;
import com.f1rst.ocatalogodosabio.dto.BookDTO;
import com.f1rst.ocatalogodosabio.dto.UserDTO;
import com.f1rst.ocatalogodosabio.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserResource {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") String id) {
        User user = userService.findById(id);
        List<BookDTO> bookDTOList = user.getLastSeen().stream().map(BookDTO::new).toList();
        UserDTO userDTO = new UserDTO(user);
        userDTO.setLastSeen(bookDTOList);
        return ResponseEntity.ok(userDTO);
    }
}
