package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;
    @Autowired
    private TestEntityManager entityManager;
    @Test
    public void testCreateUserWithOneRole(){
        Role roleAdmin = entityManager.find(Role.class,1);
        User anhTuan = new User("tuan@gmail.com",
                "tuan2024","Tuan","Phan Anh");
        anhTuan.addRole(roleAdmin);
        User savedUser =  repo.save(anhTuan);
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }
    @Test
    public void testCreateNewUserWithTwoRoles(){
        User userAnhKiet = new User("kiet@gmail.com",
                "kiet2024","Kiet","Phan Anh");
            Role roleEditor = new Role(3);
            Role roleAssistant = new Role(2);

            userAnhKiet.addRole(roleEditor);
            userAnhKiet.addRole(roleAssistant);

            User savedUser = repo.save(userAnhKiet);
            Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }
    @Test
    public void testListAllUsers(){
        Iterable<User>listUsers = repo.findAll();
        listUsers.forEach(System.out::println);
//        Assertions.assertThat(listUsers)

    }
    @Test
    public void testGetUserById(){
        User user = repo.findById(1).get();
        Assertions.assertThat(user).isNotNull();
    }
    @Test
    public void testUpdateUserDetail(){
        User user = repo.findById(1).get();
        user.setEnabled(true);
        user.setEmail("anhTuan.devJava@gmail.com");
        repo.save(user);
    }
    @Test
    public void testpdateUsersRole(){
        User user = repo.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesperson = new Role(2);
        user.getRoles().remove(roleEditor);
        user.addRole(roleSalesperson);
        repo.save(user);
    }
    @Test
    public void testDeleteUser(){
        Integer id=2;
        repo.deleteById(id);
    }
    @Test
    public void testGetUserByEmail(){
        String email = "phantuan7a5@gmail.com";
        User user = repo.getUserByEmail(email);
        Assertions.assertThat(user).isNotNull();
    }
    @Test
    public void testCountById(){
        Integer id = 1;
        Long count =  repo.countById(id);
        Assertions.assertThat(count).isNotNull().isGreaterThan(0);
    }
    @Test
    public void testDisableUser(){
        Integer id = 8;
        repo.updateEnabledStatus(id,true);
    }
    @Test
    public void testListFirstPage(){
        int pageNumber = 1;
        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<User>page =  repo.findAll(pageable);
        List<User>listUsers = page.getContent();
        listUsers.forEach(System.out::println);
        Assertions.assertThat(listUsers.size()).isGreaterThan(0);
    }
    @Test
    public void testSearchUsers(){
        String keyword = "Phan";
        int pageNumber = 0;
        int pageSize = 4;
        Pageable pageable  = PageRequest.of(pageNumber,pageSize);
        Page<User>page = repo.findAll(keyword,pageable);
        List<User>listUsers = page.getContent();
        listUsers.forEach(System.out::println);
        Assertions.assertThat(listUsers.size()).isGreaterThan(0);
    }
}
