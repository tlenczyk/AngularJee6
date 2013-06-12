/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tls.service.util;

import java.util.logging.Logger;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tomasz.Lenczyk
 */
public class Producer {

	@Produces
	@PersistenceContext(unitName = "carsPU")
	public EntityManager em;

	@Produces
	public Logger produceLog(InjectionPoint injectionPoint) {
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}
}
