package com.app.loan.idGenerator;

import java.io.Serializable;

import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;


/**
 * Custom ID generator for Hibernate that prefixes generated sequence IDs with a custom string.
 * This class extends SequenceStyleGenerator to modify the generated ID format.
 */
public class StringPrefixedSequenceIdGenerator extends SequenceStyleGenerator{
	
	
	/**
     * Parameter name for the prefix to be added to the generated ID.
     */
	public static final String VALUE_PREFIX_PARAMETER = "valuePrefix";
	
	 /**
     * Default value for the prefix if not specified.
     */
	public static final String VALUE_PREFIX_DEFAULT = "";
	private String valuePrefix;
	
	
	/**
     * Parameter name for the number format of the generated ID.
     */
	public static final String NUMBER_FORMAT_PARAMETER = "numberFormat";
	
	/**
     * Default number format if not specified.
     */
	public static final String NUMBER_FORMAT_DEFAULT = "%d";
	private String numberFormat;
	
	
	/**
     * Generates a new ID with a prefix and formatted sequence number.
     * 
     * @param session The Hibernate session used for generating the ID.
     * @param object The entity for which the ID is generated.
     * @return The generated ID, including the prefix and formatted sequence number.
     * @throws HibernateException If there is an issue with generating the ID.
     */
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException{
		return valuePrefix + String.format(numberFormat, super.generate(session, object));
	}
	
	
    /**
     * Configures the ID generator with properties and service registry.
     * 
     * @param type The type of ID to be generated (should be LongType).
     * @param params Configuration parameters for the generator.
     * @param serviceRegistry The service registry for Hibernate services.
     * @throws MappingException If there is an issue with the configuration.
     */
	@Override
    public void configure(Type type, Properties params,
            ServiceRegistry serviceRegistry) throws MappingException {
        super.configure(LongType.INSTANCE, params, serviceRegistry);
        valuePrefix = ConfigurationHelper.getString(VALUE_PREFIX_PARAMETER,
                params, VALUE_PREFIX_DEFAULT);
        numberFormat = ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER,
                params, NUMBER_FORMAT_DEFAULT);
    }
}
