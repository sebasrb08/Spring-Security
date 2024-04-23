package com.app;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.app.persistence.entity.PermissionEntity;
import com.app.persistence.entity.RoleEnum;
import com.app.persistence.entity.RolesEntity;
import com.app.persistence.entity.UserEntity;
import com.app.persistence.repository.UserRepository;

@SpringBootApplication
public class SpringSecurityAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityAppApplication.class, args);
	}
	@Bean
	CommandLineRunner init(UserRepository userRepository){
		return args ->{
			PermissionEntity createPermission= PermissionEntity.builder()
			.nombre("CREATE")
			.build();

			PermissionEntity updatePermission= PermissionEntity.builder()
			.nombre("UPDATE")
			.build();


			PermissionEntity readPermission= PermissionEntity.builder()
			.nombre("READ")
			.build();

			PermissionEntity deletePermission= PermissionEntity.builder()
			.nombre("DELETE")
			.build();

			PermissionEntity refactorPermission= PermissionEntity.builder()
			.nombre("REFACTOR")
			.build();

			// CREATE ROLES

			RolesEntity roleAdmin= RolesEntity.builder()
			.roleEnum(RoleEnum.ADMIN)
			.permissions(Set.of(createPermission,updatePermission,readPermission,deletePermission))
			.build();

			RolesEntity roleUser= RolesEntity.builder()
			.roleEnum(RoleEnum.USER)
			.permissions(Set.of(createPermission,readPermission))
			.build();

			RolesEntity roleInvited= RolesEntity.builder()
			.roleEnum(RoleEnum.INVITED)
			.permissions(Set.of(readPermission))
			.build();

			RolesEntity roleDeveloper= RolesEntity.builder()
			.roleEnum(RoleEnum.DEVELOPER)
			.permissions(Set.of(createPermission,updatePermission,readPermission,deletePermission,refactorPermission))
			.build();

			// CREATE USER

			UserEntity userSantiago=UserEntity.builder()
			.username("Santiago")
			.password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
			.isEnabled(true)
			.accountNoExpired(true)
			.accountNoLocked(true)
			.credentialNoExpired(true)
			.role(Set.of(roleAdmin))
			.build();

			UserEntity userDaniel=UserEntity.builder()
			.username("daniel")
			.password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
			.isEnabled(true)
			.accountNoExpired(true)
			.accountNoLocked(true)
			.credentialNoExpired(true)
			.role(Set.of(roleUser))
			.build();

			UserEntity userAlejandra=UserEntity.builder()
			.username("Alejandra")
			.password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
			.isEnabled(true)
			.accountNoExpired(true)
			.accountNoLocked(true)
			.credentialNoExpired(true)
			.role(Set.of(roleInvited))
			.build();

			UserEntity userSebastian=UserEntity.builder()
			.username("Sebastian")
			.password("$2a$10$cMY29RPYoIHMJSuwRfoD3eQxU1J5Rww4VnNOUOAEPqCBshkNfrEf6")
			.isEnabled(true)
			.accountNoExpired(true)
			.accountNoLocked(true)
			.credentialNoExpired(true)
			.role(Set.of(roleDeveloper))
			.build();

			userRepository.saveAll(List.of(userSantiago,userDaniel,userAlejandra,userSebastian));
			
		};
	}
}
