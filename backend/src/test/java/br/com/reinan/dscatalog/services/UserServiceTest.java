package br.com.reinan.dscatalog.services;

import br.com.reinan.dscatalog.dto.response.UserDTO;
import br.com.reinan.dscatalog.entities.User;
import br.com.reinan.dscatalog.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;
    @InjectMocks
    private UserServiceImpl userService;

    private final long nonExistId =1000L;

   private final long existId = 1L;


    @BeforeEach
    void setup(){
        User userTest = new User("Maria", "Fernanda", "maria@gmail.com", "mariaSenha");
        Page<User> usersPage = new PageImpl<User>(List.of(userTest));
        Mockito.when(repository.findAll((Pageable) any())).thenReturn(usersPage);
        Mockito.when(repository.findById(existId)).thenReturn(Optional.of(userTest));
        Mockito.doThrow(ResourceAccessException.class).when(repository).findById(nonExistId);
    }
    @Test
    public void shouldReturnPageOfUsers(){
        PageRequest pageRequest =  PageRequest.of(0, 10);
        Page<UserDTO> page = userService.findAll(pageRequest);

        Assertions.assertEquals(page.getSize(), 1); 
        Assertions.assertEquals(page.getTotalPages(), 1);
        Assertions.assertEquals(page.getContent().get(0).getFirstName(), "Maria");

        Mockito.verify(repository).findAll((Pageable) any());
    }


    @Test
    public void findByIdshouldThrowResourceNotFoundExceptionWhenIdNotExist(){
        Assertions.assertThrows(ResourceAccessException.class, () -> userService.findById(nonExistId)
        );
        Mockito.verify(repository).findById(nonExistId);
    }

    @Test
    public void findByIdshouldReturnUserWhenExistId(){
        UserDTO userDTO = userService.findById(existId);

        Assertions.assertNotNull(userDTO);
        Assertions.assertEquals("Maria", userDTO.getFirstName());


        Mockito.verify(repository).findById(existId);
    }


    @Test
    public void update(){

    }


}
