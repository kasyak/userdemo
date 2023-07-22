package com.example.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    Integer id;
    String surname;
    String name;
    String patronymic;
    String login;

    @Override
    public String toString() {
        return String.format("%s %s %s", surname, name, patronymic);
    }
}