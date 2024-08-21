package com.app.loan.entities;

import com.app.loan.idGenerator.StringPrefixedSequenceIdGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * Represents a collateral entity associated with a loan request.
 * Each collateral has a unique identifier and details about the asset,
 * its value, and a description.
 */

@Entity
@Table(name = "collateral")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Collateral extends BaseEntity{
	
	/**
     * Unique identifier for the collateral, generated using a custom sequence.
     */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "collateral_seq")
    @GenericGenerator(
        name = "collateral_seq", 
        strategy = "com.app.loan.idGenerator.StringPrefixedSequenceIdGenerator", 
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "CO_"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%01d")
        }
    )
	@Column(name="collateral_no")
	private String collateralNo;
	
	
	/**
     * Description of the asset being used as collateral.
     */
	@Column(name="asset")
	private String asset;
	
	
	/**
     * Estimated value of the collateral asset.
     */
	@Column(name="value")
	private String value;
	
	
	/**
     * Additional information or notes about the collateral.
     */
	@Column(name="description")
	private String description;
	
	
	/**
     * Reference to the associated loan request.
     * Each collateral is linked to a specific loan request.
     */
	@ManyToOne
	@JoinColumn(name="request_id", nullable = false)
	private Request request;
	
//	here when admin will send email, he will add corresponding request id by default in response. 
}
