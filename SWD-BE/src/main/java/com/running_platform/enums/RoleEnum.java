package com.running_platform.enums;

public enum RoleEnum {
      LEARNER, PLATFORM_ADMIN, COURSE_AUTHOR, CONTENT_ADMIN;

      public RoleEnum getRoleEnum(String roleName) {
            for (RoleEnum role : RoleEnum.values()) {
                  if (role.name().equalsIgnoreCase(roleName)) {
                        return role;
                  }
            }
            throw new IllegalArgumentException("No enum constant with name " + roleName);
      }
}
