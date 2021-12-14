package com.ecomshop.deskplus.repositories;

import com.ecomshop.deskplus.models.CustomersEntity;
import com.ecomshop.deskplus.models.MediumsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Author: Sheik Syed Ali
 * Date: 11 Nov 2021
 */
public interface CustomersRepository extends JpaRepository<CustomersEntity, Long> {

    @Query("SELECT m FROM CustomersEntity m WHERE m.customer_email = :email")
    CustomersEntity findCustomerByEmail(@Param("email") String email);
}
