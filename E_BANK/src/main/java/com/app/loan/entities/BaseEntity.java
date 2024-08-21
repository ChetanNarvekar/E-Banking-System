package com.app.loan.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * BaseEntity is a mapped superclass that provides common auditing fields
 * for entities that inherit from it. It includes time-stamps for creation
 * and last update.
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BaseEntity {
	
	/**
     * The date when the entity was created.
     * This field is automatically populated with the current date when the entity is persisted.
     */
	@Column(name="created_on")
	@CreationTimestamp
	private LocalDate createdOn;
	
	/**
     * The date and time when the entity was last updated.
     * This field is automatically updated with the current date and time whenever the entity is updated.
     */
	@Column(name="updated_on")
	@UpdateTimestamp
	private LocalDateTime updatedOn; 

}
