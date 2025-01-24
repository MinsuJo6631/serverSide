package com.example.serverside.jparepo;

import com.example.admin.DTO.DepartmentDTO;
import com.example.admin.Entity.UserActive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface userActiveRepo extends JpaRepository<UserActive,Long> {

    @Query(value = "select * from user_active where user_id= :userId",nativeQuery = true)
    List<UserActive> getUser(@Param("userId")Long userId);

    List<UserActive> findByDepartment_DepartmentIdAndIsActiveTrue(Long departmentId);

    @Query("SELECT ua FROM UserActive ua JOIN FETCH ua.user JOIN FETCH ua.position " +
            "WHERE ua.department.departmentId = :departmentId AND ua.isActive = true")
    List<UserActive> findActiveMembersWithUserAndPosition(@Param("departmentId") Long departmentId);
//    @Query(value = "SELECT u.name, u.email, u.phone, p.role_name, ua.is_active, d.department_name as department_name " +
//            "FROM user_active ua " +
//            "JOIN user u ON ua.user_id = u.id " +
//            "JOIN position p ON ua.position_id = p.id " +
//            "JOIN department d ON ua.department_id = d.department_id " +
//            "WHERE ua.department_id = :departmentId " +
//            "AND ua.is_active = true", nativeQuery = true)
//    List<UserActive> findDepartmentMembers(@Param("departmentId") Long departmentId);

    @Query("SELECT new com.example.admin.DTO.DepartmentDTO(" +
            "ua.id, u.name, u.email, u.phone, " +
            "p.positionName, " +
            "ua.isActive, " +
            "d.departmentName, " +
            "ua.createdAt, " +
            "ua.ipAddress, " +
            "p.rank, " +
            "u.HID) " +
            "FROM UserActive ua " +
            "JOIN ua.user u " +
            "JOIN ua.position p " +
            "JOIN ua.department d " +
            "WHERE d.departmentName = :departmentName " +
            "ORDER BY p.rank DESC") //join후 내림 차순 정렬 dto의 rank값또한 가져와서 ORDER BY p.rank DESC를 이용한 내림차순 정렬
    List<DepartmentDTO> findMembersByDepartmentName(@Param("departmentName") String departmentName);

    @Query("DELETE FROM UserActive ua WHERE ua.department.departmentName = :departmentName")
    @Modifying  // 데이터 수정/삭제 시 필요
    @Transactional// 트랜잭션 처리를 위해 필요
    void deleteUserActiveByDepartmentName(@Param("departmentName") String departmentName);

    @Query("DELETE FROM UserActive ua WHERE ua.user.HID = :HID")
    @Modifying  // 데이터 수정/삭제 시 필요
    @Transactional// 트랜잭션 처리를 위해 필요
    void deleteUserActiveByHID(@Param("HID") String HID);
}
