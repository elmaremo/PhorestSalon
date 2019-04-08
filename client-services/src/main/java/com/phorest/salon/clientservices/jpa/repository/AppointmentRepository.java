/**
 * 
 */
package com.phorest.salon.clientservices.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phorest.salon.clientservices.jpa.client.Appointments;

/**
 * @author elma
 *
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointments, String> {

}
