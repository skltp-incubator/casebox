/*
 * Copyright 2010 Inera
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *
 *   Boston, MA 02111-1307  USA
 */
package se.inera.ifv.casebox.core.repository.jpa;

import java.util.Date;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import se.inera.ifv.casebox.core.entity.MessageType;
import se.inera.ifv.casebox.core.entity.Statistic;
import se.inera.ifv.casebox.core.repository.StatisticRepository;
import se.vgregion.dao.domain.patterns.repository.db.jpa.DefaultJpaRepository;

@Repository
public class JpaStatisticRepository extends DefaultJpaRepository<Statistic> implements StatisticRepository {

    public Statistic find(MessageType messageType, String careUnit, Date created) {
        try {
            return (Statistic) entityManager.createNamedQuery("Statistic.find")
                    .setParameter("messageType", messageType).setParameter("careUnit", careUnit)
                    .setParameter("created", created).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
