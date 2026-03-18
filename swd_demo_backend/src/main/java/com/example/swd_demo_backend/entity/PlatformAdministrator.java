package com.example.swd_demo_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "platform_administrator")
@PrimaryKeyJoinColumn(name = "user_id")
@Getter
@Setter
public class PlatformAdministrator extends User {
    // Platform specifically requested by the user for UC-55 Context
    // Can leave without additional fields if none specified.
}
