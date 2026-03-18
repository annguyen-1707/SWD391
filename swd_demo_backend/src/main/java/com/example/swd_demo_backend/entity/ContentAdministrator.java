package com.example.swd_demo_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "content_administrator")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
public class ContentAdministrator extends User {
    private String department;
}
